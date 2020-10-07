package cr424q.phantuananh.homework1.Model;

import java.io.Serializable;

public class Department implements Serializable {
    int idKhoa;
    String tenKhoa;

    public Department() {

    }

    public Department(int idKhoa, String tenKhoa) {
        this.idKhoa = idKhoa;
        this.tenKhoa = tenKhoa;
    }

    public int getIdKhoa() {
        return idKhoa;
    }

    public void setIdKhoa(int idKhoa) {
        this.idKhoa = idKhoa;
    }

    public String getTenKhoa() {
        return tenKhoa;
    }

    public void setTenKhoa(String tenKhoa) {
        this.tenKhoa = tenKhoa;
    }
}
