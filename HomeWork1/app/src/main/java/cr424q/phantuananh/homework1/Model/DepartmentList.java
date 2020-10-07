package cr424q.phantuananh.homework1.Model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Database.StudentDB;
import cr424q.phantuananh.homework1.Adapter.DepartmentAdapter;
import cr424q.phantuananh.homework1.EditDepartment;
import cr424q.phantuananh.homework1.R;

public class DepartmentList extends AppCompatActivity {
    EditText edtSearch;
    ListView lvDSKhoa;
    DepartmentAdapter departmentAdapter;
    List<Department> departmentList;
    List<Department> departmentListTmp = new ArrayList<>();
    StudentDB studentDB;
    Department department;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);

        edtSearch = findViewById(R.id.edtTimKiem);
        lvDSKhoa = findViewById(R.id.lvDSKhoa);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        studentDB = new StudentDB(this,"Student.db",null,1);

        departmentList = (List<Department>) getIntent().getSerializableExtra("departmentList");
        setResult(5);
        departmentAdapter = new DepartmentAdapter(this,R.layout.information_faculty,departmentList);
        lvDSKhoa.setAdapter(departmentAdapter);

        registerForContextMenu(lvDSKhoa);


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = edtSearch.getText().toString();
                int le = str.length();
                if (!departmentListTmp.isEmpty()) {
                    departmentListTmp.clear();
                }
                for (int j = 0; j < departmentList.size(); j++) {
                    if (le < departmentList.get(j).getTenKhoa().length()) {
                        if (edtSearch.getText().toString().equalsIgnoreCase((String) departmentList.get(j).getTenKhoa().subSequence(0, le))) {
                            departmentListTmp.add(departmentList.get(j));
                            departmentAdapter = new DepartmentAdapter(DepartmentList.this,R.layout.information_faculty,departmentListTmp);
                            lvDSKhoa.setAdapter(departmentAdapter);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            loadDepartment();
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
                department = departmentList.get(pos);
                Intent intent = new Intent(DepartmentList.this, EditDepartment.class);
                intent.putExtra("updateDepartment",department);
                startActivityForResult(intent,1);
                return true;
            case R.id.mnDelete:
                showDialog();
                return true;
            default:
            return super.onContextItemSelected(item);
        }
    }

    public void loadDepartment(){
        departmentList = new ArrayList<>();
        departmentList = studentDB.getAllDepartment();
        departmentAdapter = new DepartmentAdapter(this,R.layout.information_faculty,departmentList);
        lvDSKhoa.setAdapter(departmentAdapter);
    }
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc chắn muốn xóa khoa này không ? ")
                .setTitle("Thông báo ");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                department = departmentList.get(pos);
                studentDB.deleteKhoa(department);
                departmentAdapter.remove(department);
                departmentAdapter.notifyDataSetChanged();
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
        builder.setMessage("Xóa khoa thành công");
        builder.setTitle("Thông báo");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
