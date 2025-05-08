package com.nhnacademy.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String homePage() {
        return "index"; // templates/home.html 을 찾아 렌더링
    }
}
