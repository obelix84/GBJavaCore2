package ru.gb.java2.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private Socket socket;
    private Server server;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
        this.server = server;

        new Thread(() -> {
            try {
                //цикл авторизации
                while (true) {
                    String msg = in.readUTF();
                    // /login Alex
                    if(msg.startsWith("/login ")) {
                        String usernameFromLogin = msg.split("\\s")[1];

                        if(server.isNickBusy(usernameFromLogin)) {
                            sendMessage("/login_failed Current nickname has already been occupied");
                            continue;
                        }

                        username = usernameFromLogin;
                        sendMessage("/login_ok " + username);
                        server.subscribe(this);
                        break;
                    }
                }

                //цикл общения
                while (true) {
                    String msg = in.readUTF();

                    if(msg.startsWith("/who_am_i")) {
                        this.sendMessage("Ваш логин "+ username + "\n");
                        continue;
                    }
                    if(msg.startsWith("/w ")) {
                        //вот тут ощибки могут быть, т.к. строка не обязатнльно состоит из 3х частей
                        String[] parts = msg.split("\\s", 3);
                        server.privateMessage(this, parts[2], parts[1]);
                        continue;
                    }
                    if(msg.startsWith("/exit")) {
                        //отправляем сообщение клиенту о выходе
                        //Вот тут не совсем хорошо... Надо на клиенте обрабоать сначала
                        //server.commandMessage(this,"/exit", this.username);
                        server.broadcastMessage(this.getUsername() + " вышел из чата...\n");
                        this.disconnect();
                        break;
                    }

                    server.broadcastMessage(username + ": " + msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }).start();
    }

    private void disconnect() {
        server.unsubscribe(this);
        if(in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        out.writeUTF(message);
    }

    public String getUsername() {
        return username;
    }
}
