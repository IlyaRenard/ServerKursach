/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsuir.pokos.database.dao.utilsDAO;

import by.bsuir.pokos.database.Connector;
import by.bsuir.pokos.database.dao.entity.Loading;
import by.bsuir.pokos.database.dao.entity.Unloading;
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
public class UnloadingDAO {
    
    private final  Connection cn;
    private ResultSet rs;
    private PreparedStatement st;
    
    
    private static final String SQL_ADD_UNLOADING="INSERT into slls.unloadings (id,unloadingStockID,"
            + "unloadingClient,unloadingCity,unloadingCountry,unloadingDate,unloadingTime) VALUES(?,?,?,?,?,?,?);";
    
    private static final String SQL_GET_UNLOADING_ID="SELECT id FROM slls.unloadings WHERE unloadingStockID=? AND unloadingClient=? AND unloadingCity=? AND unloadingCountry=?"
            + " AND unloadingDate=? AND unloadingTime=?";
    private static final String SQL_GET_UNLOADING_BY_ID="SELECT * FROM slls.unloadings WHERE id=?";

    public UnloadingDAO(Connection cn) {
        this.cn = cn;
    }
    
    public boolean addLoading(Unloading unloading) {
        try {
            st = cn.prepareStatement(SQL_ADD_UNLOADING);
            
            st.setInt(1,unloading.getID());
            st.setInt(2,unloading.getUnloadingStockID());
            st.setString(3, unloading.getUnloadingClient());
            st.setString(4,unloading.getUnloadingCity());
            st.setString(5,unloading.getUnloadingCountry());
            st.setDate(6,unloading.getUnloadingDate());
            st.setTime(7,unloading.getUnloadingTime());
            
            st.executeUpdate();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public int getUnloadingID(Unloading unloading) {
            
        try {
            st = cn.prepareStatement(SQL_GET_UNLOADING_ID);
            
            st.setInt(1,unloading.getUnloadingStockID());
            st.setString(2, unloading.getUnloadingClient());
            st.setString(3,unloading.getUnloadingCity());
            st.setString(4,unloading.getUnloadingCountry());
            st.setDate(5,unloading.getUnloadingDate());
            st.setTime(6,unloading.getUnloadingTime());
            
            rs = st.executeQuery();
            if(rs.next()) {
                int unloadingID=rs.getInt(1);
                return unloadingID;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return -1;    
    }
    
    public Unloading getUnloadingByID(int unloadingID) {
        try {
            st = cn.prepareStatement(SQL_GET_UNLOADING_BY_ID);
            st.setInt(1, unloadingID);
            
            rs = st.executeQuery();
            if(rs.next()) {
                return new Unloading(rs.getInt(1),
                                   rs.getInt(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getDate(6),
                                   rs.getTime(7));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CargoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
