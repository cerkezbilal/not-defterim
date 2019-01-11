package com.bilalkarademir.notdefterim;

public class Not {

    int id;
    String baslik;
    String icerik;

    public Not() {
    }

    public Not(String baslik, String icerik) {
        this.baslik = baslik;
        this.icerik = icerik;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }
}
