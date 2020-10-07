package cr424q.phantuananh.homework1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Database.StudentDB;
import cr424q.phantuananh.homework1.Model.Department;
import cr424q.phantuananh.homework1.Model.Student;
import cr424q.phantuananh.homework1.Model.StudentList;
import cr424q.phantuananh.homework1.R;
import cr424q.phantuananh.homework1.StudentListOfDepartment;

public class DepartmentAdapter extends ArrayAdapter<Department> {
    Context context;
    int layout;
    List<Department> list = new ArrayList<>() ;
    StudentDB studentDB;
    List<Student> studentList;
    List<Student> studentListTMP;
    String idKhoa;

    public DepartmentAdapter(Context context, int resource, List<Department> objects) {
        super(context, resource, objects);
       this.context = context;
       this.layout = resource;
       this.list = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cellView = convertView;
        if (cellView == null);
        cellView = LayoutInflater.from(context).inflate(this.layout,parent,false);

        studentDB = new StudentDB(getContext(),"Student.db",null,1);

        final Department department = list.get(position);

        TextView tvIdKhoa = cellView.findViewById(R.id.tvShowIdOfKhoa);
        tvIdKhoa.setText("Id khoa : " + String.valueOf(department.getIdKhoa()));
        TextView tvTenKhoa = cellView.findViewById(R.id.tvShowTenOfKhoa);
        tvTenKhoa.setText("Tên khoa : " + department.getTenKhoa());
        Button btnXemDSKhoa = cellView.findViewById(R.id.btnXemDSSVKhoa);

        btnXemDSKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                studentList = new ArrayList<>();
                studentListTMP = new ArrayList<>();
                idKhoa = String.valueOf(department.getIdKhoa());
                try {
                    studentListTMP = studentDB.getAllStudent();
                } catch (CursorIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                if (studentListTMP.isEmpty()) {
                    Toast.makeText(getContext(), "Danh sách sinh viên trống", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < studentListTMP.size(); i++) {
                        if (String.valueOf(studentListTMP.get(i).getIdKhoa()).equalsIgnoreCase(idKhoa)) {
                            studentList.add(studentListTMP.get(i));
                        }
                    }
                    Intent intent = new Intent(context, StudentListOfDepartment.class);
                    intent.putExtra("studentOfDepartment", (ArrayList<Student>) studentList);
                    intent.putExtra("idKhoa",idKhoa);
                    context.startActivity(intent);
                }
            }
        });

        cellView.setLongClickable(true);
        return cellView;
    }

}
