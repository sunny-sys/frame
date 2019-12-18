package io.smallbird.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.smallbird.common.annotation.SysLog;
import io.smallbird.common.model.BaseResp;
import io.smallbird.common.model.DetailResp;
import io.smallbird.common.utils.*;
import io.smallbird.common.validator.ValidatorUtils;
import io.smallbird.common.validator.group.AddGroup;
import io.smallbird.modules.sys.dto.UpdatePhoneDto;
import io.smallbird.modules.sys.entity.SysConfigEntity;
import io.smallbird.modules.sys.entity.SysMessageEntity;
import io.smallbird.modules.sys.entity.SysUserEntity;
import io.smallbird.modules.sys.service.SysConfigService;
import io.smallbird.modules.sys.service.SysMessageService;
import io.smallbird.modules.sys.service.SysUserRoleService;
import io.smallbird.modules.sys.service.SysUserService;
import io.smallbird.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 系统用户
 */
@RestController
@RequestMapping("/sys/user")
@Api(value = "用户-接口", tags = "用户信息管理")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysMessageService sysMessageService;
    @Autowired
    private SysConfigService configService;


    @PostMapping("/updatePhoneNum")
    @ApiOperation("修改手机号")
    public BaseResp updatePhoneNum(@Validated @RequestBody UpdatePhoneDto dto, BindingResult result) {
        if (result.hasErrors()) return BaseResp.error(result.getAllErrors().get(0).getDefaultMessage());
        SysUserEntity userEntity = sysUserService.getById(dto.getUserId());
        if (Objects.isNull(userEntity)) return BaseResp.error("该用户的数据不存在");
        userEntity.setTelephoneNum(dto.getTelephoneNum().trim());
        sysUserService.updateById(userEntity);
        return BaseResp.success();
    }

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserService.queryPage(params);

        return Result.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public Result info() {
        return Result.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public Result password(String password, String newPassword) throws Exception{
        if (Util.isEmpty(newPassword)) throw new Exception("新密码不为能空");

        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return Result.error("原密码不正确");
        }

        return Result.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public Result info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return Result.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @RequiresPermissions("sys:user:save")
    public Result save(@RequestBody SysUserEntity user) {
        if (StringUtils.isNotBlank(user.getPassword())) {
            if (!user.getPassword().equals(user.getConfirmPwd())) {
                return Result.error("两次输入密码不一致,用户数据保存失败");
            }
        }
        if (StringUtils.isEmpty(user.getUsername()) && StringUtils.isEmpty(user.getTelephoneNum())) {
            return Result.error("缺少必要参数,用户数据保存失败");
        }
        if (StringUtils.isNotEmpty(user.getTelephoneNum())) {
            user.setUsername(user.getTelephoneNum());///!!! 支持通过 手机号码(当做用户名)+密码登录，走UserNamePwdRealm校验
            user.setLoginType("telephoneNum");
        } else {
            user.setLoginType("userName");
        }
        sysUserService.saveUser(user);

        return Result.ok();
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ApiOperation(value = "普通/vip用户注册")
    public DetailResp regist(@ApiParam("用户对象") @RequestBody SysUserEntity user) {
        logger.info("进入注册接口");
        user.setLoginType("telephoneNum");//
        ValidatorUtils.validateEntity(user, AddGroup.class);

        if (!AccountValidatorUtil.isMobile(user.getTelephoneNum())) {
            return DetailResp.error("手机号码不正确，请重新输入合法手机号！");
        }

        //查询用户信息
        SysUserEntity one = sysUserService.getOne(new QueryWrapper<SysUserEntity>().lambda().eq(SysUserEntity::getTelephoneNum, user.getTelephoneNum()));
        //账号不存在
        if (one != null) {
            return DetailResp.error("手机号码已被注册，请更换手机号或直接登录！");
        }

        if (StringUtils.isEmpty(user.getVerificationCode())) {
            return DetailResp.error("验证码为空");
        }

        SysMessageEntity messageEntity = sysMessageService.getOne(
                new QueryWrapper<SysMessageEntity>()
                        .eq("telephone_num", user.getTelephoneNum())
                        .eq("msg_type", 0)
                        .orderByDesc("create_time"));
        if (messageEntity != null) {
            SysConfigEntity config = configService.getOne(new LambdaQueryWrapper<SysConfigEntity>().eq(SysConfigEntity::getParamKey, Constant.VERIFICATION_CODE_TIME));
            if (Util.isEmpty(config) || Util.isEmpty(config.getParamValue())) {
                return DetailResp.error("验证码有效期不能为空");
            }
            if (!Util.isNumber(config.getParamValue())) {
                return DetailResp.error("验证码有效期值不正确");
            }
            long timeInMillis = Calendar.getInstance().getTimeInMillis();
            long time = messageEntity.getCreateTime().getTime();
            Date exprTime = messageEntity.getExprTime();
            if ((timeInMillis < (time + Integer.parseInt(config.getParamValue()) * 60 * 1000L))
                    || (exprTime != null && timeInMillis < exprTime.getTime())) {
                if (!user.getVerificationCode().equalsIgnoreCase(messageEntity.getMsgCotent())) {
                    return DetailResp.error("验证码错误，请重新输入！");
                }
            } else {
                return DetailResp.error("验证码失效，请重新获取验证码！");
            }
        } else {
            return DetailResp.error("验证码无效，请重新获取验证码！");
        }

        if (user.getUserType().equals("vip")) {
            if (StringUtils.isEmpty(user.getCompanyName())) {
                return DetailResp.error("公司名称不能为空");
            }
            if (StringUtils.isEmpty(user.getBusinessLicensePic())) {
                return DetailResp.error("营业执照图片不能为空");
            }
            if (StringUtils.isEmpty(user.getIdentityCardsFrontPic())) {
                return DetailResp.error("身份证正面照不能为空");
            }
            if (StringUtils.isEmpty(user.getIdentityCardsBackPic())) {
                return DetailResp.error("身份证反面照不能为空");
            }
            if (StringUtils.isEmpty(user.getLegalFrontPic())) {
                return DetailResp.error("法人身份证正面照不能为空");
            }
            if (StringUtils.isEmpty(user.getLegalBackPic())) {
                return DetailResp.error("法人身份证反面照不能为空");
            }
        }

        SysUserEntity sysUserEntity = sysUserService.getOne(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getOpenid, user.getOpenid()));
        if (sysUserEntity == null) {
            return DetailResp.error("数据有误，请联系管理员或重新进入个人中心");
        }
        user.setStatus(1);
        user.setUsername(user.getTelephoneNum());
        user.setUserId(sysUserEntity.getUserId());
        sysUserService.saveUser(user);
        return DetailResp.success(user);
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public Result update(@RequestBody SysUserEntity user) {
        //ValidatorUtils.validateEntity(user, UpdateGroup.class);

        sysUserService.update(user);

        return Result.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public Result delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return Result.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return Result.error("当前用户不能删除");
        }

        sysUserService.removeByIds(Arrays.asList(userIds));

        return Result.ok();
    }
}
