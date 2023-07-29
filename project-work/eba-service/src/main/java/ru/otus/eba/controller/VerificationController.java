package ru.otus.eba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.eba.controller.openapi.VerificationApi;
import ru.otus.eba.dto.openapi.VerificationDto;
import ru.otus.eba.dto.openapi.VerificationResultDto;
import ru.otus.eba.service.VerificationService;

@RestController
@RequiredArgsConstructor
public class VerificationController implements VerificationApi {

    private final VerificationService verificationService;

    @Override
    public ResponseEntity<VerificationResultDto> verify(VerificationDto verificationDto) {
        return ResponseEntity.ok(
                verificationService.verifyPerson(verificationDto));
    }
}
