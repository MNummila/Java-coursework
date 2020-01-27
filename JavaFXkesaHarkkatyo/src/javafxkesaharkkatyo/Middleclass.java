/*
 * Tämä luokka toimii välikätenä käyttöliittymän ja muun ohjelman välillä.
 */
package javafxkesaharkkatyo;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author n4121
 */
public class Middleclass {

    SqlStuff ss = SqlStuff.getInstance();
    PostPackage p = null;

    public Middleclass() {
    }

    /**
     * Pääsivun alustus, päivittää listat, tekee default esineet.
     */
    public void mainDocInitialazion() {
        ss.createTables();
        if (ss.checkSmartPosts() < 1) {

            ss.insertXmlToDb();
        }
        if (ss.checkPackageClasses() < 1) {
            this.packageClassCreation();
        }
        if (ss.checkItems() < 1) {
            this.addDefaultItems();
        }
        ss.updateAllSmartpostCreated(false);
        ss.readCreatedPackages();
    }

    /**
     * Paketinluonti sivun alustus, päivittää listat.
     */
    public void packageDockInitialazion() {
        ss.getItemsData();
        ss.readCreatedSmartposts();
        ss.readPackageClasses();
    }

    /**
     * Lisää default esineet tietokantaan.
     */
    public void addDefaultItems() {
        DataHandler.itemlist.clear();
        Item i1 = new Item(0,"autonrengas", 50, 25, false);
        Item i2 = new Item(0,"magic 8-ball", 1, 1, true);
        Item i3 = new Item(0,"näyttö", 25, 5, true);
        Item i4 = new Item(0,"lyijykuutio", 900, 900, false);
        ss.insertItem(i1);
        ss.insertItem(i2);
        ss.insertItem(i3);
        ss.insertItem(i4);
        DataHandler.itemlist.add(i1);
        DataHandler.itemlist.add(i2);
        DataHandler.itemlist.add(i3);
        DataHandler.itemlist.add(i4);
    }

    /**
     * Resetoi tietokannan ja initialisoi uudelleen.
     */
    public void resetDB() {
        ss.resetDatabase();
        this.mainDocInitialazion();
    }

    /**
     * Poistaa esineen ja päivittää esinelistan.
     */
    public void deleteItem(String item) {
        ss.deleteItem(item);
        DataHandler.itemoblist.clear();
        ss.getItemsData();
    }

    /**
     * Luo paketin, lisää sen varastoon ja päivittää pakettilistan.
     */
    public void packageCreation(String item, int packclass, String startpoint,
            String endpoint, String distance) {
        Createdpackage cp = new Createdpackage(0,packclass,item,startpoint,endpoint,distance,"","","");
        ss.insertCreatedPackage(cp);
        ss.insertToStorage(ss.getLastInsertRowid());
        ss.readCreatedPackages();
    }

    /**
     * Luo uuden esineen, lisää sen tietokantaan ja päivittää esinelistan.
     */
    public String addItem(String name, String size1, String size2, String size3,
            String weight, boolean breaks) {
        int size;
        String errortext;
        int size11 = this.stringToInt(size1);
        int size22 = this.stringToInt(size2);
        int size33 = this.stringToInt(size3);
        if (size11 <= 0 || size22 <= 0 || size33 <= 0) {
            errortext = "Esineen koko ei voi olla negatiivinen.";
            return errortext;
        }
        size = size11 * size22 * size33;
        size = size / 1000;
        if (size < 1) {
            size = 1;
        }
        int weight2 = Integer.parseInt(weight);
        if (weight2 < 0) {
            errortext = "Esineen paino ei voi olla negatiivinen.";
            return errortext;
        }
        Item item = new Item(0,name, size, weight2, breaks);
        ss.insertItem(item);
        DataHandler.itemlist.add(item);
        DataHandler.itemoblist.clear();
        ss.getItemsData();
        return "";
    }

    /**
     * Lukee tiedot merkkiä varten ja lisää ne listaan.
     */
    public void addMark(String postmat) {
        ss.readSmartpostAndAddress(postmat);
    }

    /**
     * Luo pakettiluokat ja päivittää listan.
     */
    public void packageClassCreation() {
        p = new firstclass(1, 500, 400, "hajoaa", "150");
        ss.insertPackageClassInfo(p.packclass, p.maxsize, p.maxweight, p.ekstra, p.maxrangekm);
        p = new secondclass(2, 250, 100, null, null);
        ss.insertPackageClassInfo(p.packclass, p.maxsize, p.maxweight, p.ekstra, p.maxrangekm);
        p = new thirdclass(3, 1000, 1000, "hajoaa", null);
        ss.insertPackageClassInfo(p.packclass, p.maxsize, p.maxweight, p.ekstra, p.maxrangekm);

        ss.readCreatedPackages();
    }

    /**
     * Luodun paketin täydellinen poisto.
     */
    public void createdpackdeletion(String pack) {
        String packclass = pack.substring(pack.length() - 1, pack.length());
        String[] packname = pack.split(",");
        String packnum = packname[0];
        int packid = stringToInt(packnum);
        //System.out.println(itemname + "    " + packclass);
        //int packid = ss.getPacketID(itemname, packclass);
        ss.removeFromStorage(packid);
        ss.removeFromCreatedPackages(packid);
        ss.removeFromPacketInSmartpost(packid);
        ss.readCreatedPackages();
    }

    public int stringToInt(String number) {
        int i = Integer.parseInt(number);
        return i;
    }

    public float stringToFloat(String number) {
        float f = Float.parseFloat(number);
        return f;
    }

    /**
     * Tarkistaa hajoaako esine
     */
    public String packageSendingCheck(String pack) {
        String packclass = pack.substring(pack.length() - 1, pack.length());
        String[] packname = pack.split(",");
        String packnum = packname[0];
        String errortext = "";
        int i = stringToInt(packclass);
        int packid = stringToInt(packnum);

        String breaks = ss.checkIfBreaks(packid);
        if (i == 1 && breaks.compareTo("true") == 0) {
            errortext = "Paketin sisältämä esine särkyi matkan aikana.";
        }
        if (i == 3 && breaks.compareTo("true") == 0) {
            errortext = "Paketin sisältämä esine särkyi matkan aikana.";
        }
        return errortext;
    }

    /**
     * Lähetettävän paketin koordinaattien haku, lisäys saapuviin paketteihin,
     * varastosta poisto ja listojen päivitys.
     */
    public int sendPackage(String pack) {
        String packclass = pack.substring(pack.length() - 1, pack.length());
        String[] packname = pack.split(",");
        String packn = packname[1].substring(1);
        String packnum = packname[0];
        String errortext = "";
        int i = stringToInt(packclass);
        int packid = stringToInt(packnum);
        String breaks = ss.checkIfBreaks(packid);
        ss.getLatLng(packid);
        int smartid = ss.getSmartpostid(DataHandler.toscriptlist.get(2));

        ss.insertArrivingPackage(packid, smartid);
        ss.removeFromStorage(packid);
        ss.readCreatedPackages();
        return i;
    }

    /**
     * Tarkistaa voidaanko pakettia luoda.
     */
    public int checkCompatibility(String item, int packclass, String start, String end) {
        //jos esine ei mahdu pakettiin palauttaa 1 muuten palauttaa 0
        int i = ss.getItemid(item);
        int itemsize = ss.getItemSize(i);
        int itemweight = ss.getItemWeight(i);
        int packagesize = ss.getPackclassMaxSize(packclass);
        int packageweight = ss.getPackclassMaxWeight(packclass);
        if (start == null || end == null) {
            System.out.println("Paketilla ei määränpäätä");
            return 2;
        }
        if (itemsize > packagesize || itemweight > packageweight) {
            System.out.println("Esine ei mene pakettiin");
            return 1;
        } else {
            return 0;
        }
    }

    public ObservableList getCityoblist() {
        ObservableList<String> oblist;
        oblist = ss.readCities();
        return oblist;
    }

    public ObservableList getCreatedPackageoblist() {
        ObservableList<String> oblist;
        oblist = Storage.createdpackageoblist;
        return oblist;
    }

    public ArrayList getCoordinatesList(String startpoint, String endpoint) {
        ArrayList<String> arlist;
        arlist = ss.getCoordinates(startpoint, endpoint);
        return arlist;
    }

    public ObservableList getItemsoblist() {
        ObservableList<String> oblist;
        oblist = DataHandler.itemoblist;
        return oblist;
    }

    public ObservableList getCreatedPostoblist() {
        ObservableList<String> oblist;
        oblist = DataHandler.createdpostsoblist;
        return oblist;
    }

    public ObservableList getPackageClassoblist() {
        ObservableList<Integer> oblist;
        oblist = DataHandler.packageclassoblist;
        return oblist;
    }

    public ArrayList getAddToMapList() {
        ArrayList<String> arlist;
        arlist = DataHandler.addtomaplist;
        return arlist;
    }

    public ArrayList<String> getToScriptList() {
        ArrayList<String> arlist;
        arlist = DataHandler.toscriptlist;
        return arlist;
    }

    public ObservableList getItemData() {
        ObservableList<Item> oblist;
        oblist = ss.getItemsData();
        return oblist;
    }

    public ObservableList getSmartpostData() {
        ObservableList<SmartPost> oblist;
        oblist = ss.readSmartpostData();
        return oblist;
    }

    public ObservableList getCreatedPackageData() {
        ObservableList<Createdpackage> oblist;
        oblist = ss.getCreatedPackageData();
        return oblist;
    }

    /**
     * Tableviewiä varten tehty luokka johon kerätään pakettien tiedot
     */
    public static class Createdpackage {

        private final SimpleIntegerProperty Packageid;
        private final SimpleIntegerProperty Packageclass;
        private final SimpleStringProperty Itemname;
        private final SimpleStringProperty Startpoint;
        private final SimpleStringProperty Destination;
        private final SimpleStringProperty Triplenght;
        private final SimpleStringProperty CurrentLocation;
        private final SimpleStringProperty CurrentAction;
        private final SimpleStringProperty Time;

        public Createdpackage(int id, int classnum, String item, String start,
                String end, String lenght, String location, String action, String time) {
            this.Packageid = new SimpleIntegerProperty(id);
            this.Packageclass = new SimpleIntegerProperty(classnum);
            this.Itemname = new SimpleStringProperty(item);
            this.Startpoint = new SimpleStringProperty(start);
            this.Destination = new SimpleStringProperty(end);
            this.Triplenght = new SimpleStringProperty(lenght);
            this.CurrentLocation = new SimpleStringProperty(location);
            this.CurrentAction = new SimpleStringProperty(action);
            this.Time = new SimpleStringProperty(time);
        }

        public String getCurrentAction() {
            return CurrentAction.get();
        }

        public String getCurrentLocation() {
            return CurrentLocation.get();
        }

        public int getPackageid() {
            return Packageid.get();
        }

        public int getPackageclass() {
            return Packageclass.get();
        }

        public String getItemname() {
            return Itemname.get();
        }

        public String getStartpoint() {
            return Startpoint.get();
        }

        public String getDestination() {
            return Destination.get();
        }

        public String getTriplenght() {
            return Triplenght.get();
        }

        public String getTime() {
            return Time.get();
        }
    }
}
