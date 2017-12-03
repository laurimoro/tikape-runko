package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.RaakaAine;

public class RaakaAineDao {

    private Database database;

    public RaakaAineDao(Database database) {
        this.database = database;
    }

    public RaakaAine findOne(String raakaAines) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine WHERE nimi = ?");
        stmt.setString(1, raakaAines);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        RaakaAine o = new RaakaAine(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }
    
        public static RaakaAine save(RaakaAine raakaAine) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:drinkit.db");

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO raakaAine (nimi) VALUES (?)");
        stmt.setString(1, raakaAine.getNimi());

        int changes = stmt.executeUpdate();

        stmt.close();
        connection.close();

     return null; 
    }
    

    public List<RaakaAine> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM RaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<RaakaAine> raakaAineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            raakaAineet.add(new RaakaAine(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return raakaAineet;
    }


    public void delete(Integer key) throws SQLException {
        // ei toteutettut
    }

}
