package ru.otus.eba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.eba.dto.openapi.VerificationDto;
import ru.otus.eba.dto.openapi.VerificationResultDto;
import ru.otus.eba.exception.ExceptionCode;
import ru.otus.eba.exception.RestException;
import ru.otus.eba.mapper.VerificationMapper;
import ru.otus.eba.util.CryptoUtil;

@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService{
    private final PersonService personService;
    private final VerificationMapper verificationMapper;

    @Override
    public VerificationResultDto verifyPerson(VerificationDto verifyData){
        var personDto = personService.getByEmail(verifyData.getEmail());
        var personPubKey = personDto.getPublicKey();
        var decryptedText = CryptoUtil.decryptByPublicKey(verifyData.getEncryptedText(), personPubKey);
        if (decryptedText.equals(verifyData.getEmail())){
            return verificationMapper.toResult(personDto);
        }
        throw new RestException("You are not authenticated", ExceptionCode.VALIDATION);
    }
}
