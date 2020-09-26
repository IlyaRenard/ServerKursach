package by.bsuir.pokos.Server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;
public class Server {

    public static void main(String[] args) {
        try {
            //Создаем слушатель
            
            String IP=args[0];
            int PORT=Integer.parseInt(args[1]);
            
            InetAddress address = InetAddress.getByName(IP);
            ServerSocket socketListener = new ServerSocket(PORT,100,address);
            System.out.println("("+ Calendar.getInstance().getTime()+")"+"Сервер запущен: IP-адрес ="+ address.getHostAddress()+", PORT="+PORT);

            while (true) {
                Socket client = null;
                while (client == null) {
                    client = socketListener.accept();
                }
                ClientThread clientThread = new ClientThread(client); //Создаем новый поток, которому передаем сокет
                System.out.println("\n[Клиент подключен]: Время"+"("+ Calendar.getInstance().getTime()+")");
            }
        } catch (SocketException e) {
            System.err.println("Socket exception");
        } catch (IOException e) {
            System.err.println("I/O exception");
        }
        System.out.println("("+ Calendar.getInstance().getTime()+")"+"Сервер остановлен");

    }

}


