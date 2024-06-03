package com.github.ManMaxMan.FinanceApp.serviceUser.controller.http.cabinet;

import com.github.ManMaxMan.FinanceApp.serviceUser.controller.converter.api.IConverter;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.UserRegistrationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.core.dto.VerificationDTO;
import com.github.ManMaxMan.FinanceApp.serviceUser.service.api.IRegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cabinet")
public class RegistrationController {

    private final IRegistrationService registrationService;
    private final IConverter converter;

    public RegistrationController(IRegistrationService registrationService, IConverter converter) {
        this.registrationService = registrationService;
        this.converter = converter;
    }

    @PostMapping("/registration")
    public ResponseEntity<UserDTO> registration(@RequestBody UserRegistrationDTO registrationDTO) {
        UserDTO userDTO = converter.convert(registrationService.registration(registrationDTO));
        return ResponseEntity.status(200).body(userDTO);
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
