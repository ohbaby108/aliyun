package com.xinrui.service;

import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public interface SendSms {
    boolean send(String phoneNum, String templateCode, Map<String,Object> code);
}
