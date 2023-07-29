package ru.otus.eba.service;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.eba.dto.openapi.PersonDto;
import ru.otus.eba.dto.openapi.VerificationDto;
import ru.otus.eba.dto.openapi.VerificationResultDto;
import ru.otus.eba.exception.RestException;
import ru.otus.eba.mapper.VerificationMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static ru.otus.eba.util.TestKeysUtils.TEST_ENCRYPTED_BY_PRIVATE_KEY;
import static ru.otus.eba.util.TestKeysUtils.TEST_PUBLIC_KEY;

@ExtendWith(MockitoExtension.class)
class VerificationServiceImplTest {

    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private PersonService personService;
    @Mock
    private VerificationMapper verificationMapper;
    @InjectMocks
    private VerificationServiceImpl verificationService;

    @Test
    void verifyPerson() {
        // given
        var email = "test";
        var verifyData = new VerificationDto()
                .email(email)
                .encryptedText(TEST_ENCRYPTED_BY_PRIVATE_KEY);

        var personDto = easyRandom.nextObject(PersonDto.class);
        personDto.setEmail(email);
        personDto.setPublicKey(TEST_PUBLIC_KEY);

        var verifyResult = easyRandom.nextObject(VerificationResultDto.class);

        // when
        when(personService.getByEmail(anyString())).thenReturn(personDto);
        when(verificationMapper.toResult(any())).thenReturn(verifyResult);

        // then
        var actual = verificationService.verifyPerson(verifyData);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(verifyResult);
    }

    @Test
    void verifyPerson_throwException() {
        // given
        var email = "error email";
        var verifyData = new VerificationDto()
                .email(email)
                .encryptedText(TEST_ENCRYPTED_BY_PRIVATE_KEY);

        var personDto = easyRandom.nextObject(PersonDto.class);
        personDto.setEmail(email);
        personDto.setPublicKey(TEST_PUBLIC_KEY);

        var verifyResult = easyRandom.nextObject(VerificationResultDto.class);

        // when
        when(personService.getByEmail(anyString())).thenReturn(personDto);

        // then
        assertThatThrownBy(() -> verificationService.verifyPerson(verifyData))
                .isInstanceOf(RestException.class)
                .hasMessage("You are not authenticated");
    }
}