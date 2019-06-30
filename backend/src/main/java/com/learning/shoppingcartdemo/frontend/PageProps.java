package com.learning.shoppingcartdemo.frontend;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class PageProps {
    String user;
}
