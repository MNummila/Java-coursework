/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesav3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author n4121
 */
public class WebDoc {
    private Document doc;
    public ArrayList<Theatre> theatrelist = new ArrayList();
    public ArrayList<Film> filmlist = new ArrayList();
 
    public void WebDoc(){
        
    }
    
    public String getXML(String weburl) {
        try {
            URL url = new URL(weburl);
            
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
            
            String content = "";
            String line;
            
            while((line = br.readLine()) != null){
                content += line + "\n";
            }
            return content;
        } catch (IOException ex) {
            Logger.getLogger(WebDoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Virhe";
    }
    
    public Document docBuilderi(String content){
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            
            doc = dBuilder.parse(new InputSource(new StringReader(content)));
            doc.getDocumentElement().normalize();
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(WebDoc.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }
    
    public void parseTheatreData(Document doc){
        NodeList nodesTheatreList =  doc.getElementsByTagName("TheatreArea");
        
        for(int i = 0; i<nodesTheatreList.getLength();i++){
            Node nodeTheatreArea = nodesTheatreList.item(i);
            
            if(nodeTheatreArea.getNodeType() == Node.ELEMENT_NODE){
                Element elementTheatreArea = (Element) nodeTheatreArea;

                NodeList idList = elementTheatreArea.getElementsByTagName("ID");
                Element idElement = (Element)idList.item(0);               
                
                NodeList nameList = elementTheatreArea.getElementsByTagName("Name");
                Element nameElement =  (Element)nameList.item(0);
                
                Theatre tre = new Theatre();
                tre.setId(idElement.getChildNodes().item(0).getNodeValue().trim());
                tre.setName(nameElement.getChildNodes().item(0).getNodeValue().trim());
                theatrelist.add(tre);
            }
        }    
        
    }
    public void parseByTheatreAndDayData(Document doc){
        NodeList nodesFilmsList =  doc.getElementsByTagName("Show");
        
        for(int i = 0; i<nodesFilmsList.getLength();i++){
            Node nodeFilms = nodesFilmsList.item(i);
            
            if(nodeFilms.getNodeType() == Node.ELEMENT_NODE){
                Element elementFilm = (Element) nodeFilms;

                NodeList titleList = elementFilm.getElementsByTagName("Title");
                Element titleElement =  (Element)titleList.item(0);
                String title = titleElement.getChildNodes().item(0).getNodeValue().trim();
                
                NodeList timeList = elementFilm.getElementsByTagName("dttmShowStart");
                Element timeElement =  (Element)timeList.item(0);
                String time = timeElement.getChildNodes().item(0).getNodeValue().trim().substring(11,16);

                NodeList TheatreAndAuditoriumList = elementFilm.getElementsByTagName("TheatreAndAuditorium");
                Element TheatreAndAuditoriumElement = (Element)TheatreAndAuditoriumList.item(0);
                String TheatreAndAuditorium = TheatreAndAuditoriumElement.getChildNodes().item(0).getNodeValue().trim();
                
                Film fil = new Film();
                fil.setTitle(title);
                fil.setStartTime(time);
                fil.setTheatreAndAuditorium(TheatreAndAuditorium);
                filmlist.add(fil);
            }
        }
    }
    public void checkTimes(String starttime, String endtime){
        try {
            SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
            Date start = parser.parse(starttime);
            Date end = parser.parse(endtime);
            System.out.println(start);
            System.out.println(end);
            
            for(int i=0;i<filmlist.size();i++){
                Date current = parser.parse(filmlist.get(i).getStartTime());
                System.out.println(current);
                if (current.compareTo(start) < 0){
                    filmlist.remove(i);
                    i--;
                    System.out.println("POISTETTU: checktimes 1");
                }
                else if(current.compareTo(end) > 0){
                    filmlist.remove(i);
                    i--;
                    System.out.println("POISTETTU: checktimes 2");
                }
            } 
        } catch (ParseException ex) {
            Logger.getLogger(WebDoc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void checkNames(String filmname){
        for (int i = 0;i<filmlist.size();i++){
            String title = filmlist.get(i).getTitle().toLowerCase();
            String film = filmname.toLowerCase();
            if (title.contains(film)){
                System.out.println("haun sisällä");
            }
            else{
                filmlist.remove(i);
                i--;
                System.out.println("POISTETTU: checknames");
            }
        }
    }
    public void combineStrings(int x){
        String time;
        String name;
        String auditorium;
        String endstring = "";
        for(int i = 0;i<filmlist.size();i++){
            time = filmlist.get(i).getStartTime();
            name = filmlist.get(i).getTitle();
            auditorium = filmlist.get(i).getTheatreAndAuditorium();
            if(x == 1){
                endstring = time+"\t"+auditorium;
            }
            if(x == 0){
                endstring = time+"\t"+name;
            }
            filmlist.get(i).setStartTime(endstring);
        }
    }
    
    public ObservableList arrayToObservableListTheatre(){
        ObservableList<String> oblist;
        oblist = FXCollections.observableArrayList();
        for(Theatre theatre : theatrelist){
            oblist.add(theatre.getName());
        }
        return oblist;
    }
    public ObservableList arrayToObservableListTimes(){
        ObservableList<String> oblist;
        oblist = FXCollections.observableArrayList();
        for (Film fil : filmlist){
            oblist.add(fil.getStartTime());
        }
        return oblist;
    }
}