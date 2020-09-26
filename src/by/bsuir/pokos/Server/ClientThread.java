package by.bsuir.pokos.Server;

import by.bsuir.pokos.database.Connector;
import by.bsuir.pokos.database.dao.entity.Admin;
import by.bsuir.pokos.database.dao.entity.Worker;
import by.bsuir.pokos.database.dao.entity.User;
import by.bsuir.pokos.database.dao.utilsDAO.WorkerDAO;
import by.bsuir.pokos.message.Message;
import by.bsuir.pokos.message.SignInDATA;
import by.bsuir.pokos.utils.Actions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientThread extends Thread {

    private String login=null;
    private Socket socket;
    private Message message;
    public ObjectOutputStream outputStream;
    public ObjectInputStream inputStream;
    public Connection cn;

    public ClientThread(Socket socket) {
        this.socket = socket;
        this.start();
    }

    @Override
    public void run() {
        try {

            //потоки ввода, вывода для работы с сокетом
            inputStream = new ObjectInputStream(this.socket.getInputStream());
            outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            cn = Connector.getConnection();
            int roleID;
                
            while (true) {
                this.message = (Message) inputStream.readObject();
                SignInDATA data=(SignInDATA)this.message.getStructure();
                //авторизация
                roleID=SignIn(data);
                    
                if(roleID!=-1) {
                    outputStream.writeObject(new Message(String.valueOf(roleID)));
                    if(login==null) {
                        login=data.getLogin();
                        System.out.println("Логин:"+login);
                    } 
                    break;
                }
                else 
                    outputStream.writeObject(new Message("-1"));
                }
                
                //выбор пользователя
                User user=null;
                switch(roleID) {
                    case 1: user= new Admin(roleID,login);break;
                    case 3: case 2: user= new Worker(roleID, login);break;
                }
                user.Work(inputStream,outputStream,cn);
                
                inputStream.close();
                outputStream.close();
                
        } catch (SocketException e) {
            if(login==null)
                System.out.println("Неавторизованный пользователь отключился от сервера!!!");
            else
                System.out.println(login + " отключисля от сервера!"); 
        }   
          catch (IOException e) {
              System.out.println("YOOOOOOOOO");
          } catch (ClassNotFoundException ex) {
            System.out.println("выход");
        }finally{
            try {
                cn.close();
                System.out.println("db close");
            } catch (SQLException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Время отключения:"+Calendar.getInstance().getTime());
    }
    
    public int SignIn(SignInDATA data) {
        int roleID;
        WorkerDAO dao=new WorkerDAO(cn);
        roleID=dao.isWorkerWasRegistered(data.getLogin(),data.getPassword());
        return roleID;
    }
    
}
        
 



