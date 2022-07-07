package pl.pomoku.loginsystem.db;

import pl.pomoku.loginsystem.models.Players;

import java.sql.*;

public class Database {
    private Connection con;
    public Connection getCon() throws SQLException{
        if(con != null){
            return con;
        }
        String url = "jdbc:mysql://localhost/users";
        String user = "root";
        String password = "";

        this.con = DriverManager.getConnection(url, user, password);
        System.out.println("Connected to the Users Database.");

        return this.con;
    }
    public void initializeDatabase() throws SQLException{
        Statement statement = getCon().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS players(uuid varchar(36) primary key, player_name TEXT, ip TEXT, email TEXT, email_confirm BOOLEAN, password TEXT, sign_in_date DATE, ban BOOLEAN, last_x DOUBLE, last_y DOUBLE, last_z DOUBLE)";
        statement.execute(sql);
        statement.close();
        System.out.println("Create the players table in the database.");
    }
    public Players findPlayerByUUID(String uuid) throws SQLException{
        PreparedStatement statement = getCon().prepareStatement("SELECT * FROM players WHERE uuid = ?");
        statement.setString(1, uuid);
        ResultSet results = statement.executeQuery();

        if(results.next()){
            String player_name = results.getString("player_name");
            String ip = results.getString("ip");
            String email = results.getString("email");
            boolean email_confirm = results.getBoolean("email_confirm");
            String password = results.getString("password");
            Date sign_in_date = results.getDate("sign_in_date");
            boolean ban = results.getBoolean("ban");
            double last_x = results.getDouble("last_x");
            double last_y = results.getDouble("last_y");
            double last_z = results.getDouble("last_z");
            Players players = new Players(uuid, player_name, ip, email, email_confirm, password, sign_in_date, ban, last_x, last_y, last_z);
            statement.close();
            return players;
        }
        statement.close();
        return null;
    }
    public void createPlayers(Players pla) throws SQLException{
        PreparedStatement statement = getCon().prepareStatement("INSERT INTO players(uuid, player_name,ip, email, email_confirm, password, sign_in_date, ban, last_x, last_y, last_z) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
        statement.setString(1, pla.getUuid());
        statement.setString(2, pla.getPlayer_name());
        statement.setString(3, pla.getIp());
        statement.setString(4, pla.getEmail());
        statement.setBoolean(5, pla.isEmail_confirm());
        statement.setString(6, pla.getPassword());
        statement.setDate(7, new Date(pla.getSign_in_date().getTime()));
        statement.setBoolean(8, pla.isBan());
        statement.setDouble(9, pla.getLast_x());
        statement.setDouble(10, pla.getLast_y());
        statement.setDouble(11, pla.getLast_z());

        statement.executeUpdate();
        statement.close();
    }
    public void updatePlayers(Players pla) throws SQLException{
        PreparedStatement statement = getCon().prepareStatement("UPDATE players SET player_name = ?, ip = ?, email = ?, email_confirm = ?, password = ?, sign_in_date = ?, ban = ?, last_x = ?, last_y = ?, last_z = ? WHERE uuid = ?");

        statement.setString(1, pla.getPlayer_name());
        statement.setString(2, pla.getIp());
        statement.setString(3, pla.getEmail());
        statement.setBoolean(4, pla.isEmail_confirm());
        statement.setString(5, pla.getPassword());
        statement.setDate(6, new Date(pla.getSign_in_date().getTime()));
        statement.setBoolean(7, pla.isBan());
        statement.setDouble(8, pla.getLast_x());
        statement.setDouble(9, pla.getLast_y());
        statement.setDouble(10, pla.getLast_z());

        statement.setString(11, pla.getUuid());

        statement.executeUpdate();
        statement.close();
    }
}
