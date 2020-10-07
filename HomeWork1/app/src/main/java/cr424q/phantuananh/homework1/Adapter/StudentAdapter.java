package cr424q.phantuananh.homework1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cr424q.phantuananh.homework1.R;
import cr424q.phantuananh.homework1.ShowInformation;
import cr424q.phantuananh.homework1.Model.Student;

public class StudentAdapter extends ArrayAdapter<Student> {
    Context context;
    int layout;
    List<Student> list = new ArrayList<>();

    public StudentAdapter(Context context, int resource, List<Student> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout =resource;
        this.list = objects;
    }

    @Override
    public View getView(int position, final View convertView, final ViewGroup parent) {
    View cellView = convertView;
    if (cellView == null);
    cellView = LayoutInflater.from(context).inflate(this.layout,parent,false);

    final Student student = list.get(position);

            TextView tvHoTen = cellView.findViewById(R.id.tvFullName);
            tvHoTen.setText("Họ và tên : " + student.getFullName());
            TextView tvidKhoa = cellView.findViewById(R.id.tvidKhoaaa);
            tvidKhoa.setText("Id khoa : " + String.valueOf(student.getIdKhoa()));

        cellView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowInformation.class);
                intent.putExtra("student",student);
                context.startActivity(intent);
            }
        });
        cellView.setLongClickable(true);
        return  cellView;
    }



}
