package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cr424q.phantuananh.homework1.Model.Department;
import cr424q.phantuananh.homework1.Model.Student;

public class StudentDB extends SQLiteOpenHelper {
    public StudentDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public StudentDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

//    public StudentDB(Context context, String name, int version, SQLiteDatabase.OpenParams openParams) {
//        super(context, name, version, openParams);
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE \"Student\" (\n" +
                "\t\"MaSV\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                "\t\"Ho\"\tTEXT NOT NULL,\n" +
                "\t\"HoLot\"\tTEXT NOT NULL,\n" +
                "\t\"Ten\"\tTEXT NOT NULL,\n" +
                "\t\"DienThoai\"\tTEXT NOT NULL,\n" +
                "\t\"NgaySinh\"\tTEXT NOT NULL,\n" +
                "\t\"GioiTinh\"\tTEXT NOT NULL,\n" +
                "\t\"IdKhoa\"\tINTEGER NOT NULL\n" +
                ");";
        sqLiteDatabase.execSQL(createTable);

        String createTable2 = "CREATE TABLE \"Department\" (\n" +
                "\t\"IdKhoa\"\tINTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" +
                "\t\"TenKhoa\"\tTEXT NOT NULL\n" +
                ");";
        sqLiteDatabase.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addKhoa(Department department){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("IdKhoa",department.getIdKhoa());
        cv.put("TenKhoa",department.getTenKhoa());
        db.insert("Department",null,cv);
        db.close();
    }

    public void updateKhoa(Department department){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("IdKhoa",department.getIdKhoa());
        cv.put("TenKhoa",department.getTenKhoa());

        db.update("Department", cv , "IdKhoa" + " = ?", new String[]{String.valueOf(department.getIdKhoa())});
        db.close();
    }

    public void deleteKhoa(Department department){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Department", "IdKhoa" + " = ?", new String[]{String.valueOf(department.getIdKhoa())});
        db.close();
    }
    public void addSinhVien(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MaSV",student.getMaSV());
        cv.put("Ho",student.getHo());
        cv.put("HoLot",student.getHoLot());
        cv.put("Ten",student.getTen());
        cv.put("DienThoai",student.getDienThoai());
        cv.put("NgaySinh",dateToString(student.getNgaySinh()));
        cv.put("GioiTinh",showGioiTinh(student.getGioiTinh()));
        cv.put("IdKhoa",student.getIdKhoa());

        db.insert("Student",null,cv);
        db.close();
    }

    public void updateSinhVien(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("MaSV",student.getMaSV());
        cv.put("Ho",student.getHo());
        cv.put("HoLot",student.getHoLot());
        cv.put("Ten",student.getTen());
        cv.put("DienThoai",student.getDienThoai());
        cv.put("NgaySinh",dateToString(student.getNgaySinh()));
        cv.put("GioiTinh",showGioiTinh(student.getGioiTinh()));
        cv.put("IdKhoa",student.getIdKhoa());

        db.update("Student", cv , "MaSV" + " = ?", new String[]{String.valueOf(student.getMaSV())});
        db.close();
    }

    public void deleteSinhVien(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Student", "MaSV" + " = ?", new String[]{String.valueOf(student.getMaSV())});
        db.close();
    }
    public Student getSinhVien(int idKHOA){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.query("Student", new String[]{"MaSV", "Ho", "HoLot", "Ten", "DienThoai", "NgaySinh", "GioiTinh", "IdKhoa"}, "IdKhoa" + "= ?",
                new String[]{ String.valueOf(idKHOA)}, null, null, null, null);
        if (cs != null && cs.getCount() > 0){
            cs.moveToFirst();
            Student student = new Student(cs.getString(1),
                    cs.getString(2),
                    cs.getString(3),
                    cs.getString(0),
                    cs.getString(4),
                    cs.getString(5),
                    gT(cs.getString(6)),
                    cs.getInt(7));
            return student;
        }
        else
            return null;
    }

    public List<Student> getAllStudent(){
        List<Student> studentList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + "Student";
        Cursor cs = db.rawQuery(selectQuery, null);
        if (cs != null)
            cs.moveToFirst();
        do{
            Student student = new Student(cs.getString(1),
                    cs.getString(2),
                    cs.getString(3),
                    cs.getString(0),
                    cs.getString(4),
                    cs.getString(5),
                    gT(cs.getString(6)),
                    cs.getInt(7));
            studentList.add(student);
        }
        while (cs.moveToNext());
        return studentList;
    }


    public List<Department> getAllDepartment(){
        List<Department> departmentList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + "Department";
        Cursor cs = db.rawQuery(selectQuery, null);
        if (cs != null)
            cs.moveToFirst();
        do{
            Department department = new Department(cs.getInt(0),cs.getString(1));
            departmentList.add(department);
        }
        while (cs.moveToNext());
        return departmentList;
    }


    public String  dateToString(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    private String showGioiTinh(boolean ktgioiTinh){
        if(ktgioiTinh){
            return "Nam";
        }else{
            return "Nữ";
        }
    }

    private  boolean gT(String gTinh){
        if (gTinh.equalsIgnoreCase("Nam")){
            return true;
        }else{
            return false;
        }
    }
}
