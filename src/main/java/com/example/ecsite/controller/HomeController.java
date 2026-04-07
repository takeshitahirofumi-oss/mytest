package com.example.ecsite.controller;

// トップページや共通ページの表示を担当するコントローラー
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // ルートURL（"/"）へのGETリクエストでindex.htmlを返す
    @GetMapping("/")
    public String home() {
        return "index"; // templates/index.htmlを表示
    }
}
