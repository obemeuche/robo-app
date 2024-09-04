//package com.robobob;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.robobob.entity.FAQEntity;
//import com.robobob.service.impl.FAQServiceImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//import org.mockito.Mockito;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class FAQServiceImplTest {
//
//    private FAQServiceImpl faqService;
//    private ObjectMapper objectMapperMock;
//    private InputStream inputStreamMock;
//
//    @TempDir
//    Path tempDir;
//
//    @BeforeEach
//    public void setUp() throws IOException {
//        // Mock ObjectMapper and InputStream
//        objectMapperMock = Mockito.mock(ObjectMapper.class);
//        inputStreamMock = Mockito.mock(InputStream.class);
//
//        // Create a temporary JSON file for testing
//        File tempFile = Files.createFile(tempDir.resolve("faq.json")).toFile();
//
//        // Set up FAQServiceImpl instance with mocked ObjectMapper and InputStream
//        faqService = new FAQServiceImpl() {
//            {
//                this.objectMapper = objectMapperMock;
//                this.FILE_PATH = tempFile.getName();
//            }
//
//            @Override
//            protected InputStream getResourceAsStream(String fileName) {
//                return inputStreamMock;
//            }
//        };
//    }
//
//    @Test
//    public void testGetFaqByQuestion_WhenQuestionExists() throws IOException {
//        // Arrange
//        List<FAQEntity> faqEntities = new ArrayList<>();
//        faqEntities.add(new FAQEntity(1L, "What is Java?", "Java is a programming language."));
//        when(objectMapperMock.readValue(inputStreamMock, new TypeReference<List<FAQEntity>>() {}))
//                .thenReturn(faqEntities);
//
//        faqService = FAQServiceImpl.getInstance();
//
//        // Act
//        Optional<String> answer = faqService.getFaqByQuestion("What is Java?");
//
//        // Assert
//        assertTrue(answer.isPresent());
//        assertEquals("Java is a programming language.", answer.get());
//    }
//
//    @Test
//    public void testGetFaqByQuestion_WhenQuestionDoesNotExist() throws IOException {
//        // Arrange
//        List<FAQEntity> faqEntities = new ArrayList<>();
//        when(objectMapperMock.readValue(inputStreamMock, new TypeReference<List<FAQEntity>>() {}))
//                .thenReturn(faqEntities);
//
//        faqService = FAQServiceImpl.getInstance();
//
//        // Act
//        Optional<String> answer = faqService.getFaqByQuestion("Non-existent question");
//
//        // Assert
//        assertFalse(answer.isPresent());
//    }
//
//    @Test
//    public void testSaveFaq() throws IOException {
//        // Arrange
//        List<FAQEntity> faqEntities = new ArrayList<>();
//        when(objectMapperMock.readValue(inputStreamMock, new TypeReference<List<FAQEntity>>() {}))
//                .thenReturn(faqEntities);
//
//        faqService = FAQServiceImpl.getInstance();
//
//        // Act
//        faqService.saveFaq("What is Python?", "Python is a programming language.");
//
//        // Assert
//        verify(objectMapperMock).writerWithDefaultPrettyPrinter();
//    }
//}
//
