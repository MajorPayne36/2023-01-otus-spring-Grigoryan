package ru.otus.eba.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;

/**
 * Базовый конфиг для всех мапперов.
 */
@MapperConfig(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface BaseMapperConfig {
}
