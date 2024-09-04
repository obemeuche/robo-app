package com.robobob;

import com.robobob.service.FAQService;
import com.robobob.service.QuestionService;
import com.robobob.service.impl.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {

    private FAQService faqServiceMock;
    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        faqServiceMock = mock(FAQService.class);
        questionService = (QuestionServiceImpl) spy(QuestionServiceImpl.getInstance());

        // Here, we are using reflection to set the mocked FAQService in the questionService
        // so that attemptFAQ method uses the mocked FAQService.
        doReturn(faqServiceMock).when(questionService).attemptFAQ(anyString());
    }

    @Test
    void testGetInstance() {
        QuestionService instance1 = QuestionServiceImpl.getInstance();
        QuestionService instance2 = QuestionServiceImpl.getInstance();

        assertSame(instance1, instance2, "getInstance() should return the same instance");
    }

    @Test
    void testHandleUserRequest_faqQuestion() {
        String question = "What is your return policy?";
        String expectedAnswer = "Our return policy is 30 days with receipt.";

        when(faqServiceMock.getFaqByQuestion(question)).thenReturn(Optional.of(expectedAnswer));

        String result = questionService.handleUserRequest(question);

        assertEquals(expectedAnswer, result, "The FAQ answer should be returned.");
        verify(faqServiceMock).getFaqByQuestion(question);
    }

    @Test
    void testHandleUserRequest_arithmeticExpression() throws ScriptException {
        String arithmeticQuestion = "2 + 2";
        String expectedAnswer = "4";

        doReturn(Optional.empty()).when(faqServiceMock).getFaqByQuestion(arithmeticQuestion);

        String result = questionService.handleUserRequest(arithmeticQuestion);

        assertEquals(expectedAnswer, result, "The arithmetic answer should be returned.");
    }

    @Test
    void testHandleUserRequest_invalidArithmeticExpression() {
        String invalidArithmetic = "2 + ";

        doReturn(Optional.empty()).when(faqServiceMock).getFaqByQuestion(invalidArithmetic);

        String result = questionService.handleUserRequest(invalidArithmetic);

        assertEquals("Please provide a valid question", result, "An invalid arithmetic expression should return a default message.");
    }

    @Test
    void testHandleUserRequest_unknownQuestion() {
        String unknownQuestion = "How many stars are in the sky?";

        doReturn(Optional.empty()).when(faqServiceMock).getFaqByQuestion(unknownQuestion);

        String result = questionService.handleUserRequest(unknownQuestion);

        assertEquals("Please provide a valid question", result, "An unknown question should return a default message.");
    }
}
