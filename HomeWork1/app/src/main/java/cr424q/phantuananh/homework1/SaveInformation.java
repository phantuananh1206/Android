package cr424q.phantuananh.homework1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cr424q.phantuananh.homework1.Model.Student;

public class SaveInformation extends AppCompatActivity {
    TextView tvShowHo, tvShowHoLot, tvShowTen, tvShowMaSV, tvShowDienThoai, tvShowNgaySinh, tvShowGioiTinh, tvShowIdKhoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_information);

        tvShowHo = findViewById(R.id.tvShowHo);
        tvShowHoLot = findViewById(R.id.tvShowHoLot);
        tvShowTen = findViewById(R.id.tvShowTen);
        tvShowMaSV = findViewById(R.id.tvShowMaSV);
        tvShowDienThoai = findViewById(R.id.tvShowDienThoai);
        tvShowNgaySinh = findViewById(R.id.tvShowNgaySinh);
        tvShowGioiTinh = findViewById(R.id.tvShowGioiTinh);
        tvShowIdKhoa = findViewById(R.id.tvShowIDKhoa);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Student student = (Student) getIntent().getSerializableExtra("student");

        tvShowHo.setText(student.getHo());
        tvShowHoLot.setText(student.getHoLot());
        tvShowTen.setText(student.getTen());
        tvShowMaSV.setText(student.getMaSV());
        tvShowDienThoai.setText(student.getDienThoai());
        tvShowNgaySinh.setText(dateToString(student.getNgaySinh()));
        if(student.getGioiTinh()){
            tvShowGioiTinh.setText("Nam");
        }else{
            tvShowGioiTinh.setText("Nữ");
        }
        tvShowIdKhoa.setText(String.valueOf(student.getIdKhoa()));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    public String  dateToString(Date date){
        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

}
