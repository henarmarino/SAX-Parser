package p4;

import java.io.*;
import java.util.*;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.xml.sax.SAXException;

import javax.swing.SortingFocusTraversalPolicy;
import javax.swing.GroupLayout.Alignment;
import javax.xml.parsers.*;
import p4.*;
import java.net.*;
import java.net.ContentHandler;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

//This class is in charge of collecting all the required data (countries, albums and songs). Moreover, in this class all the files are parsed and classified in error, fatalerror and correct files
public class DataModel extends DefaultHandler{

    //The boolean and string variables that are needed to get the data from the files
    boolean bName,bCountry,bYear,bPerformer,bISBN,bCompany,bComposer,bDuration,bGenre,bMuML,bTitle,bReview,bTexto = false;
    String year,country,name,aid,format,sid,lang,performer,ISBN,company,title,duration,genre,composer,MuML,reviewFin,reviewPrin,review;
    
    //The list of songs of a given album
    static ArrayList<Song> songsOfAlbum=new ArrayList<Song>();
    //The list of genres of a given song
    static ArrayList<String> genresSong= new ArrayList<String>();

    //The list of all found countries
    static ArrayList<String> countriesList= new ArrayList<String>();
    //The list of all valid albums
    static ArrayList<Album> albumsList= new ArrayList<Album>();

    //The list of all found muMLs
    static ArrayList<String> allURLS= new ArrayList<String>();
    //The list of the documents that are going to be parsed
    static LinkedList<String> pendientes = new LinkedList<String>();
    //The lists with all the error and fatalerror files, both in browser and auto mode. The difference between them is the format because of the screens
    static ArrayList<String> errorsFile = new ArrayList<String>();
    static ArrayList<String> errorsFileAUTO = new ArrayList<String>();
    static ArrayList<String> fatalerrorsFileAUTO = new ArrayList<String>();
    static ArrayList<String> fatalerrorsFile = new ArrayList<String>();

    int x,index;
    boolean welform;
    String firstUrl;
    SAXParserFactory parserFactory ;
    SAXParser parser ;
    XMLReader xr;
    public void parserDocs(){
        try{
            //The URL of the first document
            firstUrl="http://alberto.gil.webs.uvigo.es/SINT/22-23/muml2001.xml";
            //The first document is parsed
            URL url = new URL(firstUrl);
            //The SAX parser is created to parse all the files
            parserFactory = SAXParserFactory.newInstance();
            parser = parserFactory.newSAXParser();
            xr = parser.getXMLReader();
            //To call some methods of the class (startElement(), endElement(), characters()) and manage the events
            xr.setContentHandler(this);
            xr.setErrorHandler(this);
            BufferedReader file = new BufferedReader(new InputStreamReader(url.openStream()));
            InputSource is = new InputSource(file);
            is.setEncoding("UTF-8");
            //The document is parsed
            xr.parse(is); 
            //It is welformed because there is no exception
            welform=true;
            //The year of the document is analyzed
            int yearFirstDocInt = Integer.parseInt (year);
            if(yearFirstDocInt <= 1980 || yearFirstDocInt >= 2021){
                //The year is incorrect so the file is added to the error files (both in auto and browser mode)
                errorsFile.add(firstUrl); 
                errorsFileAUTO.add(pendientes.get(0));
            }   
        }catch(Exception e){
            //It is not wellformed, so it is added to the fatalerror files (both in auto and browser mode)
            welform=false;
            fatalerrorsFile.add(firstUrl);
            fatalerrorsFileAUTO.add(pendientes.get(0));
        }
        
        if(welform){
            //The document is welformed and all the correct files are going to be parsed
            while(!pendientes.isEmpty()) { 
                String urlGeneral= "http://alberto.gil.webs.uvigo.es/SINT/22-23/";
                //To get the URL of the file that is going to be parsed
                String urlToParse= urlGeneral+pendientes.get(0);
                try{
                    URL urlNext = new URL(urlToParse);
                    //The document is parsed with a SAX parser
                    BufferedReader fileNext = new BufferedReader(new InputStreamReader(urlNext.openStream()));
                    InputSource fileIn = new InputSource(fileNext);
                    fileIn.setEncoding("UTF-8");
                    xr.parse(fileIn);
                    //The year of the document is analyzed
                    int yearOfDocNextInt = Integer.parseInt (year);
                    if(yearOfDocNextInt <= 1980 || yearOfDocNextInt >= 2021){
                        //The year is incorrect so the file is added to the error files (both in auto and browser mode)
                        errorsFile.add(urlToParse); 
                        errorsFileAUTO.add(pendientes.get(0));
                    }
                    //The file that was parsed is remove from pendientes list
                    pendientes.pop();
                }catch(Exception e2){
                    //It is not wellformed, so it is added to the fatalerror files (both in auto and browser mode)
                    fatalerrorsFile.add(urlToParse);
                    fatalerrorsFileAUTO.add(pendientes.get(0));
                    //The file that was parsed is remove from pendientes list
                    pendientes.pop();
                    welform=false;
                }
            }
        }
    }

    //This method is called when a tag element is started in the file
    public void startElement(String uri, String localName,String qName, Attributes atts) {
    
        //It is a year element
        if(qName.equalsIgnoreCase("Year")) {
            //The boolean variable is true to get the data in the method characters()
            bYear= true;   
        }
        //It is an album element
        if(qName.equalsIgnoreCase("Album")) {
            //We create the list of songs of a given album
            songsOfAlbum= new ArrayList<Song>();
            //We get the attributes of the album
            aid = atts.getValue("aid");
            format = atts.getValue("format");
            //The boolean variable is true to get the data in the method characters()
            bReview=true; 
        }
        //It is a name element
        if(qName.equalsIgnoreCase("Name")) {
            //The boolean variable is true to get the data in the method characters()
            bName = true;
        }
        //It is a country element
        if(qName.equalsIgnoreCase("Country")) {
            //The boolean variable is true to get the data in the method characters()
            bCountry = true;
        }
        //It is a singer element
        if(qName.equalsIgnoreCase("Singer")) {
            //The boolean variable is true to get the data in the method characters()
            bPerformer = true;
        }
        //It is a group element
        if(qName.equalsIgnoreCase("Group")) {
            //The boolean variable is true to get the data in the method characters()
            bPerformer = true;
        }
        //It is an ISBN element
        if(qName.equalsIgnoreCase("ISBN")) {
            //The boolean variable is true to get the data in the method characters()
            bISBN = true;
        }
        //It is a company element
        if(qName.equalsIgnoreCase("Company")) {
            //The boolean variable is true to get the data in the method characters()
            bCompany = true;
        }
        //It is a song element
        if(qName.equalsIgnoreCase("Song")) {
            //We get the attributes of the song
            sid = atts.getValue("sid");
            lang = atts.getValue("lang");
        }
        //It is a title element
        if(qName.equalsIgnoreCase("Title")) {
            //The boolean variable is true to get the data in the method characters()
            bTitle = true;
        }
        //It is a duration element
        if(qName.equalsIgnoreCase("Duration")) {
            //The boolean variable is true to get the data in the method characters()
            bDuration = true;
        }
        //It is a genre element
        if(qName.equalsIgnoreCase("Genre")) {
            //The boolean variable is true to get the data in the method characters()
            bGenre = true;
        }
        //It is a composer element
        if(qName.equalsIgnoreCase("Composer")) {
            //The boolean variable is true to get the data in the method characters()
            bComposer = true;
        }
        //It is a MuML element
        if(qName.equalsIgnoreCase("MuML")) {
            //The boolean variable is true to get the data in the method characters()
            bMuML = true;
        }
        //To get later the review
        if(qName.length()!=0){
            //The boolean variable is true to get the data in the method characters()
            bTexto=true;
        }
    }

    //This method is called when a tag element is finished in the file
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //To get the year of the document. We have to proof that it is a correct year, that is, that the year corresponds to the correct range
        int yearInt = Integer.parseInt (year);

        //The album element is finished
        if (qName.equalsIgnoreCase("Album") ) {
            //We call to the constructor of the Album class with all its attributes
            Album AlbumObj = new Album(name,country,performer,ISBN,company,aid,year,review,format, songsOfAlbum);
            //The album is added to the list if the year is correct and there is no repeat album 
            if (!albumsList.contains(AlbumObj) && yearInt >= 1980 && yearInt <= 2021) {
                //The album is added to the list of correct albums
                albumsList.add(AlbumObj); 
            }
            
        }

        //The country element is finished
        if (qName.equalsIgnoreCase("Country") ) {
            //The country is added to the list if the year is correct and there is no repeat country
            if (!countriesList.contains(country) && yearInt >= 1980 && yearInt <= 2021) {
                //The country is added to the list of countries
                countriesList.add(country); 
            }
        }

        //The song element is finished
        if (qName.equalsIgnoreCase("Song") ) {
           //We pass the genres of the song to a string
           String genresToString = String.join(", ", genresSong);
           //We call to the constructor of the Song class with all its attributes
           Song SongObj = new Song(composer,title,duration,genresToString,MuML,lang,sid);
           //The list of genres of a song is restarted
           genresSong.clear();
           genresToString="";
           //The song is added to the list if the year is correct and there is no repeat song
           if (!songsOfAlbum.contains(SongObj) && yearInt >= 1980 && yearInt <= 2021) {
                //The song is added to the list of correct songs
                songsOfAlbum.add(SongObj);
            }
        }

        //The music element is finished. This means that the file is finished
        if (qName.equalsIgnoreCase("Music")) {
            //If the year of the document is in the correct range we have to add its MuMLs in the pendientes list to parser those files
            if(yearInt >= 1980 && yearInt <= 2021){
                for(index=0; index<allURLS.size(); index++){
                    //To not repeat MuMLs 
                    if(pendientes.indexOf(allURLS.get(index))<1){
                        pendientes.add(allURLS.get(index)); 
                    }
                }
            }
            //The list of all found URLs is restarted
            allURLS.clear();
        }
    }

    //This methos is called when the parser reads text. It is used to get all the data from the files
    public void characters(char ch[], int start, int length){
        
        if (bYear) {
            //We get the year because it is a year element
            year= new String(ch, start, length);
            bYear= false;
        }
        if (bName) {
            //We get the name because it is a name element
            name =new String(ch, start, length);
            bName = false;
        }
        if (bCountry) {
            //We get the country because it is a country element
            country= new String(ch, start, length);
            bCountry = false;
        }
        if (bPerformer) {
            //We get the performer because it is a performer element
            performer= new String(ch, start, length);
            bPerformer = false;
        }
        if (bISBN) {
            //We get the ISBN because it is a ISBN element
            ISBN= new String(ch, start, length);
            bISBN = false;
        }
        if (bCompany) {
            //We get the company because it is a company element
            company= new String(ch, start, length);
            bCompany = false;
        }
        if (bTitle) {
            //We get the title because it is a title element
            title= new String(ch, start, length);
            bTitle = false;
        }
        if (bDuration) {
            //We get the duration because it is a duration element
            duration= new String(ch, start, length);
            bDuration = false;
        }
        if (bGenre) {
            //We get the genre because it is a genre element
            genre= new String(ch, start, length);
            //We add it to the list of genres of a song
            genresSong.add(genre);
            bGenre = false;
        }
        if (bComposer) {
            //We get the composer because it is a composer element
            composer= new String(ch, start, length);
            bComposer = false;
        }
        if (bMuML) {
            //We get the MuML because it is a MuML element
            MuML= new String(ch, start, length);
            //We add it to the list of MuMLs
            allURLS.add(MuML);
            bMuML = false;
        }
        if(bReview){
            //We get the review because it is a album element. This only gets the reviews that are in the first part of an album
            reviewPrin = new String(ch, start, length).trim(); //To remove the black spaces at the end of the string
            bReview=false;
            //If the review in this section is not an empty string, it is the real review of the album 
            if(reviewPrin.length()!=0){
                review= reviewPrin;
            }
        }
        if(bTexto){
            //To get the review without overwriting it with the data from the other attributes
            bTexto=false;
        }else{
            //We get the review if it is in the last part of an album, that is, at the end
            reviewFin = new String(ch, start, length).trim(); //To remove the black spaces at the end of the string
            //If the review in this section is not an empty string, it is the real review of the album 
            if(reviewFin.length()!=0){
                review= reviewFin;
            }
        }        
    }
    
    //Method used to get all songs of a given country and aid that are in the documents
    public ArrayList<Song> getQ1Songs (String country, String album) { 
        //The list of all songs of the given country and aid
        ArrayList<Song> songsOfThisCountryAndAlbum= new ArrayList<Song>();
        //The list of all found albums is traversed
        for(index =0; index<albumsList.size(); index++){
            //The aid of the album and the country are compared with the correct ones
            if(albumsList.get(index).getAid().equals(album) && albumsList.get(index).getCountry().equals(country)){ 
                //We get the list of songs of the correct album
                ArrayList<Song> songsDelAlbum= albumsList.get(index).getSongs();
                //The list of songs of the correct album is traversed
                for(x=0; x<songsDelAlbum.size();x++){
                    //To get song by song
                    Song s= songsDelAlbum.get(x);
                    //To get only the pop songs
                    if(s.getGenre().contains("Pop")){
                        //To not repeat songs
                        if(!songsOfThisCountryAndAlbum.contains(s)){
                                songsOfThisCountryAndAlbum.add(s);
                        }
                    }
                }
            }   
        }
        //The songs must be in alphabetical order
        Collections.sort(songsOfThisCountryAndAlbum);
        return songsOfThisCountryAndAlbum;
    }

    //Method used to get all albums of a given country that are in the documents
    public ArrayList<Album> getQ1Albums (String country) {
        //The list of albums of the given country
        ArrayList<Album> albumsOfThisCountry= new ArrayList<Album>();
        //The list of all found albums is traversed
        for (index = 0; index < albumsList.size(); index++) {
            //To get album by album
            Album alb= albumsList.get(index);
            //To see if the country is the correct one
            if(alb.getCountry().equals(country)){
                albumsOfThisCountry.add(alb);
            }
        }
        //The albums must be in alphabetical order
        Collections.sort(albumsOfThisCountry);
        return albumsOfThisCountry;   
    }

    //Methos used to get the list of all found countries
    public ArrayList<String> getQ1Countries (){  
        //To order in reverse alphabetical order
        Collections.sort(countriesList);
        Collections.reverse(countriesList);
        return countriesList;
    }

    //To get the list of files with errors (in auto mode)
    public ArrayList<String> getErrorsFilesAUTO(){
        return errorsFileAUTO;
    }

    //To get the list of files with fatal errors (in auto mode)
    public ArrayList<String> getFatalErrorsFilesAUTO(){
        return fatalerrorsFileAUTO;
    }             

    //To get the list of files with errors (in browser mode)
    public ArrayList<String> getErrorsFiles(){
        return errorsFile;
    }

    //To get the list of files with fatal errors (in browser mode)
    public ArrayList<String> getFatalErrorsFiles(){
        return fatalerrorsFile;
    }
}
