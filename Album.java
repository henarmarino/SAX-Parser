package p4;

import p4.*;
import java.io.*;
import java.util.*;

//This class is for creating all the albums with their attributes. Some of the attributes will appear in the screens of the FrontEnd in auto or browser mode
public class Album implements Comparable<Album>{

    //The private attributes of the Album class
    private String name;
    private String country;
    private String performer;
    private String ISBN;
    private String company; 
    private String year; 
    private String aid;
    private String review;
    private String format;
    private ArrayList<Song> songs;

    //The constructor of the class Album with all its attributes
    public Album (String name, String country, String performer,String ISBN,String company, String aid, String year, String review, String format, ArrayList<Song> songs) {
        this.name = name;
        this.country = country;
        this.ISBN=ISBN;
        this.performer=performer;
        this.company=company;
        this.aid= aid;
        this.year= year;
        this.review= review;
        this.format=format;
        this.songs=songs;
    }
    
    //Getters of the attributes of the class Album
    public String getName() { 
        return name; 
    }
    public ArrayList<Song> getSongs(){
        return songs;
    }
    public String getFormat() { 
        return format; 
    }
    public String getReview() { 
        return review; 
    }
    public String getYear() { 
        return year; 
    }
    public String getAid() { 
        return aid; 
    }
    public String getPerformer() { 
        return performer; 
    }
    public String getCountry() { 
        return country; 
    }
    public String getISBN() { 
        return ISBN; 
    }
    public String getCompany() { 
        return company; 
    }

    //This method orders the albums in a way that they will be grouped by year (in ascending order), and, for the same year, in alphabetical order of album name
    public int compareTo(Album alb){

        if(Integer.parseInt(this.getYear()) == Integer.parseInt (alb.getYear())) {
            return this.getName().compareTo(alb.getName());
        }
        else if(Integer.parseInt(this.getYear()) > Integer.parseInt (alb.getYear())) {
            return 1;
        }else{
            return -1;
        }
    }
}
