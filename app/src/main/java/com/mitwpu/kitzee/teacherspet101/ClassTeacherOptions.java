package com.mitwpu.kitzee.teacherspet101;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ClassTeacherOptions extends AppCompatActivity {

    public Button add_students, take_attendance;
    public Intent intent;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public String teacher_id;
    public int back = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_options);

        init();
        performClassTeacherOptions();
    }

    public void init(){
        sharedPreferences = getSharedPreferences("TeachersPet101", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        teacher_id = sharedPreferences.getString("teacher_id", null);
      //  Toast.makeText(ClassTeacherOptions.this,"Teacher id is "+teacher_id, Toast.LENGTH_LONG).show();

        add_students=(Button)findViewById(R.id.add_students);
        take_attendance=(Button)findViewById(R.id.take_attendance);
    }

    public void performClassTeacherOptions(){
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
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                if (back == 0)
                {
                    Toast.makeText(ClassTeacherOptions.this, "Press back again to exit!", Toast.LENGTH_SHORT).show();
                    back++;
                }
                else
                    this.finishAffinity();
                return true;
            case R.id.logout:
                editor.putInt("login_state", 0);
                editor.apply();
                intent = new Intent(ClassTeacherOptions.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (back == 0)
        {
            Toast.makeText(ClassTeacherOptions.this, "Press back again to exit!", Toast.LENGTH_SHORT).show();
            back++;
        }
        else
            this.finishAffinity();
    }
}
