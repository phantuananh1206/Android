package cr424q.phantuananh.homework1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import cr424q.phantuananh.homework1.Model.Student;

public class ShowInformation extends AppCompatActivity {
    TextView tvShowFullname, tvShowMaSV, tvShowTuoi, tvShowDienThoai, tvShowGioiTinh, tvShowNgaySinh, tvShowIDKhoa;
    ImageButton imgBtnCall, imgBtnMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_information);

        tvShowFullname = findViewById(R.id.tvShowHoVaTen);
        tvShowDienThoai = findViewById(R.id.tvShowDT);
        tvShowTuoi = findViewById(R.id.tvShowTuoi);
        tvShowMaSV = findViewById(R.id.tvShowMaSinhVien);
        tvShowDienThoai = findViewById(R.id.tvShowDT);
        tvShowNgaySinh = findViewById(R.id.tvShowNSinh);
        tvShowGioiTinh = findViewById(R.id.tvShowGT);
        imgBtnCall = findViewById(R.id.imgBtnCall);
        imgBtnMessage = findViewById(R.id.imgBtnMessage);
        tvShowIDKhoa = findViewById(R.id.tvshowIDKHOA);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Student student = (Student) getIntent().getSerializableExtra("student");

        tvShowFullname.setText(student.getFullName());
        tvShowTuoi.setText(String.valueOf(student.tinhTuoi()));
        if(student.getGioiTinh()){
            tvShowGioiTinh.setText("Nam");
        }else{
            tvShowGioiTinh.setText("Nữ");
        }
        tvShowNgaySinh.setText(dateToString(student.getNgaySinh()));
        tvShowDienThoai.setText(student.getDienThoai());
        tvShowMaSV.setText(student.getMaSV());
        tvShowIDKhoa.setText(String.valueOf(student.getIdKhoa()));

        imgBtnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+ student.getDienThoai()));
                startActivity(intent);
            }
        });
        imgBtnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:"+ student.getDienThoai()));
                startActivity(intent);
            }
        });


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
