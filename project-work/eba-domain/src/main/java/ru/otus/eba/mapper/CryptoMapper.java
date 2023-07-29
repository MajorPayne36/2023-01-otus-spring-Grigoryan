package ru.otus.eba.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.eba.dto.openapi.CryptoKeysDto;

@Mapper(config = BaseMapperConfig.class)
public interface CryptoMapper {

    @Mapping(target = "publicKey", source = "publicKey")
    @Mapping(target = "privateKey", source = "privateKey")
    CryptoKeysDto toDto(String privateKey, String publicKey);
}
