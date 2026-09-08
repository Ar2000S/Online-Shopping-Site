package com.online_shopping_backend.ctrl;

import com.online_shopping_backend.util.MessageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageCtrl {

    @Autowired
    private MessageHelper messages;

    @GetMapping
    public Map<String, String> getAllMessages() {
        Map<String, String> result = new LinkedHashMap<>();
        // Placeholders, that will later grab the strings, so return placeholders asis
        String[] codes = {
                "MSG001","MSG002","MSG003","MSG004","MSG005","MSG006","MSG007",
                "MSG008","MSG009","MSG010","MSG011","MSG012","MSG013","MSG014"
        };
        for (String code : codes) {
            result.put(code, messages.get(code));
        }
        return result;
    }
}