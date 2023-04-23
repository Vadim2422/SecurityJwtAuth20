package com.flumine.securityjwtauth20.exceptions.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Data
public class ValidationErrorResponse {
    private final List<Violation> violations;
}