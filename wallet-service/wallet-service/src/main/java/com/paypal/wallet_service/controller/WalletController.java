package com.paypal.wallet_service.controller;

import com.paypal.wallet_service.dto.*;
import com.paypal.wallet_service.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping
    public ResponseEntity<WalletResponse> createWallet(
            @RequestBody CreateWalletRequest request,
            @RequestHeader("X-User-Id") String userId
    ) {
        request.setUserId(Long.parseLong(userId));
        return ResponseEntity.ok(walletService.createWallet(request));
    }

    @PostMapping("/credit")
    public ResponseEntity<WalletResponse> credit(
            @RequestBody CreditRequest request,
            @RequestHeader("X-User-Id") String userId
    ) {
        request.setUserId(Long.parseLong(userId));
        return ResponseEntity.ok(walletService.credit(request));
    }

    @PostMapping("/debit")
    public ResponseEntity<WalletResponse> debit(
            @RequestBody DebitRequest request,
            @RequestHeader("X-User-Id") String userId
    ) {
        request.setUserId(Long.parseLong(userId));
        return ResponseEntity.ok(walletService.debit(request));
    }

    @GetMapping("/me")
    public ResponseEntity<WalletResponse> getWallet(
            @RequestHeader("X-User-Id") String userId
    ) {
        return ResponseEntity.ok(walletService.getWallet(Long.parseLong(userId)));
    }

    @PostMapping("/hold")
    public ResponseEntity<HoldResponse> placeHold(
            @RequestBody HoldRequest request,
            @RequestHeader("X-User-Id") String userId
    ) {
        request.setUserId(Long.parseLong(userId));
        return ResponseEntity.ok(walletService.placeHold(request));
    }

    @PostMapping("/capture")
    public ResponseEntity<WalletResponse> capture(
            @RequestBody CaptureRequest request
    ) {
        return ResponseEntity.ok(walletService.captureHold(request));
    }

    @PostMapping("/release/{holdReference}")
    public ResponseEntity<HoldResponse> release(@PathVariable String holdReference) {
        return ResponseEntity.ok(walletService.releaseHold(holdReference));
    }
}