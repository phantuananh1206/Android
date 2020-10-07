package cr424q.phantuananh.homework1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import Database.StudentDB;
import cr424q.phantuananh.homework1.Model.Department;

public class EditDepartment extends AppCompatActivity {
    EditText edtIdKhoa, edtTenKhoa;
    ImageButton imgBtnSave;
    StudentDB studentDB;
    Button btnThoat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_department);
        edtIdKhoa = findViewById(R.id.edtAddIdKhoa2);
        edtTenKhoa = findViewById(R.id.edtAddTenKhoa2);
        imgBtnSave = findViewById(R.id.imgbtnSave2);
        btnThoat = findViewById(R.id.btnExit2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        studentDB = new StudentDB(this,"Student.db",null,1);
        Department department = (Department) getIntent().getSerializableExtra("updateDepartment");

        edtIdKhoa.setEnabled(false);
        edtIdKhoa.setText(String.valueOf(department.getIdKhoa()));
        edtTenKhoa.setText(department.getTenKhoa());

        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Department department1 = new Department(Integer.parseInt(edtIdKhoa.getText().toString()),edtTenKhoa.getText().toString());
                    studentDB.updateKhoa(department1);
                    setResult(1);
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
    public void showDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sửa thông tin khoa thành công");
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
        builder.setMessage("Sửa thông tin khoa thất bại").setTitle("Thông báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
