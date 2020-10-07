package cr424q.phantuananh.homework1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Database.StudentDB;
import cr424q.phantuananh.homework1.Adapter.SpinnerAdapter;
import cr424q.phantuananh.homework1.Adapter.StudentAdapter;
import cr424q.phantuananh.homework1.Model.Department;
import cr424q.phantuananh.homework1.Model.Student;

import static java.util.Collections.sort;

public class EditInformation extends AppCompatActivity {
    EditText edtHo, edtHoLot, edtTen, edtMaSV, edtDienThoai, edtNgaySinh;
    List<Student> studentList = new ArrayList<>();
    Button btnLuu;
    DatePickerDialog datePickerDialog;
    int h,m,d,mm,y;
    RadioButton rdNam, rdNu;
    StudentDB studentDB;
    Spinner spnIDKhoa;
    int viTriKhoa;
    RadioGroup radioGroup;
    List<Department> departmentList;
    SpinnerAdapter spinnerAdapter;
    Button btnThoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        edtHo = findViewById(R.id.edtHo5);
        edtHoLot = findViewById(R.id.edtHoLot5);
        edtTen = findViewById(R.id.edtTen4);
        edtMaSV = findViewById(R.id.edtMaSV4);
        edtDienThoai = findViewById(R.id.edtDienThoai4);
        edtNgaySinh = findViewById(R.id.edtNgaySinh4);
        btnLuu = findViewById(R.id.btnLuu3);
        radioGroup = findViewById(R.id.rdGioiTinh2);
        rdNam = findViewById(R.id.rdbtnNam2);
        rdNu = findViewById(R.id.rdbtnNu2);
        spnIDKhoa = findViewById(R.id.spnIdKhoa3);
        btnThoat = findViewById(R.id.btnExit);

        rdNam.setOnCheckedChangeListener(listener);
        rdNu.setOnCheckedChangeListener(listener);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        studentDB = new StudentDB(this,"Student.db",null,1);
        Student student = (Student) getIntent().getSerializableExtra("updateStudent");
        this.setResult(2);

        edtHo.setText(student.getHo());
        edtHoLot.setText(student.getHoLot());
        edtTen.setText(student.getTen());
        edtMaSV.setText(student.getMaSV());
        edtDienThoai.setText(student.getDienThoai());
        edtNgaySinh.setText(dateToString(student.getNgaySinh()));
        if (student.getGioiTinh() == true){
            rdNam.setChecked(true);
        }else{
            rdNu.setChecked(true);
        }

        try {
            departmentList = studentDB.getAllDepartment();
            spinnerAdapter = new SpinnerAdapter(this, R.layout.spinner_department, departmentList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spnIDKhoa.setAdapter(spinnerAdapter);
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        spnIDKhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viTriKhoa = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        edtMaSV.setEnabled(false);

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
                    Student student1 = new Student(edtHo.getText().toString(), edtHoLot.getText().toString(), edtTen.getText().toString(),
                            edtMaSV.getText().toString(), edtDienThoai.getText().toString(), edtNgaySinh.getText().toString(),gioiTinh(),idKhoa);
                        studentDB.updateSinhVien(student1);
                        showDialog(view);
                }catch (Exception ex){
                    showDialog2(view);
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
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


    public String dateToString(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public void showDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sửa thông tin sinh viên thành công");
        builder.setTitle("Thông báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showDialog2(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sửa thông tin sinh viên thất bại").setTitle("Thông báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
