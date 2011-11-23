package com.google.dbcheck.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.code.dbcheck.DatabaseLoader;
import com.google.code.dbcheck.model.Database;

public class OracleDatabase {
    public DatabaseLoader loader;

    public OracleDatabase(DatabaseLoader loader) {
        this.loader = loader;
    }

    public Database loadDatabaseMeta() throws ClassNotFoundException, SQLException {
        Database database;
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.241.14.172:1521:cmdbdev",
                    "cmdbas", "cmdbas");

            database = loader.load("CMDBAS", conn);

        } finally {
            if (conn != null)
                conn.close();
        }
        return database;
    }
}
