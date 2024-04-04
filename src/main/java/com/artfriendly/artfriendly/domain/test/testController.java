package com.artfriendly.artfriendly.domain.test;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class testController {

    @GetMapping("")
    public ResponseEntity<String> getHello() {
        return ResponseEntity.status(HttpStatus.OK).body("Hello");
    }
}
