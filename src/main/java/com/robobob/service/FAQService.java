package service;


import com.robobob.entity.FAQEntity;

import java.util.List;
import java.util.Optional;

public interface FAQService {

    Optional<String> getFaqById(Long id);

    Optional<String> getFaqByQuestion(String question);

    void saveFaq(String question, String answer);

    List<FAQEntity> fetchAllFaqs();
}
