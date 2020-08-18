package com.lethanh98.controller;

import com.lethanh98.annotation.ApiResponsesBase;
import com.lethanh98.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/api/multi-threaded-application")
@ApiResponsesBase()
@Slf4j
public class MultithreadedApplication {

    @Autowired
    UserRepo userRepo;

    @GetMapping()
    public void get() {
        request(Arrays.asList("test", "Test1", "test2"));
    }

    public void request(List<String> request) {
        request.forEach(s -> new Thread(() -> log.info("xử lý " + s)).start());
    }

}
