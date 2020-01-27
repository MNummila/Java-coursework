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
public class Theatre {
    private String name;
    private String id;
    private String searchID;
    
    private void Theatre(){
        
    }
    public String getName(){
        return name;
    }
    public String getId(){
        return id;
    }
    public void setName(String newname){
        name = newname;
    }
    public void setId(String newid){
        id = newid;
    }
    public void setSearchID(String x){
        searchID = x;
    }
    public String getSearchID(){
        return searchID;
    }
}
