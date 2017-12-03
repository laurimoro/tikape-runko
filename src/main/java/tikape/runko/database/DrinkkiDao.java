/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Drinkki;

public class DrinkkiDao {

    private Database database;

    public DrinkkiDao(Database database) {
        this.database = database;
    }

    public Drinkki findOne(String drinksu) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki WHERE nimi = ?");
        stmt.setString(1, drinksu);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Drinkki o = new Drinkki(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    public Drinkki findOne2(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String nimi = rs.getString("nimi");

        Drinkki o = new Drinkki(id, nimi);

        rs.close();
        stmt.close();
        connection.close();

        return o;
    }

    public List<Drinkki> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Drinkki");

        ResultSet rs = stmt.executeQuery();
        List<Drinkki> drinkit = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String nimi = rs.getString("nimi");

            drinkit.add(new Drinkki(id, nimi));
        }

        rs.close();
        stmt.close();
        connection.close();

        return drinkit;
    }

    public static Drinkki save(Drinkki drinkki) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:drinkit.db");

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO Drinkki (nimi) VALUES (?)");
        stmt.setString(1, drinkki.getNimi());

        int changes = stmt.executeUpdate();

        stmt.close();
        connection.close();

        return null;
    }

    public static Drinkki update(Integer iidee, Integer raaka_aine, Integer jarjestys, String maara, String ohje) throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:sqlite:drinkit.db");

        PreparedStatement stmt = connection.prepareStatement("UPDATE Drinkki SET raaka_aine_id = ?,jarjestys = ?, maara = ?, ohje = ?  WHERE id = ?");
        stmt.setInt(1, raaka_aine);
        stmt.setInt(2, jarjestys);
        stmt.setString(3, maara);
        stmt.setString(4, ohje);
        stmt.setInt(5, iidee);

        int changes = stmt.executeUpdate();

        stmt.close();
        connection.close();

        return null;
    }

    public void delete(Integer key) throws SQLException {
        // ei toteutettu
    }

}
