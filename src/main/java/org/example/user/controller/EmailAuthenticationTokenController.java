package org.example.user.controller;

import org.example.user.command.CreateEmailAuthenticationToken;
import org.example.user.service.EmailAuthenticationTokenService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email-authentication-token")
public class EmailAuthenticationTokenController {

    private final EmailAuthenticationTokenService emailAuthenticationTokenService;

    public EmailAuthenticationTokenController(EmailAuthenticationTokenService emailAuthenticationTokenService) {
        //
        this.emailAuthenticationTokenService = emailAuthenticationTokenService;
    }

    @PostMapping
    public long createEmailAuthenticationToken(@RequestBody CreateEmailAuthenticationToken command) {
        //
        return this.emailAuthenticationTokenService.createEmailAuthenticationToken(command);
    }

    @GetMapping
    public boolean checkEmailAuthenticationToken(@RequestParam String connectId, @RequestHeader String token) {
        //
        return this.emailAuthenticationTokenService.checkEmailAuthenticationToken(connectId, token);
    }
}
