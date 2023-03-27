package ru.otus.spring.integration.service;

import org.springframework.stereotype.Service;

@Service
public class HandleInOut {
    public void outAndVk(String s){
        System.out.println(s);
    }
}
