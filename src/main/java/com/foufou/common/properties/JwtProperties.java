package com.foufou.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fou.jwt")
@Data
public class JwtProperties {

    private String stuSecretKey;
    private long stuTtl;
    private String stuTokenName;

    private String teaSecretKey;
    private long teaTtl;
    private String teaTokenName;

}
