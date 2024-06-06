package com.github.ManMaxMan.FinanceApp.serviceUser.controller.http.cabinet;

import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserRegistrationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.VerificationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabinet")
public class RegistrationController {

    private final IRegistrationService registrationService;

    public RegistrationController(IRegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody UserRegistrationDTO registrationDTO) {
        registrationService.registration(registrationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь зарегистрирован");
    }

    @GetMapping("/verification")
    public ResponseEntity<String> verification(@RequestParam("code") String code, @RequestParam("mail") String mail) {
        VerificationDTO verificationDTO= VerificationDTO.builder()
                .email(mail)
                .verificationCode(code)
                .build();
        registrationService.verification(verificationDTO);
        return ResponseEntity.ok("Пользователь верифицирован");
    }
}
