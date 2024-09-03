package com.robobob.controller;

import com.robobob.requestDTO.FAQDTORequest;
import com.robobob.service.impl.FAQApiServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class FAQController {

    private final FAQApiServiceImpl faqApiService;

    @PostMapping("/add-faq")
    public ResponseEntity<?> addQuestionAndAnswer(@RequestBody @Valid FAQDTORequest faqDTORequest) {
        faqApiService.saveFaq(faqDTORequest.getQuestion(), faqDTORequest.getAnswer());
        return ResponseEntity.ok().build();
    }
}
