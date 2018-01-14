package com.example.admin.miniproject;

/**
 * Created by ADMIN on 06-09-2017.
 */

public class Artwork {
    private String name;
    private String artist;
    private int price;
    private String thmbimg;
    private int category;
    private String description;
    long start;
    long stop;

    Artwork()
    {

    }
    public Artwork(String name, String artist, int price, String thmbimg, int category, String description) {
        this.name = name;
        this.artist=artist;
        this.price=price;
        this.thmbimg=thmbimg;
        this.category=category;
        this.description= description;
    }


    public Artwork(String name, String artist, int price, String thmbimg, int category, String description,long start,long stop) {
        this.name = name;
        this.artist=artist;
        this.price=price;
        this.thmbimg=thmbimg;
        this.category=category;
        this.description= description;
        this.start=start;
        this.stop=stop;
    }

    public long getStart() {
        return start;
    }

    public long getStop() {
        return stop;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void setStop(long stop) {
        this.stop = stop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist){this.artist=artist;}

    public String getArtist(){return artist;}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getThmbimg() {
        return thmbimg;
    }

    public void setThmbimg(String thmbimg) {
        this.thmbimg = thmbimg;
    }

    public void setCategory(int category){this.category=category;}

    public int getCategory(){return category;}

    public void setDescription(String des) {this.description=des;}

    public String getDescription() {return description;}

}
