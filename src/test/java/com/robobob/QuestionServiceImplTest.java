package com.robobob;

import com.robobob.service.FAQService;
import com.robobob.service.QuestionService;
import com.robobob.service.impl.QuestionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        // Directly instantiate the singleton instance
        questionService = (QuestionServiceImpl) QuestionServiceImpl.getInstance();

        // Inject the mocked FAQService into the QuestionServiceImpl using reflection
        // This approach avoids spying and ensures the singleton instance is used correctly.
        setFaqService(questionService, faqServiceMock);
    }

    @Test
    void testGetInstance() {
        QuestionService instance1 = QuestionServiceImpl.getInstance();
        QuestionService instance2 = QuestionServiceImpl.getInstance();

        assertSame(instance1, instance2, "getInstance() should return the same instance");
    }

    @Test
    void testHandleUserRequest_faqQuestion() {
        String question = "What is RoboBob?";
        String expectedAnswer = "RoboBob is an AI assistant.";

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

    // Utility method to inject the mocked FAQService into the singleton instance
    private void setFaqService(QuestionServiceImpl instance, FAQService faqServiceMock) {
        try {
            java.lang.reflect.Field faqServiceField = QuestionServiceImpl.class.getDeclaredField("faqService");
            faqServiceField.setAccessible(true);
            faqServiceField.set(instance, faqServiceMock);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

