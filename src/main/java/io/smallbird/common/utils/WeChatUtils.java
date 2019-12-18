package io.smallbird.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public class WeChatUtils {


    //公众号唯一标识
    public static final String APPID = "wx65ce5bc986b4ec00";
    //公众号的appsecret
    public static final String APPSECRET = "d8fcdae75adb82cc7d1a3cd01378a913";

    //获取接口凭据accessToken
    public static final String GET_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    //获取网页授权accessToken和openId的接口
    public static final String GET_ACCESSTOKEN_OPENID_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    //获取用户信息的接口
    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    //获取永久二维码ticket的接口
    public static final String GET_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create";

    //获取二维码图片的接口
    public static final String GET_IMAGE_URL = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";

    /**
     * 获取网页授权的AccessToken凭据和openId
     *
     * @return
     */
    public static JSONObject getAccessTokenAndOpenId(String code) {
        String result = HttpToolkit.doGet(GET_ACCESSTOKEN_OPENID_URL.replace("APPID", APPID).replace("SECRET", APPSECRET).replace("CODE", code), null);
        JSONObject json = JSONObject.parseObject(result);
        return json;
    }

    /**
     * 获取用户信息
     */
    public static JSONObject getUserInfo(String accessToken, String openId) {
        String result = HttpToolkit.doGet(GET_USERINFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId), null);
        JSONObject json = JSONObject.parseObject(result);
        return json;
    }

    /**
     * 获取微信二维码
     */
    public static String getImage(String accessToken) {
        TreeMap<String, String> params = new TreeMap<String, String>();
        params.put("access_token", accessToken);
        Map<String, Integer> intMap = new HashMap<String, Integer>();
        intMap.put("scene_id", 123);
        Map<String, Map<String, Integer>> mapMap = new HashMap<String, Map<String, Integer>>();
        mapMap.put("scene", intMap);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("action_name", "QR_LIMIT_SCENE");
        paramsMap.put("action_info", mapMap);
        String data = new Gson().toJson(paramsMap);
        String result1 = HttpToolkit.HttpsDefaultExecute(HttpToolkit.POST_METHOD, GET_TICKET_URL, params, data);
        JSONObject json1 = JSONObject.parseObject(result1);
        return GET_IMAGE_URL.replace("TICKET", json1.getString("ticket"));
    }
}
