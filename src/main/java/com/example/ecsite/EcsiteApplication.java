package com.example.ecsite;

// Spring Bootアプリケーションのエントリーポイント
// mainメソッドからアプリケーション全体が起動される
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcsiteApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcsiteApplication.class, args); // アプリケーションを起動
    }
}
