package ru.gb.java2.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    TextField msgField, usernameField;

    @FXML
    TextArea msgArea;

    @FXML
    HBox loginPanel, msgPanel;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String username;
    private Thread dataThread;
    private boolean listening = true;

    public void sendMsg(ActionEvent actionEvent) {
        String msg = msgField.getText() + '\n';
        //если /exit, сначала отправим серверу, а внизу сделаем дисконнект
        try {
            out.writeUTF(msg);
            msgField.clear();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Невозможно отправить сообщение", ButtonType.OK);
            alert.showAndWait();
        }
        //а вот тут делаем дисконнект
        if(msg.startsWith("/exit")) {

            listening = false;
            this.disconnect();
        }
    }

    public void login(ActionEvent actionEvent) {
        if(socket == null || socket.isClosed()) {
            connect();
        }

        if(usernameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Имя пользователя не может быть пустым",
                    ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            out.writeUTF("/login " + usernameField.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        this.username = username;
        if(username != null) {
            loginPanel.setVisible(false);
            loginPanel.setManaged(false);
            msgPanel.setVisible(true);
            msgPanel.setManaged(true);
        } else {
            loginPanel.setVisible(true);
            loginPanel.setManaged(true);
            msgPanel.setVisible(false);
            msgPanel.setManaged(false);
            //очищаем поле сообщений
            msgArea.clear();

        }
    }

    public void connect() {
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            this.dataThread = new Thread(() -> {
                try {
                    //цикл авторизации
                    while (true) {
                        String msg = in.readUTF();

                        //login_ok Alex
                        if(msg.startsWith("/login_ok ")) {
                            setUsername(msg.split("\\s")[1]);
                            listening = true;
                            break;
                        }

                        if(msg.startsWith("/login_failed ")) {
                            String cause = msg.split("\\s", 2)[1];
                            msgArea.appendText(cause + '\n');
                        }

                    }

                    //цикл общения
                    //добавим ВЫХОД
                    //вот тут  надо убрать работу цикла по /exit
                    while (listening) {
                        String msg = in.readUTF();
                        msgArea.appendText(msg);
                    }
                } catch (IOException e) {
                    //Я не понял как избавиться от этого эксепшена при закрытии сокета по /exit
                    //Тогда тупо обработаем и все... Если ошибка не по exit, то тогда просто проигнорируем
                    if (listening) {
                        e.printStackTrace();
                    }
                } finally {
                    disconnect();
                }
            });
            dataThread.start();

        } catch (IOException e) {
            throw new RuntimeException("Unable to connect to server [localhost: 8189]");
        }
    }

    public void disconnect() {
        setUsername(null);
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
