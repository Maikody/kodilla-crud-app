package com.crud.tasks.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class Mail {
    private final String mailTo;
    private String toCc;
    private final String subject;
    private final String message;
}
