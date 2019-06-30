package com.learning.shoppingcartdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Configuration
@Validated
@Component
public class ApplicationProperties {
    @Value("${shoppingcart.frontend-dist-dir}")
    @NotBlank
    private String frontendDistDir;

    public String getFrontendDistDir() {
        return frontendDistDir;
    }
}
