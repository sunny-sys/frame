package io.smallbird.modules.wx.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.smallbird.common.model.DetailResp;
import io.smallbird.common.utils.CodeConstant;
import io.smallbird.common.utils.Util;
import io.smallbird.common.utils.WeChatUtils;
import io.smallbird.modules.sys.dto.OpenIdDto;
import io.smallbird.modules.sys.entity.SysUserEntity;
import io.smallbird.modules.sys.service.SysUserService;
import io.smallbird.modules.sys.shiro.UserNamePasswordTelphoneToken;
import io.smallbird.modules.sys.vo.PermListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @Description:    微信授权及用户信息处理
* @Author:         chenshao
* @CreateDate:     2019/7/05 19 14:25
*/
@Slf4j
@RestController
@RequestMapping(value = "/wx/auth")
@Api(tags = "微信授权及用户信息处理", description = "微信授权及用户信息处理")
public class WxAuthInfoController {

    @Autowired
    private SysUserService sysUserService;

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @PostMapping("/getOpenIdAndInDatabase/{code}")
    @ApiOperation("获取微信用户信息(含用户头像，二维码等)并将openid传给前端，用于注册、验证是否授权等等等")
    public DetailResp getOpenIdAndInDatabase(@PathVariable(value = "code")String code) {
        if (StringUtils.isEmpty(code)) {
            return DetailResp.error("数据异常");
        }
        logger.info("授权code ========="+code);
        try {
            JSONObject json1 = WeChatUtils.getAccessTokenAndOpenId(code);
            String openId = json1.getString("openid");
            logger.info("授权openId ========="+openId);
            String accessToken = json1.getString("access_token");

            if(Util.isEmpty(openId)){
                return DetailResp.error("code过期或者已使用，请重新授权！");
            }
            //判断用户是否存在
            SysUserEntity userEntity = sysUserService.getOne(new LambdaQueryWrapper<SysUserEntity>()
                    .eq(SysUserEntity::getOpenid, openId)
            );
            if(userEntity==null){
                JSONObject jsonUser = WeChatUtils.getUserInfo(accessToken,openId);
                SysUserEntity user = new SysUserEntity();
                user.setOpenid(jsonUser.getString("openid"));
                user.setCity(jsonUser.getString("city"));
                user.setSex(Integer.parseInt(jsonUser.getString("sex")));
                user.setHeadimgurl(jsonUser.getString("headimgurl"));
//                user.setSubscribeTime(DateUtils.stringToDate(jsonUser.getString("subscribe_time"),DateUtils.DATE_TIME_PATTERN));
//                String token = WeChatUtils.getAccessToken();
//                System.out.println(token);
//                String stringImage = WeChatUtils.getImage(token);
//                user.setTwoDimensionCode(stringImage);
                sysUserService.saveUser(user);//入库--若未注册则收集信息，在user表中生成一条记录
            }
            Map<String,String> map = new HashMap();
            map.put("openid",openId);
            return DetailResp.success(map);
        } catch (Exception e) {
            logger.error("授权失败！", e);
            return DetailResp.error("授权失败！");
        }
    }
    @PostMapping("/authorise")
    @ApiOperation("通过openid获取用户信息（登录绑定openid后的信息）")
    public DetailResp authorise(@RequestBody OpenIdDto dto, BindingResult result) {
        if (result.getAllErrors().size() > 0) {
            return DetailResp.error(CodeConstant.LOSE_PARAM_ERROR, result.getAllErrors().get(0).getDefaultMessage());
        }
        //判断用户是否存在
        SysUserEntity userEntity = sysUserService.getOne(new LambdaQueryWrapper<SysUserEntity>().
                eq(SysUserEntity::getOpenid, dto.getOpenid())
        );
        List<PermListVo> permsList = sysUserService.queryAllPermList(userEntity.getUserId());
        userEntity.setPermsList(permsList);
        if(userEntity.getLoginStatus()==1){
            Subject subject = SecurityUtils.getSubject();
            UserNamePasswordTelphoneToken token = new UserNamePasswordTelphoneToken(userEntity.getTelephoneNum());
            subject.login(token);
        }
        return DetailResp.success(userEntity);
    }

}
