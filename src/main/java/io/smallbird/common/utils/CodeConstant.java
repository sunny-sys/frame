package io.smallbird.common.utils;

/**
 * @Des: 返回给前台的错误码对照实体类 <br>
 * @Author: xupj <br>
 * @Date: 2019/5/24 14:34 <br>
 **/
public class CodeConstant {

    //操作结果，0:成功，1:失败
    public static final int OK = 0;
    /**业务错误*/
    public static final int FAIL = 1;
    /**缺失参数*/
    public static final int LOSE_PARAM_ERROR = 2;
    /**数据库主键冲突*/
    public static final int DUPLICATE_KEY_ERROR = 3;
    /**没有访问权限*/
    public static final int NO_AUTHORIZATION_ERROR = 4;

}
