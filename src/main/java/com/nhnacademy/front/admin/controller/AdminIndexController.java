package com.nhnacademy.front.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@Controller
public class AdminIndexController {
    @GetMapping
    public String getIndex() {
        return "admin/index";
    }
}
