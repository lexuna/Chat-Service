package de.lexuna.school.chatservice.service;

import java.io.IOException;
import java.net.Socket;

import de.lexuna.school.chat.dto.Login;
import de.lexuna.school.chat.dto.Message;
import de.lexuna.school.chat.io.StreamDeserializer;
import de.lexuna.school.chat.io.StreamSerealizer;

public class ClientConnection extends Thread {

    private Socket socket;
    private StreamSerealizer serializer;
    private StreamDeserializer deserializer;

    public ClientConnection(Socket clientSocket) {
        this.socket = clientSocket;
        try {
            serializer = new StreamSerealizer(socket.getOutputStream());
            deserializer = new StreamDeserializer(socket.getInputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                getMessage(deserializer.read());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            }
        }

    }

    private void getMessage(Object obj) {
        if (obj instanceof Message) {
            Message message = (Message) obj;
            System.out.println("Get message from: " + message.getSenderId() + " to: " + message.getReceverId());
            try {
                sendMessage(message);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else if (obj instanceof Login) {
            System.out.println("Get new login");
            Login login = (Login) obj;
            login.getUserId();
            ChatService.CONNECTION_MAP.computeIfAbsent(login.getUserId(), key -> this);
        }
    }

    private void sendMessage(Message message) throws IOException {
        ClientConnection connection = ChatService.CONNECTION_MAP.get(message.getReceverId());
        if (connection == null) {
            return;
        }
        connection.serializer.send(message);
    }
}
