package com.learning.shoppingcartdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.shoppingcartdemo.frontend.FrontendManifest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.File;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class FrontendConfig  {

    private static final String UTF_8 = "UTF-8";

    @Bean
    @SneakyThrows
    public FrontendManifest frontendManifest(ApplicationProperties applicationProperties) {
        File manifestFile = new File(applicationProperties.getFrontendDistDir(), "manifest.json");
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(manifestFile, FrontendManifest.class);
    }

    @Bean
    public FileTemplateResolver frontendResourceResolver(ApplicationProperties applicationProperties) {
        var templateResolver = new FileTemplateResolver();
        var frontendDistDir = new File(applicationProperties.getFrontendDistDir()).getAbsolutePath();

        templateResolver.setCheckExistence(true);

        templateResolver.setPrefix(frontendDistDir + "/");
        templateResolver.setTemplateMode(TemplateMode.RAW);
//        templateResolver.setJavaScriptTemplateModePatterns(Set.of("**.js"));
//        templateResolver.setCSSTemplateModePatterns(Set.of("**.css"));
        templateResolver.setCharacterEncoding(UTF_8);

        return templateResolver;
    }

}
