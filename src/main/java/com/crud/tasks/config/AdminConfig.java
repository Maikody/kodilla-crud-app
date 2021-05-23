package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@ConfigurationProperties("admin")
public class AdminConfig {
    private String mail;
    private String name;
}
