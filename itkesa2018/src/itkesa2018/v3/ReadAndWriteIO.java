/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itkesa2018.v3;

/**
 *
 * @author n4121
 */
import static java.awt.SystemColor.text;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author n4121
 */
public class ReadAndWriteIO {
    private String filename = "testi.txt";
    private String s;
    public ReadAndWriteIO(String name){
        filename = name;
}

    public String ReadFile(){
        
        try(BufferedReader in = new BufferedReader(new FileReader(filename))) {
            while(( s = in.readLine()) != null) {
                System.out.println(s);
            }
            in.close();
            
        }catch(IOException i){
        }
        return s;
    }
    
    public void WriteFile(String text){
        try(BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {
            out.write(text);
        }catch (IOException i){
        }
    }
    public void readAndWrite(String inputfile, String outputfile) throws FileNotFoundException, IOException{
        
        BufferedReader in;
        BufferedWriter out;
        
        in = new BufferedReader(new FileReader(inputfile));
        out = new BufferedWriter(new FileWriter(outputfile));
        String text;
        while ((text = in.readLine()) != null){
            if(text.length()<30){
                if(text.isEmpty()==false){
                    if((text.trim()).isEmpty()==false){
                        if(text.contains("v")==true){
                            out.write(text + "\n");
                        }else{
                            continue;
                        }
                    }else{
                        continue;
                    }
                }else{
                    continue;
                }
            }else{
                continue;
            }
            
        }
        in.close();
        out.close();
    }
    public void readZip(String inputfile) throws IOException{
        String s = null;
        ZipFile zipFile = new ZipFile(inputfile);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        
        //while(entries.hasMoreElements()){
            ZipEntry entry = entries.nextElement();            
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
            
            while((s = br.readLine())!=null){
            System.out.println(s);
            }
    
    }
}