package by.bsuir.pokos.database.dao.entity;

import by.bsuir.pokos.database.dao.utilsDAO.WorkerDAO;
import by.bsuir.pokos.message.Message;
import by.bsuir.pokos.message.ShowAllWorkersDATA;
import by.bsuir.pokos.utils.Actions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import by.bsuir.pokos.database.dao.entity.User;

public class Admin extends User implements Serializable{
    
    public Connection cn;
    
    public Admin(int postID, String login) {
        super(postID, login);
    }
    
    
    @Override
    public void Work(ObjectInputStream inputStream,ObjectOutputStream outputStream,Connection cn) {
        
        Message message;
        this.cn=cn;
        
        try {
        try {
            
            while(true) {
                message = (Message) inputStream.readObject();
                Actions sw = message.getAction();
                
                switch(sw) {
                    case GetAllWorkers:
                        outputStream.writeObject(new Message(getAllWorkers()));
                        break;
                    case GetWorkerPostByLogin:
                         outputStream.writeObject(new Message(getWorkerPostByLogin(message.getMessage())));
                         break;    
                     case GetWorkerID:
                        outputStream.writeObject(new Message(String.valueOf(getWorkerID(message.getMessage()))));
                        break;
                     case AddWorker:
                         if(addWorker((by.bsuir.pokos.message.User)message.getStructure()))
                            outputStream.writeObject(new Message("Success"));
                         else
                             outputStream.writeObject(new Message("Fail"));
                         break;
                     case ChangeWorker:
                         if(changeWorker((by.bsuir.pokos.message.User)message.getStructure()))
                            outputStream.writeObject(new Message("Success"));
                         else
                             outputStream.writeObject(new Message("Fail"));
                         break;
                     case DeleteWorker:
                          if(deleteWorker(Integer.parseInt(message.getMessage())))
                            outputStream.writeObject(new Message("Success"));
                         else
                             outputStream.writeObject(new Message("Fail"));
                         break;
                         
                }
                
            }
            
        } catch (ClassNotFoundException ex) {System.out.println("YOOOOOOOOOOO");}
          catch (IOException ex) {  System.out.println("YOOOOOOOOOOO");  return;} 
        } finally {
                try {
                    cn.close();
                    System.out.println("db close");
                } catch (SQLException ex) {
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
    }
    
    public boolean deleteWorker(int workerID) {
        WorkerDAO dao = new WorkerDAO(cn);
        return dao.deleteWorker(workerID);
    }
    
    public boolean changeWorker(by.bsuir.pokos.message.User user) {
        WorkerDAO dao = new WorkerDAO(cn);
        return dao.changeWorker(user);
    }
    
    public boolean addWorker(by.bsuir.pokos.message.User user) {
        WorkerDAO dao = new WorkerDAO(cn);
        return dao.addWorker(user);
    }
    
    public ShowAllWorkersDATA getAllWorkers() {
        WorkerDAO  dao = new WorkerDAO(cn);
        return dao.getAllWorkers();
    }
    
    public String  getWorkerPostByLogin(String workerLogin) {
        WorkerDAO dao = new WorkerDAO(cn);
        return dao.getWorkerPostByLogin(workerLogin);
    }
    
    public int getWorkerID(String login) {
        WorkerDAO dao= new WorkerDAO(cn);
        int k=dao.getWorkerIDbyLogin(login);
        return k;
    }
    
}
