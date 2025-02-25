package com.magmutual.magusers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponseDTO {

    private String apiPath;
    private HttpStatus status;
    private String message;
    private LocalDateTime time;
}

