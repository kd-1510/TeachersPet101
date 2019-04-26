package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public int flag=1, entered_roll_num_validation=0,class_id, number_of_students, class_strength;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Intent intent;
    public String teacher_id, class_name, roll_num_format;
    public TextView disp_class_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_teacher_adds_students);

        init();
        insertAndFetchStudents();
    }

    public void init()
    {
        /* The following line is used in order to build the database and give the database its name, this same statement could be written more than once in different places
        in order to create different databases (by changing the database name), having the same schema. */
        studentDatabase = Room.databaseBuilder(this, StudentDatabase.class, "student_database").build();

        roll_num = (EditText) findViewById(R.id.roll_num);
        stud_name = (EditText) findViewById(R.id.stud_name);
        disp_class_name = findViewById(R.id.disp_class_name);
        add = (Button) findViewById(R.id.add);

        sharedPreferences = getSharedPreferences("TeachersPet101", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        teacher_id = sharedPreferences.getString("teacher_id", null);

        FetchClassNameForClassTeacher fetchClassNameForClassTeacher = new FetchClassNameForClassTeacher();
        fetchClassNameForClassTeacher.execute();
    }

    public void insertAndFetchStudents()
    {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entered_roll_num_validation = 0;
                for(int i=1; i<=class_strength+10;i++)
                {
                    if(i<10)
                    {
                        System.out.println("************************************ "+roll_num.getText()+" ---> "+roll_num_format+"0"+i);
                        if(roll_num.getText().toString().equals(roll_num_format+"0"+i))
                        {
                            entered_roll_num_validation = 1;
                            break;
                        }
                    }
                    else
                    {
                        System.out.println("************************************ "+roll_num.getText()+" ---> "+roll_num_format+i);
                        if(roll_num.getText().toString().equals(roll_num_format+i))
                        {
                            entered_roll_num_validation = 1;
                            break;
                        }
                    }
                }
                if(entered_roll_num_validation == 0)
                {
                    Toast.makeText(ClassTeacherAddsStudents.this, "ENTERED ROLL NUMBER IS NOT IN THE CORRECT FORMAT", Toast.LENGTH_SHORT).show();
                }
                else if(stud_name.getText().toString().equals("")||stud_name.getText().toString().equals(" ")) {
                    Toast.makeText(ClassTeacherAddsStudents.this, "STUDENT NAME CANNOT BE EMPTY", Toast.LENGTH_SHORT).show();
                }
                else {
                    FetchInsertedStudentsCount fetchInsertedStudentsCount = new FetchInsertedStudentsCount();
                    fetchInsertedStudentsCount.execute();
                    // Insert Task is called in the Post execute of this fetch task
                }
            }
        });

    }

    /* The following code is used in order to execute an asynchronous task, so that the database operations are executed in the background, we do this so that the database
        operations aren't executed on the main thread and so that the application does not crash/lag.*/
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
                Toast toast = Toast.makeText(ClassTeacherAddsStudents.this, "Roll number already exists. CANNOT ADD",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP|Gravity.CENTER,0,0);
                toast.show();
                flag = 1;
            }
            // Fetching will take place after Insert
            roll_num.setText(roll_num_format);
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
            studentEntityList = studentDatabase.studentDao().getAllStudents(class_id);
            return studentEntityList;
        }

        @Override
        protected void onPostExecute(List<StudentEntity> studentEntityList) {
            super.onPostExecute(studentEntityList);
            setUpRecyclerView(studentEntityList);

        }
    }

    private void setUpRecyclerView(List<StudentEntity> studentEntityList) {
        // RecyclerView Binding
        recyclerView = findViewById(R.id.teacher_add_recycler_view);

        //Attach LayoutManager
        // if a problem comes in the layout, replace RecyclerView.LayoutManager with LinearLayoutManager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //Attach Adapter with ViewHolder
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
                roll_num_format = "17MCA0";
            }
            else if(class_id==2)
            {
                roll_num_format = "18MCA0";
            }
            roll_num.setText(roll_num_format);
            FetchClassStrengthForAddCheck fetchClassStrengthForAddCheck = new FetchClassStrengthForAddCheck();
            fetchClassStrengthForAddCheck.execute();
            FetchStudentsTask fetchStudentsTask = new FetchStudentsTask();
            fetchStudentsTask.execute();
        }
    }
    public class FetchClassStrengthForAddCheck extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            class_strength = studentDatabase.classDao().getClassStrength(class_id);
            System.out.println("********************************************* Class strength"+class_strength);
            return null;
        }
    }
    public class FetchInsertedStudentsCount extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            number_of_students = studentDatabase.studentDao().getInsertedStudentsCount(class_id);
            System.out.println("********************************************* Number of students"+number_of_students);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(number_of_students < class_strength)
            {
                InsertTask insertTask = new InsertTask();
                insertTask.execute();
            }
            else {
                Toast.makeText(ClassTeacherAddsStudents.this, "CANNOT ADD MORE STUDENTS", Toast.LENGTH_LONG).show();
            }
        }
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
                intent = new Intent(ClassTeacherAddsStudents.this, ClassTeacherOptions.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                editor.putInt("login_state", 0);
                editor.apply();
                intent = new Intent(ClassTeacherAddsStudents.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
