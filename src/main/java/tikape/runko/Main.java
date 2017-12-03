package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.DrinkkiDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Drinkki;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:drinkit.db");
        database.init();

        DrinkkiDao drinkkiDao = new DrinkkiDao(database);
        RaakaAineDao raakaAineDao = new RaakaAineDao(database);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/drinkit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());

            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());

        get("/drinkit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkki", drinkkiDao.findOne2(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());

        get("/lisaa", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", drinkkiDao.findAll());
            map.put("raakaAineet", raakaAineDao.findAll());

            return new ModelAndView(map, "lisays");
        }, new ThymeleafTemplateEngine());

        post("/lisatty", (req, res) -> {
            Drinkki drinkki = new Drinkki(-1, req.queryParams("uusiDrinkki"));
            drinkkiDao.save(drinkki);

            res.redirect("/lisaa");
            return "moikka";
        });
        post("/lisaa", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("drinksu"));
            int raakaAineid = Integer.parseInt(req.queryParams("raakaAine"));
            int jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");

            drinkkiDao.update(id, raakaAineid, jarjestys, maara, ohje);

            res.redirect("/lisaa");
            return "moikka";
        });

        post("/raakaaine", (req, res) -> {
            RaakaAine raakis = new RaakaAine(-1, req.queryParams("uusiraaka"));

            raakaAineDao.save(raakis);

            res.redirect("/lisaa");
            return "moikka";
        });

        post("/poista", (req, res) -> {

            drinkkiDao.delete(Integer.parseInt(req.queryParams("drinkkinen")));
            System.out.println(req.queryParams("drinkkinen"));
            res.redirect("/drinkit");
            return null;
        });

    }
}
