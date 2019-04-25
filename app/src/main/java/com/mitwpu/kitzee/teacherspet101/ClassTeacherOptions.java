package com.mitwpu.kitzee.teacherspet101;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ClassTeacherOptions extends AppCompatActivity {

    public Button add_students, take_attendance;
    public Intent intent;
    public SharedPreferences sharedPreferences;
    public String teacher_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_options);

        sharedPreferences = getSharedPreferences("TeachersPet101", MODE_PRIVATE);
        teacher_id = sharedPreferences.getString("teacher_id", null);
        Toast.makeText(ClassTeacherOptions.this,"Teacher id is "+teacher_id, Toast.LENGTH_LONG).show();

        add_students=(Button)findViewById(R.id.add_students);
        take_attendance=(Button)findViewById(R.id.take_attendance);

        add_students.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ClassTeacherOptions.this, ClassTeacherAddsStudents.class );
                startActivity(intent);
            }
        });


        take_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ClassTeacherOptions.this, ClassSelectionForTeacher.class );
                //TODO: Change this intent later
                startActivity(intent);
            }
        });
    }
}
