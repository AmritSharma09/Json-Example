package com.example.golu.jsonprojecteg;

/**
 * Created by GOLU on 29-06-2017.
 */

public class Actor {
    private String sno,actorName,email,mobile;

    public Actor(String sno, String actorName, String email, String mobile) {
        this.sno = sno;
        this.actorName = actorName;
        this.email = email;
        this.mobile = mobile;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
