package com.github.ManMaxMan.FinanceApp.serviceUser.controller.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class InternalController {

    @GetMapping(value = "/check")
    public ResponseEntity<Boolean> checkAccess(){

        return ResponseEntity.status(HttpStatus.OK).body(true);
    }

}
