package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ClassTeacherAddsStudents extends AppCompatActivity {

    private StudentDatabase studentDatabase;
    private RecyclerView recyclerView;
    private List<StudentEntity> studentEntityList = new ArrayList<>();
    private TeacherAddAdapter teacherAddAdapter;
    private Button add;
    private EditText roll_num, stud_name;
    public int flag=1;
    public SharedPreferences sharedPreferences;
    public String teacher_id;
    public String class_name;
    public int class_id;
    public TextView disp_class_name;
    public int number_of_students, class_strength=88;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_adds_students);

        /* The following line is used in order to build the database and give the database its name, this same statement could be written more than once in different places
        in order to create different databases (by changing the database name), having the same schema. */
        studentDatabase = Room.databaseBuilder(this, StudentDatabase.class, "student_database").build();

        // Add to recycler view
        //teacherAddAdapter.studentEntityList.add(3,new StudentEntity(roll_num.getText().toString(),stud_name.getText().toString()));
        //teacherAddAdapter.notifyItemInserted(3);
        roll_num = (EditText) findViewById(R.id.roll_num);
        stud_name = (EditText) findViewById(R.id.stud_name);

        sharedPreferences = getSharedPreferences("TeachersPet101", MODE_PRIVATE);
        teacher_id = sharedPreferences.getString("teacher_id", null);

        FetchClassNameForClassTeacher fetchClassNameForClassTeacher = new FetchClassNameForClassTeacher();
        fetchClassNameForClassTeacher.execute();

        //Toast.makeText(ClassTeacherAddsStudents.this, "Class id is "+class_id,Toast.LENGTH_LONG).show();
        //Toast.makeText(ClassTeacherAddsStudents.this, "Class Name is "+class_name,Toast.LENGTH_LONG).show();
        disp_class_name = findViewById(R.id.disp_class_name);


        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /* The following code is used in order to execute an asynchronous task, so that the database operations are executed in the background, we do this so that the database
        operations aren't executed on the main thread and so that the application does not crash/lag.*/
                System.out.println("YYYYYYYYYYYYY Class ID"+class_id);

                FetchClassStrengthForAddCheck fetchClassStrengthForAddCheck = new FetchClassStrengthForAddCheck();
                fetchClassStrengthForAddCheck.execute();
                FetchInsertedStudentsCount fetchInsertedStudentsCount = new FetchInsertedStudentsCount();
                fetchInsertedStudentsCount.execute();

                System.out.println("============= Number of students"+number_of_students);
                    System.out.println("============Class strength"+class_strength);
                    if(number_of_students == class_strength)
                    {
                        Toast.makeText(ClassTeacherAddsStudents.this,"CANNOT ADD MORE STUDENTS",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        InsertTask insertTask = new InsertTask();
                        insertTask.execute();
                    }
             }
        });



    }

    // The 3 parameters Void,Vod,Void denote the parameters of onPreExecute,doInBackground and onPostExecute respectively.

    public class InsertTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //ToDo: Add Loader
        }

        @Override
        protected Void doInBackground(Void... voids) {
            createAndAddStudent();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(flag==0)
            {
                Toast toast = Toast.makeText(ClassTeacherAddsStudents.this, "RE-ENTER",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                toast.show();
                flag = 1;
            }
            // Fetching will take place after Insert
            if(class_id == 1)
            {
                roll_num.setText("17MCA0");
            }
            else if(class_id==2)
            {
                roll_num.setText("18MCA0");
            }
            stud_name.setText("");
            FetchStudentsTask fetchStudentsTask = new FetchStudentsTask();
            fetchStudentsTask.execute();
        }
    }

    /* The following function is used in order to create an object of the "Entity" and using the database and dao to call the insert function from the DAO */
    private void createAndAddStudent() {
        StudentEntity studentEntity = new StudentEntity(roll_num.getText().toString(), stud_name.getText().toString(),class_id);
        // TODO: ID SHOULD BE ACCORDING TO THE SELECTED CLASS
        try{
            studentDatabase.studentDao().insertStudent(studentEntity);
        }
        catch (Exception e)
        {
            flag = 0;
        }

    }

    public class FetchStudentsTask extends AsyncTask<Void, Void, List<StudentEntity>> {


        @Override
        protected List<StudentEntity> doInBackground(Void... voids) {
            // List<StudentEntity> studentEntities= studentDatabase.studentDao().getAllStudents();
            // return studentEntities;

            studentEntityList = studentDatabase.studentDao().getAllStudents(class_id);
            return studentEntityList;
        }

        @Override
        protected void onPostExecute(List<StudentEntity> studentEntityList) {
            super.onPostExecute(studentEntityList);
            setUpRecyclerView(studentEntityList);
            //Log.e("Student Count",studentEntities.size()+"") ;
        }
    }

    private void setUpRecyclerView(List<StudentEntity> studentEntityList) {
        // RecyclerView Binding
        recyclerView = findViewById(R.id.teacher_add_recycler_view);

        //Attatch LayoutManager
        // if a problem comes in the layout, replace RecyclerView.LayoutManager with LinearLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //Attatch Adapter with ViewHolder
        teacherAddAdapter = new TeacherAddAdapter(studentEntityList);
        recyclerView.setAdapter(teacherAddAdapter);
    }

    public class FetchClassNameForClassTeacher extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            class_name = studentDatabase.classDao().getClassNameForClassTeacher(teacher_id);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            disp_class_name.setText(class_name);
            FetchClassIdForClassName fetchClassIdForClassName = new FetchClassIdForClassName();
            fetchClassIdForClassName.execute();
        }
    }

    public class FetchClassIdForClassName extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            class_id = studentDatabase.classDao().getClassId(class_name);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(class_id == 1)
            {
                roll_num.setText("17MCA0");
            }
            else if(class_id==2)
            {
                roll_num.setText("18MCA0");
            }
            FetchStudentsTask fetchStudentsTask = new FetchStudentsTask();
            fetchStudentsTask.execute();
        }
    }
    public class FetchClassStrengthForAddCheck extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            class_strength = studentDatabase.classDao().getClassStrength(teacher_id);
            return null;
        }
    }
    public class FetchInsertedStudentsCount extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            number_of_students = studentDatabase.studentDao().getInsertedStudentsCound(class_id);
            return null;
        }
    }

}
