package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ClassSelectionForTeacher extends AppCompatActivity {

    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public String teacher_id;
    private StudentDatabase studentDatabase;
    public List<String> class_names;
    public Spinner class_selection_spinner;
    public Button class_selected_now_next;
    public String selected_class_name;
    public TextView disp_teacher_name;
    public String teacher_name;
    public Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_selection_for_teacher);

        // Fetch classes that teacher teaches

        sharedPreferences = getSharedPreferences("TeachersPet101", MODE_PRIVATE);
        teacher_id = sharedPreferences.getString("teacher_id", null);

        studentDatabase = Room.databaseBuilder(this, StudentDatabase.class, "student_database").build();

        class_selection_spinner = (Spinner) findViewById(R.id.class_selection_spinner);
        disp_teacher_name = findViewById(R.id.disp_teacher_name);

        FetchTeacherName fetchTeacherName = new FetchTeacherName();
        fetchTeacherName.execute();

        FetchClasses fetchClasses = new FetchClasses();
        fetchClasses.execute();


        class_selected_now_next = (Button) findViewById(R.id.class_selected_now_next);
        class_selected_now_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                selected_class_name = String.valueOf(class_selection_spinner.getSelectedItem());
                intent = new Intent(ClassSelectionForTeacher.this, SubjectSelectionForTeacher.class);
                intent.putExtra("selected_class_name",selected_class_name);
                startActivity(intent);


            }
        });

    }


    public class FetchClasses extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            class_names = studentDatabase.classDao().getClassNames(teacher_id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ClassSelectionForTeacher.this,android.R.layout.simple_spinner_item, class_names);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            class_selection_spinner.setAdapter(dataAdapter);
        }
    }

    public class FetchTeacherName extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            teacher_name = studentDatabase.teacherDao().getTeacherName(teacher_id);
            disp_teacher_name.setText(teacher_name);

            return null;
        }
    }
}
