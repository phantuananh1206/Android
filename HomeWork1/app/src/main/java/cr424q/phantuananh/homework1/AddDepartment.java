package cr424q.phantuananh.homework1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.StudentDB;
import cr424q.phantuananh.homework1.Model.Department;

public class AddDepartment extends AppCompatActivity {

    EditText edtADDIDdKhoa, edtADDTENKhoa;
    ImageButton btnSave;
    Department department;
    List<Department> departmentList = new ArrayList<>();
    StudentDB studentDB;
    Button btnThoat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_department);
        edtADDIDdKhoa = findViewById(R.id.edtAddIdKhoa);
        edtADDTENKhoa = findViewById(R.id.edtAddTenKhoa);
        btnSave = findViewById(R.id.imgbtnSave);
        btnThoat = findViewById(R.id.btnExit3);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        studentDB = new StudentDB(this, "Student.db", null, 1);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    department = new Department(Integer.parseInt(edtADDIDdKhoa.getText().toString()), edtADDTENKhoa.getText().toString());
                    departmentList.add(department);
                    studentDB.addKhoa(departmentList.get(departmentList.size() - 1));
                    clearEDT();
                    setResult(3);
                    showDialog(view);
                } catch (Exception ex) {
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

    public void clearEDT() {
        edtADDIDdKhoa.setText("");
        edtADDTENKhoa.setText("");
    }

    public void showDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thêm khoa thành công");
        builder.setTitle("Thông báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        }
    public void showDialog2(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thêm khoa thất bại").setTitle("Thông báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
