package com.robobob.service.impl;

import com.robobob.service.FAQService;
import com.robobob.service.QuestionService;

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

//        return String.valueOf(attemptArithmeticViaAlgorithm(question));
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


    //this second engine can be plugged and works for all Java versions
    public static double attemptArithmeticViaAlgorithm(final String str) {
        try {
            return new Object() {
                int pos = -1, ch;

                void nextChar() {
                    ch = (++pos < str.length()) ? str.charAt(pos) : -1;
                }

                boolean eat(int charToEat) {
                    while (ch == ' ') nextChar();
                    if (ch == charToEat) {
                        nextChar();
                        return true;
                    }
                    return false;
                }

                double parse() {
                    nextChar();
                    double x = parseExpression();
                    if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                    return x;
                }

                double parseExpression() {
                    double x = parseTerm();
                    for (;;) {
                        if      (eat('+')) x += parseTerm(); // addition
                        else if (eat('-')) x -= parseTerm(); // subtraction
                        else return x;
                    }
                }

                double parseTerm() {
                    double x = parseFactor();
                    for (;;) {
                        if      (eat('*')) x *= parseFactor(); // multiplication
                        else if (eat('/')) x /= parseFactor(); // division
                        else return x;
                    }
                }

                double parseFactor() {
                    if (eat('+')) return parseFactor(); // unary plus
                    if (eat('-')) return -parseFactor(); // unary minus

                    double x;
                    int startPos = this.pos;
                    if (eat('(')) { // parentheses
                        x = parseExpression();
                        eat(')');
                    } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                        while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                        x = Double.parseDouble(str.substring(startPos, this.pos));
                    } else if (ch >= 'a' && ch <= 'z') { // functions
                        while (ch >= 'a' && ch <= 'z') nextChar();
                        String func = str.substring(startPos, this.pos);
                        x = parseFactor();
                        if (func.equals("sqrt")) x = Math.sqrt(x);
                        else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                        else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                        else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                        else throw new RuntimeException("Unknown function: " + func);
                    } else {
                        throw new RuntimeException("Unexpected: " + (char)ch);
                    }

                    if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                    return x;
                }
            }.parse();
        } catch (Exception e) {
            return -1;
        }
    }
}
