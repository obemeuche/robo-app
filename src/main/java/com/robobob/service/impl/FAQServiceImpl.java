package com.robobob.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robobob.entity.FAQEntity;
import service.FAQService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FAQServiceImpl implements FAQService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String FILE_PATH = "faq.json";

    List<FAQEntity> FAQEntityList;

    private static final FAQServiceImpl INSTANCE = new FAQServiceImpl();

    public static FAQServiceImpl getInstance() {
        return INSTANCE;
    }

    private FAQServiceImpl() {
        InputStream inputStream = FAQServiceImpl.class.getClassLoader().getResourceAsStream(FILE_PATH);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found!");
        }
        try {
            FAQEntityList = objectMapper.readValue(inputStream, new TypeReference<List<FAQEntity>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<String> getFaqById(Long id) {
        FAQEntity entity = FAQEntityList.stream()
                .filter(FAQEntity -> Objects.equals(FAQEntity.getId(), id))
                .findAny()
                .orElse(null);
        String value = entity == null ? null : entity.getAnswer();
        return Optional.ofNullable(value);
    }

    @Override
    public Optional<String> getFaqByQuestion(String question) {
        FAQEntity entity = FAQEntityList.stream()
                .filter(FAQEntity -> FAQEntity.getQuestion().equalsIgnoreCase((question.toLowerCase())))
                .findAny()
                .orElse(null);
        String value = entity == null ? null : entity.getAnswer();
        return Optional.ofNullable(value);
    }

    @Override
    public void saveFaq(String question, String answer){
        int nextId = FAQEntityList.stream()
                .mapToInt(a -> Math.toIntExact(a.getId()))
                .max()
                .orElse(0) + 1;

        FAQEntity faqEntity = new FAQEntity((long)nextId,question,answer);
        FAQEntityList.add(faqEntity);

        File jsonFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(FILE_PATH)).getFile());
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, FAQEntityList);
        }catch (IOException e) {
            System.out.println("an error occurred while saving FAQ");
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<FAQEntity> fetchAllFaqs() {
        return FAQEntityList;
    }
}
