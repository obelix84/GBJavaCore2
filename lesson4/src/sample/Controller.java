package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    TextArea chatText;

    @FXML
    TextField messageField;

    @FXML
    Button btnSend;


    public void messageKeyReleased(KeyEvent KeyEvent) {
        String message = messageField.getText();
        if (message != "") {
            btnSend.setDisable(false);
        } else {
            btnSend.setDisable(true);
        }
    }

    public void messageEnterTap(ActionEvent actionEvent) {
        String message = messageField.getText();
        if (message != "") {
            chatText.appendText("Я: " + message + "\n");
        }
        messageField.clear();
    }

    public void btnSendMouseClick(MouseEvent mouseEvent) {
        String message = messageField.getText();
        //по идее кнопка не должна работать при пустом сообщении
        //но оставим на всякий
        if (message != "") {
            chatText.appendText("Я: " + message + "\n");
        }
        messageField.clear();
        btnSend.setDisable(true);

    }

}
