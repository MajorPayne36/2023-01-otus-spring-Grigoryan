package ru.otus.eba.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import ru.otus.eba.dto.openapi.CipherToolDto;
import ru.otus.eba.dto.openapi.CipherToolDto.OperationTypeEnum;
import ru.otus.eba.dto.openapi.CryptoKeysDto;
import ru.otus.eba.mapper.CryptoMapper;
import ru.otus.eba.util.Base64Utils;
import ru.otus.eba.util.CryptoUtils;
import ru.otus.eba.util.RsaKeyUtils;

@Service
@RequiredArgsConstructor
public class CryptoServiceImpl implements CryptoService {
    private final CryptoMapper mapper;

    @Override
    public String processOperation(CipherToolDto cipherToolDto) {
        return switch (cipherToolDto.getOperationType()) {
            case DECRYPTBYPRIVATEKEY -> decryptByPrivateKey(cipherToolDto);
            case DECRYPTBYPUBLICKEY -> decryptByPublicKey(cipherToolDto);
            case ENCRYPTBYPRIVATEKEY -> encryptByPrivateKey(cipherToolDto);
            case ENCRYPTBYPUBLICKEY -> encryptByPublicKey(cipherToolDto);
        };
    }

    @Override
    @SneakyThrows
    public CryptoKeysDto generateKeys() {
        var keyPair = RsaKeyUtils.generateKeyPair();
        return mapper.toDto(
                Base64Utils.base64(keyPair.getPrivate()),
                Base64Utils.base64(keyPair.getPublic())
        );
    }

    /**
     * {@link OperationTypeEnum#DECRYPTBYPRIVATEKEY}
     */
    private String decryptByPrivateKey(CipherToolDto cipherToolDto) {
        var textToDecrypt = cipherToolDto.getText();
        var privateKey = RsaKeyUtils.toPrivateKey(cipherToolDto.getKeyString());
        return CryptoUtils.decrypt(textToDecrypt, privateKey);
    }

    /**
     * {@link OperationTypeEnum#DECRYPTBYPUBLICKEY}
     */
    private String decryptByPublicKey(CipherToolDto cipherToolDto) {
        var textToDecrypt = cipherToolDto.getText();
        var publicKey = RsaKeyUtils.toPublicKey(cipherToolDto.getKeyString());
        return CryptoUtils.decrypt(textToDecrypt, publicKey);
    }

    /**
     * {@link OperationTypeEnum#ENCRYPTBYPRIVATEKEY}
     */
    private String encryptByPrivateKey(CipherToolDto cipherToolDto) {
        var textToEncrypt = cipherToolDto.getText();
        var privateKey = RsaKeyUtils.toPrivateKey(cipherToolDto.getKeyString());
        return CryptoUtils.encrypt(textToEncrypt, privateKey);
    }

    /**
     * {@link OperationTypeEnum#ENCRYPTBYPUBLICKEY}
     */
    private String encryptByPublicKey(CipherToolDto cipherToolDto) {
        var textToEncrypt = cipherToolDto.getText();
        var publicKey = RsaKeyUtils.toPublicKey(cipherToolDto.getKeyString());
        return CryptoUtils.encrypt(textToEncrypt, publicKey);
    }
}
