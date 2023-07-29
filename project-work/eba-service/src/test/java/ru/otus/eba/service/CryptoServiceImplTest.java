package ru.otus.eba.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.eba.dto.openapi.CipherToolDto;
import ru.otus.eba.mapper.CryptoMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.otus.eba.util.TestKeysUtils.TEST_ENCRYPTED_BY_PRIVATE_KEY;
import static ru.otus.eba.util.TestKeysUtils.TEST_ENCRYPTED_BY_PUBLIC_KEY;
import static ru.otus.eba.util.TestKeysUtils.TEST_PRIVATE_KEY;
import static ru.otus.eba.util.TestKeysUtils.TEST_PUBLIC_KEY;
import static ru.otus.eba.util.TestKeysUtils.createCryptoKeysDto;

@ExtendWith(MockitoExtension.class)
class CryptoServiceImplTest {
    @Mock
    private CryptoMapper mapper;
    @InjectMocks
    private CryptoServiceImpl service;

    @ParameterizedTest
    @CsvSource({
            TEST_PRIVATE_KEY + ", " + TEST_ENCRYPTED_BY_PUBLIC_KEY + ", decryptByPrivateKey, test",
            TEST_PRIVATE_KEY + ", test, encryptByPrivateKey, " + TEST_ENCRYPTED_BY_PRIVATE_KEY,
            TEST_PUBLIC_KEY + ", " + TEST_ENCRYPTED_BY_PRIVATE_KEY + ", decryptByPublicKey, test"
    })
    void processOperation(String key, String text, String type, String expectedResult) {
        // given
        var cipherToolDto = new CipherToolDto()
                .keyString(key)
                .text(text)
                .operationType(CipherToolDto.OperationTypeEnum.fromValue(type));

        // then
        var actual = service.processOperation(cipherToolDto);
        assertThat(actual).isEqualTo(expectedResult);
    }

    @Test
    void generateKeys() {
        // given
        var expected = createCryptoKeysDto();

        // when
        when(mapper.toDto(any(), any())).thenReturn(createCryptoKeysDto());

        // then
        var actual = service.generateKeys();
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}