package com.robobob.requestDTO;


import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class FAQDTORequest {

    @NotEmpty(message = "Invalid Question")
    String question;

    @NotEmpty(message = "Invalid Answer")
    String answer;
}
