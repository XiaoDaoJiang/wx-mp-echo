package com.xiaodao.wx.conch.service;


import com.xiaodao.wx.conch.dto.InstapaperAddDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author xiaodaojiang
 * @Classname InstapaperClientTest
 * @Version 1.0.0
 * @Date 2024-07-16 23:10
 * @Created by xiaodaojiang
 */
@SpringBootTest
class InstapaperClientTest {

    @Autowired
    private InstapaperClient instapaperClient;

    @Test
    public void test_authenticate() {
        assertThat(instapaperClient.authenticate()).isTrue();
    }

    @Test
    void addUrl() {
        final boolean added = instapaperClient.addUrl("https://spring.io/guides/gs/rest-service");
        assertThat(added).isTrue();
    }


    @Test
    void add() {
        final String url = "https://spring.io/guides/gs/rest-service";
        final InstapaperAddDTO addDTO = new InstapaperAddDTO().setUrl(url)
                .setSelection("create a simple RESTful web service with Spring Boot")
                .setTitle("Building a RESTful Web Service");

        final boolean added = instapaperClient.add(addDTO);

        assertThat(added).isTrue();
    }
}