package com.xinrui;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class SmsverificationcodeApplicationTests {

    @Test
    void contextLoads() {

                //连接阿里云
                DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "accessKeyId", "secret");
                IAcsClient client = new DefaultAcsClient(profile);

                //构建请求
                CommonRequest request = new CommonRequest();

                request.setSysMethod(MethodType.POST);
                request.setSysDomain("dysmsapi.aliyuncs.com");
                request.setSysVersion("2017-05-25");
                request.setSysAction("SendSms");

                //自定义的参数
                request.putQueryParameter("PhoneNumbers", "10086");//手机号码
                request.putQueryParameter("SignName", "javastudy");//签名
                request.putQueryParameter("TemplateCode", "SMS_211275872");//模板
                //构建一个短信验证码
                Map<String,Object> map=new HashMap<>();
                map.put("code",1324);
                request.putQueryParameter("TemplateParam", JSONObject.toJSONString(map));


                try {
                    CommonResponse response = client.getCommonResponse(request);
                    System.out.println(response.getData());
                } catch (ServerException e) {
                    e.printStackTrace();
                } catch (ClientException e) {
                    e.printStackTrace();
                }

    }

}
