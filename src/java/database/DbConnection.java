/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vdtru
 */
public class DbConnection {

    private Connection con = null;

    private final String DB_URL = "jdbc:mysql://localhost:3306/ecommerce";
    private final String USER = "root";
    private final String PASSWORD = "";

    public void open() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.con = DriverManager.getConnection(this.DB_URL, this.USER, this.PASSWORD);
    }

    public void close() {
        if (con != null) {
            try {
                this.con.close();
                this.con = null;
            } catch (SQLException ex) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (this.con == null) {
            this.open();
        }
        return this.con;
    }

    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet rs = null;
        Statement stmt = this.con.createStatement();
        rs = stmt.executeQuery(query);
        return rs;
    }

    public int executeUpdate(String query, List<String> paramValues) throws SQLException {

        PreparedStatement pstmt = this.con.prepareStatement(query);
        int i = 1;
        for (String value : paramValues) {
            pstmt.setString(i, value);
            i++;
        }

        return pstmt.executeUpdate();
    }
}
