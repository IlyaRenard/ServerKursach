/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsuir.pokos.database.dao.utilsDAO;

import by.bsuir.pokos.database.Connector;
import by.bsuir.pokos.database.dao.entity.Stock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gavrilik
 */
public class StockDAO {
    
    public static final String SQL_GET_STOCK_BY_NAME="SELECT * FROM slls.stocks WHERE stockName=?;";
    public static final String SQL_GET_STOCK_BY_ID="SELECT * FROM slls.stocks WHERE id=?";
    
    private final  Connection cn;      
    private ResultSet rs;
    private PreparedStatement st;
    
    public StockDAO(Connection cn) {
        this.cn = cn;
    }
    
    public Stock getStockByName(String stockName) {
        try {
            st = cn.prepareStatement(SQL_GET_STOCK_BY_NAME);
            st.setString(1, stockName);
            rs=st.executeQuery();
            if(rs.next()) {
                
                 Stock stock=new Stock(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                 return stock;
            }
            
       }catch (SQLException e) {
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
    
    public Stock getStockByID(int stockID) {
        try {
            st = cn.prepareStatement(SQL_GET_STOCK_BY_ID);
            st.setInt(1, stockID);
            rs=st.executeQuery();
            if(rs.next()) {
                
                 Stock stock=new Stock(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6));
                 return stock;
            }
            
       }catch (SQLException e) {
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
}
