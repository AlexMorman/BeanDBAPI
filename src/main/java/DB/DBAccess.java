package DB;

import DataStructures.Bean;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class DBAccess
{
    private String dbLocation;
    private Properties properties;

    public DBAccess() throws Exception
    {
        properties = new Properties();
        properties.load(new FileInputStream("Parameters.config"));
        dbLocation = properties.getProperty("DB_LOCATION");
    }

    public void executeUpdate(String sql) throws Exception
    {
        Connection c = null;
        Statement stmt = null;

        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:" + dbLocation);

        stmt = c.createStatement();
        stmt.executeUpdate(sql);
        stmt.close();
        c.close();
    }

    public ArrayList<Bean> executeQuery(String sql)
    {
        ArrayList<Bean> Beans = new ArrayList<>();
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + dbLocation);
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String type = rs.getString("type");
                int mass = rs.getInt("mass");
                int value = rs.getInt("value");

                Bean Bean = new Bean(id, type, mass, value);
                Beans.add(Bean);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        return Beans;
    }

    /**
     * Code used to instantiate the BEAN_DB table if it does not exist
     */
    public void createTable() throws Exception
    {
        String createTable = "CREATE TABLE BEAN_DB " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " TYPE           TEXT    NOT NULL, " +
                " MASS           INT     NOT NULL, " +
                " VALUE          INT     NOT NULL )";

        executeUpdate(createTable);
    }

    /**
     * Add DataStuctures.Bean to the db
     * @param Bean
     */
    public void insert(Bean Bean) throws Exception
    {
        String insert = "INSERT INTO BEAN_DB (ID,TYPE,MASS,VALUE) " + Bean.toString();
        executeUpdate(insert);
    }

    public String queryParse(String sql)
    {
        ArrayList<Bean> Beans = executeQuery(sql);

        StringBuilder BeanBuilder = new StringBuilder();
        for (Bean Bean : Beans)
        {
            BeanBuilder.append(Bean.toString());
            BeanBuilder.append("\n");
        }

        return BeanBuilder.toString();
    }

    public String queryAll()
    {
        String queryAll = "SELECT * FROM BEAN_DB;";
        return queryParse(queryAll);
    }

    public String queryByID(int id)
    {
        String queryByID = "SELECT * FROM BEAN_DB" +
                " WHERE ID=" + id + ";";
        return queryParse(queryByID);
    }
}
