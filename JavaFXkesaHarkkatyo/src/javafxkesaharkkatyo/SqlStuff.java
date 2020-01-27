/*
 * Tämä luokka sisältää kaiken tietokantaan liittyvän.
 */
package javafxkesaharkkatyo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafxkesaharkkatyo.Middleclass.Createdpackage;

/**
 *
 * @author n4121
 */
public class SqlStuff {

    XmlStuff xml = new XmlStuff();
    DataHandler DataHandler;
    Connection c = null;
    Statement stmt = null;
    static private SqlStuff ss = null;

    private SqlStuff() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
            System.out.println("DB opened");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static SqlStuff getInstance() {
        if (ss == null) {
            ss = new SqlStuff();
        }
        return ss;
    }

    /**
     * Luo pöydät tietokantaan jos niitä ei vielä ole
     */
    public void createTables() {
        try {
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS \"paketti\" (\n"
                    + "\"luokka\" INTEGER PRIMARY KEY,\n"
                    + "\"koko\" INTEGER NOT NULL,\n"
                    + "\"paino\" INTEGER NOT NULL,\n"
                    + "\"muuta\" VARCHAR(100) DEFAULT 'ei',\n"
                    + "\"kantamakm\" VARCHAR(10) DEFAULT '1000',\n"
                    + "\n"
                    + "CHECK(\"koko\" > 0),\n"
                    + "CHECK(\"paino\" > 0),\n"
                    + "CHECK(\"luokka\" IN (1,2,3))\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS \"esineet\" (\n"
                    + "\"esineID\" INTEGER PRIMARY KEY,\n"
                    + "\"nimi\" VARCHAR(30) NOT NULL,\n"
                    + "\"koko\" INTEGER NOT NULL,\n"
                    + "\"paino\" INTEGER NOT NULL,\n"
                    + "\"hajoaa\" BOOLEAN NOT NULL,\n"
                    + "\n"
                    + "CHECK(\"esineID\" > 0),\n"
                    + "CHECK(\"koko\" > 0),\n"
                    + "CHECK(\"paino\" > 0)\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS \"varasto\" (\n"
                    + "\"varastoID\" INTEGER PRIMARY KEY,\n"
                    + "\"varastointiaika\" DATETIME NOT NULL,\n"
                    + "\n"
                    + "CHECK(\"varastoID\" > 0),\n"
                    + "FOREIGN KEY(\"varastoID\") REFERENCES \"luodutpaketit\" (\"varastoID\")\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS \"osoitteet\" (\n"
                    + "\"postinumero\" VARCHAR(5) PRIMARY KEY,\n"
                    + "\"kaupunki\" VARCHAR(30) NOT NULL\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS \"luodutpaketit\" (\n"
                    + "\"pakettiID\" INTEGER PRIMARY KEY,\n"
                    + "\"esineID\" INTEGER NOT NULL, \n"
                    + "\"varastoID\" INTEGER, \n"
                    + "\"luokka\" INTEGER NOT NULL,\n"
                    + "\"lahtopaikka\" VARCHAR(60) NOT NULL,\n"
                    + "\"maaranpaa\" VARCHAR(60) NOT NULL,\n"
                    + "\"matka\" VARCHAR(10) NOT NULL,\n"
                    + "\n"
                    + "CHECK(\"pakettiID\" > 0),\n"
                    + "\n"
                    + "FOREIGN KEY(\"esineID\") REFERENCES \"esineet\" (\"esineID\"),\n"
                    + "FOREIGN KEY(\"luokka\") REFERENCES \"paketti\" (\"luokka\"),\n"
                    + "FOREIGN KEY(\"varastoID\") REFERENCES \"varasto\" (\"varastoID\")\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS \"smartpost\" (\n"
                    + "\"automaattiID\" INTEGER PRIMARY KEY,\n"
                    + "\"postinumero\" VARCHAR(5) NOT NULL,\n"
                    + "\"aukioloajat\" VARCHAR(60) NOT NULL,\n"
                    + "\"postitoimisto\" VARCHAR(60) NOT NULL,\n"
                    + "\"katuosoite\" VARCHAR(30) NOT NULL,\n"
                    + "\"latitude\" CHAR(10) NOT NULL,\n"
                    + "\"longitude\" CHAR(10) NOT NULL,\n"
                    + "\"luotu_ei\" BOOLEAN NOT NULL,\n"
                    + "\n"
                    + "CHECK(\"automaattiID\" > 0),\n"
                    + "CHECK(\"osoiteID\" > 0),\n"
                    + "\n"
                    + "FOREIGN KEY(\"postinumero\") REFERENCES \"osoitteet\" (\"postinumero\") ON DELETE CASCADE\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS \"automaatissaolevatpaketit\" (\n"
                    + "\"automaattiID\" INTEGER ,\n"
                    + "\"pakettiID\" INTEGER ,\n"
                    + "\"saapumisaika\" DATETIME NOT NULL,\n"
                    + "\n"
                    + "PRIMARY KEY(\"automaattiID\",\"pakettiID\"),\n"
                    + "FOREIGN KEY(\"pakettiID\") REFERENCES \"paketti\" (\"pakettiID\"),\n"
                    + "FOREIGN KEY(\"automaattiID\") REFERENCES \"smartpost\" (\"automaattiID\") \n"
                    + ");";
            stmt.executeUpdate(sql);
            System.out.println("Taulut luotu/tarkistettu.");
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Hakee aloitus ja lopetus paikan koordinaatit String listaan
     */
    public ArrayList getCoordinates(String startpoint, String endpoint) {
        ArrayList<String> arr = new ArrayList();
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT latitude,longitude FROM "
                    + "smartpost WHERE postitoimisto = '" + startpoint + "'");
            rs.next();
            String lat = rs.getString("latitude");
            String lng = rs.getString("longitude");
            arr.add(lat);
            arr.add(lng);

            stmt = c.createStatement();
            rs = stmt.executeQuery("SELECT latitude,longitude FROM "
                    + "smartpost WHERE postitoimisto = '" + endpoint + "'");
            rs.next();
            lat = rs.getString("latitude");
            lng = rs.getString("longitude");
            arr.add(lat);
            arr.add(lng);
            //return arr;
            System.out.println(lat + lng);
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }

    /**
     * lukee smartpostin tiedot merkkiä varten listaan.
     */
    public void readSmartpostAndAddress(String city) {
        try {
            DataHandler.addtomaplist.clear();
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM smartpost "
                    + "INNER JOIN osoitteet ON osoitteet.postinumero = smartpost.postinumero "
                    + "WHERE osoitteet.kaupunki = '" + city + "';");

            while (rs.next()) {
                int id = rs.getInt("automaattiID");
                String ajat = rs.getString("aukioloajat").replace(",", ".");
                String postnro = rs.getString("postinumero");
                String office = rs.getString("postitoimisto").replace(",", ".");
                String citys = rs.getString("kaupunki");
                String addr = rs.getString("katuosoite");

                String addressinfo = addr + " " + postnro + " " + citys;
                String smartinfo = office + " " + ajat;
                DataHandler.addtomaplist.add(addressinfo);
                DataHandler.addtomaplist.add(smartinfo);

                DataHandler.idlist.add(id);
            }
            rs.close();
            stmt.close();

            //päivittää tiedon automaatin luonnista tietokantaan.
            while (DataHandler.idlist.isEmpty() == false) {
                this.updateSmartPostCreated(DataHandler.idlist.get(0), true);
                DataHandler.idlist.remove(0);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("LUETTU");
    }

    /**
     * Lisää tiedot automaateista tietokantaan.
     */
    public void insertXmlToDb() {
        xml.parseData(xml.docBuilderi(xml.getXML(
                "http://iseteenindus.smartpost.ee/api/?request=destinations&country=FI&type=APT")));
        int i = 1;
        System.out.println(DataHandler.smartlist.size());
        try {
            stmt = c.createStatement();
            while (DataHandler.smartlist.size() > i - 1) {
                c.setAutoCommit(false);
                String sql = "INSERT INTO smartpost (automaattiID,postinumero,aukioloajat,"
                        + "postitoimisto,katuosoite,latitude,longitude,luotu_ei)"
                        + "VALUES(" + i + ","
                        + "'" + DataHandler.smartlist.get(i - 1).getPostnumber() + "',"
                        + "'" + DataHandler.smartlist.get(i - 1).getAvailability() + "',"
                        + "'" + DataHandler.smartlist.get(i - 1).getPostoffice() + "',"
                        + "'" + DataHandler.smartlist.get(i - 1).getAddress() + "',"
                        + "'" + DataHandler.smartlist.get(i - 1).getGp().getLatitude() + "',"
                        + "'" + DataHandler.smartlist.get(i - 1).getGp().getLongitude() + "',"
                        + "'" + DataHandler.smartlist.get(i - 1).getCreated() + "'" + ");";
                stmt.executeUpdate(sql);
                try {
                    sql = "INSERT INTO osoitteet (postinumero,kaupunki)"
                            + "VALUES('" + DataHandler.smartlist.get(i - 1).getPostnumber() + "',"
                            + "'" + DataHandler.smartlist.get(i - 1).getCity() + "');";
                    stmt.executeUpdate(sql);
                } catch (SQLException sQLException) {
                }
                i++;
            }
            stmt.close();
            c.commit();
            c.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("ALL INSERTED");
    }

    /**
     * Lukee kaupunkien nimet observable listaan.
     */
    public ObservableList readCities() {
        ObservableList<String> oblist = FXCollections.observableArrayList();
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT kaupunki "
                    + "FROM osoitteet ORDER BY kaupunki;");
            while (rs.next()) {
                String city = rs.getString("kaupunki");
                oblist.add(city);
            }
            rs.close();
            stmt.close();
            return oblist;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oblist;
    }

    /**
     * Lukee kaikkien smartpostien datan olio listaan
     */
    public ObservableList readSmartpostData() {
        ObservableList<SmartPost> oblist;
        oblist = FXCollections.observableArrayList();
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM smartpost "
                    + "INNER JOIN osoitteet ON osoitteet.postinumero = smartpost.postinumero;");
            while (rs.next()) {
                int id = rs.getInt("automaattiID");
                String availability = rs.getString("aukioloajat");
                String postnro = rs.getString("postinumero");
                String office = rs.getString("postitoimisto");
                String citys = rs.getString("kaupunki");
                String addr = rs.getString("katuosoite");
                String lat = rs.getString("latitude");
                String lng = rs.getString("longitude");
                String breaks = rs.getString("luotu_ei");
                boolean bool;
                if (breaks.equals("true")) {
                    bool = true;
                } else {
                    bool = false;
                }
                SmartPost smartpost = new SmartPost(postnro, citys, addr,
                        availability, office, id, bool, lat, lng);
                oblist.add(smartpost);
                DataHandler.smartlist.add(smartpost);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oblist;
    }

    /**
     * Lisää uuden esineen tietokantaan.
     */
    public void insertItem(Item i) {
        try {

            String sql = "INSERT INTO esineet (esineID,nimi,koko,paino,hajoaa) "
                    + "VALUES (null,'" + i.getItemName() + "','" + i.getItemSize()
                    + "','" + i.getItemWeight() + "','" + i.getItemBreaks() + "');";
            stmt.executeUpdate(sql);

        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Esine lisätty");
    }
    
    /**
     * Lisää uuden pakettiluokan tietokantaan.
     */
    public void insertPackageClassInfo(int luokka, int maxkoko, int maxpaino, String muuta, String kantamakm) {
        try {
            if(muuta != null){
                muuta = "'"+muuta+"'";
            }
            if(kantamakm != null){
                kantamakm = "'"+kantamakm+"'";
            }
            String sql = "INSERT INTO paketti"
                    + " VALUES (" + luokka + ", " + maxkoko + ", " + maxpaino + ", "
                    + muuta + ", " + kantamakm + ");";
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Pakettiluokat lisätty");
    }

    /**
     * Lukee pakettiluokat ja lisää observable listaan.
     */
    public void readPackageClasses() {
        DataHandler.packageclassoblist.clear();
        try {
            ResultSet rs = stmt.executeQuery("SELECT luokka FROM paketti;");
            while (rs.next()) {
                int classnumber = rs.getInt("luokka");
                //System.out.println(classnumber);
                DataHandler.packageclassoblist.add(classnumber);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hakee esineID:n esineen nimen perusteella
     */
    public int getItemid(String itemname) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT esineID FROM esineet"
                    + " WHERE nimi = '" + itemname + "';");
            int itemid = rs.getInt("esineID");
            return itemid;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Lisää luodun paketin tietokantaan.
     */
    public void insertCreatedPackage(Createdpackage cp) {
        int itemid = SqlStuff.getInstance().getItemid(cp.getItemname());
        if (itemid == 0) {
            System.out.println("Esinettä ei löytynyt");
        }
        try {
            String sql = "INSERT INTO luodutpaketit"
                    + " VALUES (null," + itemid + ",null ," + cp.getPackageclass() + ",'"
                    + cp.getStartpoint() + "', '" + cp.getDestination() + "', '" + cp.getTriplenght() + "');";
            stmt.executeUpdate(sql);
            System.out.println("Paketti luotu.");
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hakee esineiden tiedot listaan tableviewiä varten.
     */
    public ObservableList getItemsData() {
        DataHandler.itemoblist.clear();
        ObservableList<Item> oblist;
        oblist = FXCollections.observableArrayList();
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM esineet;");
            while (rs.next()) {
                String name = rs.getString("nimi");
                int size = rs.getInt("koko");
                int weight = rs.getInt("paino");
                String bool = rs.getString("hajoaa");
                int id = rs.getInt("esineID");
                boolean breaks;
                if ("true".equals(bool)) {
                    breaks = true;
                } else {
                    breaks = false;
                }
                Item item = new Item(id,name, size, weight, breaks);
                oblist.add(item);
                DataHandler.itemoblist.add(name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oblist;
    }

    /**
     * Hakee tehtyjen pakettien tiedot listaan tableviewiä varten.
     */
    public ObservableList getCreatedPackageData() {
        ObservableList<Createdpackage> oblist;
        oblist = FXCollections.observableArrayList();
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT luodutpaketit.pakettiID,nimi,luokka,"
                    + "lahtopaikka,maaranpaa,matka,postitoimisto,saapumisaika,varastointiaika "
                    + "FROM luodutpaketit "
                    + "INNER JOIN esineet ON luodutpaketit.esineID = "
                    + "esineet.esineID "
                    + "LEFT OUTER JOIN automaatissaolevatpaketit ON "
                    + "luodutpaketit.pakettiID = automaatissaolevatpaketit.pakettiID "
                    + "LEFT OUTER JOIN smartpost ON automaatissaolevatpaketit.automaattiID "
                    + "= smartpost.automaattiID "
                    + "LEFT OUTER JOIN varasto ON luodutpaketit.varastoID = "
                    + "varasto.varastoID;");
            while (rs.next()) {
                int id = rs.getInt(1);
                String item = rs.getString(2);
                int packclass = rs.getInt(3);
                String start = rs.getString(4);
                String end = rs.getString(5);
                String trip = rs.getString(6);
                String location = rs.getString(7);
                String stotime = rs.getString(9);
                String arrtime = rs.getString(8);
                String currentloc = "";
                String action = "";
                String time = "";
                if (location == null) {
                    currentloc = "Varasto";
                    action = "Odottaa lähettämistä";
                } else {
                    currentloc = location;
                    action = "Saapunut määränpäähän";
                }
                if (stotime == null) {
                    time = arrtime;
                } else {
                    time = stotime;
                }
                oblist.add(new Createdpackage(id, packclass, item, start, end,
                        trip, currentloc, action, time));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return oblist;
    }

    /**
     * Lukee luodut paketit observable listaan.
     */
    public void readCreatedPackages() {
        Storage.createdpackageoblist.clear();
        try {

            ResultSet rs = stmt.executeQuery("SELECT esineet.nimi,"
                    + "luodutpaketit.luokka,luodutpaketit.pakettiID "
                    + "FROM luodutpaketit "
                    + "INNER JOIN esineet ON luodutpaketit.esineID = esineet.esineID "
                    + "INNER JOIN varasto ON varasto.varastoID = luodutpaketit.varastoID;");
            while (rs.next()) {
                String packages = rs.getInt(3) + ", " + rs.getString(1)
                        + ", pakettiluokka: " + rs.getString(2);
                Storage.createdpackageoblist.add(packages);
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Luodut paketit listattu");
    }

    public void deleteItem(String item) {
        try {
            String sql = "DELETE FROM esineet WHERE nimi = '" + item + "';";
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("ESINE POISTETTU");
    }

    /**
     * Poistaa luodun paketin tietokannasta luodutpaketit taulusta.
     */
    public void removeFromCreatedPackages(int packid) {
        try {
            String sql = "DELETE FROM luodutpaketit "
                    + "WHERE pakettiID = " + packid + ";";
            stmt.executeUpdate(sql);
            System.out.println("PAKETTI POISTETTU LUODUISTA PAKETEISTA");
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Poistaa luodun paketin tietokannasta varasto taulusta.
     */
    public void removeFromStorage(int packid) {
        try {
            String sql = "DELETE FROM varasto "
                    + "WHERE varastoID = " + packid + ";";
            stmt.executeUpdate(sql);
            System.out.println("PAKETTI POISTETTU VARASTOSTA");
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Poistaa luodun paketin tietokannasta automaatissaolevatpaketit taulusta.
     */
    public void removeFromPacketInSmartpost(int packid) {
        try {
            String sql = "DELETE FROM automaatissaolevatpaketit "
                    + "WHERE pakettiID = " + packid + ";";
            stmt.executeUpdate(sql);
            System.out.println("PAKETTI POISTETTU AUTOMAATISSA OLEVISTA PAKETEISTA");
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Päivittää automaatin luotu/ei arvon.
     */
    public void updateSmartPostCreated(int id, boolean created) {
        try {
            String sql = "UPDATE smartpost "
                    + "SET luotu_ei = '" + created + "' "
                    + "WHERE automaattiID = " + id + ";";
            stmt.executeUpdate(sql);
            System.out.println("luotu/ei UPDATED    " + id + "  " + created);
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Lisää luodun paketin varastoon.
     */
    public void insertToStorage(int packid) {
        try {
            String sql = "INSERT INTO varasto "
                    + "VALUES (" + packid + ",CURRENT_TIMESTAMP);";
            stmt.executeUpdate(sql);
            sql = "UPDATE luodutpaketit "
                    + "SET varastoID = '" + packid + "' "
                    + "WHERE pakettiID = '" + packid + "';";
            stmt.executeUpdate(sql);
            System.out.println("paketti VARASTOITU");
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Tarkastaa onko esineet taulussa esineitä
     */
    public int checkItems() {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS rows FROM esineet;");
            rs.next();
            int count = rs.getInt(1);
            System.out.println(count);
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Tarkastaa onko smartpost taulussa automaatteja
     */
    public int checkSmartPosts() {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS rows FROM smartpost;");
            rs.next();
            int count = rs.getInt(1);
            System.out.println(count);
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Tarkastaa onko paketti taulussa pakettiluokkia
     */
    public int checkPackageClasses() {
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS rows FROM paketti;");
            rs.next();
            int count = rs.getInt(1);
            System.out.println(count);
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    /**
     * Lukee kartalle lisätyt automaatit observable listaan.
     */
    public void readCreatedSmartposts() {
        DataHandler.createdpostsoblist.clear();
        try {
            ResultSet rs = stmt.executeQuery("SELECT postitoimisto FROM smartpost"
                    + "	WHERE luotu_ei = 'true';");
            while (rs.next()) {
                String postoffice = rs.getString(1);
                DataHandler.createdpostsoblist.add(postoffice);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Päivittää kaikki automaatit luoduiksi tai ei.
     */
    public void updateAllSmartpostCreated(boolean created) {
        boolean created2;
        created2 = created == false;
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT automaattiID FROM smartpost "
                    + "WHERE luotu_ei = '" + created2 + "';");
            while (rs.next()) {
                int id = rs.getInt(1);
                DataHandler.idlist.add(id);
            }
            while (DataHandler.idlist.isEmpty() == false) {
                updateSmartPostCreated(DataHandler.idlist.get(0), created);
                DataHandler.idlist.remove(0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Lisää automaattiin saapuneen paketin tietokantaan.
     */
    public void insertArrivingPackage(int packid, int smartid) {
        try {
            String sql = "INSERT INTO automaatissaolevatpaketit "
                    + "VALUES(" + smartid + "," + packid + ",CURRENT_TIMESTAMP);";
            stmt.executeUpdate(sql);
            System.out.println("PAKETTI SAAPUNUT");
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hakee paketin sisältämän esineen "hajoaa" arvon
     */
    public String checkIfBreaks(int packageid) {
        String breaks = "";
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT hajoaa "
                    + "FROM luodutpaketit INNER JOIN esineet ON "
                    + "luodutpaketit.esineID = esineet.esineID "
                    + "WHERE pakettiID = '" + packageid + "';");
            rs.next();
            breaks = rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return breaks;
    }

    /**
     * Tyhjentää kaikki tietokannan taulut.
     */
    public void resetDatabase() {
        try {
            stmt = c.createStatement();
            stmt.executeUpdate("DELETE FROM smartpost;");
            stmt.executeUpdate("DELETE FROM luodutpaketit;");
            stmt.executeUpdate("DELETE FROM osoitteet;");
            stmt.executeUpdate("DELETE FROM esineet;");
            stmt.executeUpdate("DELETE FROM paketti;");
            stmt.executeUpdate("DELETE FROM varasto;");
            stmt.executeUpdate("DELETE FROM automaatissaolevatpaketit;");
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getLastInsertRowid() {
        try {
            ResultSet rs = stmt.executeQuery("SELECT LAST_INSERT_ROWID();");
            rs.next();
            int rowid = rs.getInt(1);
            System.out.println("last rowid: " + rowid);
            return rowid;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getSmartpostid(String lat) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT automaattiID FROM smartpost "
                    + "WHERE latitude = '" + lat + "';");
            rs.next();
            int smartid = rs.getInt(1);
            return smartid;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public void getLatLng(int id) {
        DataHandler.toscriptlist.clear();
        System.out.println("lista = " + DataHandler.toscriptlist);
        try {
            ResultSet rs = stmt.executeQuery("SELECT smartpost.latitude, "
                    + "smartpost.longitude FROM smartpost "
                    + "INNER JOIN luodutpaketit ON smartpost.postitoimisto = "
                    + "luodutpaketit.lahtopaikka "
                    + "WHERE luodutpaketit.pakettiID = " + id + ";");
            rs.next();
            String coord = rs.getString(1);
            DataHandler.toscriptlist.add(coord);
            System.out.println(coord);
            coord = rs.getString(2);
            DataHandler.toscriptlist.add(coord);
            System.out.println(coord);

            ResultSet rs2 = stmt.executeQuery("SELECT smartpost.latitude, "
                    + "smartpost.longitude FROM smartpost "
                    + "INNER JOIN luodutpaketit ON smartpost.postitoimisto = "
                    + "luodutpaketit.maaranpaa "
                    + "WHERE luodutpaketit.pakettiID = " + id + ";");
            rs2.next();
            coord = rs2.getString(1);
            DataHandler.toscriptlist.add(coord);
            System.out.println(coord);
            coord = rs2.getString(2);
            DataHandler.toscriptlist.add(coord);
            System.out.println(coord);

            System.out.println("Koordinaatit selvillä");
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int getItemSize(int id) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT koko FROM esineet "
                    + "WHERE esineID = " + id + ";");
            rs.next();
            int size = rs.getInt(1);
            return size;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getItemWeight(int id) {
        try {
            System.out.println(id);
            ResultSet rs = stmt.executeQuery("SELECT paino FROM esineet "
                    + "WHERE esineID = " + id + ";");
            rs.next();
            int weight = rs.getInt(1);
            return weight;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getPackclassMaxSize(int classid) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT koko FROM paketti "
                    + "WHERE luokka = " + classid + ";");
            rs.next();
            int size = rs.getInt(1);
            return size;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int getPackclassMaxWeight(int classid) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT paino FROM paketti "
                    + "WHERE luokka = " + classid + ";");
            rs.next();
            int weight = rs.getInt(1);
            return weight;
        } catch (SQLException ex) {
            Logger.getLogger(SqlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
}