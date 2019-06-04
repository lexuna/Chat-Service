package de.lexuna.school.chatservice;

import java.io.IOException;

import de.lexuna.school.chatservice.service.ChatService;

public class Main {

    public static void main(String[] args) {
        try {
            ChatService.startService(Integer.parseInt(args[0]));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
