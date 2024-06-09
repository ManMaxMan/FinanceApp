package com.github.ManMaxMan.FinanceApp.serviceAudit.controller.filter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CommonExceptionHandler {

    private final static Logger logger = LogManager.getLogger();

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> handleIllegalArgumentException(IllegalArgumentException ex) {

        logger.log(Level.WARN, ex.getMessage());

        Map<String,String> map = new HashMap<>();
        map.put("error", "error");
        map.put("message", "Запрос содержит некорректные данные. Измените запрос и отправьте его ещё раз");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String,String>> handleRuntimeException(RuntimeException ex) {

        logger.log(Level.ERROR, ex.getMessage());

        Map<String,String> map = new HashMap<>();
        map.put("error", "error");
        map.put("message", "Сервер не смог корректно обработать запрос. Пожалуйста обратитесь к администратору");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(map);
    }
}
