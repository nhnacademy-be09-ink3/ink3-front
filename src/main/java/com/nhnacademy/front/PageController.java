package com.nhnacademy.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String homePage() {
        return "header"; // templates/home.html 을 찾아 렌더링
    }
}
