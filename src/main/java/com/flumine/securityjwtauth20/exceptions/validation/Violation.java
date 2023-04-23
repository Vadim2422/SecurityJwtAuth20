package com.flumine.securityjwtauth20.exceptions.validation;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class Violation {
    private final String fieldName;
    private final String message;
}
