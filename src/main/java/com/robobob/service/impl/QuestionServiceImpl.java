package com.robobob.service.impl;

import com.robobob.service.QuestionService;
import service.FAQService;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Optional;

public class QuestionServiceImpl implements QuestionService {

    private final FAQService faqService = FAQServiceImpl.getInstance();

    private final ScriptEngineManager manager = new ScriptEngineManager();

    private final ScriptEngine engine = manager.getEngineByName("JavaScript");

    private QuestionServiceImpl() {

    }

    private static final QuestionServiceImpl INSTANCE = new QuestionServiceImpl();

    public static QuestionService getInstance() {
        return INSTANCE;
    }


    @Override
    public String handleUserRequest(String question) {

        Optional<String> faqAnswer = attemptFAQ(question);
        if (faqAnswer.isPresent()) {
            return faqAnswer.get();
        }
        Optional<String> arithmeticAnswer = attemptArithmetic(question);

        return arithmeticAnswer.orElse("Please provide a valid question");
    }

    public Optional<String> attemptFAQ(String question) {
        return faqService.getFaqByQuestion(question);
    }

    public Optional<String> attemptArithmetic(String question) {
        try {
            Object result = engine.eval(question);
            return Optional.of(String.valueOf(result));
        }catch (ScriptException e) {
            return Optional.empty();
        }
    }
}
