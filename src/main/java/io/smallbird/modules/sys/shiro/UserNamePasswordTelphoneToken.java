package io.smallbird.modules.sys.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

import java.io.Serializable;
 
public class UserNamePasswordTelphoneToken extends UsernamePasswordToken implements Serializable {
 
	private static final long serialVersionUID = 4812793519945855483L;
 
	// 手机号码
	private String telphoneNum;
 
	/**
	 * 重写getPrincipal方法
	 */
	public Object getPrincipal() {
		// 如果获取到用户名，则返回用户名，否则返回电话号码
		if (telphoneNum == null) {
			return getUsername();
		} else {
			return getTelphoneNum();
		}
	}
 
	/**
	 * 重写getCredentials方法
	 */
	public Object getCredentials() {
		// 如果获取到密码，则返回密码，否则返回null
		if (telphoneNum == null) {
			return getPassword();
		} else {
			return "123456";
		}
	}
 
	public UserNamePasswordTelphoneToken() {
	}
 
	public UserNamePasswordTelphoneToken(final String telphoneNum) {
		this.telphoneNum = telphoneNum;
	}
 
	public UserNamePasswordTelphoneToken(final String userName, final String password) {
		super(userName, password);
	}
 
	public String getTelphoneNum() {
		return telphoneNum;
	}
 
	public void setTelphoneNum(String telphoneNum) {
		this.telphoneNum = telphoneNum;
	}
 
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
 
	@Override
	public String toString() {
		return "TelphoneToken [telephoneNum=" + telphoneNum + "]";
	}
 
}