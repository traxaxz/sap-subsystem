package com.sap.subsystem.secret.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record SecretView(
        String businessId,
        String secret
) {}
