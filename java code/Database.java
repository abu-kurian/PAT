/**
 * Created by Kanishth on 9/10/2016.
 */

import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;

public class Database
{
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String DB_FREQUENCY_URL = "jdbc:mysql://localhost/attack_frequency";
    public static final String DB_INDIA_URL = "jdbc:mysql://localhost/terrorist_attack";

    //  Database credentials
    public static final String USER = "root";
    public static final String PASS = "kateisdog";

    public static void executeStatement(String query)
    {
        Connection connection = null;
        Statement statement  = null;
        try{
            Class.forName(Database.JDBC_DRIVER);
            connection = DriverManager.getConnection(Database.DB_INDIA_URL,Database.USER,Database.PASS);
            statement = connection.createStatement();
            statement.executeUpdate(query);

            statement.close();
            connection.close();
            //Clean-up environment
        }
        catch(Exception e) {e.printStackTrace(); }
        finally { //finally block used to close resources
            try{
                if(statement!=null) statement.close();
                if(connection!=null) connection.close();
            } catch(SQLException se2) {se2.printStackTrace();}// nothing we can do
        } //end finally, try
    }

    public static Structure read_lat_lng(String query)
    {
        Connection connection = null;
        Statement statement  = null;
        try{
            Class.forName(Database.JDBC_DRIVER);
            connection = DriverManager.getConnection(Database.DB_INDIA_URL,Database.USER,Database.PASS);
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            ArrayList<Double> value_lat = new ArrayList<>();
            ArrayList<Double> value_lng = new ArrayList<>();
            while(resultSet.next()) {
                value_lat.add(resultSet.getDouble("LAT"));
                value_lng.add(resultSet.getDouble("LONG"));
            }

            resultSet.close();
            statement.close();
            connection.close();

            Structure structure= new Structure(value_lat,value_lng);

            if(structure!=null)
                return structure;
            return null;

            //Clean-up environment
        }
        catch(Exception e) {e.printStackTrace(); }
        finally { //finally block used to close resources
            try{
                if(statement!=null) statement.close();
                if(connection!=null) connection.close();
            } catch(SQLException se2) {se2.printStackTrace(); }// nothing we can do
        } //end finally, try
        return null;
    }

}

