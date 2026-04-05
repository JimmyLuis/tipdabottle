package de.lbeck.tipdabottle.account.controller;

import de.lbeck.tipdabottle.account.dto.in.RequestAccountCreateDTO;
import de.lbeck.tipdabottle.account.dto.in.RequestAccountLoginDTO;
import de.lbeck.tipdabottle.account.dto.out.ResponseAccountPublicDTO;
import de.lbeck.tipdabottle.account.model.Account;
import de.lbeck.tipdabottle.account.service.AccountService;
import de.lbeck.tipdabottle.common.annotations.View;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody RequestAccountLoginDTO requestAccountLoginDTO, HttpServletResponse response){
        ResponseCookie cookie = ResponseCookie.from("token", accountService.login(requestAccountLoginDTO, response))
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(60*60*24)
                .build();
        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().build();
    }


    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {

        ResponseCookie cookie = ResponseCookie.from("token", "")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok().build();
    }

    @View(Account.class)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public Account createAccount(@Valid @RequestBody RequestAccountCreateDTO requestAccountCreateDTO){
        return accountService.createAccount(requestAccountCreateDTO);
    }

    @View(Account.class)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping()
    public Account deleteAccount(@RequestParam String email){
        return accountService.deleteAccountByEmail(email);
    }
}
