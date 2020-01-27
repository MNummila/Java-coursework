/*
 *Tämä luokka sisältää smartpost olion ja osaluokan geograafisesta datasta.
 */
package javafxkesaharkkatyo;

/**
 *
 * @author n4121
 */
public class SmartPost {

    private final Integer IdNumber;
    private final String Postnumber;
    private final String City;
    private final String Address;
    private final String Availability;
    private final String Postoffice;
    private final Boolean Created;
    private final geopoint gp;

    public SmartPost(String post, String cit, String addr, String times,
            String office, int id, boolean Createdd, String lat, String lng) {
        Created = Createdd;
        Postnumber = post;
        City = cit;
        Address = addr;
        Availability = times;
        Postoffice = office;
        IdNumber = id;
        gp = new geopoint(lat, lng);
    }

    public int getIdNumber() {
        return IdNumber;
    }

    public String getPostnumber() {
        return Postnumber;
    }

    public String getCity() {
        return City;
    }

    public String getAddress() {
        return Address;
    }

    public String getAvailability() {
        return Availability;
    }

    public String getPostoffice() {
        return Postoffice;
    }

    public boolean getCreated() {
        return Created;
    }

    public geopoint getGp() {
        return gp;
    }

    public class geopoint {

        private final String latitude;
        private final String longitude;

        private geopoint(String x, String y) {
            latitude = x;
            longitude = y;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }
    }
}
