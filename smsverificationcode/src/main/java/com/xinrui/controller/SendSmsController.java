package com.xinrui.controller;


import com.aliyuncs.utils.StringUtils;
import com.xinrui.service.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin    //跨域的支持
public class SendSmsController {

    @Autowired
    private SendSms sendSms;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/send/{phone}")
    public String code(@PathVariable("phone") String phone){
        //调用发送方法
        String code=redisTemplate.opsForValue().get(phone);//通过redisTemplate去看redis里面是否存在手机号码
        if (!StringUtils.isEmpty(code)){//如果手机号码已经存在 验证码还没有过期
            return phone+":"+code+"已经存在，还没有过期";
        }

        //生成验证码并存储到redis中
        code= UUID.randomUUID().toString().substring(0,4);
        HashMap<String, Object> param = new HashMap<>();
        param.put("code",code);
        //是否发送成功
        boolean isSend = sendSms.send(phone, "SMS_211275872", param);
        //判断是否发送成功
        if (isSend){//发送成功 存储到redis 并设置5分钟过期时间
            redisTemplate.opsForValue().set(phone,code,5,TimeUnit.SECONDS);
            return phone+":"+code+"发送成功！";
        }

        return "发送失败";




    }



}
