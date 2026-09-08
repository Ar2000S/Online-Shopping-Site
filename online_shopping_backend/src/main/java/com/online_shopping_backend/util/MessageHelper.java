package com.online_shopping_backend.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageHelper {

    @Autowired
    private MessageSource messageSource;

    public String get(String code) {
        return messageSource.getMessage(code, null, Locale.getDefault());
    }

    public String get(String code, Object... args) {
        return messageSource.getMessage(code, args, Locale.getDefault());
    }

    public String getRaw(String code) {
        // returns the template WITHOUT formatting, keeps the {0} intact
        return messageSource.getMessage(code, null, Locale.getDefault());
    }
}