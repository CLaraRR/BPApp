package com.example.bpapp.entity;

/**
 * Created by ningrun on 2017/6/4.
 */
public class Data {
    private int highscore;
    private int lowscore;
    private int heartbeat;
    private String datatime;

    public Data(int highscore,int lowscore,int heartbeat,String datatime){
        this.highscore=highscore;
        this.lowscore=lowscore;
        this.heartbeat=heartbeat;
        this.datatime=datatime;
    }
    public void setHighscore(int highscore){
        this.highscore=highscore;
    }
    public int getHighscore(){
        return this.highscore;
    }

    public void setLowscore(int lowscore){
        this.lowscore=lowscore;
    }
    public int getLowscore(){
        return this.lowscore;
    }

    public void setHeartbeat(int heartbeat){
        this.heartbeat=heartbeat;
    }
    public int getHeartbeat(){
        return this.heartbeat;
    }

    public void setDatatime(String datatime){
        this.datatime=datatime;
    }
    public String getDatatime(){
        return this.datatime;
    }


}
