package cr424q.phantuananh.homework1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.StudentDB;
import cr424q.phantuananh.homework1.Adapter.StudentAdapter;
import cr424q.phantuananh.homework1.Model.Department;
import cr424q.phantuananh.homework1.Model.Student;
import cr424q.phantuananh.homework1.Model.StudentList;

public class StudentListOfDepartment extends AppCompatActivity {
    ListView lvStudent;
    EditText edtSearch;
    StudentAdapter studentAdapter;
    List<Student> studentList;
    List<Student> studentListTmp = new ArrayList<>();
    List<Student> studentListTMP;
    StudentDB studentDB;
    Student student;
    int pos;
    String idKhoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_of_department);

        lvStudent = findViewById(R.id.lvStudentListOfDepartment);
        edtSearch = findViewById(R.id.edtSEARCH);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        studentDB = new StudentDB(this,"Student.db",null,1);


        studentList = (ArrayList<Student>) getIntent().getSerializableExtra("studentOfDepartment");
        studentAdapter = new StudentAdapter(this,R.layout.information_student,studentList);
        lvStudent.setAdapter(studentAdapter);

        registerForContextMenu(lvStudent);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = edtSearch.getText().toString();
                int le = str.length();
                if (!studentListTmp.isEmpty()) {
                    studentListTmp.clear();
                }
                for (int j = 0; j < studentList.size(); j++) {
                    if (le < studentList.get(j).getFullName().length()) {
                        if (edtSearch.getText().toString().equalsIgnoreCase((String) studentList.get(j).getFullName().subSequence(0, le))) {
                            studentListTmp.add(studentList.get(j));
                            studentAdapter = new StudentAdapter(StudentListOfDepartment.this, R.layout.information_student, studentListTmp);
                            lvStudent.setAdapter(studentAdapter);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 2){
            loadStudent();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_delete_student,menu);
        menu.setHeaderTitle("Select The Action");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
         pos = info.position;
        switch (item.getItemId()) {
            case R.id.mnEdit:
                student = studentList.get(pos);
                Intent intent1 = new Intent(StudentListOfDepartment.this, EditInformation.class);
                intent1.putExtra("updateStudent",student);
                startActivityForResult(intent1,2);
                return true;
            case R.id.mnDelete:
                showDialog();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void loadStudent(){
        studentList = new ArrayList<>();
        studentListTMP = new ArrayList<>();
        idKhoa =  getIntent().getStringExtra("idKhoa");
        try {
            studentListTMP = studentDB.getAllStudent();
        } catch (CursorIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
            for (int i = 0; i < studentListTMP.size(); i++) {
                if (String.valueOf(studentListTMP.get(i).getIdKhoa()).equalsIgnoreCase(idKhoa)) {
                    studentList.add(studentListTMP.get(i));
                }
            }
        studentAdapter = new StudentAdapter(this,R.layout.information_student,studentList);
        lvStudent.setAdapter(studentAdapter);
    }
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn xóa sinh viên này không ? ")
                .setTitle("Thông báo ");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                student = studentList.get(pos);
                studentDB.deleteSinhVien(student);
                studentAdapter.remove(student);
                studentAdapter.notifyDataSetChanged();
                showDialog2();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void showDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Xóa sinh viên thành công");
        builder.setTitle("Thông báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }



}
