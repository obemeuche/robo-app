package com.robobob;

import com.robobob.controller.FAQController;
import com.robobob.requestDTO.FAQDTORequest;
import com.robobob.service.impl.FAQApiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class FAQControllerTest {

    @Mock
    private FAQApiServiceImpl faqApiService;

    @InjectMocks
    private FAQController faqController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddQuestionAndAnswer() {

        FAQDTORequest faqDTORequest = new FAQDTORequest();
        faqDTORequest.setQuestion("What is RoboBob?");
        faqDTORequest.setAnswer("RoboBob is an AI assistant.");

        doNothing().when(faqApiService).saveFaq(faqDTORequest.getQuestion(), faqDTORequest.getAnswer());
        ResponseEntity<?> response = faqController.addQuestionAndAnswer(faqDTORequest);

        assertEquals(ResponseEntity.ok().build(), response);
        verify(faqApiService).saveFaq(faqDTORequest.getQuestion(), faqDTORequest.getAnswer());
    }
}

