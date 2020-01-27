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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author n4121
 */
public class Mainclass {
    public static void main(String[] args) throws IOException{
        String filename, inputText, outputText;
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
       // System.out.println("Anna tiedostolle nimi: ");
       // filename = br.readLine();
          filename = "input.txt";
        
      //  System.out.println("Anna tekstiÃ¤: ");
      //  inputText = br.readLine();
        
        ReadAndWriteIO rwio = new ReadAndWriteIO(filename);
        
        //rwio.WriteFile(inputText);
        
        //outputText = rwio.ReadFile();
        //System.out.println(outputText);
        
       // rwio.readAndWrite("input.txt", "output.txt");
       rwio.readZip("zipinput.zip");
    }
    
}
