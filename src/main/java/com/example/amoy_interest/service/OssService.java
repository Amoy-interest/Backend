package com.example.amoy_interest.service;

import com.example.amoy_interest.dto.OssCallbackResult;
import com.example.amoy_interest.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

public interface OssService {
    OssPolicyResult policy();
    OssCallbackResult callback(HttpServletRequest request);
}
