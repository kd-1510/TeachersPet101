package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class SubjectSelectionForTeacher extends AppCompatActivity {

    public Intent intent;
    String selected_class_name;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public String teacher_id;
    private StudentDatabase studentDatabase;
    public List<String> subject_names;
    public Spinner subject_selection_spinner;
    public Button subject_selected_now_next;
    public TextView disp_teacher_name;
    public String teacher_name;
    public String selected_subject_name;
    public int class_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_selection_for_teacher);

        intent = getIntent();
        selected_class_name = intent.getStringExtra("selected_class_name");
        Toast.makeText(this, "Selected class :" + selected_class_name, Toast.LENGTH_SHORT).show();

        sharedPreferences = getSharedPreferences("TeachersPet101", MODE_PRIVATE);
        teacher_id = sharedPreferences.getString("teacher_id", null);

        studentDatabase = Room.databaseBuilder(this, StudentDatabase.class, "student_database").build();

        subject_selection_spinner = (Spinner) findViewById(R.id.subject_selection_spinner);
        disp_teacher_name = findViewById(R.id.disp_teacher_name);

        FetchTeacherName fetchTeacherName = new FetchTeacherName();
        fetchTeacherName.execute();

        FetchClassId fetchClassId = new FetchClassId();
        fetchClassId.execute();


        subject_selected_now_next = (Button) findViewById(R.id.subject_selected_now_next);
        subject_selected_now_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                selected_subject_name = String.valueOf(subject_selection_spinner.getSelectedItem());
                intent = new Intent(SubjectSelectionForTeacher.this, Attendance.class);
                intent.putExtra("selected_class_name", selected_class_name);
                intent.putExtra("selected_subject_name", selected_subject_name);
                startActivity(intent);
            }
        });

    }

    public class FetchSubjects extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            subject_names = studentDatabase.classDao().getCourseNames(class_id, teacher_id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(SubjectSelectionForTeacher.this,android.R.layout.simple_spinner_item, subject_names);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            subject_selection_spinner.setAdapter(dataAdapter);
        }
    }

    public class FetchClassId extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            class_id = studentDatabase.classDao().getClassId(selected_class_name);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            editor = sharedPreferences.edit();
            editor.putInt("class_id",class_id);
            editor.apply();
            FetchSubjects fetchSubjects = new FetchSubjects();
            fetchSubjects.execute();
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
