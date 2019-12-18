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
 * @date 2019-04-19 16:45:03
 */
@Data
@TableName("sys_file")
public class SysFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long fileId;
	/**
	 * 文件大小,字节数
	 */
	private Long fileSize;
	/**
	 * 文件原始名称
	 */
	private String originalName;
	/**
	 * 相对路径
	 */
	private String relativePath;
	/**
	 * 新文件名
	 */
	private String newFileName;
	/**
	 * 文件类型
	 */
	private String contentType;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 上传人
	 */
	private Long uploadUserId;
	/**
	 * 外键
	 **/
	private String foreignKey;

	/**
	 * 图片需要展示到什么位置的类型：0：列表，1：详情，2：其他(默认)
	 */
	private Integer viewType;

	/**
	 表示该图片是否是商品的图片，0(默认)：表示不是，1：表示是
	 **/
	private Integer isGoodType;

}
