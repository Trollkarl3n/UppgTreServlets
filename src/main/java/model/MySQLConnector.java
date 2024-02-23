package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

public class MySQLConnector {

    // Database connection information
    private final String databaseName = "gritacademydb";
    private final String host = "localhost";
    private final int port = 3306;
    private final String username = "User";
    private final String password = "";

    final private String queriesPath = "src/main/java/model/queries.csv";
    private static MySQLConnector connector = null;

    private MySQLConnector(){}

    public static MySQLConnector getConnector() {
        if (connector == null) {
            connector = new MySQLConnector();
        }
        return connector;
    }
    public LinkedList<String[]> selectQuery(String queryName, String... args) throws NullPointerException{
        try(BufferedReader br = new BufferedReader(new FileReader(queriesPath))) {
            String query = br.readLine();
            String[] line = null;
            while (query != null){
                line = query.trim().split(";");
                if(line[0].equals(queryName)){
                    for (int i = 0; i < args.length; i++) {
                        line[1] = line[1].replace("{"+i+"}",args[i]) ;
                    }
                    System.out.println("Executing query: "+queryName +" - "+ line[1]);
                    return select(line[1], line[2], line[3], line[4], line[5], line[6]);
                }
                query = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private LinkedList<String[]> select(String query, String db, String ip, String port, String user, String password){
        LinkedList<String[]> queryReturn = new LinkedList<String[]>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData md = rs.getMetaData();
            int columnCount = md.getColumnCount();

            //add header row to queryReturn
            String[] headerRow = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                headerRow[i-1] = md.getColumnName(i);
            }
            queryReturn.add(headerRow);

            //add data row to queryReturn
            while (rs.next()){
                String[] dataRow = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    dataRow[i-1] = rs.getString(i);
                }
                queryReturn.add(dataRow);
            }
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return queryReturn;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName, username, password);
    }
}
