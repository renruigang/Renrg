package cn.renrg.comm;

import java.util.HashMap;

public class Constants {

    public static final String MY_APP = "RenRG";  //应用信息存储
    //配置数据
    public static final int LIMIT_LINES = 20;
    public static final String VERSIONCODE = "versioncode";  //引导页
    public static final String IGNORE_VERSION = "ignore_version";  //忽略版本更新
    //rrg
    public static final HashMap<String, String> ORDER_STATUS = new HashMap<String, String>() {
        {//定单状态:0创建；1确认；2；支付；3已到达；4约会完成；5钱已给对方待评价;6、7、8、9：取消
            put("0", "创建");
            put("1", "确认");
            put("2", "支付成功");
            put("3", "已到达");
            put("4", "约会完成");
            put("5", "待评价");
            put("6", "取消订单");
            put("7", "取消");
            put("8", "取消");
            put("9", "取消");
        }
    };
    public static final String BASE_LOGIN_STATUS = "loginStatus";//登录状态
    public static final String USER_INFO = "userInfo";//用户登录成功返回的信息
    public static final String USER_VERIFY = "isverify";//用户实名验证的状态
}
