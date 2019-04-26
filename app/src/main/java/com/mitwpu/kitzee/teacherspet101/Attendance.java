package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance extends AppCompatActivity {

    public Intent intent;
    public String selected_subject_name, selected_class_name, course_code, roll_number_format, teacher_id, dummy;
    public StudentDatabase studentDatabase;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public TextView disp_class_name, disp_subject_name, disp_date;
    private RecyclerView recyclerView;
    private StudentDisplayAdapter studentDisplayAdapter;
    private List<StudentEntity> studentEntityList = new ArrayList<>();
    public int class_id, number_of_students,number_of_present_students=0;
    public Button submit_attendance_list;
    public Map<String,Integer> get_attendance_list= new HashMap< String,Integer>();
    public long date_in_long;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        init();
        takeAttendance();
        }

        public void init(){
            studentDatabase = Room.databaseBuilder(this, StudentDatabase.class, "student_database").build();

            sharedPreferences = getSharedPreferences("TeachersPet101", MODE_PRIVATE);
            editor = sharedPreferences.edit();

            class_id = sharedPreferences.getInt("class_id",0);
            FetchClassStrength fetchClassStrength = new FetchClassStrength();
            fetchClassStrength.execute();

            teacher_id = sharedPreferences.getString("teacher_id",null);

            intent = getIntent();
            selected_subject_name = intent.getStringExtra("selected_subject_name");
            selected_class_name = intent.getStringExtra("selected_class_name");

            FetchSubjectId fetchSubjectId = new FetchSubjectId();
            fetchSubjectId.execute();

            editor.putString("course_code",course_code);
            editor.apply();

            disp_class_name = findViewById(R.id.disp_class_name);
            disp_subject_name = findViewById(R.id.disp_subject_name);
            disp_date = findViewById(R.id.disp_date);
            submit_attendance_list = findViewById(R.id.submit_attendance_list);

            disp_class_name.setText(selected_class_name);
            disp_subject_name.setText(selected_subject_name);

            Date d = new Date();
            disp_date.setText(DateFormat.format("d MMM, yyyy ", d.getTime()));
            long date_in_long = d.getTime();

            if(class_id == 1) {
                roll_number_format = "17MCA0";
            }
            else if(class_id == 2)
            {
                roll_number_format = "18MCA0";
            }
        }

        public void takeAttendance()
        {
            FetchStudentsForAttendance fetchStudentsForAttendance = new FetchStudentsForAttendance();
            fetchStudentsForAttendance.execute();

            submit_attendance_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InsertAttendanceRecords insertAttendanceRecords= new InsertAttendanceRecords();
                    insertAttendanceRecords.execute();
                }
            });

            //TODO: Date (Set Current date in TextView)
        }


        public void setUpAttendanceList()
        {
            String dummy;
            for(int i=1; i<=number_of_students;i++)
            {
                if(i<10)
                {
                    dummy=roll_number_format+"0"+i;
                    }
                else
                {
                    dummy=roll_number_format+i;
                }
                get_attendance_list.put(dummy,0);
                System.out.println("xxxxxxxxxxxxxxx"+dummy+"=>"+get_attendance_list.get(dummy));
            }
            studentDisplayAdapter.initialiseAttendanceList(get_attendance_list);
        }


    private void setUpRecyclerView(List<StudentEntity> studentEntityList) {
        // RecyclerView Binding
        recyclerView = findViewById(R.id.disp_students_recycler_view);

        //Attatch LayoutManager
        // if a problem comes in the layout, replace RecyclerView.LayoutManager with LinearLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //Attatch Adapter with ViewHolder
        studentDisplayAdapter = new StudentDisplayAdapter(studentEntityList);
        recyclerView.setAdapter(studentDisplayAdapter);
        System.out.println("**************************************************** IN RECYCLER VIEW SET UP");
        setUpAttendanceList();

    }

        public class FetchSubjectId extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                course_code = studentDatabase.classDao().getCourseCode(selected_subject_name);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
    }

    public class FetchStudentsForAttendance extends AsyncTask<Void, Void, List<StudentEntity>> {


        @Override
        protected List<StudentEntity> doInBackground(Void... voids) {
            studentEntityList = studentDatabase.studentDao().getAllStudents(class_id);
            return studentEntityList;
        }

        @Override
        protected void onPostExecute(List<StudentEntity> studentEntityList) {
            super.onPostExecute(studentEntityList);
            setUpRecyclerView(studentEntityList);
        }
    }

    public class InsertAttendanceRecords extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            intent = new Intent(Attendance.this, AttendanceTaken.class);
            intent.putExtra("selected_class_name",selected_class_name);
            intent.putExtra("selected_subject_name",selected_subject_name);
            intent.putExtra("number_of_present_students",number_of_present_students);

            FetchInsertedAttendaceCount fetchInsertedAttendaceCount = new FetchInsertedAttendaceCount();
            fetchInsertedAttendaceCount.execute();

            startActivity(intent);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            get_attendance_list = studentDisplayAdapter.getAttendanceList();
            // ONLY FOR CMD DISPLAY PURPOSE
            int attendance_status;
            AttendanceEntity attendanceEntity;
            for(int i=1; i<=number_of_students;i++)
            {
                if(i<10)
                {
                    dummy=roll_number_format+"0"+i;
                }
                else
                {
                    dummy=roll_number_format+i;
                }
                attendance_status = get_attendance_list.get(dummy);
                if(attendance_status == 1)
                {
                    number_of_present_students++;
                }
                System.out.println("xxxxx"+dummy+"=>"+attendance_status);
                attendanceEntity = new AttendanceEntity(0,date_in_long,teacher_id,course_code,class_id,dummy,attendance_status);
                studentDatabase.attendanceDao().insertAttendance(attendanceEntity);
            }

            System.out.println("******************************************* Number of present students"+number_of_present_students);

            return null;
        }
    }

    public class FetchInsertedAttendaceCount extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            int count = studentDatabase.attendanceDao().getCountOfAttendanceRecords();
            System.out.println("INSERTED RECORDS ARE ****************"+count);
            return null;
        }
    }

    public class FetchClassStrength extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            number_of_students = studentDatabase.classDao().getClassStrength(class_id);
            return null;
        }
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
                intent = new Intent(Attendance.this, SubjectSelectionForTeacher.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                editor.putInt("login_state", 0);
                editor.apply();
                intent = new Intent(Attendance.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

