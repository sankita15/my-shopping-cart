package com.learning.shoppingcartdemo.controller;

import com.learning.shoppingcartdemo.config.ApplicationProperties;
import com.learning.shoppingcartdemo.frontend.FrontendManifest;
import com.learning.shoppingcartdemo.frontend.FrontendSourcesProvider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@Controller
@RequiredArgsConstructor
public class FrontendController {

    private final FrontendSourcesProvider frontendSourcesProvider;
    private final ApplicationProperties applicationProperties;
    private final FrontendManifest manifest;


    @GetMapping(value = "/static/styles/main.css")
    public String serveStyleSheet() {

        return manifest.getStylesheetName();
    }

    @GetMapping(value = "/")
    public String serveHelloPage(Model model){
        model.addAttribute("mainScript", frontendSourcesProvider.getMainScript());
        model.addAttribute("stylesheet", frontendSourcesProvider.getStylesheet());

        return "page";
    }

}
