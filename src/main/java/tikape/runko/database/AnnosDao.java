package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Annos;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.RaakaAine;

public class AnnosDao {

    private Database database;

    public AnnosDao(Database database) {
        this.database = database;
    }

    public Annos findOne(String annos) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos WHERE nimi = ?");
        stmt.setString(1, annos);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Annos o = new Annos(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }
    
        public static Annos save(Annos annos) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:drinkit.db");

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO raakaAine (nimi) VALUES (?)");
        stmt.setString(1, annos.getNimi());

        int changes = stmt.executeUpdate();

        stmt.close();
        connection.close();

     return null; 
    }
    

    public List<Annos> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Annos");

        ResultSet rs = stmt.executeQuery();
        List<Annos> annokset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            annokset.add(new Annos(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return annokset;
    }


    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
