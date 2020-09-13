package com.example.mad_bestplace;

public class foods {

    private String fid;
    private String fsid;
    private String fname;
    private String fprice;
    private byte[] fimage;

    public foods(String fid, String fsid, String fname, String fprice, byte[] fimage) {
        this.fid = fid;
        this.fsid = fsid;
        this.fname = fname;
        this.fprice = fprice;
        this.fimage = fimage;
    }


    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFsid() {
        return fsid;
    }

    public void setFsid(String fsid) {
        this.fsid = fsid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFprice() {
        return fprice;
    }

    public void setFprice(String fprice) {
        this.fprice = fprice;
    }

    public byte[] getFimage() {
        return fimage;
    }

    public void setFimage(byte[] fimage) {
        this.fimage = fimage;
    }
}
