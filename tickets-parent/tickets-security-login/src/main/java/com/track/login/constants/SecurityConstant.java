package com.track.login.constants;

/**
 * @Author huangwancheng
 * @create 2021/9/3 10:13
 *
 * 权限认证常量
 *
 */
public interface SecurityConstant {

    /**
     * token分割
     */
    String TOKEN_SPLIT = "Bearer ";

    /**
     * JWT签名加密key
     */
    String JWT_SIGN_KEY = "123123";

    /**
     * token参数头
     */
    String HEADER = "accessToken";

    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";

    /**
     * 用户选择JWT保存时间参数头
     */
    String SAVE_LOGIN = "saveLogin";

    /**
     * 交互token前缀key
     */
    String TOKEN_PRE = "DISTRIBUTION_TOKEN_PRE:";

    /**
     * 用户token前缀key 单点登录使用
     */
    String USER_TOKEN = "DISTRIBUTION_USER_TOKEN:";

    /**
     * 缓存用户页面菜单数据
     */
    String USER_MENU_LIST = "permission::userMenuList:";

    /**
     * 用户默认头像
     */
    String USER_DEFAULT_PASSWORD = "123456";

    /**
     * 用户默认头像
     */
    String USER_DEFAULT_AVATAR = "http://bo2h.com/20190716/png/c1d1afe8c96248a48542a17cc53a6de8.png";

    /**
     * 用户默认昵称
     */
    String USER_DEFAULT_NICKNAME = "昵称";

    /**
     * 用户默认性别--0 未知
     */
    Integer USER_DEFAULT_SEX = 0;

    /**
     * 用户正常状态
     */
//    Integer USER_STATUS_NORMAL = 0;

    /**
     * 用户默认登录名字段
     */
    String LOGIN_NAME_PARAM = "username";

    /**
     * 用户默认登录密码字段
     */
    String LOGIN_PASSWOED_PARAM = "password";

    /**
     * 用户邮件登录字段
     */
    String EMAIL = "email";

    /**
     * 登录额外字段--手机号
     */
    String PHONE = "phone";

    /**
     * 登录额外字段--微信传的code
     */
    String CODE = "code";

    /**
     * 登录额外字段--通过第三方登录获得的用户唯一idf
     */
    String UNION_ID = "unionId";

    /**
     * 登录额外字段--微信小程序openId
     */
    String OPEN_ID = "openId";

    /**
     * 登录额外字段--验证码
     */
    String VERIFY_CODE = "verifyCode";

    /**
     * 登录额外字段--登录方式
     */
    String LOGIN_TYPE = "loginType";

    /**
     * 用户禁用状态
     */
//    Integer USER_STATUS_LOCK = -1;

    /**
     * 普通用户
     */
    Integer USER_TYPE_NORMAL = 0;

    /**
     * 管理员
     */
    Integer USER_TYPE_ADMIN = 1;

    /**
     * 平台后台用户
     */
    Integer SYS_TYPE_MANAGER = 1;

    /**
     * 商家端用户
     */
    Integer SYS_TYPE_SUPPLIER = 2;

    /**
     * 全部数据权限
     */
    Integer DATA_TYPE_ALL = 0;

    /**
     * 自定义数据权限
     */
    Integer DATA_TYPE_CUSTOM = 1;

    /**
     * 正常状态
     */
//    Integer STATUS_NORMAL = 0;

    /**
     * 禁用状态
     */
//    Integer STATUS_DISABLE = -1;

    /**
     * 删除标志
     */
    Integer DEL_FLAG = 1;

    /**
     * 顶部菜单类型权限
     */
    Integer PERMISSION_NAV = -1;

    /**
     * 页面类型权限
     */
    Integer PERMISSION_PAGE = 0;

    /**
     * 操作类型权限
     */
    Integer PERMISSION_OPERATION = 1;

    /**
     * 1级菜单父id
     */
    String PARENT_ID = "0";

    /**
     * 0级菜单
     */
    Integer LEVEL_ZERO = 0;

    /**
     * 1级菜单
     */
    Integer LEVEL_ONE = 1;

    /**
     * 2级菜单
     */
    Integer LEVEL_TWO = 2;

    /**
     * 3级菜单
     */
    Integer LEVEL_THREE = 3;

    /**
     * 部门负责人类型 主负责人
     */
    Integer HEADER_TYPE_MAIN = 0;

    /**
     * 部门负责人类型 副负责人
     */
    Integer HEADER_TYPE_VICE = 1;
}