package com.example.admin.miniproject;

/**
 * Created by ADMIN on 17-09-2017.
 */

public class Paintings_Info {

   String desc;
    String cost;
    String fromUID;
    String PID;
    String type;
    long start;
    long stop;
    Paintings_Info()
    {

    }

    Paintings_Info(String desc,String cost,String fromUID,String PID,String type)
    {
        this.type=type;
        this.PID=PID;
        this.fromUID=fromUID;
        this.cost=cost;
        this.desc=desc;
    }

    Paintings_Info(String desc,String cost,String fromUID,String PID,String type,long start,long stop)
    {
        this.start=start;
        this.stop=stop;
        this.type=type;
        this.PID=PID;
        this.fromUID=fromUID;
        this.cost=cost;
        this.desc=desc;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public void setStop(long stop) {
        this.stop = stop;
    }

    public long getStart() {
        return start;
    }

    public long getStop() {
        return stop;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPID() {
        return PID;
    }

    public String getFromUID() {
        return fromUID;
    }

    public String getDesc() {
        return desc;
    }

    public String getCost() {
        return cost;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setFromUID(String fromUID) {
        this.fromUID = fromUID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }
}


