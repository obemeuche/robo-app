package com.robobob;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.robobob.entity.FAQEntity;
import com.robobob.exception.DatabaseException;
import com.robobob.repository.FAQRepository;
import com.robobob.service.impl.FAQApiServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class FAQApiServiceImplTest {

    @Mock
    private FAQRepository faqRepository;

    @InjectMocks
    private FAQApiServiceImpl faqService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveFaq_Success() {
        // Arrange
        String question = "What is RoboBob?";
        String answer = "RoboBob is an AI assistant.";

        // Act
        faqService.saveFaq(question, answer);

        // Assert
        verify(faqRepository, times(1)).save(any(FAQEntity.class));
    }

    @Test
    public void testSaveFaq_Exception() {
        // Arrange
        String question = "What is RoboBob?";
        String answer = "RoboBob is an AI assistant.";

        doThrow(new RuntimeException("Database error")).when(faqRepository).save(any(FAQEntity.class));

        // Act & Assert
        DatabaseException exception = assertThrows(DatabaseException.class, () -> {
            faqService.saveFaq(question, answer);
        });

        assertEquals("UNABLE TO SAVE FAQ TO THE DATABASE", exception.getMessage());
    }
}

