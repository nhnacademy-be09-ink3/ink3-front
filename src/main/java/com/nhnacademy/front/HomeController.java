package com.nhnacademy.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String homePage() {
        return "adminpage";
    }

    @GetMapping("/test12")
    public String testPage() {
        return "test";
    }
}
