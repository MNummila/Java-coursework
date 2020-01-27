/*
 * Tämä luokka sisältää kaiken Xml:lään liittyvän.
 */
package javafxkesaharkkatyo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class XmlStuff {

    private Document doc;

    public void XmlStuff() {

    }

    /**
     * Lukee urlin osoittaman sivun tiedot stringiin
     */
    public String getXML(String weburl) {
        try {
            URL url = new URL(weburl);

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));

            String content = "";
            String line;

            while ((line = br.readLine()) != null) {
                content += line + "\n";
            }
            System.out.println("Stringi returnattu");
            return content;
        } catch (IOException ex) {
            Logger.getLogger(XmlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Virhe";
    }

    /**
     * rakentaa stringistä docin
     */
    public Document docBuilderi(String content) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            doc = dBuilder.parse(new InputSource(new StringReader(content)));
            doc.getDocumentElement().normalize();

        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(XmlStuff.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("doc buildattu");
        return doc;
    }

    /**
     * Parsee docin datan joka lisätään oliolistaan.
     */
    public void parseData(Document doc) {
        DataHandler.smartlist.clear();
        int counter = 0;
        NodeList nodesTheatreList = doc.getElementsByTagName("item");

        for (int i = 0; i < nodesTheatreList.getLength(); i++) {
            Node nodeTheatreArea = nodesTheatreList.item(i);

            if (nodeTheatreArea.getNodeType() == Node.ELEMENT_NODE) {
                Element elementTheatreArea = (Element) nodeTheatreArea;

                NodeList codelist = elementTheatreArea.getElementsByTagName("postalcode");
                Element codeElement = (Element) codelist.item(0);
                String code = codeElement.getChildNodes().item(0).getNodeValue();

                NodeList cityList = elementTheatreArea.getElementsByTagName("city");
                Element cityElement = (Element) cityList.item(0);
                String city = cityElement.getChildNodes().item(0).getNodeValue();

                NodeList addresslist = elementTheatreArea.getElementsByTagName("address");
                Element addressElement = (Element) addresslist.item(0);
                String address = addressElement.getChildNodes().item(0).getNodeValue();

                NodeList availabilityList = elementTheatreArea.getElementsByTagName("availability");
                Element availabilityElement = (Element) availabilityList.item(0);
                String availability = availabilityElement.getChildNodes().item(0).getNodeValue();

                NodeList officeList = elementTheatreArea.getElementsByTagName("name");
                Element officeElement = (Element) officeList.item(0);
                String office = officeElement.getChildNodes().item(0).getNodeValue();

                NodeList latList = elementTheatreArea.getElementsByTagName("lat");
                Element latElement = (Element) latList.item(0);
                String lat = latElement.getChildNodes().item(0).getNodeValue();

                NodeList lngList = elementTheatreArea.getElementsByTagName("lng");
                Element lngElement = (Element) lngList.item(0);
                String lng = lngElement.getChildNodes().item(0).getNodeValue();

                DataHandler.smartlist.add(new SmartPost(code, city,
                        address, availability, office, counter, false, lat, lng));
                counter++;
            }
        }
        System.out.println("data parsettu");
    }
}
