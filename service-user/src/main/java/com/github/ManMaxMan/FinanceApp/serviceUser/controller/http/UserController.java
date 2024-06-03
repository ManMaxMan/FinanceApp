package com.github.ManMaxMan.FinanceApp.serviceUser.controller.http;


import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.PageOfUserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserCreateDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    public UserController() {
    }

    @PostMapping
    public ResponseEntity<UserCreateDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {

        return ResponseEntity.status(201).body(userCreateDTO);
    }

    @GetMapping
    public ResponseEntity<PageOfUserDTO> getPageUsers(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                      @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {

        PageOfUserDTO pageOfUserDTO = new PageOfUserDTO();

        return ResponseEntity.ok(pageOfUserDTO);
    }

    @GetMapping("{uuid}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("uuid") UUID uuid) {
        UserDTO userDTO = new UserDTO();


        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<UserCreateDTO> updateUser(@PathVariable("uuid") UUID uuid,
                                              @PathVariable("dt_update") Instant dtUpdate,
                                              @RequestBody UserCreateDTO userCreateDTO) {

        return ResponseEntity.ok(null);
    }

}
