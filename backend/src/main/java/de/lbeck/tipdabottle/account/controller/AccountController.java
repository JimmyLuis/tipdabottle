package de.lbeck.tipdabottle.account.controller;

import de.lbeck.tipdabottle.account.dto.in.RequestAccountLoginDTO;
import de.lbeck.tipdabottle.account.dto.out.ResponseAccountPublicDTO;
import de.lbeck.tipdabottle.account.model.Account;
import de.lbeck.tipdabottle.account.service.AccountService;
import de.lbeck.tipdabottle.common.annotations.View;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/login")
    public ResponseAccountPublicDTO login(@Valid @RequestBody RequestAccountLoginDTO requestAccountLoginDTO){
        return accountService.login(requestAccountLoginDTO);
    }


    @PostMapping("/logout")
    public ResponseAccountPublicDTO logout(){
        return accountService.logout();
    }
}
