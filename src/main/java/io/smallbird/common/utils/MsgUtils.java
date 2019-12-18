package io.smallbird.common.utils;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MsgUtils {

    @Value("${ali.sms.access.key.id}")
    private String AccessKeyID;
    @Value("${ali.sms.access.key.secret}")
    private String AccessKeySecret;
    @Value("${ali.sms.templateCode}")
    private String TemplateCode;

    private final String SignName = "怡富公众号";

    public boolean sendMsg(String receivePhoneNumber, String codeValue) {
        DefaultProfile profile = DefaultProfile.getProfile("default", AccessKeyID, AccessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", receivePhoneNumber);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + codeValue + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String result = JSON.parseObject(response.getData()).getString("Code");
            if (result.equalsIgnoreCase("ok")) {
//                cacheSmsCode(getSmsKey(receivePhoneNumber, CommonEnum.CodeEnum.COMMON_CODE.getValue()), codeValue);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean sendSms(String receivePhoneNumber, String codeValue) {
        DefaultProfile profile = DefaultProfile.getProfile("default", AccessKeyID, AccessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", receivePhoneNumber);
        request.putQueryParameter("SignName", SignName);
        request.putQueryParameter("TemplateCode", TemplateCode);
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + codeValue + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String result = JSON.parseObject(response.getData()).getString("Code");
            if (result.equalsIgnoreCase("ok")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getSmsKey(String mobile, String type) {
        StringBuilder bud = new StringBuilder(mobile).append("_").append(type);
        return bud.toString();
    }

    public void main(String[] args) {
        boolean flag = sendMsg("xxxx", "333");
        System.out.println(flag);
    }
}
