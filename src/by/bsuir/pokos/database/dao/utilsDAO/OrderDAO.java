package by.bsuir.pokos.database.dao.utilsDAO;

import by.bsuir.pokos.database.Connector;
import by.bsuir.pokos.database.dao.entity.Order;
import by.bsuir.pokos.database.dao.entity.Payment;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO {
    
    private final  Connection cn;            
    private ResultSet rs;
    private PreparedStatement st;
    
    private static final String SQL_GET_ALL="SELECT * FROM slls.orders";
    private static final String SQL_ADD_ORDER="INSERT into slls.orders (id,dateOfOrder,numberOfOrder,orderStatus,orderBYforwarderID,orderPLforwarderID,"
            + "carrierID,loadingID,unloadingID,cargoID,freightCost,paymentPeriod) VALUES (?,?,?,?,?,?,?,?,?,?,?,?);";
    private static final String SQL_GET_COUNT_ORDERS_BETWEEN="SELECT COUNT(id) FROM slls.orders WHERE dateOfOrder BETWEEN ? and ?;";
    private static final String SQL_GET_ALL_ORDERS_IN_REALIZATION="SELECT * FROM slls.orders WHERE orderStatus IN (?,?,?); ";
    private static final String SQL_GET_ORDER_BY_ID="SELECT * FROM slls.orders WHERE orders.id=?;";
    private static final String SQL_CHANGE_STATUS_OF_ORDER="UPDATE slls.orders SET orderStatus=? WHERE id=?";

    public OrderDAO(Connection cn) {
        this.cn = cn;
    }
    
    public boolean addOrder(Order order) {
       try {
            
            st = cn.prepareStatement(SQL_ADD_ORDER);
            st.setInt(1,order.getID());
            st.setDate(2,order.getDateOfOrder());
            st.setString(3,order.getNumberOfOrder());
            st.setString(4,order.getOrderStatus());
            st.setInt(5,order.getOrderBYforwarderID());
            st.setInt(6,order.getOrderPLforwarderID());
            st.setInt(7,order.getCarrierID());
            st.setInt(8,order.getLoadingID());
            st.setInt(9,order.getUnloadingID());
            st.setInt(10,order.getCargoID());
            st.setFloat(11,order.getPayment().getFreightCost());
            st.setString(12,order.getPayment().getPaymentPeriod());
            
            st.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getCountOfOrdersBetweenDate(Date rangeDate,Date currentDateOfOrder) {
        try {
             st = cn.prepareStatement(SQL_GET_COUNT_ORDERS_BETWEEN);
             st.setDate(1, rangeDate);
             st.setDate(2, currentDateOfOrder);
             
            rs=st.executeQuery();
            if(rs.next()) {
                int k=rs.getInt(1);
                return k;
            }
             
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    
    public ArrayList getAllOrdersInRealization() {
        
         ArrayList<Order> result = new ArrayList<>();
        
        try {
            st = cn.prepareStatement(SQL_GET_ALL_ORDERS_IN_REALIZATION);
            st.setString(1, "IsInImplementation");
            st.setString(2, "IsInStock");
            st.setString(3, "IsLoaded");
            rs = st.executeQuery();
            
            while (rs.next()) {
                result.add(new Order(rs.getInt(1),
                        rs.getDate(2),
                        rs.getString(3),
                        rs.getString(4),
                         rs.getInt(5),
                         rs.getInt(6),
                         rs.getInt(7),
                         rs.getInt(8),
                         rs.getInt(9),
                         rs.getInt(10),
                         new Payment(rs.getFloat(11),rs.getString(11))   
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }
    
    public Order getOrderByID(int orderID) {
         try {
            
            st = cn.prepareStatement(SQL_GET_ORDER_BY_ID);
            st.setInt(1,orderID);
            rs=st.executeQuery();
            
            if(rs.next()) {
                return new Order(rs.getInt(1),rs.getDate(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7),
                        rs.getInt(8),rs.getInt(9),rs.getInt(10),new Payment(rs.getFloat(11),rs.getString(12)));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         return null;
    }
    
    public boolean changeStatusOfOrder(String newStatus,String orderID)
    {
        try {
            st = cn.prepareStatement(SQL_CHANGE_STATUS_OF_ORDER);
            st.setString(1,newStatus);
            st.setInt(2,Integer.parseInt(orderID));
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
   
}
