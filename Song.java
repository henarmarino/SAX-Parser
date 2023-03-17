package p4;
import p4.*;
import java.io.*;

//This class is for creating all the songs with their attributes. Some of the attributes will appear in the screens of the FrontEnd in auto or browser mode
public class Song implements Comparable<Song>{
    
    //The private attributes of the Song class
    private String title;
    private String duration;
    private String genre;
    private String composer;
    private String muML;
    private String lang;
    private String sid;

    //The constructor of the class Song with all its attributes
    public Song (String composer, String title, String duration, String genre, String muML, String lang, String sid) {
        this.title = title;
        this.duration=duration;
        this.genre=genre;
        this.composer=composer;
        this.muML=muML;
        this.lang=lang;
        this.sid=sid;
    }

    //Gettes of the attributes of the class Song
    public String getSid() { 
        return sid; 
    }
    public String getLang() { 
        return lang; 
    }
    public String getTitle() { 
        return title; 
    }
    public String getMuML() { 
        return muML; 
    }
    public String getDuration() { 
        return duration; 
    }
    public String getGenre() { 
        return genre; 
    }
    public String getComposer() { 
        return composer; 
    }

    //This method orders the songs in a way that the songs with the least number of genres will appear first
    //For equal number of genres, songs will be sorted alphabetically by title
    public int compareTo(Song song){
        String separador = ",";
        //To get the number of genres that the object song has. We use the ',' to calculate the number of genres
        String[] arrayGenres2 = song.getGenre().split(separador);
        String[] arrayGenres = this.getGenre().split(separador);
        int numberGenres2= arrayGenres2.length;
        int numberGenres= arrayGenres.length;
        if(numberGenres == numberGenres2) {
            return this.getTitle().compareTo(song.getTitle());
        }
        else if(numberGenres>numberGenres2) {
            return 1;
        }
        else {
            return -1;
        }
   
    }  
}