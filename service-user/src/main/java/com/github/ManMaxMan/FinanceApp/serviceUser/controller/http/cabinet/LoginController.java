package com.github.ManMaxMan.FinanceApp.serviceUser.controller.http.cabinet;

import com.github.ManMaxMan.FinanceApp.serviceUser.controller.converter.ConverterToDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.TokenDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserLoginDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.ILoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabinet")
public class LoginController {

    private final ILoginService loginService;
    private final ConverterToDTO converterToDTO;

    public LoginController(ILoginService loginService, ConverterToDTO converterToDTO) {
        this.loginService = loginService;
        this.converterToDTO = converterToDTO;
    }

    @PostMapping(value = "/login", produces = "application/jwt")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        String token = loginService.login(userLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @GetMapping(value = "/me", produces = "application/json")
    public ResponseEntity<UserDTO> aboutUser (@RequestHeader("Authorization") String token) {
        TokenDTO tokenDTO = TokenDTO.builder()
                .token(token)
                .build();

        UserDTO user = converterToDTO.convert(loginService.getUser(tokenDTO));
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
