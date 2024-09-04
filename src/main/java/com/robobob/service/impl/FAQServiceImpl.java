package com.robobob.service.impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.robobob.entity.FAQEntity;
import com.robobob.service.FAQService;


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

    //creates an instance of the class
    private FAQServiceImpl() {
        // reads from the file path
        InputStream inputStream = FAQServiceImpl.class.getClassLoader().getResourceAsStream(FILE_PATH);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found!");
        }
        try {
            // maps the input into the entity class
            FAQEntityList = objectMapper.readValue(inputStream, new TypeReference<List<FAQEntity>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // fetches answers by question in the json file
    @Override
    public Optional<String> getFaqByQuestion(String question) {
        FAQEntity entity = FAQEntityList.stream()
                .filter(FAQEntity -> FAQEntity.getQuestion().equalsIgnoreCase((question.toLowerCase())))
                .findAny()
                .orElse(null);
        String value = entity == null ? null : entity.getAnswer();
        return Optional.ofNullable(value);
    }

    // saves a new faq and answer in the json file
    @Override
    public void saveFaq(String question, String answer){
        // get the new ID of the json file
        int nextId = FAQEntityList.stream()
                .mapToInt(a -> Math.toIntExact(a.getId()))
                .max()
                .orElse(0) + 1;

        FAQEntity faqEntity = new FAQEntity((long)nextId,question,answer);
        FAQEntityList.add(faqEntity);

        // writes into the json file
        File jsonFile = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(FILE_PATH)).getFile());
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, FAQEntityList);
        }catch (IOException e) {
            System.out.println("an error occurred while saving FAQ");
            throw new RuntimeException(e);
        }

    }

}
