package ru.gb.java2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port;
    private List<ClientHandler> clients;

    public Server(int port) {
        this.port = port;
        this.clients = new ArrayList<>();
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен на порту " + port + "...");
            while (true) {
                System.out.println("Ждём нового клиента...");
                Socket socket = serverSocket.accept();
                System.out.println("Клиент подключился");
                new ClientHandler(socket, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public void broadcastMessage(String message) throws IOException {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(message);
        }
    }

    public void commandMessage(ClientHandler sender, String message, String nick) throws IOException {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(nick)) {
                client.sendMessage(message);
                return;
            }
        }
        sender.sendMessage("Такого пользователя нет!");
    }

    public void privateMessage(ClientHandler sender, String message, String nick) throws IOException {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(nick)) {
                client.sendMessage("Личное сообщение от " + sender.getUsername() + ": " + message);
                return;
            }
        }
        sender.sendMessage("Такого пользователя нет!\n");
    }

    public boolean isNickBusy(String nickname) {
        for (ClientHandler clientHandler : clients) {
            if(clientHandler.getUsername().equals(nickname)) {
                return true;
            }
        }
        return false;
    }
}
