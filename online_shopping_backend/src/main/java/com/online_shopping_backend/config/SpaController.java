package com.online_shopping_backend.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpaController {

    // Admin root the bare /admin and /admin/ make both accessible
    @GetMapping({ "/admin", "/admin/" })
    public String adminRoot() {
        return "forward:/admin/index.html";
    }

    //Admin routing
    @RequestMapping("/admin/{path:[^\\.]*}")
    public String adminForward() {
        return "forward:/admin/index.html";
    }

    // User routes
    @RequestMapping({ "/{path:[^\\.]*}", "/**/{path:[^\\.]*}" })
    public String userForward() {
        return "forward:/index.html";
    }
}