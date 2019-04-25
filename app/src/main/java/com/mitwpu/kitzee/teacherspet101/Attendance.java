package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance extends AppCompatActivity {

    public Intent intent;
    public String selected_subject_name;
    public String selected_class_name;
    public StudentDatabase studentDatabase;
    public String course_code;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public TextView disp_class_name, disp_subject_name, disp_date;
    private RecyclerView recyclerView;
    private StudentDisplayAdapter studentDisplayAdapter;
    //Check next statement
    private List<StudentEntity> studentEntityList = new ArrayList<>();
    public int class_id;
    public Button submit_attendance_list;
    public Map<String,Integer> get_attendance_list= new HashMap< String,Integer>();
    public String roll_number_format;
    public int number_of_students;
    public String teacher_id;
    public String dummy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        System.out.println("__________________________________________ IN ATTENDANCE");
        studentDatabase = Room.databaseBuilder(this, StudentDatabase.class, "student_database").build();

        sharedPreferences = getSharedPreferences("TeachersPet101", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        class_id = sharedPreferences.getInt("class_id",0);
        FetchClassStrength fetchClassStrength = new FetchClassStrength();
        fetchClassStrength.execute();


        teacher_id = sharedPreferences.getString("teacher_id",null);
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ Class ID"+class_id);
        intent = getIntent();
        selected_subject_name = intent.getStringExtra("selected_subject_name");
        selected_class_name = intent.getStringExtra("selected_class_name");

        Toast.makeText(this, selected_class_name + ", " + selected_subject_name, Toast.LENGTH_SHORT).show();

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
        disp_date.setText("DATE");

        if(class_id == 1) {
            roll_number_format = "17MCA0";
        }
        else if(class_id == 2)
        {
            roll_number_format = "18MCA0";
        }

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

        // Insert Data Into the Database
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
            // List<StudentEntity> studentEntities= studentDatabase.studentDao().getAllStudents();
            // return studentEntities;

            studentEntityList = studentDatabase.studentDao().getAllStudents(class_id);
            System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII 1st student roll number"+studentEntityList.get(0).getRoll_no());
            return studentEntityList;
        }

        @Override
        protected void onPostExecute(List<StudentEntity> studentEntityList) {
            super.onPostExecute(studentEntityList);
            setUpRecyclerView(studentEntityList);
            //Log.e("Student Count",studentEntities.size()+"") ;
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
            FetchInsertedAttendaceCount fetchInsertedAttendaceCount = new FetchInsertedAttendaceCount();
            fetchInsertedAttendaceCount.execute();
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
                System.out.println("xxxxx"+dummy+"=>"+attendance_status);
                attendanceEntity = new AttendanceEntity(0,8797778,teacher_id,course_code,class_id,dummy,attendance_status);
                studentDatabase.attendanceDao().insertAttendance(attendanceEntity);
            }

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

}

