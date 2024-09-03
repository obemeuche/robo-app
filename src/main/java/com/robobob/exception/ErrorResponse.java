package com.robobob.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String responseCode;
    private String responseMsg;
}
