
package by.bsuir.pokos.database.dao.entity;

import by.bsuir.pokos.database.dao.utilsDAO.CargoDAO;
import by.bsuir.pokos.database.dao.utilsDAO.CarrierDAO;
import by.bsuir.pokos.database.dao.utilsDAO.LoadingDAO;
import by.bsuir.pokos.database.dao.utilsDAO.OrderDAO;
import by.bsuir.pokos.database.dao.utilsDAO.StockDAO;
import by.bsuir.pokos.database.dao.utilsDAO.UnloadingDAO;
import by.bsuir.pokos.database.dao.utilsDAO.WorkerDAO;
import by.bsuir.pokos.message.Message;
import by.bsuir.pokos.utils.Actions;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Worker extends User implements Serializable{

    public Connection cn;
    
     public Worker(int id, String login) {
        super(id, login);
    }

    @Override
    public  void Work(ObjectInputStream inputStream,ObjectOutputStream outputStream,Connection cn) {
        
        Message message;
        this.cn=cn;
        
        try {
        while(true) {
            try {
                
                message = (Message) inputStream.readObject();
                Actions sw = message.getAction();
                
                switch(sw){
                    
                    case GetStock:
                        outputStream.writeObject(new Message((Stock)getStock(message.getMessage())));
                        break;
                    case GetStockByID:
                        outputStream.writeObject(new Message((Stock)getStockByID(Integer.parseInt(message.getMessage()))));
                        break;
                    case GetWorkerID:
                        outputStream.writeObject(new Message(String.valueOf(getWorkerID(message.getMessage()))));
                        break;
                    case AddCargo:
                        if(addCargo((Cargo)message.getStructure()))
                            outputStream.writeObject(new Message("Success"));
                        break;
                    case AddCarrier:
                        if(addCarrier((Carrier)message.getStructure()))
                            outputStream.writeObject(new Message("Success"));
                        break;
                    case AddLoading:
                        if(addLoading((Loading)message.getStructure()))
                            outputStream.writeObject(new Message("Success"));
                        break;
                    case AddUnloading:
                        if(addUnloading((Unloading)message.getStructure()))
                            outputStream.writeObject(new Message("Success"));
                        break;
                    case AddOrder:
                        System.out.println("Cервер получил команду к доавлению заказа на загрузку");
                        if(addOrder((Order)message.getStructure()))
                            outputStream.writeObject(new Message("Success"));
                        else 
                            outputStream.writeObject(new Message("Fail"));
                        break;
                    case GenerateNewOrderNumber:
                        outputStream.writeObject(new Message(generateNewOrderNumber()));
                        break;
                    case GetAllForwarders:
                        outputStream.writeObject(new Message(getAllForwarders()));
                        break;
                    case GetCargoID:
                        outputStream.writeObject(new Message(String.valueOf(getCargoID((Cargo)message.getStructure()))));
                        break;
                    case GetCarrierID:
                        outputStream.writeObject(new Message(String.valueOf(getCarrierID((Carrier)message.getStructure()))));
                        break;
                    case GetLoadingID:
                        outputStream.writeObject(new Message(String.valueOf(getLoadingID((Loading)message.getStructure()))));
                        break;
                     case GetUnloadingID:
                        outputStream.writeObject(new Message(String.valueOf(getUnloadingID((Unloading)message.getStructure()))));
                        break;  
                     case GetOrdersInRealization:
                         System.out.println("Сервер получил команду к выводы всех заказов в реализации");
                         outputStream.writeObject(new Message(getAllOrdersInRealization()));
                         break;
                     case GetCargoByID:
                         outputStream.writeObject(new Message(getCargoByID(Integer.parseInt(message.getMessage()))));
                         break;
                     case GetCarrierByID:
                         outputStream.writeObject(new Message(getCarrierByID(Integer.parseInt(message.getMessage()))));
                         break;
                     case GetLoadingByID:
                         outputStream.writeObject(new Message(getLoadingByID(Integer.parseInt(message.getMessage()))));
                         break;
                     case GetUnloadingByID:
                         outputStream.writeObject(new Message(getUnloadingByID(Integer.parseInt(message.getMessage()))));
                         break;
                     case GetWorkerNameByID:
                         outputStream.writeObject(new Message(getWorkerNameByID(Integer.parseInt(message.getMessage()))));
                         break;
                     case GetOrderByID:
                         outputStream.writeObject(new Message(getOrderByID(Integer.parseInt(message.getMessage()))));
                         break;
                     case GetWorkerPostByLogin:
                         outputStream.writeObject(new Message(getWorkerPostByLogin(message.getMessage())));
                         break;
                     case ChangtStatusOfOrder:
                         String[] mass = message.getMessage().split(" ",2);
                         if(changeStatusOfOrder(mass[0], mass[1]))
                            outputStream.writeObject(new Message("Success"));
                         else 
                             outputStream.writeObject(new Message("Fail"));
                         break;
                         
                }
                
            } 
            catch (ClassNotFoundException ex) {
                System.out.println("YOOOOOOOOOOO");
            } catch (IOException ex) {
                System.out.println("YOOOOOOOOOOO");
                return;
            } 
            
        }
         }finally {
                try {
                    cn.close();
                    System.out.println("db close");
                } catch (SQLException ex) {
                    Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        
    }
    
    public boolean changeStatusOfOrder(String newStatus,String orderdID) {
        OrderDAO dao = new OrderDAO(cn);
        return dao.changeStatusOfOrder(newStatus,orderdID);
    }
    
    public String  getWorkerPostByLogin(String workerLogin) {
        WorkerDAO dao = new WorkerDAO(cn);
        return dao.getWorkerPostByLogin(workerLogin);
    }
    
    public Order getOrderByID(int orderID) {
        OrderDAO dao = new OrderDAO(cn);
        return dao.getOrderByID(orderID);
    }
    public String getWorkerNameByID(int workerID) {
        WorkerDAO dao = new WorkerDAO(cn);
        return dao.getWorkerNameByID(workerID);
    }
    public Unloading getUnloadingByID(int unloadingID) {
        UnloadingDAO dao = new UnloadingDAO(cn);
        return dao.getUnloadingByID(unloadingID);
    }
    
    public Loading getLoadingByID(int loadingID) {
        LoadingDAO dao = new LoadingDAO(cn);
        return dao.getLoadingrByID(loadingID);
    }
    
    public Carrier getCarrierByID(int carrierID) {
        CarrierDAO dao = new CarrierDAO(cn);
        return dao.getCarrierByID(carrierID);
    }
    
    public Cargo getCargoByID(int cargoID) {
        CargoDAO dao = new CargoDAO(cn);
        return dao.getCargoByID(cargoID);
    }
    
    public ArrayList getAllOrdersInRealization() {
        OrderDAO dao = new OrderDAO(cn);
        return dao.getAllOrdersInRealization();
    }
    
    public int getLoadingID(Loading loading){
        LoadingDAO dao = new LoadingDAO(cn);
        return dao.getLoadingByID(loading);
    }
    
    public int getUnloadingID(Unloading unloading) {
        UnloadingDAO dao = new UnloadingDAO(cn);
        return dao.getUnloadingID(unloading);
    }
    
    public int getCargoID(Cargo cargo){
        CargoDAO dao = new CargoDAO(cn);
        return dao.getCargoID(cargo);
    }
    
    public int getCarrierID(Carrier carrier) {
        CarrierDAO dao = new CarrierDAO(cn);
        return dao.getCarrierID(carrier);
    }
    
    public ArrayList getAllForwarders() {
        WorkerDAO dao= new WorkerDAO(cn);
        return dao.getAllForwarders();
    }
    
    public  Stock getStock(String stockName) {
        StockDAO dao = new StockDAO(cn);
        return dao.getStockByName(stockName);
    }
    
      public  Stock getStockByID(int stockID) {
        StockDAO dao = new StockDAO(cn);
        return dao.getStockByID(stockID);
    }
    
    public int getWorkerID(String login) {
        WorkerDAO dao= new WorkerDAO(cn);
        int k=dao.getWorkerIDbyLogin(login);
        return k;
    }
    
    public boolean addCargo(Cargo cargo) {
        CargoDAO dao = new CargoDAO(cn);
        return dao.addCargo(cargo);
   } 
    
    public boolean addCarrier(Carrier carrier) {
        CarrierDAO dao= new CarrierDAO(cn);
        return dao.addCarrier(carrier);
    }
    
    public boolean addLoading(Loading loading) {
        LoadingDAO dao= new LoadingDAO(cn);
        return dao.addLoading(loading);
    }
    
    public boolean addUnloading(Unloading unloading) {
       UnloadingDAO dao= new UnloadingDAO(cn);
       return dao.addLoading(unloading);
    }
    
    public boolean addOrder(Order order) {
        OrderDAO dao= new OrderDAO(cn);
        return dao.addOrder(order);
    }
    
    public String generateNewOrderNumber() {
        
        java.sql.Date dateOfOrder = new java.sql.Date(Calendar.getInstance().getTime().getTime());//current Date
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        java.sql.Date rangeDate= new java.sql.Date(cal.getTimeInMillis());
        
        OrderDAO dao = new OrderDAO(cn);
        int countOfOrdersInCurrentMounth=dao.getCountOfOrdersBetweenDate(rangeDate, dateOfOrder);
        
        DecimalFormat nf2 = new DecimalFormat("#00");
        DecimalFormat nf3 = new DecimalFormat("#000");
        
        String numberOfOrder="P"+String.valueOf(nf2.format(cal.get((Calendar.MONTH))+1))+String.valueOf(nf3.format(countOfOrdersInCurrentMounth+1));
            
        return numberOfOrder;
        
    }
    
}
