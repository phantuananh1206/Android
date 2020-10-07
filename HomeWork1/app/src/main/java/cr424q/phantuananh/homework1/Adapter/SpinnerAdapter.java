package cr424q.phantuananh.homework1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import cr424q.phantuananh.homework1.Model.Department;
import cr424q.phantuananh.homework1.R;

public class SpinnerAdapter extends ArrayAdapter<Department> {
    Context context;
    int layout;
    List<Department> list;
    LayoutInflater inflater;
    public SpinnerAdapter(Context context, int resource, List<Department> objects) {
        super(context, resource,0, objects);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.layout = resource;
        this.list = objects;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewRow = inflater.inflate(R.layout.spinner_department,parent,false);

        Department department = list.get(position);
        TextView idKhoa = viewRow.findViewById(R.id.textView10);
        idKhoa.setText((String.valueOf(department.getIdKhoa())) +" - "+ department.getTenKhoa().toString());
        return viewRow;
    }
}
