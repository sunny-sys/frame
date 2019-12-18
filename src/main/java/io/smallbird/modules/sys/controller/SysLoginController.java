package io.smallbird.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.smallbird.common.model.DetailResp;
import io.smallbird.common.utils.*;
import io.smallbird.modules.sys.dto.PcLoginDto;
import io.smallbird.modules.sys.dto.TelNoLoginDto;
import io.smallbird.modules.sys.entity.SysConfigEntity;
import io.smallbird.modules.sys.entity.SysMessageEntity;
import io.smallbird.modules.sys.entity.SysUserEntity;
import io.smallbird.modules.sys.service.SysConfigService;
import io.smallbird.modules.sys.service.SysMessageService;
import io.smallbird.modules.sys.service.SysUserRoleService;
import io.smallbird.modules.sys.service.SysUserService;
import io.smallbird.modules.sys.shiro.ShiroUtils;
import io.smallbird.modules.sys.shiro.UserNamePasswordTelphoneToken;
import io.smallbird.modules.sys.vo.PermListVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

/**
 * 登录相关
 *
 *
 */
@Api("用户登录")
@Controller
public class SysLoginController extends AbstractController{

	@Autowired
    private SysMessageService sysMessageService;
	@Autowired
	private SysConfigService configService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private MsgUtils msgUtils;


	/**
	 * 短信验证码登录
	 * @return
	 */
	@RequestMapping(value = "/sys/codeLogin", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "短信验证码登录(手机)")
	public DetailResp codeLogin(@Validated @RequestBody TelNoLoginDto dto, BindingResult result) {
		if (result.getAllErrors().size() > 0){
			return DetailResp.error(result.getAllErrors().get(0).getDefaultMessage());
		}
		//校验验证码是否正确
		if (!AccountValidatorUtil.isMobile(dto.getTelephoneNum())) {
			return DetailResp.error("手机号码不正确，请重新输入合法手机号！");
		}
        SysUserEntity sysUserEntity = sysUserService.getOne(new LambdaQueryWrapper<SysUserEntity>().eq(SysUserEntity::getTelephoneNum, dto.getTelephoneNum()));
		if(null == sysUserEntity){
			return DetailResp.error("该用户不存在，请注册后再登录");
		}
		if("vip".equals(sysUserEntity.getUserType()) && sysUserEntity.getAuditStatus()==0){
            return DetailResp.error("该用户未审核，不能登录");
        }
		SysMessageEntity messageEntity = sysMessageService.getOne(
				new QueryWrapper<SysMessageEntity>()
						.eq("telephone_num", dto.getTelephoneNum())
						.eq("msg_type", 0)
						.orderByDesc("create_time"));
		try {
			if (messageEntity != null) {
				SysConfigEntity config = configService.getOne(new LambdaQueryWrapper<SysConfigEntity>().eq(SysConfigEntity::getParamKey, Constant.VERIFICATION_CODE_TIME));
				if (Util.isEmpty(config) || Util.isEmpty(config.getParamValue())){
					return DetailResp.error("验证码有效期不能为空");
				}
				if (!Util.isNumber(config.getParamValue())){
					return DetailResp.error("验证码有效期值不正确");
				}
				long timeInMillis = Calendar.getInstance().getTimeInMillis();
				long time = messageEntity.getCreateTime().getTime();
				Date exprTime = messageEntity.getExprTime();
				if ((timeInMillis < (time + Integer.parseInt(config.getParamValue()) * 60 * 1000l))
						|| (exprTime != null && timeInMillis < exprTime.getTime())) {
					if (!dto.getVerificationCode().equalsIgnoreCase(messageEntity.getMsgCotent())) {
						return DetailResp.error("验证码错误，请重新输入！");
					}
				}else{
					return DetailResp.error("验证码失效，请重新获取验证码！");
				}
			}else{
				return DetailResp.error("验证码无效，请重新获取验证码！");
			}
		} catch (Exception e) {
			logger.error("查询消息实体出错", e);
			return DetailResp.error("短信验证码验证失败，请重试！");
		}

		try{
			Subject subject = SecurityUtils.getSubject();
			UserNamePasswordTelphoneToken token = new UserNamePasswordTelphoneToken(dto.getTelephoneNum());
			subject.login(token);
		}catch (UnknownAccountException e) {
			return DetailResp.error("手机号不存在");
		}catch (IncorrectCredentialsException e) {
			return DetailResp.error("密码不正确");
		}catch (LockedAccountException e) {
			return DetailResp.error("账号已被锁定,请联系管理员");
		}catch (AuthenticationException e) {
			return DetailResp.error("账户验证失败");
		}catch (Exception e){
			return DetailResp.error("服务器出错，请稍后重试");
		}
		SysUserEntity user = new SysUserEntity();
		user.setUserId(sysUserEntity.getUserId());
		user.setLoginStatus(1);
		sysUserService.updateById(user);
		List<PermListVo> permsList = sysUserService.queryAllPermList(sysUserEntity.getUserId());
		List<Long> rolesList = sysUserRoleService.queryRoleIdList(user.getUserId());
		sysUserEntity.setLoginStatus(1);
//		return DetailResp.success(sysUserEntity);
		Map<String, String> map = new HashMap<>();
		try {
			map = BeanUtils.describe(sysUserEntity);
			Map<String, String> claims = new HashMap<>();
			claims.put("phone", dto.getTelephoneNum());
			map.put("jwtToken", JwtHelper.genToken(claims));
			map.put("permsList",permsList.toString());
			map.put("rolesList",rolesList.toString());
		} catch (Exception e) {
			return DetailResp.error("服务器出错，请稍后重试");
		}
		return DetailResp.success(map);
	}

	@RequestMapping(value = "/sys/validateToken", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "验证jwtToken有效性")
	@ApiImplicitParam(name="jwtToken",value = "jwtToken",required = true, dataType = "String")
	public DetailResp verifyToken(String jwtToken) throws IOException {
		if (StringUtils.isEmpty(jwtToken)) {
			return DetailResp.error("jwtToken不能为空！");
		}
		try {
			Map<String, String> map = JwtHelper.verifyToken(jwtToken);
			if (MapUtils.isNotEmpty(map)) {
				List<SysUserEntity> userEntities = sysUserService.list(new QueryWrapper<SysUserEntity>().lambda()
						.eq(SysUserEntity::getTelephoneNum, map.get("phone")));
				if (CollectionUtils.isNotEmpty(userEntities) && userEntities.size() == 1) {
					map.put("msg", "令牌有效，登录状态有效。");
					return DetailResp.success(map);
				}
			}
		} catch (Exception e) {
			return DetailResp.error("服务器出错，请稍后重试。");
		}
		return DetailResp.error("令牌校验失败，请重新登录再试。");
	}


	@RequestMapping(value = "/sys/verificationCode", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "获取短信验证码")
	@ApiImplicitParam(name="telephoneNum",value = "手机号",required = true, dataType = "String")
	public DetailResp verificationCode(String telephoneNum)throws IOException {
        if (!AccountValidatorUtil.isMobile(telephoneNum)) {
            return DetailResp.error("手机号码不正确，请重新输入合法手机号！");
        }
		//1.查询数据次手机号是否5分钟内接受过短信验证码
        //2.若5分钟内未接受短信,则发送短信 并 新增 短信信息实体到数据库；否则 返回json(请求短信接口过于频繁,请等待)
        try {
            SysMessageEntity messageEntity = sysMessageService.getOne(
                    new QueryWrapper<SysMessageEntity>().lambda()
                            .eq(SysMessageEntity::getTelephoneNum, telephoneNum)
							.eq(SysMessageEntity::getMsgType, 0)
                            .orderByDesc(SysMessageEntity::getCreateTime));
			SysConfigEntity config = configService.getOne(new LambdaQueryWrapper<SysConfigEntity>().eq(SysConfigEntity::getParamKey, Constant.VERIFICATION_CODE_TIME));
            if (messageEntity != null) {
				long timeInMillis = Calendar.getInstance().getTimeInMillis();
                long time = messageEntity.getCreateTime().getTime();
				Date exprTime = messageEntity.getExprTime();
				if (Util.isEmpty(config) || Util.isEmpty(config.getParamValue())){
					return DetailResp.error("验证码有效期不能为空");
				}
				if (!Util.isNumber(config.getParamValue())){
					return DetailResp.error("验证码有效期值不正确");
				}
//				if ((timeInMillis < (time + Integer.parseInt(config.getParamValue()) * 60 * 1000l))
//						|| (exprTime != null && timeInMillis < exprTime.getTime())) {
//					return DetailResp.error("短信验证码已发，请勿重复请求。");
//				}
            }
			String msgContent = RandomCharsUtils.getSixNum();
			SysMessageEntity sysMessageEntity = new SysMessageEntity();
			boolean sendFlag = msgUtils.sendMsg(telephoneNum, msgContent);
			if (sendFlag) {
				sysMessageEntity.setCreateTime(new Date());
				sysMessageEntity.setExprTime(new Date(Calendar.getInstance().getTimeInMillis() + Integer.parseInt(config.getParamValue()) * 60 * 1000l));
				sysMessageEntity.setLastUpdateTime(new Date());
				sysMessageEntity.setMsgCotent(msgContent);
				sysMessageEntity.setMsgType(0);//验证码
				sysMessageEntity.setTelephoneNum(telephoneNum);
				sendFlag = sysMessageService.save(sysMessageEntity);
			}
			if (!sendFlag) {
				return DetailResp.error("短信发送失败，请稍后重试。");
			}
        } catch (Exception e) {
            logger.error("查询消息实体出错", e);
            return DetailResp.error("短信发送失败，请稍后重试。");
        }
		return DetailResp.success("短信已发送");
	}

    @ResponseBody
    @PostMapping("/sys/login")
    @ApiOperation(value = "PC端登录")
    public Result login(@RequestBody PcLoginDto dto, BindingResult result) {
        if (result.getAllErrors().size() > 0){
            return Result.error(result.getAllErrors().get(0).getDefaultMessage());
        }
        try{
            Subject subject = ShiroUtils.getSubject();
            UserNamePasswordTelphoneToken token = new UserNamePasswordTelphoneToken(dto.getUsername(), dto.getPassword());
            subject.login(token);
        }catch (UnknownAccountException e) {
            return Result.error("手机号不存在");
        }catch (IncorrectCredentialsException e) {
            return Result.error("密码不正确");
        }catch (LockedAccountException e) {
            return Result.error("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return Result.error("账户验证失败");
        }catch (Exception e){
            return Result.error("服务器出错，请稍后重试");
        }

        return Result.ok();
    }

	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ApiOperation(value = "退出登录)")
	@ApiImplicitParam(name="loginType",value = "登录类型: loginType='telephoneNum' 标识手机', userName'标识PC ",required = true, dataType = "String")
	public String logout(String loginType) {
		SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
		user.setUserId(user.getUserId());
		user.setLoginStatus(0);
		sysUserService.updateById(user);
		ShiroUtils.logout();
		if ("telephoneNum".equalsIgnoreCase(loginType)) {
			return new Gson().toJson(Result.ok("登出成功"));
		}
		return "redirect:login.html";
	}
	
}
