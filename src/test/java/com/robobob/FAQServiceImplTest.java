package com.robobob;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robobob.entity.FAQEntity;
import com.robobob.service.impl.FAQServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FAQServiceImplTest {

    private FAQServiceImpl faqService;
    private ObjectMapper objectMapperMock;
    private InputStream inputStreamMock;

    @TempDir
    Path tempDir;

    @BeforeEach
    public void setUp() throws Exception {
        objectMapperMock = Mockito.mock(ObjectMapper.class);
        inputStreamMock = Mockito.mock(InputStream.class);

        // Initialize the service with reflection
        faqService = FAQServiceImpl.getInstance();

        // Use reflection to set the FAQEntityList
        List<FAQEntity> faqEntities = new ArrayList<>();
        faqEntities.add(new FAQEntity(1L, "What is RoboBob?", "RoboBob is an AI assistant."));

        Field faqEntityListField = FAQServiceImpl.class.getDeclaredField("FAQEntityList");
        faqEntityListField.setAccessible(true);
        faqEntityListField.set(faqService, faqEntities);
    }

    @Test
    public void testGetFaqByQuestion_WhenQuestionExists() {
        // Act
        Optional<String> answer = faqService.getFaqByQuestion("What is RoboBob");

        // Assert
        assertTrue(answer.isPresent());
        assertEquals("RoboBob is an AI assistant.", answer.get());
    }

    @Test
    public void testGetFaqByQuestion_WhenQuestionDoesNotExist() throws IOException {
        // Arrange
        List<FAQEntity> faqEntities = new ArrayList<>();
        when(objectMapperMock.readValue(inputStreamMock, new TypeReference<List<FAQEntity>>() {}))
                .thenReturn(faqEntities);

        // Act
        Optional<String> answer = faqService.getFaqByQuestion("Non-existent question");

        // Assert
        assertFalse(answer.isPresent());
    }

}


