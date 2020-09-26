/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsuir.pokos.database.dao.utilsDAO;

import by.bsuir.pokos.database.Connector;
import by.bsuir.pokos.database.dao.entity.Cargo;
import by.bsuir.pokos.database.dao.entity.Carrier;
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
public class CarrierDAO {
    
    private final Connection cn;
     private ResultSet rs;
    private PreparedStatement st;
    
    private static final String SQL_ADD_CARRIER="INSERT into slls.carriers (id,carrierCompanyName,"
            + "carrierContact,carrierTelephone,carrierElMail,carrierForeignRegNumber,carrierDriverTelephoneNumber) VALUES(?,?,?,?,?,?,?);";
    private static final String SQL_GET_CARRIER_ID="SELECT carriers.id from slls.carriers WHERE carrierCompanyName=? AND carrierContact=? AND carrierTelephone=? AND carrierElMail=?"
            + " AND carrierForeignRegNumber=? AND carrierDriverTelephoneNumber=?";
    private static final String SQL_GET_CARRIER_BY_ID="SELECT * FROM slls.carriers WHERE id=?";

    public CarrierDAO(Connection cn) {
        this.cn = cn;
    }
    
    public boolean addCarrier(Carrier carrier) {
        try {
            st = cn.prepareStatement(SQL_ADD_CARRIER);
            
            st.setInt(1,carrier.getID());
            st.setString(2,carrier.getCarrierCompanyName());
            st.setString(3, carrier.getCarrierContact());
            st.setString(4,carrier.getCarrierTelephone());
            st.setString(5, carrier.getCarrierElMail());
            st.setString(6, carrier.getCarrierForeignRegNumber());
            st.setString(7, carrier.getCarrierDriverTelephoneNumber());
            
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
    
    public int getCarrierID(Carrier carrier) {
            
        try {
            st = cn.prepareStatement(SQL_GET_CARRIER_ID);
            
            st.setString(1, carrier.getCarrierCompanyName());
            st.setString(2, carrier.getCarrierContact());
            st.setString(3,carrier.getCarrierTelephone());
            st.setString(4, carrier.getCarrierElMail());
            st.setString(5, carrier.getCarrierForeignRegNumber());
            st.setString(6, carrier.getCarrierDriverTelephoneNumber());
            
            rs = st.executeQuery();
            if(rs.next()) {
                int carrierID=rs.getInt(1);
                return carrierID;
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
    
    public Carrier getCarrierByID(int carrierID) {
        try {
            st = cn.prepareStatement(SQL_GET_CARRIER_BY_ID);
            st.setInt(1, carrierID);
            
            rs = st.executeQuery();
            if(rs.next()) {
                return new Carrier(rs.getInt(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   rs.getString(7));
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
