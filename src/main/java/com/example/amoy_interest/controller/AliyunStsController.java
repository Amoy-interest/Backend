package com.example.amoy_interest.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.example.amoy_interest.dto.OssTokenDTO;
import com.example.amoy_interest.msgutils.Msg;
import com.example.amoy_interest.msgutils.MsgUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.example.amoy_interest.constant.StsSecurityConstants.*;

@Slf4j
@RestController
@Api(tags = "阿里云STS模块")
@RequestMapping("/aliyun/sts")
public class AliyunStsController {

    @GetMapping("/oss/tokens")
    public Msg<OssTokenDTO> getOssToken() {
        // 创建阿里云 OSS 临时token
        AssumeRoleResponse ossToken = createOssToken();
        if (Objects.isNull(ossToken)) {
            return MsgUtil.error("获取 OSS 签名授权失败！");
        }
        // 阿里 OSS 配置
        AssumeRoleResponse.Credentials credentials = ossToken.getCredentials();
        // 构建 OSS token 返回给前端
        OssTokenDTO ossTokenVO = OssTokenDTO.builder()
                // region每个地方不一样，下面是深圳的：shenzhen
                .region("oss-cn-shenzhen")
                .accessKeyId(credentials.getAccessKeyId())
                .accessKeySecret(credentials.getAccessKeySecret())
                .securityToken(credentials.getSecurityToken())
                .expiration(credentials.getExpiration())
                // OSS bucket名称
                .bucket("leigq-bucket")
                .build();
        return MsgUtil.ok(ossTokenVO);
    }

    private AssumeRoleResponse createOssToken() {
        /*
         * 子账号的 accessKeyId、accessKeySecret、roleArn，注意：子账号需赋予 AliyunSTSAssumeRoleAccess(调用STS服务AssumeRole接口的权限)权限
         * */
        String accessKeyId = ACCESS_KEY_ID;
        String accessKeySecret = ACCESS_KEY_SECRET;
        String roleArn = ROLE_ARN;

        //roleSessionName时临时Token的会话名称，自己指定用于标识你的用户，或者用于区分Token颁发给谁
        //要注意roleSessionName的长度和规则，不要有空格，只能有'-'和'_'字母和数字等字符
        String roleSessionName = ROLE_SESSION_NAME;
        try {
            // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
            DefaultProfile.addEndpoint("", "", "Sts", "sts.aliyuncs.com");
            IClientProfile profile = DefaultProfile.getProfile("", accessKeyId, accessKeySecret);
            DefaultAcsClient client = new DefaultAcsClient(profile);
            // 创建一个 AssumeRoleRequest 并设置请求参数
            final AssumeRoleRequest request = new AssumeRoleRequest();
            //POST请求
            request.setMethod(MethodType.POST);
            //https协议
            request.setProtocol(ProtocolType.HTTPS);
            //持续时间, 只能设置 15min - 1hr 之间
            request.setDurationSeconds(900L);
            //角色id
            request.setRoleArn(roleArn);
            //应用程序标识(自己定义)
            request.setRoleSessionName(roleSessionName);
            // 发起请求，并得到response
            AssumeRoleResponse acsResponse = client.getAcsResponse(request);
            return acsResponse;
        } catch (ClientException e) {
            log.error("创建阿里云 OSS 临时token异常", e);
        }
        return null;
    }
}
