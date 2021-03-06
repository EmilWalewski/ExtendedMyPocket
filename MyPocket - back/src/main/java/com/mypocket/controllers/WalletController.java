package com.mypocket.controllers;

import com.mypocket.storeManagement.entities.Wallet;
import com.mypocket.storeManagement.entities.WalletWrapper;
import com.mypocket.storeManagement.storeUtilities.WalletStorage;
import com.mypocket.storeManagement.utilities.res.ApiResponse;
import com.mypocket.storeManagement.validators.ValidationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletStorage walletStorage;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createWallet(@Validated @RequestBody Wallet wallet, Errors errors, HttpServletRequest request){


        if (errors.hasErrors()){
            return ResponseEntity.badRequest().body(ValidationBuilder.buildErrorMessage(errors));
        }


        return ResponseEntity.ok(new ApiResponse<>(walletStorage.saveWallet(wallet, request.getHeader("E-SEL-MAR-XX")), HttpStatus.CREATED));
    }

    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createWallet2(@RequestBody(required = false) List<Wallet> walletList, HttpServletRequest request){

        return ResponseEntity.ok(new ApiResponse<>(walletStorage.saveWallets(walletList, request.getHeader("E-SEL-MAR-XX")), HttpStatus.CREATED));
    }


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserWallets(HttpServletRequest request){

        return ResponseEntity.ok(walletStorage.getWallets(request.getHeader("E-SEL-MAR-XX")));
    }
}
