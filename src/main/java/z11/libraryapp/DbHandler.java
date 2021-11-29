package z11.libraryapp;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ResultSet;

import z11.libraryapp.errors.*;

public class DbHandler {

    private String login = "dsavytsk";
    private String password = "dsavytsk";

    private Connection con;

    public DbHandler() throws  UnavailableDB, ClassNotFoundException{
        getConnection();
    }

    public void getConnection() throws  UnavailableDB, ClassNotFoundException{
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@ora4.ii.pw.edu.pl:1521/pdb1.ii.pw.edu.pl",
                    login, password);
        } catch (SQLException e){
            throw new UnavailableDB(e);
        }

    }

    public void closeConnetion() {
        try{
            con.close();
        } catch(SQLException ingored) {}
        finally{
            con = null;
        }
    }

    public boolean isConnected() {
        try{
            return this.con != null && !this.con.isClosed();
        } catch (SQLException ignored) {}

        return false;
    }

    public ResultSet ddlQuery(String query) throws  DdlQueryError, UnavailableDB, ClassNotFoundException{
        getConnection();
        try {
            return con.createStatement().executeQuery(query);
        } catch (SQLException e){
            throw new DdlQueryError(e);
        } finally{
            closeConnetion();
        }
    }

    public void dmlQuery(String query) throws DmlQueryError, UnavailableDB, ClassNotFoundException{
        getConnection();
        try {
            con.createStatement().executeUpdate(query);
        } catch (SQLException e){
            throw new DmlQueryError(e);
        } finally{
            closeConnetion();
        }
    }

    public void dmlTramsaction(String[] queries) throws  SQLException, TransactionError, UnavailableDB, ClassNotFoundException{
        getConnection();
        con.setAutoCommit(false);
        try{
            for(String query : queries){
                con.createStatement().executeUpdate(query);
            }
            con.commit();
        }
        catch (Exception e){
            con.rollback();
            throw new TransactionError(e);
        } finally{
            closeConnetion();
        }
    }

    public void finalize () {
        closeConnetion();
    }


}
