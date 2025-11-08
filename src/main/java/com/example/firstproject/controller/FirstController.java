package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {

    @GetMapping("/hi")
    public String niceToMeetYou(Model model) {
        model.addAttribute("username", "장발장");
        return "greetings"; // templates/greetings.mustache -> 브라우저로 전송
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model) {
        model.addAttribute("username", "홍길동");
        return "goodbye";
    }
}
/*
    어노테이션 (@)
    소스코드에 추가해 사용하는 메타 데이터의 일종이다
    메타데이터는 프로그램에서 처리해야할 데이터가 아니라 컴파일 및 실행 과정에서 코드를 어떻게 처리해야
    하는지 알려주는 추가정보를 뜻함
*/