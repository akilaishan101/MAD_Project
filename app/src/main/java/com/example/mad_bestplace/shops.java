package com.example.mad_bestplace;

import android.graphics.Bitmap;

public class shops {

    private String Sid;
    private String Sname;
    private String Scompany;
    private String Saddress;
    private String Sdis;
    private byte[] simage;

    public shops(String sid, String sname, String scompany, String saddress, String sdis, byte[] simage) {
        Sid = sid;
        Sname = sname;
        Scompany = scompany;
        Saddress = saddress;
        Sdis = sdis;
        this.simage = simage;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getScompany() {
        return Scompany;
    }

    public void setScompany(String scompany) {
        Scompany = scompany;
    }

    public String getSaddress() {
        return Saddress;
    }

    public void setSaddress(String saddress) {
        Saddress = saddress;
    }

    public String getSdis() {
        return Sdis;
    }

    public void setSdis(String sdis) {
        Sdis = sdis;
    }

    public byte[] getSimage() {
        return simage;
    }

    public void setSimage(byte[] simage) {
        this.simage = simage;
    }
}
