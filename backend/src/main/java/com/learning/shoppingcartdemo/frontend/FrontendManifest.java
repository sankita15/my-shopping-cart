package com.learning.shoppingcartdemo.frontend;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = FrontendManifest.FrontendManifestBuilder.class)
public class FrontendManifest {
    @JsonProperty("main.js")
    String mainScriptName;

    @JsonProperty("styles/main.scss")
    String stylesheetName;

    @JsonPOJOBuilder(withPrefix = "")
    public static class FrontendManifestBuilder {

    }
}
