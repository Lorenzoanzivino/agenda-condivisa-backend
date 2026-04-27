package com.catalog.calendarservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // <--- AGGIUNGI QUESTA
public class CalendarServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalendarServiceApplication.class, args);
    }
}