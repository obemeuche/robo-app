package com.robobob.requestDTO;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class FAQDTORequest {

    @NotEmpty(message = "Invalid Question")
    String question;

    @NotEmpty(message = "Invalid Answer")
    String answer;
}
