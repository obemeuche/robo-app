package com.robobob.controller;

import com.robobob.service.QuestionService;
import com.robobob.service.impl.QuestionServiceImpl;
import lombok.Getter;
import ui.MenuUi;

import java.util.Scanner;

public class ReplController {

    private final Scanner scanner;
    private final QuestionService questionService;

    private ReplController(){
        scanner = new Scanner(System.in);
        questionService = QuestionServiceImpl.getInstance();
    }

    @Getter
    private static final ReplController instance = new ReplController();

    public void startRoboBobApp(){

        MenuUi.welcomeMenu();

        System.out.print(MenuUi.userCursor());
        String userRequest = scanner.nextLine();
        while ( !"--quit".equals(userRequest)){

            System.out.print(MenuUi.appCursor());
            System.out.println(questionService.handleUserRequest(userRequest));

            System.out.print(MenuUi.userCursor());
            userRequest = scanner.nextLine();
        }

        System.out.println(MenuUi.appCursor()+" Thank you for spending time with me!!!");

    }




}
