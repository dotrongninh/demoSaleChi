package models;

public class Student {
    private String hoTen;
    private String tuoi;

    public Student() {
        // Empty constructor needed for Firebase
    }

    public Student(String hoTen, String tuoi) {
        this.hoTen = hoTen;
        this.tuoi = tuoi;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getTuoi() {
        return tuoi;
    }
}

