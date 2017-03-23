package com.nikolaev.clientside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 *
 * @author Adm_54
 */
public class ProcessConnect {
   protected String host;
   protected String port;

   public ProcessConnect(String Host, String Port){
       host=Host;
       port=Port;
   }

   public String getInformaShionByServer(String qery){
      Socket s=null;
      String answer="";
      try {
           s = new Socket(host,Integer.valueOf(port));
      } catch (IOException ex) {
          showErrorMassage("Ошибка при поключении", ex.getMessage());
      }
      if(s!=null){
          try {
              BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
              PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream())); 
              out.println(qery); 
              out.flush(); 
              answer = input.readLine();
              out.println("{\"action\":\"quit\"}"); 
              out.flush(); 
          } catch (IOException ex) {
              showErrorMassage("Ошибка чтения", ex.getMessage());
          }
      }
      return answer;
    }

    protected void showErrorMassage(String header,String message){
        JFrame frame = new JFrame("JOptionPane showMessageDialog example");
        JOptionPane.showMessageDialog(frame, message, header,JOptionPane.ERROR_MESSAGE);
    }
}