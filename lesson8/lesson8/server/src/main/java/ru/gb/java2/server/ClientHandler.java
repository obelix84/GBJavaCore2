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
                    if(msg.startsWith("/")) {
                        if (executeCommand(msg)) break;
                    }
                }

                //цикл общения
                while (true) {
                    String msg = in.readUTF();
                    if(msg.startsWith("/")) {
                        if (executeCommand(msg)) break;
                        continue;
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
    //возвращает true когда необходимо сделать break из цикла, например при /exit
    //false означает продолжаем цикл сообщений, true прерываем
    private boolean executeCommand(String cmd) throws IOException {

        if(cmd.startsWith("/exit")) {
            // выходим из цикла
            return true;
        }
        if(cmd.startsWith("/login ")) {
            String usernameFromLogin = cmd.split("\\s")[1];

            if(server.isNickBusy(usernameFromLogin)) {
                sendMessage("/login_failed Current nickname has already been occupied");
                return false;
            }

            username = usernameFromLogin;
            sendMessage("/login_ok " + username);
            server.subscribe(this);
            return true;
        }
        if(cmd.startsWith("/who_am_i")) {
            this.sendMessage("Ваш логин "+ username + "\n");
            return false;
        }
        if(cmd.startsWith("/w ")) {
            //вот тут ощибки могут быть, т.к. строка не обязатнльно состоит из 3х частей
            String[] parts = cmd.split("\\s", 3);
            server.privateMessage(this, parts[2], parts[1]);
            return false;
        }
        return false;
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

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            System.out.println("ТУтттта");
            disconnect();
        }
    }

    public String getUsername() {
        return username;
    }
}
