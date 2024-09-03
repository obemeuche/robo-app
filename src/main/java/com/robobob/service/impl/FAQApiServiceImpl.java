package com.robobob.service.impl;

import com.robobob.entity.FAQEntity;
import com.robobob.exception.DatabaseException;
import com.robobob.repository.FAQRepository;
import com.robobob.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FAQApiServiceImpl implements FAQService {

    private final FAQRepository faqRepository;

    @Override
    public Optional<String> getFaqByQuestion(String question) {
        return Optional.empty();
    }

    @Override
    public void saveFaq(String question, String answer) {
        FAQEntity faq = new FAQEntity();
        faq.setQuestion(question);
        faq.setAnswer(answer);
        try{
            faqRepository.save(faq);
        }catch (Exception e){
            throw new DatabaseException("UNABLE TO SAVE FAQ TO THE DATABASE");
        }
    }
}
