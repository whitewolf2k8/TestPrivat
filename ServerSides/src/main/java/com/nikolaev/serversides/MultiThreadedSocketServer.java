package com.nikolaev.serversides;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class MultiThreadedSocketServer {

    ServerSocket myServerSocket;
    boolean ServerOn = true;

    public MultiThreadedSocketServer() 
    { 
        try 
        { 
            myServerSocket = new ServerSocket(11111); 
        } 
        catch(IOException ioe) 
        { 
            System.out.println("Could not create server socket on port 11111. Quitting."); 
            System.exit(-1); 
        } 


        Calendar now = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz");
        System.out.println("It is now : " + formatter.format(now.getTime()));

        while(ServerOn) 
        {                        
            try 
            { 
                Socket clientSocket = myServerSocket.accept(); 
                ClientServiceThread cliThread = new ClientServiceThread(clientSocket);
                cliThread.start(); 
            } 
            catch(IOException ioe) 
            { 
                System.out.println("Exception encountered on accept. Ignoring. Stack Trace :"); 
                ioe.printStackTrace(); 
            } 
        }

        try 
        { 
            myServerSocket.close(); 
            System.out.println("Server Stopped"); 
        } 
        catch(Exception ioe) 
        { 
            System.out.println("Problem stopping server socket"); 
            System.exit(-1); 
        } 

    } 

    public static void main (String[] args) 
    { 
        new MultiThreadedSocketServer();        
    } 


    class ClientServiceThread extends Thread 
    { 
        Socket myClientSocket;
        boolean m_bRunThread = true; 

        public ClientServiceThread() 
        { 
            super(); 
        } 

        ClientServiceThread(Socket s) 
        { 
            myClientSocket = s; 
        } 

        public void run() 
        {            
            BufferedReader in = null; 
            PrintWriter out = null; 

            System.out.println("Accepted Client Address - " + myClientSocket.getInetAddress().getHostName()); 

            try 
            {                                
                in = new BufferedReader(new InputStreamReader(myClientSocket.getInputStream())); 
                out = new PrintWriter(new OutputStreamWriter(myClientSocket.getOutputStream())); 
                while(m_bRunThread) 
                {                    
                    String clientCommand = in.readLine(); 
                   
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(clientCommand);
                    JSONObject jsonObj = (JSONObject) obj;
                
                    System.out.println("");
                    if(!ServerOn) 
                    { 
                        System.out.print("Server has already stopped"); 
                        out.println("Server has already stopped"); 
                        out.flush(); 
                        m_bRunThread = false;   
                    } 

                    if(String.valueOf(jsonObj.get("action")).equalsIgnoreCase("quit")) { 
                        m_bRunThread = false;   
                        System.out.print("Stopping client thread for client : "); 
                    } else if(String.valueOf(jsonObj.get("action")).equalsIgnoreCase("add")){
                        System.out.println("I was start generete filex");
                        FileProcess files=new FileProcess();
                        JSONObject objRes=new JSONObject();
                        objRes.put("resStr",files.addPerson(jsonObj));
                        out.println(objRes.toJSONString()); 
                        out.flush(); 
                    } else if(String.valueOf(jsonObj.get("action")).equalsIgnoreCase("list")){
                        FileProcess files=new FileProcess();
                        JSONObject objRes=new JSONObject();
                        objRes.put("resStr",files.readList());
                        out.println(objRes.toJSONString()); 
                        out.flush(); 
                    }else if(String.valueOf(jsonObj.get("action")).equalsIgnoreCase("sum")){
                        FileProcess files=new FileProcess();
                        JSONObject objRes=new JSONObject();
                        objRes.put("resStr",files.countSumDeposits());
                        out.println(objRes.toJSONString()); 
                        out.flush(); 
                    }else if(String.valueOf(jsonObj.get("action")).equalsIgnoreCase("count")){
                        FileProcess files=new FileProcess();
                        JSONObject objRes=new JSONObject();
                        objRes.put("resStr",files.countDeposit());
                        out.println(objRes.toJSONString()); 
                        out.flush(); 
                    }else if(String.valueOf(jsonObj.get("action")).equalsIgnoreCase("info account")){
                        FileProcess files=new FileProcess();
                        JSONObject objRes=new JSONObject();
                        objRes.put("resStr",files.getInfoByAcountId(jsonObj));
                        out.println(objRes.toJSONString()); 
                        out.flush(); 
                    }else if(String.valueOf(jsonObj.get("action")).equalsIgnoreCase("info depositor")){
                        FileProcess files=new FileProcess();
                        JSONObject objRes=new JSONObject();
                        objRes.put("resStr",files.getInfoByDepositor(jsonObj));
                        out.println(objRes.toJSONString()); 
                        out.flush(); 
                    }else if(String.valueOf(jsonObj.get("action")).equalsIgnoreCase("show type")){
                        FileProcess files=new FileProcess();
                        JSONObject objRes=new JSONObject();
                        objRes.put("resStr",files.getInfoByType(jsonObj));
                        out.println(objRes.toJSONString()); 
                        out.flush(); 
                    }else if(String.valueOf(jsonObj.get("action")).equalsIgnoreCase("show bank")){
                        FileProcess files=new FileProcess();
                        JSONObject objRes=new JSONObject();
                        objRes.put("resStr",files.getInfoByBank(jsonObj));
                        out.println(objRes.toJSONString()); 
                        out.flush(); 
                    }else if(String.valueOf(jsonObj.get("action")).equalsIgnoreCase("delete")){
                        FileProcess files=new FileProcess();
                        JSONObject objRes=new JSONObject();
                        objRes.put("resStr",files.deleByAcountId(jsonObj));
                        out.println(objRes.toJSONString()); 
                        out.flush(); 
                    }else
                    {
                       out.println("Server Says : " + clientCommand); 
                       JSONObject objRes=new JSONObject();
                       objRes.put("resStr","Команда не найдена "+jsonObj.get("action"));
                       out.println(objRes.toJSONString()); 
                       out.flush(); 
                    }
                } 
            } 
            catch(Exception e) 
            { 
                e.printStackTrace(); 
            } 
            finally 
            { 
                try 
                {                    
                    in.close(); 
                    out.close(); 
                    myClientSocket.close(); 
                    System.out.println("...Stopped"); 
                } 
                catch(IOException ioe) 
                { 
                    ioe.printStackTrace(); 
                } 
            } 
        } 
    } 
}