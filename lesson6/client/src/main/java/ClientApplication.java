import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 8290);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner sc = new Scanner(System.in);
            //вот тут будем читать сообщения
            Thread tRead = new Thread( () -> {
                while (true) {
                    String msg = null;
                    try {
                        msg = in.readUTF();
                        System.out.printf("Сообщение от сервера: %s \n", msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            //вот тут будем писать сообщения серверу
            Thread tWrite = new Thread( () -> {
                while (true) {
                    String msg = null;
                    try {
                        while (true) {
                            System.out.println("Отправить сообщение:");
                            msg = sc.nextLine();
                            out.writeUTF(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            tRead.start();
            tWrite.start();
        } catch (IOException e) {
            throw new RuntimeException("Не могу подключиться [localhost: 8290]");
        }

    }
}
