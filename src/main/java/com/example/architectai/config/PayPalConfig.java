package com.example.architectai.config;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayPalConfig {

    @Value("${app.paypal.clientId}")
    private String clientId;

    @Value("${app.paypal.clientSecret}")
    private String clientSecret;

    @Value("${app.paypal.mode}")
    private String mode;

    public APIContext apiContext() {
        return new APIContext(clientId, clientSecret, mode);
    }
}
