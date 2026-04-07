package com.example.ecsite.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HomeControllerTest {

    @Test
    void homeReturnsIndexViewName() {
        HomeController homeController = new HomeController();

        String view = homeController.home();

        assertEquals("index", view);
    }
}
