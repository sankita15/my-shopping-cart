package com.learning.shoppingcartdemo.frontend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FrontendSourcesProvider {
    private static final String URL_PREFIX = "/static/";

    private final FrontendManifest manifest;

    public String getMainScript() {
        return URL_PREFIX + manifest.getMainScriptName();
    }

    public String getStylesheet() {
        return URL_PREFIX + manifest.getStylesheetName();
    }
}
