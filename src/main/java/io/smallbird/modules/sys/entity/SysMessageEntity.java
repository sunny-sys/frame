package io.smallbird.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author xml
 * @email xxx
 * @date 2019-04-11 15:34:20
 */
@Data
@TableName("sys_message")
public class SysMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *  
	 */
	@TableId
	private Long id;
	/**
	 * 消息内容
	 */
	private String msgCotent;
	/**
	 * 消息类型: 0(验证码) 1(短信)
	 */
	private Integer msgType;
	/**
	 * 手机号码
	 */
	private String telephoneNum;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 失效时间
	 */
	private Date exprTime;
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;
	/**
	 * 用户id号(备用回填字段)
	 */
	private Long userId;

}
