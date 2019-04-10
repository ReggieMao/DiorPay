package com.gene.library.constant;

/**
 * Created by MaoLJ on 2018/7/18.
 * 错误编码
 */

public class DPCode {

    //未知错误
    public static final int UNKNOWN = 1000;

    //解析错误
    public static final int PARSE_ERROR = 1001;

    //网络错误
    public static final int NETWORD_ERROR = 1002;

    //协议出错
    public static final int HTTP_ERROR = 1003;

    //证书出错
    public static final int SSL_ERROR = 1005;

    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;

    //50001 空数据返回，如果需要特别声明空数据时使用
    public static final int EMPTY_DATA = 50001;

    //50003 业务异常
    public static final int BUSSINESS_EXCEPTION = 50003;

    //20004 会话令牌超时失效
    public static final int OUTDATE_OF_SESSION = 20004;

    //20005 会话令牌因用户抢登失效
    public static final int FOURCE_OUT_OF_SESSION = 20005;

    //20006 会话验证错误，可能原因： 1） 会话令牌非法，令牌不存在或者不在正确的状态 2） 方法调用提供的调用安全码校验不正确
    public static final int SECURITY_ERROR = 20006;

    //20007 无权限进行某操作，或无权限访问该资源
    public static final int AUTHORIZATION_FAILURE = 20007;

    //20000 服务器内部错误
    public static final int SERVER_INTERNAL_ERROR = -20000;

}
