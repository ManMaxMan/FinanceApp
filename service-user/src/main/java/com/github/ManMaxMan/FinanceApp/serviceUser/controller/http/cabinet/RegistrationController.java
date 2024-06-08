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

    @PostMapping(value = "/registration", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void registration(@RequestBody UserRegistrationDTO registrationDTO) {
        registrationService.registration(registrationDTO);
    }

    @GetMapping(value = "/verification", produces = "application/json")
    public ResponseEntity<?> verification(@RequestParam("code") String code, @RequestParam("mail") String mail) {
        VerificationDTO verificationDTO= VerificationDTO.builder()
                .email(mail)
                .verificationCode(code)
                .build();
        registrationService.verification(verificationDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
