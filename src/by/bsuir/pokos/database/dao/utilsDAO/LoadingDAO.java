/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsuir.pokos.database.dao.utilsDAO;

import by.bsuir.pokos.database.Connector;
import by.bsuir.pokos.database.dao.entity.Loading;
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
public class LoadingDAO {
    
    private final Connection cn;
    private ResultSet rs;
    private PreparedStatement st;
    
    private static final String SQL_ADD_LOADING="INSERT into slls.loadings (id,loadingCompanyName,loadingAdress,loadingPostalCode,"
            + "loadingCity,loadingCountry,loadingDate,loadingTime) VALUES(?,?,?,?,?,?,?,?);";
    private static final String SQL_GET_LOADING_ID="SELECT id FROM slls.loadings WHERE loadingCompanyName=? AND loadingAdress=? AND loadingPostalCode=? AND loadingCity=?"
            + " AND loadingCountry=? AND loadingDate=? AND loadingTime=?;";
    private static final String SQL_GET_LOADING_BY_ID="SELECT * FROM slls.loadings WHERE id=?";

    public LoadingDAO(Connection cn) {
        this.cn = cn;
    }
    
    public boolean addLoading(Loading loading) {
        try {
            st = cn.prepareStatement(SQL_ADD_LOADING);
            
            st.setInt(1,loading.getID());
            st.setString(2,loading.getLoadingCompanyName());
            st.setString(3, loading.getLoadingAdress());
            st.setString(4,loading.getLoadingPostalCode());
            st.setString(5,loading.getLoadingCity());
            st.setString(6,loading.getLoadingCountry());
            st.setDate(7,loading.getLoadingDate());
            st.setTime(8, loading.getLoadingTime());
            
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
    
    public int getLoadingByID(Loading loading) {
            
        try {
            st = cn.prepareStatement(SQL_GET_LOADING_ID);
            
            st.setString(1,loading.getLoadingCompanyName());
            st.setString(2, loading.getLoadingAdress());
            st.setString(3,loading.getLoadingPostalCode());
            st.setString(4,loading.getLoadingCity());
            st.setString(5,loading.getLoadingCountry());
            st.setDate(6,loading.getLoadingDate());
            st.setTime(7, loading.getLoadingTime());
            
            rs = st.executeQuery();
            if(rs.next()) {
                int loadingID=rs.getInt(1);
                return loadingID;
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
    
    public Loading getLoadingrByID(int loadingID) {
        try {
            st = cn.prepareStatement(SQL_GET_LOADING_BY_ID);
            st.setInt(1, loadingID);
            
            rs = st.executeQuery();
            if(rs.next()) {
                return new Loading(rs.getInt(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   rs.getDate(7),
                                   rs.getTime(8));
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
