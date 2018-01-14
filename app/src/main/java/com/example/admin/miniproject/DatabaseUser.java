package com.example.admin.miniproject;

/**
 * Created by ADMIN on 13-09-2017.
 */

public class DatabaseUser {

    private String DatabaseUser_email;
    private String DatabaseUser_name;
    private String DatabaseUser_mobno;
    private String DatabaseUser_uid;
    private int balance;
//    private Map m;
    private String profilephoto;

    public DatabaseUser() {

    }

    public DatabaseUser(String x,String y,String z,String w,String s,int balance)
    {
        this.DatabaseUser_name=x;
        this.DatabaseUser_email=y;
        this.DatabaseUser_mobno=z;
        this.DatabaseUser_uid=w;
        //this.m=m;
        this.balance=balance;
        this.profilephoto=s;
    }

    public int getBalance() {
        return balance;
    }

    public String getDatabaseUser_email() {
        return DatabaseUser_email;
    }

    public String getDatabaseUser_uid() {
        return DatabaseUser_uid;
    }

    public String getDatabaseUser_name() {
        return DatabaseUser_name;
    }

    public String getDatabaseUser_mobno() {
        return DatabaseUser_mobno;
    }

    public String getProfilephoto() {
        return profilephoto;
    }

    public void setDatabaseUser_email(String databaseUser_email) {
        DatabaseUser_email = databaseUser_email;
    }

    public void setDatabaseUser_name(String databaseUser_name) {
        DatabaseUser_name = databaseUser_name;
    }

    public void setDatabaseUser_mobno(String databaseUser_mobno) {
        DatabaseUser_mobno = databaseUser_mobno;
    }

    public void setDatabaseUser_uid(String databaseUser_uid) {
        DatabaseUser_uid = databaseUser_uid;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void setProfilephoto(String profilephoto) {
        this.profilephoto = profilephoto;
    }


}
