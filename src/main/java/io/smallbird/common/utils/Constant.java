package io.smallbird.common.utils;

import lombok.Getter;

/**
 * 常量
 *
 *
 */
public class Constant {

	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
    /** 数据权限过滤 */
	public static final String SQL_FILTER = "sql_filter";
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";

	/**
	 * 菜单类型
	 */
    public enum MenuType {
        /**
         * 目录
         */
    	CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    
    /**
     * 定时任务状态
     */
    public enum ScheduleStatus {
        /**
         * 正常
         */
    	NORMAL(0),
        /**
         * 暂停
         */
    	PAUSE(1);

        private int value;

        ScheduleStatus(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }

    /**设置验证码的有效时间(单位分钟)**/
    public static final String VERIFICATION_CODE_TIME = "verification_code_time";

    /**是否上架**/
    /**是否删除**/
    @Getter
    public enum del_flag {
        未删除(0), 已删除(1);
        private int val;

        del_flag(int val) {
            this.val = val;
        }
    }

    @Getter
    public enum Status{
        OK("ok","0"),FAIL("fail","1");
        private String msg;
        private String code;
        Status(String msg,String code){
            this.msg = msg;
            this.code = code;
        }
    }
    /**缓存存放数据的name**/
    public enum EhcacheDataName{
        DEFAULT,
        COMMON,//其他一些公共的存放在这个缓存中
        DICITEM//字典明细存放在这个名字下额=的缓存中
    }
}
