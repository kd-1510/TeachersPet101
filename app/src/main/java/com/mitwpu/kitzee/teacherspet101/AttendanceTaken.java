package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class AttendanceTaken extends AppCompatActivity {

    public TextView disp_class_name, disp_subject_name, disp_date, disp_number_of_present_students;
    public StudentDatabase studentDatabase;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public int class_id, number_of_present_students;
    public String teacher_id, course_code;
    public Button exit;
    public Intent intent;
    public String selected_subject_name,selected_class_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_taken);

        init();
        backToHomePage();
    }

    public void init(){
        studentDatabase = Room.databaseBuilder(this, StudentDatabase.class, "student_database").build();

        sharedPreferences = getSharedPreferences("TeachersPet101", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        class_id = sharedPreferences.getInt("class_id",0);

        teacher_id = sharedPreferences.getString("teacher_id",null);
        course_code = sharedPreferences.getString("course_code",null);

        disp_class_name = findViewById(R.id.disp_class_name);
        disp_subject_name = findViewById(R.id.disp_subject_name);
        disp_date = findViewById(R.id.disp_date);
        disp_number_of_present_students = findViewById(R.id.disp_number_of_present_students);
        exit = findViewById(R.id.exit);

        intent = getIntent();
        selected_subject_name = intent.getStringExtra("selected_subject_name");
        selected_class_name = intent.getStringExtra("selected_class_name");
        number_of_present_students = intent.getIntExtra("number_of_present_students",0);
        System.out.println("********* IN ATTENDANCE TAKEN\n Number of present students are: "+number_of_present_students);

        disp_class_name.setText(selected_class_name);
        disp_subject_name.setText(selected_subject_name);

        Date d = new Date();
        disp_date.setText(DateFormat.format("d MMM, yyyy ", d.getTime()));

        disp_number_of_present_students.setText(number_of_present_students+"");
    }

    public void backToHomePage()
    {
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceTaken.this.finishAffinity();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu_class_teacher_adds_students, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                Toast.makeText(AttendanceTaken.this, "You can now take a new attendance",Toast.LENGTH_SHORT).show();
                intent = new Intent(AttendanceTaken.this, ClassSelectionForTeacher.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                editor.putInt("login_state", 0);
                editor.apply();
                intent = new Intent(AttendanceTaken.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(AttendanceTaken.this, "You can now take a new attendance",Toast.LENGTH_SHORT).show();
        intent = new Intent(AttendanceTaken.this, ClassSelectionForTeacher.class);
        startActivity(intent);
    }

}
