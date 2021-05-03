import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerApplication {

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(8290)) {

            System.out.println("Сервер на порту 8290. Ждем клиента...");
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Клиент подключился");
            Scanner sc = new Scanner(System.in);

            //вот тут будем читать сообщения
            Thread tRead = new Thread( () -> {
                while (true) {
                    String msg = null;
                    try {
                        msg = in.readUTF();
                        System.out.printf("Сообщение от клиента: %s \n", msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            //вот тут будем писать сообщения клиенту
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
            e.printStackTrace();
        }
    }
}
