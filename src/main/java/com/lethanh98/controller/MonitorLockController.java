package com.lethanh98.controller;

import com.lethanh98.annotation.ApiResponsesBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/monitor-lock")
@ApiResponsesBase()
@Slf4j
public class MonitorLockController {
    @GetMapping()
    public void get() {
        Safe safe = new Safe();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> safe.get(Thread.currentThread().getName())).start();
            new Thread(() -> safe.get2(Thread.currentThread().getName())).start();
            new Thread(() -> safe.get3("test")).start();
        }

    }

    static class Safe {
        public void get(String name) {
            synchronized (this) {
                try {
                    System.out.println(name + " : open");
                    Thread.sleep(1000);
                    System.out.println(name + " : end");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

        public synchronized void get2(String name) {
            try {
                System.out.println(name + " : open  2");
                Thread.sleep(1000);
                System.out.println(name + " : end 2");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        public void get3(String name) {
            synchronized (name) {
                try {
                    System.out.println(name + " : open 3");
                    Thread.sleep(1000);
                    System.out.println(name + " : end 3 ");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
