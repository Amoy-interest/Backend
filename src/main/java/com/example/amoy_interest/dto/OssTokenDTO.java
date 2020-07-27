package com.example.amoy_interest.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OssTokenDTO {
        private String region;

        private String accessKeyId;

        private String accessKeySecret;

        private String securityToken;

        private String bucket;

        private String expiration;
}
