package de.lexuna.school.chatservice.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import de.lexuna.school.chat.io.StreamDeserializer;

public class ChatService {

    public static final Map<String, ClientConnection> CONNECTION_MAP = new HashMap<>();
    public static final Map<String, byte[]> KEY_MAP = new HashMap<>();

    public static void startService(int port) throws IOException {
        StreamDeserializer deSer = null;
        try (ServerSocket listener = new ServerSocket(port)) {
            System.out.println("Startet chat-service: " + port);
            while (true) {
                Socket socket = listener.accept();
                System.out.println("Accept connection: " + socket.getInetAddress().toString()+":" + socket.getPort());
                new ClientConnection(socket).start();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (deSer != null) {
                deSer.close();
            }
        }
    }
}
