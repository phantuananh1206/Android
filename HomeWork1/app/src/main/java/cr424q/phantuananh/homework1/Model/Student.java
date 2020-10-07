package cr424q.phantuananh.homework1.Model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Student implements Serializable {
    String ho, hoLot, ten, maSV, dienThoai;
    Date ngaySinh;
    Boolean gioiTinh;
    int idKhoa;
    public Student() {

    }

    public Student(String ho, String hoLot, String ten, String maSV, String dienThoai, String ngaySinh, Boolean gioiTinh, int idKhoa) {
        this.ho = ho;
        this.hoLot = hoLot;
        this.ten = ten;
        this.maSV = maSV;
        this.dienThoai = dienThoai;
        this.ngaySinh = stringToDate(ngaySinh);
        this.gioiTinh = gioiTinh;
        this.idKhoa = idKhoa;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getHoLot() {
        return hoLot;
    }

    public void setHoLot(String hoLot) {
        this.hoLot = hoLot;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Boolean getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(Boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getIdKhoa() {
        return idKhoa;
    }

    public void setIdKhoa(int idKhoa) {
        this.idKhoa = idKhoa;
    }

    public static Comparator<Student> getStudentComparator() {
        return studentComparator;
    }

    public static void setStudentComparator(Comparator<Student> studentComparator) {
        Student.studentComparator = studentComparator;
    }

    public Date stringToDate(String date){
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFullName(){
        return ho.toString() + " " + hoLot.toString() + " " + ten.toString();
    }

    public int tinhTuoi(){
        int tuoi = (new Date().getYear() - ngaySinh.getYear() );
        return tuoi;
    }

    public static Comparator<Student> studentComparator = new Comparator<Student>() {
        @Override
        public int compare(Student student, Student t1) {
            return student.getFullName().compareToIgnoreCase(t1.getFullName());
        }
    };

    private String showGioiTinh(boolean ktgioiTinh){
        if(ktgioiTinh){
            return "Nam";
        }else{
            return "Nữ";
        }
    }

}
