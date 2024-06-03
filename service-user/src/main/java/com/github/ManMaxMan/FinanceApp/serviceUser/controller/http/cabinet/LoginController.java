package com.github.ManMaxMan.FinanceApp.serviceUser.controller.http.cabinet;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserLoginDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.ILoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabinet")
public class LoginController {

    private final ILoginService loginService;

    public LoginController(ILoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        //String token = registrationUserService.login(userLoginDTO);
        return ResponseEntity.ok("Вход выполнен. Токен для Authorization Header ");// + token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUserInfo(@RequestHeader("Authorization") String token) {
        //UserDTO userDTO = registrationUserService.getInfo(token);
        return ResponseEntity.ok().body(new UserDTO());
    }

}
