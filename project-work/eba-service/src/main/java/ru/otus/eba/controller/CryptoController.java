package ru.otus.eba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.eba.controller.openapi.CryptoApi;
import ru.otus.eba.dto.openapi.CipherToolDto;
import ru.otus.eba.dto.openapi.CryptoKeysDto;
import ru.otus.eba.service.CryptoService;

@RestController
@RequiredArgsConstructor
public  class CryptoController implements CryptoApi {

    private final CryptoService cryptoService;

    @Override
    public ResponseEntity<CryptoKeysDto> generateKeys() {
        return ResponseEntity.ok(cryptoService.generateKeys());
    }

    @Override
    public ResponseEntity<String> processOperation(CipherToolDto cipherToolDto) {
        return ResponseEntity.ok(cryptoService.processOperation(cipherToolDto));
    }
}
