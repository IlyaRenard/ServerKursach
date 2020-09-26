/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.bsuir.pokos.database.dao.utilsDAO;

import by.bsuir.pokos.database.Connector;
import by.bsuir.pokos.database.dao.entity.Cargo;
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
public class CargoDAO {
    
    private final Connection cn;
    private static final String SQL_ADD_CARGO="INSERT into slls.cargos (id,cargoDescription,cargoWeight,cargoCount,cargoDocument) VALUES(?,?,?,?,?);";
    private static final String SQL_GET_CARGO_ID="SELECT cargos.id FROM slls.cargos WHERE cargoDescription=? AND cargoWeight=? AND cargoCount=? AND cargoDocument=?";
    private static final String SQL_GET_CARGO_BY_ID="SELECT * FROM slls.cargos WHERE id=?";
    
    private ResultSet rs;
    private PreparedStatement st;
    
    public CargoDAO(Connection cn) {
        this.cn=cn;
    }
    
    public boolean addCargo(Cargo cargo) {
        try {
            st = cn.prepareStatement(SQL_ADD_CARGO);
            st.setInt(1,cargo.getID());
            st.setString(2, cargo.getCargoDescription());
            st.setInt(3, cargo.getCargoWeight());
            st.setInt(4,cargo.getCargoCount());
            st.setString(5, cargo.getCargoDocument());
            
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
    
    public int getCargoID(Cargo cargo) {
            
        try {
            st = cn.prepareStatement(SQL_GET_CARGO_ID);
            
            st.setString(1, cargo.getCargoDescription());
            st.setInt(2, cargo.getCargoWeight());
            st.setInt(3,cargo.getCargoCount());
            st.setString(4, cargo.getCargoDocument());
            
            rs = st.executeQuery();
            if(rs.next()) {
                int cargoID=rs.getInt(1);
                return cargoID;
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
    
    public Cargo getCargoByID(int cargoID) {
        try {
            st = cn.prepareStatement(SQL_GET_CARGO_BY_ID);
            st.setInt(1, cargoID);
            
            rs = st.executeQuery();
            if(rs.next()) {
                return new Cargo(rs.getInt(1),
                                rs.getString(2),
                                rs.getInt(3),
                                rs.getInt(4),
                                rs.getString(5));
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
