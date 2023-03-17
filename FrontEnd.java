package p4;

import p4.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

//This class is for creating all de screens in auto or browser mode
public class FrontEnd {

    //The initial screen (in auto mode) that is called when the pphase is 1 or there is no pphase
    public void respAutoPhase01(HttpServletResponse res) {
        //The screen is in xml format
        res.setContentType("text/xml");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<service>");
            out.println("<status>OK</status>");
            out.println("</service>");
        }catch(Exception e){

        }
    }

    //The screen (in auto mode) that is called when the user wants to see the error and fatal error files. The pphase is 02
    public void respAutoPhase02(HttpServletResponse res,ArrayList<String> errorsFile,ArrayList<String> fatalerrorsFile) {
        //The screen is in xml format
        res.setContentType("text/xml");
        res.setCharacterEncoding("utf-8");

        try{
            PrintWriter out= res.getWriter();
            
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<wrongDocs>");
            out.println("<errors>");
            //To print all the files of the list
            for (int index = 0; index < errorsFile.size(); index++) {
                out.println("<error><file>" +errorsFile.get(index)+ "</file></error>" );

            }
            out.println("</errors>");

            out.println("<fatalerrors>");
            for (int index = 0; index < fatalerrorsFile.size(); index++) {
                out.println("<fatalerror><file>"+fatalerrorsFile.get(index)+"</file></fatalerror>");
            }
            out.println("</fatalerrors>");
            out.println("</wrongDocs>");
        }catch(Exception e){
    
        }      
    }

    //This screen (in auto mode) is called when the list of all the found countries has to be displayed
    public void respAutoPhase11(HttpServletResponse res, String passwd, ArrayList<String> countries) {
        //The screen is in xml format
        res.setContentType("text/xml");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<countries>");
            //To print all the countries
            for (int index = 0; index < countries.size(); index++) {
                out.println("<country>" + countries.get(index) + "</country>");
            }
    
            out.println("</countries>");
        }catch(Exception e){
    
        }
    }

    //This screen (in auto mode) is called when the list of all found albums of a certain country has to be displayed
    public void respAutoPhase12(HttpServletResponse res, ArrayList<Album> listAlbums) {
        //The screen is in xml format
        res.setContentType("text/xml");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<albums>");
    
            //To print all the found albums
            for (int x = 0; x < listAlbums.size(); x++) {
                Album album= listAlbums.get(x) ;
                //To print the required attributes
                out.println("<album year='"+album.getYear()+"' performer= '"+album.getPerformer()+"' review= '"+album.getReview()+"'>"+album.getName()+"</album>");
            }
            out.println("</albums>");
        }catch(Exception e){
    
        }
    }

    //This screen (in auto mode) is called when the list of all pop songs of a certain album and country has to be displayed
    public void respAutoPhase13(HttpServletResponse res, ArrayList<Song> listSongs) {
        //The screen is in xml format
        res.setContentType("text/xml");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<songs>");
    
            for (int i = 0; i< listSongs.size(); i++) {
                Song song= listSongs.get(i);
                //To print the required attributes
                out.println("<song lang= '"+song.getLang()+"' genres= '"+song.getGenre()+"' composer= '"+song.getComposer()+"'>"+song.getTitle()+"</song>");
            }
            out.println("</songs>");
        }catch(Exception e){
    
        }
    }

    //The screen (in auto mode) that is called when there is a parameter missing (pcountry or paid)
    public void respAutoParamMissing(HttpServletResponse res, String param) {
        //The screen is in xml format
        res.setContentType("text/xml");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<wrongRequest>no param:"+param+"</wrongRequest>");
        }catch(Exception e){
    
        }
    }

    //The screen (in auto mode) that is called when there isn't password (the password is null)
    public void respAutoPMissing(HttpServletResponse res) {
        //The screen is in xml format
        res.setContentType("text/xml");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<wrongRequest>no passwd</wrongRequest>");
        }catch(Exception e){
    
        }
    }

    //The screen (in auto mode) that is called when the password is wrong
    public void respAutoPWrong(HttpServletResponse res) {
        //The screen is in xml format
        res.setContentType("text/xml");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<?xml version='1.0' encoding='utf-8' ?>");
            out.println("<wrongRequest>bad passwd</wrongRequest>");
        }catch(Exception e){
    
        }
    }

    //The initial screen (in browser mode) that is called when the pphase is 1 or there is no pphase
    public void doGetPhase01(HttpServletResponse res, String passwd) { 
        //The screen is in html format
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset='utf-8'>");
            out.println("<title>Music information service</title>");
            out.println("<link href='./p4/p4.css' rel='stylesheet'>");
            out.println("</head><body>");
            out.println("<h1>Music information service</h1>");
            out.println("<h2>Please, select a query: </h2>");
            //The href is used to change screens
            out.println("<ul>");
            out.println("<li><a href='?p="+passwd+"&pphase=11'>Query 1: Pop songs of an Album of a Country</a></li>");
            out.println("<li><a href='?p="+passwd+"&pphase=02'>Show error files</a></li>");
            out.println("</ul>");
            out.println("<h4>Henar Mari&ntilde;o Bodel&oacute;n</h4>");
            out.println("</body></html>");
        }catch(Exception e){
    
        }
    }

    //The screen (in browser mode) that is called when the user wants to see the error and fatal error files. The pphase is 02
    public void doGetPhase02(HttpServletResponse res, String passwd,ArrayList<String> errorsFile, ArrayList<String> fatalerrorsFile) {
        //The screen is in html format
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");

        try{
            PrintWriter out= res.getWriter();
            
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Music information service</title>");
            out.println("<meta charset='utf-8'>");
            out.println("<link href='./p4/p4.css' rel='stylesheet'>");
            out.println("</head><body>");
            out.println("<h1>Music information service</h1>");
            out.println("<h2>Files with errors: "+errorsFile.size()+"</h2>");
            //To print all the files of the list
            out.println("<ul>");
            for (int index = 0; index < errorsFile.size(); index++) {
                out.println("<br>");
                out.println("<li>");
                out.println(errorsFile.get(index));
                out.println("</li>");
            }
            out.println("</ul>");
            out.println("<h2>Files with fatal errors: "+fatalerrorsFile.size()+"</h2>");
            //To print all the files of the list
            out.println("<ul>");
            for (int index = 0; index < fatalerrorsFile.size(); index++) {
                out.println("<br>");
                out.println("<li>");
                out.println(fatalerrorsFile.get(index));
                out.println("</li>");
            }
            out.println("</ul>");
            out.println("<br>");
            //The href is used to change screens
            out.println("<button type=\"button\" id='home' onclick=\"location.href='?p="+passwd+"&pphase=01';\">Home</button>");
            out.println("<br>");
            out.println("<h4>Henar Mari&ntilde;o Bodel&oacute;n</h4>");
            out.println("</body></html>");
    
        }catch(Exception e){
    
        }
    }

    //This screen (in browser mode) is called when the list of all the found countries has to be displayed
    public void doGetPhase11 (HttpServletResponse res, ArrayList<String> countries,String passwd){
        //The screen is in html format
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
        
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset='utf-8'>");
            out.println("<title>Music information service</title>");
            out.println("<link href='./p4/p4.css' rel='stylesheet'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Music Information service</h1>");
            out.println("<br><p>Please, select a Country: </p>");

            out.println("<ol>");
            //To print all the countries
            for(int i=0; i<countries.size() ; i++ ){
                //The href is used to change screens
                out.println("<li><a href='?p="+passwd+"&pphase=12&pcountry="+countries.get(i)+"'>"+countries.get(i)+"</a>");
            }
            out.println("</ol>");
            //The href is used to change screens
            out.println("<button type=\"button\" id='home' onclick=\"location.href='?p="+passwd+"&pphase=01';\">Home</button>");
            out.println("<br>");
            out.println("<h4>Henar Mari&ntilde;o Bodel&oacute;n</h4>");
            out.println("</body></html>");
        }catch(Exception e){
    
        }
    }

    //This screen (in browser mode) is called when the list of all found albums of a certain country has to be displayed
    public void doGetPhase12 (HttpServletResponse res, String country, ArrayList<Album> listAlbums, String passwd){
        //The screen is in html format
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset='utf-8'>");
            out.println("<title>Music information service</title>");
            out.println("<link href='./p4/p4.css' rel='stylesheet'>");
            out.println("</head><body>");
            out.println("<h1>Music Information service</h1>");
            out.println("<p> Query 1: Phase 2 ( Country = "+ country +") <p>");
            out.println("<br><p>Please, select an Album: </p>");
            out.println("<ol>");

            //To print all the found albums
            for(int i=0; i< listAlbums.size() ; i++ ){
                Album album= listAlbums.get(i);
                //The href is used to change screens
                //To print the required attributes
                out.println("<li><p><a href='?p="+passwd+"&pphase=13&pcountry="+country+"&paid="+album.getAid()+"'>Album= '"+album.getName()+"</a>' --- Year='"+album.getYear()+"' --- Performer= '"+album.getPerformer()+"' --- Review= '"+album.getReview()+"'</p>");
            }
            out.println("</ol>");
            out.println("<br>");
            //The href is used to change screens
            out.println("<button type=\"button\" id='home' onclick=\"location.href='?p="+passwd+"&pphase=01';\">Home</button>");
            out.println("<button type=\"button\"  id='back' onclick=\"location.href='?p="+passwd+"&pphase=11';\">Back</button>");
            out.println("<h4>Henar Mari&ntilde;o Bodel&oacute;n</h4>");
            out.println("</body></html>");
        }catch(Exception e){
    
        }
    }

    //This screen (in auto mode) is called when the list of all pop songs of a certain album and country has to be displayed.
    public void doGetPhase13 (HttpServletResponse res, String country, ArrayList<Song> songs, String passwd, String paid){
        //The screen is in html format
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<meta charset='utf-8'>");
            out.println("<title>Music information service</title>");
            out.println("<link href='./p4/p4.css' rel='stylesheet'>");
            out.println("</head><body>");
            out.println("<h1>Music Information service</h1>");
            out.println("<p> Query 1: Phase 2 ( Country = "+ country +", Album = "+paid+")<p>"); //falta el aid
            out.println("<br><p>This is the query result: </p>");
            out.println("<ol>");

            //To print all the songs
            for (int i=0; i<songs.size(); i++){
                Song song= songs.get(i);
                //To print the required attributes
                out.println("<li> Title= '"+song.getTitle()+"' --- Language= '"+song.getLang()+"' --- Genres= '"+song.getGenre()+"' --- Composer= '"+song.getComposer()+"'");

            }
            out.println("</ol>");
            out.println("<br>");
            //The href is used to change screens
            out.println("<button type=\"button\" id='home' onclick=\"location.href='?p="+passwd+"&pphase=01';\">Home</button>");
            out.println("<button type=\"button\" id='back' onclick=\"location.href='?p="+passwd+"&pphase=12&pcountry="+country+"';\">Back</button>");
            out.println("<h4>Henar Mari&ntilde;o Bodel&oacute;n</h4>");
            out.println("</body></html>");
        }catch(Exception e){
    
        }
    }
   
    //The screen (in browser mode) that is called when there is a parameter missing (pcountry or paid)
    public void doGetParamMissing(HttpServletResponse res, String param) { 
        //The screen is in html format
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Music information service</title>");
            out.println("<meta charset='utf-8'>");
            out.println("<link href='./p4/p4.css' rel='stylesheet'>");
            out.println("</head><body>");
            out.println("<p>No param:"+param+"</p><br>");
            out.println("<h4>Henar Mari&ntilde;o Bodel&oacute;n</h4>");
            out.println("</body></html>");
        }catch(Exception e){
    
        }
    }

    //The screen (in browser mode) that is called when there isn't password (the password is null)
    public void doGetPMissing(HttpServletResponse res) { 
        //The screen is in html format
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Music information service</title>");
            out.println("<meta charset='utf-8'>");
            out.println("<link href='./p4/p4.css' rel='stylesheet'>");
            out.println("</head><body>");
            out.println("<p>No passwd</p><br>");
            out.println("<h4>Henar Mari&ntilde;o Bodel&oacute;n</h4>");
            out.println("</body></html>");
        }catch(Exception e){
    
        }
    }

    //The screen (in browser mode) that is called when the password is wrong
    public void doGetPWrong(HttpServletResponse res) { 
        //The screen is in html format
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        try{
            PrintWriter out= res.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html lang=\"en\">");
            out.println("<head>");
            out.println("<title>Music information service</title>");
            out.println("<meta charset='utf-8'>");
            out.println("<link href='./p4/p4.css' rel='stylesheet' >");
            out.println("</head><body>");
            out.println("<p>Bad passwd</p><br>");
            out.println("<h4>Henar Mari&ntilde;o Bodel&oacute;n</h4>");
            out.println("</body></html>");
        }catch(Exception e){
    
        }
    }
}