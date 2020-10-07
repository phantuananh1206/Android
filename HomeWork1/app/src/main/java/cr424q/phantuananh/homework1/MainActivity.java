package cr424q.phantuananh.homework1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Database.StudentDB;
import cr424q.phantuananh.homework1.Adapter.SpinnerAdapter;
import cr424q.phantuananh.homework1.Model.Department;
import cr424q.phantuananh.homework1.Model.DepartmentList;
import cr424q.phantuananh.homework1.Model.StudentList;
import cr424q.phantuananh.homework1.Model.Student;

import static java.util.Collections.sort;

public class MainActivity extends AppCompatActivity {
    EditText edtHo, edtHoLot, edtTen, edtMaSV, edtDienThoai, edtNgaySinh;
    List<Student> studentList = new ArrayList<>();
    Button btnLuu, btnXemDanhSach;
    DatePickerDialog datePickerDialog;
    int h,m,d,mm,y;
    Student student;
    RadioButton rdNam, rdNu;
    StudentDB studentDB;
    Spinner spnIDKhoa;
    int viTriKhoa;
    RadioGroup radioGroup;
    List<Department> departmentList = new ArrayList<>();
    SpinnerAdapter spinnerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtHo = findViewById(R.id.edtHo);
        edtHoLot = findViewById(R.id.edtHoLot);
        edtTen = findViewById(R.id.edtTen);
        edtMaSV = findViewById(R.id.edtMaSV);
        edtDienThoai = findViewById(R.id.edtDienThoai);
        edtNgaySinh = findViewById(R.id.edtNgaySinh);
        btnLuu = findViewById(R.id.btnLuu);
        btnXemDanhSach = findViewById(R.id.btnXemDanhSach);
        radioGroup = findViewById(R.id.rdGioiTinh);
        rdNam = findViewById(R.id.rdbtnNam);
        rdNu = findViewById(R.id.rdbtnNu);
        spnIDKhoa = findViewById(R.id.spnIdOfKhoa);

        rdNam.setOnCheckedChangeListener(listener);
        rdNu.setOnCheckedChangeListener(listener);


        studentDB = new StudentDB(this, "Student.db", null, 1);

        try {
            departmentList = studentDB.getAllDepartment();
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        spinnerAdapter = new SpinnerAdapter(this, R.layout.spinner_department, departmentList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnIDKhoa.setAdapter(spinnerAdapter);

        spnIDKhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viTriKhoa = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sort(studentList, Student.studentComparator);

        edtNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(view);
            }
        });


        Calendar calendar = Calendar.getInstance();
        h = calendar.get(Calendar.HOUR);
        m = calendar.get(Calendar.MINUTE);
        d = calendar.get(Calendar.DAY_OF_MONTH);
        mm = calendar.get(Calendar.MONTH);
        y = calendar.get(Calendar.YEAR);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int idKhoa = departmentList.get(viTriKhoa).getIdKhoa();
                    student = new Student(edtHo.getText().toString(), edtHoLot.getText().toString(), edtTen.getText().toString(),
                            edtMaSV.getText().toString(), edtDienThoai.getText().toString(), edtNgaySinh.getText().toString(), gioiTinh(), idKhoa);
                    studentList.add(student);

                    studentDB.addSinhVien(studentList.get(studentList.size() - 1));
                    clearAllEDT();
                    Toast.makeText(getApplicationContext(), "Lưu thành công", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(MainActivity.this, SaveInformation.class);
                    intent.putExtra("student", student);
                    startActivity(intent);
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Bạn nhập thông tin sai" , Toast.LENGTH_LONG).show();
                }
            }
        });


        btnXemDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            try {
                Intent intent = new Intent(MainActivity.this, StudentList.class);
                intent.putExtra("danhsach", (ArrayList<Student>) studentDB.getAllStudent());
                startActivity(intent);
            }catch (Exception ex){
                Toast.makeText(getApplicationContext(),"Danh sách sinh viên trống",Toast.LENGTH_LONG).show();
            }
            }
        });
    }

    public void showDatePicker(View view) {
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edtNgaySinh.setText(String.valueOf(i2) + "/" + String.valueOf(i1 + 1) + "/" + String.valueOf(i));
            }
        }, y, m, d);
        datePickerDialog.show();
    }


    public void  clearAllEDT(){
        edtHo.setText("");
        edtHoLot.setText("");
        edtTen.setText("");
        edtMaSV.setText("");
        edtDienThoai.setText("");
        edtNgaySinh.setText("");
        radioGroup.clearCheck();
    }

    public boolean gioiTinh(){
        if(rdNam.isChecked()) return true;
        return false;
    }

    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            btnLuu.setEnabled(true);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 3 || resultCode == 5){
            departmentList = new ArrayList<>();
            try {
                departmentList = studentDB.getAllDepartment();
            } catch (CursorIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            spinnerAdapter = new SpinnerAdapter(this, R.layout.spinner_department, departmentList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spnIDKhoa.setAdapter(spinnerAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_faculty_management, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnAddKhoa:
                Intent intent = new Intent(MainActivity.this, AddDepartment.class);
                startActivityForResult(intent,3);
                return true;
            case R.id.mnDSKhoa:
                try {
                    Intent intent1 = new Intent(MainActivity.this, DepartmentList.class);
                    intent1.putExtra("departmentList",(ArrayList<Department>) studentDB.getAllDepartment());
                    startActivityForResult(intent1,5);
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Danh sách khoa trống",Toast.LENGTH_LONG).show();
                }

            default:
            return super.onOptionsItemSelected(item);
        }
    }


}
