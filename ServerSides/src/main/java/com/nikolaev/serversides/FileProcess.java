package com.nikolaev.serversides;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Adm_54
 */
public class FileProcess {
    private static final String path= "D://Deposits";
    List<String>files;
    
    
    public  FileProcess(){
      files=new ArrayList<String>();
      File f = new File(this.path);
      MyFileFilter filter = new MyFileFilter();
      File[] list = f.listFiles(filter);
      for(int i = 0; i<list.length; i++) {
        files.add(list[i].getName());
      }
    }
    
    public String addPerson(JSONObject jsonObj){  
        System.out.println(files.toString());
        if(!files.contains(String.valueOf(jsonObj.get("id"))+".txt")){
            String fileName=String.valueOf(jsonObj.get("id"));
            FileWriter writer=null;
            String error=checkDataToAdd(jsonObj);             
            if(error.equals("")){
                try {
                    writer= new FileWriter(path+"//"+fileName+".txt");
                    writer.write(generateDataToAdd(jsonObj).toJSONString());
                    writer.flush();
                    writer.close();  
                } catch (IOException ex) {
                    Logger.getLogger(FileProcess.class.getName()).log(Level.SEVERE, null, ex);
                }
                return "OK";
            }else{
               return "Fail:"+error; 
            }
        }else{
           return "Fail: Данные по даному счету уже существую в системе."+System.lineSeparator();
        }
    }
    
    public String  readList(){
       String results="В базе находится такие вклады :\n";
       JSONParser parser = new JSONParser();
        try {
            for (String fileName: files) {
                JSONObject object =(JSONObject) parser.parse(new FileReader(path+"/"+fileName));
                results+= "Банк : "+String.valueOf(object.get("bank"))+"; ";
                results+= "Страна : "+String.valueOf(object.get("country"))+"; ";
                results+= "Тип : "+String.valueOf(object.get("type"))+"; ";
                results+= "Вкладчик : "+String.valueOf(object.get("depositor"))+"; ";
                results+= "Номер счета - "+String.valueOf(object.get("id"))+"; ";
                results+= "Сума вклада : "+String.valueOf(object.get("amount_on_deposit"))+"; ";
                results+= "Годовой процент : "+String.valueOf(object.get("profitability"))+"; ";
                results+= "Срок вклада : "+String.valueOf(object.get("time_constraints"))+"\n";
            }          
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
       return results;
    }
    
    public String  getInfoByDepositor(JSONObject obj){
       String results="";
       int count=0;
       JSONParser parser = new JSONParser();
        try {
            for (String fileName: files) {
                JSONObject object =(JSONObject) parser.parse(new FileReader(path+"/"+fileName));
                if(String.valueOf(object.get("depositor")).equals(String.valueOf(obj.get("name")))){
                    count++;
                    results+= "Банк : "+String.valueOf(object.get("bank"))+"; ";
                    results+= "Страна : "+String.valueOf(object.get("country"))+"; ";
                    results+= "Тип : "+String.valueOf(object.get("type"))+"; ";
                    results+= "Вкладчик : "+String.valueOf(object.get("depositor"))+"; ";
                    results+= "Номер счета - "+String.valueOf(object.get("id"))+"; ";
                    results+= "Сума вклада : "+String.valueOf(object.get("amount_on_deposit"))+"; ";
                    results+= "Годовой процент : "+String.valueOf(object.get("profitability"))+"; ";
                    results+= "Срок вклада : "+String.valueOf(object.get("time_constraints"))+"\n";
                }
            }          
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        
       return (results.equals(""))?"Счетов владельцем которых есть пользователь с именем "
               +String.valueOf(obj.get("name"))+" не найдено":"Счетов владельцем которых есть пользователь с именем "
               +String.valueOf(obj.get("name"))+" найдено "+String.valueOf(count)+" записей : \n"+results;
    }
    
    
     public String  getInfoByType(JSONObject obj){
       String results="";
       int count=0;
       JSONParser parser = new JSONParser();
        try {
            for (String fileName: files) {
                JSONObject object =(JSONObject) parser.parse(new FileReader(path+"/"+fileName));
                if(String.valueOf(object.get("type")).equals(String.valueOf(obj.get("type")))){
                    count++;
                    results+= "Банк : "+String.valueOf(object.get("bank"))+"; ";
                    results+= "Страна : "+String.valueOf(object.get("country"))+"; ";
                    results+= "Тип : "+String.valueOf(object.get("type"))+"; ";
                    results+= "Вкладчик : "+String.valueOf(object.get("depositor"))+"; ";
                    results+= "Номер счета - "+String.valueOf(object.get("id"))+"; ";
                    results+= "Сума вклада : "+String.valueOf(object.get("amount_on_deposit"))+"; ";
                    results+= "Годовой процент : "+String.valueOf(object.get("profitability"))+"; ";
                    results+= "Срок вклада : "+String.valueOf(object.get("time_constraints"))+"\n";
                }
            }          
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        
       return (results.equals(""))?"Счетов с типом вклада "+String.valueOf(obj.get("type"))
               +" не найдено":"Счетов с типом вклада "+String.valueOf(obj.get("type"))
               +" найдено "+String.valueOf(count)+" записей : \n"+results;
    }
    
    public String  getInfoByBank(JSONObject obj){
       String results="";
       int count=0;
       JSONParser parser = new JSONParser();
        try {
            for (String fileName: files) {
                JSONObject object =(JSONObject) parser.parse(new FileReader(path+"/"+fileName));
                if(String.valueOf(object.get("bank")).equals(String.valueOf(obj.get("bank")))){
                    count++;
                    results+= "Банк : "+String.valueOf(object.get("bank"))+"; ";
                    results+= "Страна : "+String.valueOf(object.get("country"))+"; ";
                    results+= "Тип : "+String.valueOf(object.get("type"))+"; ";
                    results+= "Вкладчик : "+String.valueOf(object.get("depositor"))+"; ";
                    results+= "Номер счета - "+String.valueOf(object.get("id"))+"; ";
                    results+= "Сума вклада : "+String.valueOf(object.get("amount_on_deposit"))+"; ";
                    results+= "Годовой процент : "+String.valueOf(object.get("profitability"))+"; ";
                    results+= "Срок вклада : "+String.valueOf(object.get("time_constraints"))+"\n";
                }
            }          
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
        
       return (results.equals(""))?"Счетов с банком "+String.valueOf(obj.get("bank"))
               +" не найдено":"Счетов с банком "+String.valueOf(obj.get("bank"))
               +" найдено "+String.valueOf(count)+" записей : \n"+results;
    }
    
    
    public String  countSumDeposits(){
       double sum=0;
       String results="Общая сума вкладов по всем депозитам : ";
       JSONParser parser = new JSONParser();
        try {
            for (String fileName: files) {
                JSONObject object =(JSONObject) parser.parse(new FileReader(path+"/"+fileName));
              sum+=(Double) object.get("amount_on_deposit");
            }          
        }catch (IOException ex) {
            System.out.println(ex.getMessage());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }   
       return results+String.valueOf(sum) ;
    }
    
    
    public String  getInfoByAcountId(JSONObject obj){
        
       String results="";
       if(!files.contains(String.valueOf(obj.get("id"))+".txt")){
           results="Error - В системе не найдано записи с номером счета - "+String.valueOf(obj.get("id"));
       }else{
           results="";
           JSONParser parser = new JSONParser();
           try {
                JSONObject object =(JSONObject) parser.parse(new FileReader(path+"/"
                    +String.valueOf(obj.get("id"))+".txt"));
                results=" В системе найдено по номеру счета "+String.valueOf(obj.get("id"))+" такая запись : \n";
                results+= "Банк : "+String.valueOf(object.get("bank"))+"; ";
                results+= "Страна : "+String.valueOf(object.get("country"))+"; ";
                results+= "Тип : "+String.valueOf(object.get("type"))+"; ";
                results+= "Вкладчик : "+String.valueOf(object.get("depositor"))+"; ";
                results+= "Номер счета - "+String.valueOf(object.get("id"))+"; ";
                results+= "Сума вклада : "+String.valueOf(object.get("amount_on_deposit"))+"; ";
                results+= "Годовой процент : "+String.valueOf(object.get("profitability"))+"; ";
                results+= "Срок вклада : "+String.valueOf(object.get("time_constraints"))+"\n";  
           }catch (IOException ex) {
              System.out.println(ex.getMessage());
           } catch (ParseException ex) {
              System.out.println(ex.getMessage());
            }
       }
       return results ;
    }
    
    public String  deleByAcountId(JSONObject obj){
        
       String results="";
       if(!files.contains(String.valueOf(obj.get("id"))+".txt")){
           results="Error - В системе не найдано записи с номером счета - "+String.valueOf(obj.get("id"));
       }else{
           
           File file = new File(path+"/"+String.valueOf(obj.get("id"))+".txt");
           if(file.delete()){
                results="Запись с номером счета - "+String.valueOf(obj.get("id"))+" была удалена.";
           }else{
               results="Запись с номером счета - "+String.valueOf(obj.get("id"))+" не удалось удалить.";
           }
       }
       return results ;
    }
    
    public String  countDeposit(){
       return "Количество доступных вкладов в системе  : "+files.size();
    }
    
        
    protected String checkDataToAdd(JSONObject obj){
        String erMes="";
        if((String.valueOf(obj.get("id")).replaceAll(" ", "")).equals("")){
            erMes+="Номер счета не может быть пустым ."+System.lineSeparator();
        }
       if((String.valueOf(obj.get("country")).replaceAll(" ", "")).equals("")){
            erMes+="Страна регистрация не заполнена ."+System.lineSeparator();
        }
        if((String.valueOf(obj.get("type")).replaceAll(" ", "")).equals("")){
            erMes+="Не заполнено поле тип вклада ."+System.lineSeparator();
        }
        if((String.valueOf(obj.get("depositor")).replaceAll(" ", "")).equals("")){
            erMes+="Имя вкладчика не заполено ."+System.lineSeparator();
        }
        if(!(String.valueOf(obj.get("amount_on_deposit")).replaceAll(" ", "")).equals("")){
            
            Double amount=0.0;
           try{
               amount=Double.valueOf(String.valueOf(obj.get("amount_on_deposit")).replaceAll(" ", ""));
           }catch(NumberFormatException ex ){
               erMes+="Ошибка преобразования параметра размер вклада."+System.lineSeparator();    
           }
            if(amount<=0){
               erMes+="Размер вклада должен быть больше нуля ."+System.lineSeparator();    
            }
        }else{
            erMes+="Не заполнен размер вклада ."+System.lineSeparator();
        }
        if(!(String.valueOf(obj.get("profitability")).replaceAll(" ", "")).equals("")){
            Double profit=0.0;
            try{
                profit=Double.parseDouble(String.valueOf(obj.get("profitability")).replaceAll(" ", ""));
            }catch(NumberFormatException ex ){
                erMes+="Ошибка преобразования параметра размер годовой процентной ставки ."+System.lineSeparator();    
            }
            if(profit<=0){
               erMes+="Размер годовой процентной ставки должен быть больше нуля ."+System.lineSeparator();    
            }
        }else{
            erMes+="Не заполнен годовой процентной ставки ."+System.lineSeparator();
        }
        if(!(String.valueOf(obj.get("time_constraints")).replaceAll(" ", "")).equals("")){
            
            Double time=0.0;
            try{
                time=Double.parseDouble(String.valueOf(obj.get("time_constraints")).replaceAll(" ", ""));
            }catch(NumberFormatException ex ){
                erMes+="Ошибка преобразования параметра срок вклада."+System.lineSeparator();        
            }
            if(time<=0){
               erMes+="Срок вклада не может быть нулевым ."+System.lineSeparator();    
            }
        }else{
            erMes+="Не заполнен срок вклада ."+System.lineSeparator();
        }
        return erMes;
    }
    protected JSONObject generateDataToAdd(JSONObject obj){
        JSONObject resObj=new JSONObject();
        
        resObj.put("id",String.valueOf(obj.get("id")));
        resObj.put("bank",String.valueOf(obj.get("bank")));
        resObj.put("country",String.valueOf(obj.get("country")));
        resObj.put("type",String.valueOf(obj.get("type")));
        resObj.put("depositor",String.valueOf(obj.get("depositor")));
        resObj.put("amount_on_deposit",Double.parseDouble(String.valueOf(obj.get("amount_on_deposit")).replaceAll(" ", "")));
        resObj.put("profitability",Double.parseDouble(String.valueOf(obj.get("profitability")).replaceAll(" ", "")));
        resObj.put("time_constraints",Double.parseDouble(String.valueOf(obj.get("time_constraints")).replaceAll(" ", "")));
       
        return resObj;
    }
}
