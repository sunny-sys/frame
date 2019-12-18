package io.smallbird.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户session
 * 
 * @author xml
 * @email xxx
 * @date 2019-04-09 21:13:28
 */
@Data
@TableName("user_session")
public class UserSessionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * session
	 */
	private String sessionId;
	/**
	 * cookie
	 */
	private String cookie;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;
	/**
	 * 状态
	 */
	private Integer status;

}
