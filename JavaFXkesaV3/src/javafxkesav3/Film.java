/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxkesav3;

/**
 *
 * @author n4121
 */
public class Film {
    private String title;
    private String starttime;
    private String theatreAndAuditorium;

    public void Film(){
        
    }
    public String getTitle(){
        return title;
    }
    public String getStartTime(){
        return starttime;
    }
    public void setTitle(String x){
        title = x;
    }
    public void setStartTime(String x){
        starttime = x;
    }
    public void setTheatreAndAuditorium(String x){
        theatreAndAuditorium = x;
    }
    public String getTheatreAndAuditorium(){
        return theatreAndAuditorium;
    }
}
