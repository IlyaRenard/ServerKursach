package by.bsuir.pokos.database.dao.utilsDAO;

import by.bsuir.pokos.database.Connector;
import by.bsuir.pokos.message.ShowAllWorkersDATA;
import by.bsuir.pokos.message.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerDAO {
   
    private Connection cn;
    private ResultSet rs;
    private PreparedStatement st;
    private static final String SQL_IS_WORKER_ALREADY_EXIST="SELECT postID FROM slls.users WHERE login=? and password=?";
    private static final String SQL_GET_WORKER_ID_BY_LOGIN="SELECT id FROM slls.users WHERE login=?";
    private static final String SQL_GET_ALL_FORWARDERS_LOGINS="SELECT users.login FROM users WHERE postID=3;";
    private static final String SQL_GET_WORKER_NAME_BY_ID="SELECT users.name FROM slls.users WHERE id=?;";
    private static final String SQL_GET_WORKER_POST_BY_LOGIN="SELECT roles.post FROM users INNER JOIN roles ON users.postID=roles.id WHERE users.login=?";
    private static final String SQL_GET_ALL_WORKERS="SELECT * FROM slls.users";
    private static final String SQL_ADD_WORKER="INSERT INTO slls.users (id,postID,login,password,name) VALUES (?,?,?,?,?);";
    private static final String SQL_CHANGE_WORKER="UPDATE slls.users SET id=?,postID=?,login=?,password=?,name=? WHERE id=?;";
    private static final String SQL_DELETE_WORKER="DELETE FROM slls.users WHERE id=?";
    
    public WorkerDAO(Connection cn) {
        this.cn=cn;
    }
    
    public boolean deleteWorker(int workerID) {
        try {
            st=cn.prepareStatement(SQL_DELETE_WORKER);
            st.setInt(1,workerID);
           
            st.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            return false;
        }finally {
            try {
                st.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    public boolean changeWorker(User user) {
        try {
            st=cn.prepareStatement(SQL_CHANGE_WORKER);
            st.setInt(1,user.getId());
            st.setInt(2,user.getPostID());
            st.setString(3,user.getLogin());
            st.setString(4,user.getPassword());
            st.setString(5,user.getName());
            st.setInt(6,user.getId());
            
            st.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            return false;
        }finally {
            try {
                st.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    public boolean addWorker(User user) {
        
        try {
            st=cn.prepareStatement(SQL_ADD_WORKER);
            st.setInt(1,user.getId());
            st.setInt(2,user.getPostID());
            st.setString(3,user.getLogin());
            st.setString(4,user.getPassword());
            st.setString(5,user.getName());
            
            st.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            return false;
        }finally {
            try {
                st.close();
            } catch (SQLException ex) {
            }
        }
    }
    
    public ShowAllWorkersDATA getAllWorkers() {
        try {
            ShowAllWorkersDATA data = new ShowAllWorkersDATA(); 
            User user;
            ArrayList<User> list = new ArrayList<>();
            cn=Connector.getConnection();
            st=cn.prepareStatement(SQL_GET_ALL_WORKERS);
            
            rs=st.executeQuery();
            while(rs.next()) {
                user= new User(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4),rs.getString(5));
                list.add(user);
            }
            
            data.setWorkers(list);
            return data;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }

        return null;
    }
    
    public String getWorkerNameByID(int workerID) {
        try {
            cn=Connector.getConnection();
            st=cn.prepareStatement(SQL_GET_WORKER_NAME_BY_ID);
            
            st.setInt(1,workerID);
            rs=st.executeQuery();
            
            if(rs.next())
                return rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }

        return null;
    }
    
    public int  isWorkerWasRegistered(String findLogin,String findPassword) {

        try {
            st=cn.prepareStatement(SQL_IS_WORKER_ALREADY_EXIST);
            
            st.setString(1,findLogin);
            st.setString(2, findPassword);
           
            rs=st.executeQuery();
            
            if(rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }

        return -1;
    }
    
    public int getWorkerIDbyLogin(String login) {

        try {
            st=cn.prepareStatement(SQL_GET_WORKER_ID_BY_LOGIN);
            
            st.setString(1,login);
            rs=st.executeQuery();
            
            if(rs.next())
                return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }

        return -1;
    }
    
    public String getWorkerPostByLogin(String workerLogin) {
        try {
            st=cn.prepareStatement(SQL_GET_WORKER_POST_BY_LOGIN);
            
            st.setString(1,workerLogin);
            rs=st.executeQuery();
            
            if(rs.next())
                return rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }

        return null;
    }
    
    public ArrayList getAllForwarders() {
        ArrayList<String> result = new ArrayList<>();
        
        try {
            st = cn.prepareStatement(SQL_GET_ALL_FORWARDERS_LOGINS);
            rs = st.executeQuery();
            while (rs.next()) {
                result.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                st.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }
        
        return result;
    }
    
}

