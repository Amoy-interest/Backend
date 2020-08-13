package com.example.amoy_interest.constant;

public class StsSecurityConstants {
    /*
     * 子账号的 accessKeyId、accessKeySecret、roleArn，注意：子账号需赋予 AliyunSTSAssumeRoleAccess(调用STS服务AssumeRole接口的权限)权限
     * */
    public static final String ACCESS_KEY_ID = "LTAI4G42sriQefXKxFgfAMYp";
    public static final String ACCESS_KEY_SECRET = "svjN6MTu7J9pMGcXxTmZo6tQ6odCiG";
    public static final String ROLE_ARN = "acs:ram::1790295490126939:role/amoy-interest";

    //roleSessionName是临时Token的会话名称，自己指定用于标识你的用户，或者用于区分Token颁发给谁
    //要注意roleSessionName的长度和规则，不要有空格，只能有'-'和'_'字母和数字等字符
    public static final String ROLE_SESSION_NAME = "session-name";
}
