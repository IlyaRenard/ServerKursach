
package by.bsuir.pokos.database.dao.entity;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;

public abstract class User implements Serializable{
    
    private int id;
    private int postID;
    private String login;
    private String password;   
    private String name;
    
    abstract public void Work(ObjectInputStream inputStream,ObjectOutputStream outputStream,Connection cn);

    public User(int postID, String login) {
        this.postID = postID;
        this.login = login;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }
    
    public User(){}
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
