package com.hallo.helloworld;

public class Mahasiswa {

    private String nim;
    private String nama;
    private String phone;

    public Mahasiswa(String nim, String nama, String phone) {
        this.nim = nim;
        this.nama = nama;
        this.phone = phone;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

