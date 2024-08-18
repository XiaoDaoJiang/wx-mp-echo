package com.xiaodao.wx.conch.service;

import cn.hutool.core.bean.BeanUtil;
import com.xiaodao.wx.conch.config.InstapaperProperties;
import com.xiaodao.wx.conch.dto.InstapaperAddDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * instapaper api client
 *
 * @author xiaodaojiang
 * @Classname InstapaperClient
 * @Version 1.0.0
 * @Date 2024-07-16 22:42
 * @Created by xiaodaojiang
 */
@Slf4j
@Service
public class InstapaperClient {

    private final RestTemplate restTemplate;

    public InstapaperClient(RestTemplateBuilder restTemplateBuilder, InstapaperProperties instapaperProperties) {
        this.restTemplate = restTemplateBuilder
                .basicAuthentication(instapaperProperties.getUsername(), instapaperProperties.getPassword())
                .build();
    }

    public boolean authenticate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InstapaperAddDTO> request = new HttpEntity<>(null, headers);
        final ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://www.instapaper.com/api/authenticate", request, String.class);
        if (responseEntity.getStatusCode().isError()) {
            log.error("connect instapaper失败");
            return false;
        }
        return true;
    }

    public boolean addUrl(String url) {
        // Create a map for post parameters
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("url", url);

        // Set the Content-Type header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create the HttpEntity for the request
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        final ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://www.instapaper.com/api/add", request, String.class);
        if (responseEntity.getStatusCode().isError()) {
            log.error("添加instapaper失败");
            return false;
        }
        return true;
    }


    public boolean add(InstapaperAddDTO addDTO) {
        // Create a map for post parameters
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        BeanUtil.beanToMap(addDTO, false, true)
                .forEach((k, v) -> map.addAll(k, Collections.singletonList(String.valueOf(v))));

        // Set the Content-Type header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create the HttpEntity for the request
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        final ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://www.instapaper.com/api/add", request, String.class);
        if (responseEntity.getStatusCode().isError()) {
            log.error("添加instapaper失败");
            return false;
        }
        return true;
    }


}
