package com.example.coffehub;

public class Pojo {
    String lname ,lid,lemail;




    public Pojo(){

    }

    public Pojo(String lname, String lid, String lemail) {
        this.lname = lname;
        this.lid = lid;
        this.lemail = lemail;
    }

    public String getLname() {
        return lname;
    }

    public String getLid() {
        return lid;
    }

    public String getLemail() {
        return lemail;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public void setLemail(String lemail) {
        this.lemail = lemail;
    }
}
