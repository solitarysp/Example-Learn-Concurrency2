package com.lethanh98.controller;

import com.lethanh98.annotation.ApiResponsesBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = "/api/demo-error")
@ApiResponsesBase()
@Slf4j
public class DemoErrorController {
    ThreadPoolExecutor createService = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);

    @GetMapping()
    public void get() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                Account account = new Account(100d);

                Thread threadRutTien = new Thread(() -> {
                    account.rutTien(100d);
                });
                Thread threadNhanTien = new Thread(() -> {
                    account.nhanTien(100d);
                });
                createService.execute(threadRutTien);
                createService.execute(threadNhanTien);
                try {
                    createService.awaitTermination(1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("tiền còn lại là :"+account.getCurrentBalance());
            }).start();

        }
    }


    @AllArgsConstructor
    @Data
    static class Account {
        private Double currentBalance;

        public boolean rutTien(Double tien) {
            Double currentBalance = getCurrentBalance();
            if (currentBalance >= tien) {
                setCurrentBalance(getCurrentBalance() - tien);
                return true;
            }
            return false;
        }

        public void nhanTien(Double tien) {
            Double currentBalance = getCurrentBalance();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setCurrentBalance(currentBalance + tien);

        }
    }

}
