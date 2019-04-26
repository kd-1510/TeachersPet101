package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    public String teacher_id, password;
    private StudentDatabase studentDatabase;
    public Button login;
    public int isValid=0, class_teacher_status,login_state, insert_only_once;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Intent intent;
    public TeacherEntity teacherEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        login_state = sharedPreferences.getInt("login_state",0);
        if(login_state == 0) {
            setContentView(R.layout.activity_login);
            loginValidation();
        }
        else
        {
            teacher_id = sharedPreferences.getString("teacher_id",null);
            FetchTeacherStatus fetchTeacherStatus = new FetchTeacherStatus();
            fetchTeacherStatus.execute();
        }
    }

    public void init()
    {
        studentDatabase = Room.databaseBuilder(this, StudentDatabase.class, "student_database").build();

        sharedPreferences = getSharedPreferences("TeachersPet101", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        insert_only_once = sharedPreferences.getInt("insert_only_once",0);
        if(insert_only_once == 0) {
            System.out.println("******************** IN INSERT ONLY ONCE");
            InsertTeacher insertTeacher = new InsertTeacher();
            insertTeacher.execute();
            InsertSubject insertSubject = new InsertSubject();
            insertSubject.execute();
            InsertDepartment insertDepartment = new InsertDepartment();
            insertDepartment.execute();
            InsertClass insertClass = new InsertClass();
            insertClass.execute();
            InsertLinks insertLinks = new InsertLinks();
            insertLinks.execute();
            editor.putInt("insert_only_once",1);
            editor.apply();
        }
    }

    public void loginValidation(){
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacher_id = ((EditText) findViewById(R.id.teacher_id)).getText().toString();
                password = ((EditText) findViewById(R.id.password)).getText().toString();
                ValidateTeacher validateTeacher = new ValidateTeacher();
                validateTeacher.execute();
            }
        });
    }

    public class ValidateTeacher extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            if (studentDatabase.teacherDao().isValidTeacher(teacher_id) != 0) {
                isValid = 1;
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(isValid == 1)
            {
                if (teacher_id.equals(password)) {
                    editor.putString("teacher_id", teacher_id);
                    editor.putInt("login_state", 1);
                    editor.apply();
                    FetchTeacherStatus fetchTeacherStatus = new FetchTeacherStatus();
                    fetchTeacherStatus.execute();
                }
                else {
                    Toast.makeText(LoginActivity.this, "INCORRECT ID OR PASSWORD", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(LoginActivity.this, "INVALID CREDENTIALS", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class FetchTeacherStatus extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            teacherEntity = studentDatabase.teacherDao().getTeacher(teacher_id);
            class_teacher_status = teacherEntity.getClass_teacher_status();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(class_teacher_status == 1)
            {
                intent = new Intent(LoginActivity.this, ClassTeacherOptions.class );
            }
            else
            {
                intent = new Intent(LoginActivity.this, ClassSelectionForTeacher.class);
            }
            editor.putInt("class_teacher_status",class_teacher_status);
            editor.apply();
            startActivity(intent);
        }
    }

    /* THIS IS JUST FOR THE TIME-BEING
    MANUALLY ADDING EVERYTHING
*/
    public class InsertTeacher extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //ToDo: Add Loader
        }

        @Override
        protected Void doInBackground(Void... voids) {
            TeacherEntity teacherEntity1 = new TeacherEntity("MCA001", "Mrs. Suman Pasrija", 1);
            studentDatabase.teacherDao().insertTeacher(teacherEntity1);
            TeacherEntity teacherEntity2 = new TeacherEntity("MCA002", "Dr. Ashish Kulkarni", 0);
            studentDatabase.teacherDao().insertTeacher(teacherEntity2);
            TeacherEntity teacherEntity3 = new TeacherEntity("MCA003", "Mr. Dinesh Banswal", 1);
            studentDatabase.teacherDao().insertTeacher(teacherEntity3);
            TeacherEntity teacherEntity4 = new TeacherEntity("MCA004", "Mr. Shridhar G.", 0);
            studentDatabase.teacherDao().insertTeacher(teacherEntity4);
            TeacherEntity teacherEntity5 = new TeacherEntity("MCA005", "Dr. Harshali Patil", 0);
            studentDatabase.teacherDao().insertTeacher(teacherEntity5);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            }
    }


    public class InsertSubject extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //ToDo: Add Loader
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SubjectEntity subjectEntity1 = new SubjectEntity("MCA6001", "ASP.Net");
            studentDatabase.subjectDao().insertSubject(subjectEntity1);
            SubjectEntity subjectEntity2 = new SubjectEntity("MCA6002", "SSP using Php & Python");
            studentDatabase.subjectDao().insertSubject(subjectEntity2);
            SubjectEntity subjectEntity3 = new SubjectEntity("MCA6003", "Information Security");
            studentDatabase.subjectDao().insertSubject(subjectEntity3);
            SubjectEntity subjectEntity4 = new SubjectEntity("MCA6004", "Design Thinking - 1");
            studentDatabase.subjectDao().insertSubject(subjectEntity4);
            SubjectEntity subjectEntity5 = new SubjectEntity("MCA3001", "Object Oriented Analysis & Design");
            studentDatabase.subjectDao().insertSubject(subjectEntity5);
            SubjectEntity subjectEntity6 = new SubjectEntity("MCA3002", "Algorithhmic Design");
            studentDatabase.subjectDao().insertSubject(subjectEntity6);
            SubjectEntity subjectEntity7 = new SubjectEntity("MCA3003", "Core Java");
            studentDatabase.subjectDao().insertSubject(subjectEntity7);
            SubjectEntity subjectEntity8 = new SubjectEntity("MCA3004", "Optimizing Techniques");
            studentDatabase.subjectDao().insertSubject(subjectEntity8);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public class InsertDepartment extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //ToDo: Add Loader
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DepartmentEntity departmentEntity = new DepartmentEntity(1,"MCA");
            studentDatabase.departmentDao().insertDepartment(departmentEntity);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public class InsertClass extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //ToDo: Add Loader
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ClassEntity classEntity = new ClassEntity(1,2017,2020,"MCA-2",20,"MCA001");
            studentDatabase.classDao().insertClass(classEntity);
            ClassEntity classEntity2 = new ClassEntity(2,2018,2021,"MCA-1",5,"MCA003");
            studentDatabase.classDao().insertClass(classEntity2);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public class InsertLinks extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //ToDo: Add Loader
        }

        @Override
        protected Void doInBackground(Void... voids) {
            DepartmentTeacherEntity departmentTeacherEntity = new DepartmentTeacherEntity(1,1,"MCA004",1,"MCA6001");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity);
            DepartmentTeacherEntity departmentTeacherEntity2 = new DepartmentTeacherEntity(2,1,"MCA002",1,"MCA6002");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity2);
            DepartmentTeacherEntity departmentTeacherEntity3 = new DepartmentTeacherEntity(3,1,"MCA001",1,"MCA6002");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity3);
            DepartmentTeacherEntity departmentTeacherEntity4 = new DepartmentTeacherEntity(4,1,"MCA005",1,"MCA6003");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity4);
            DepartmentTeacherEntity departmentTeacherEntity5 = new DepartmentTeacherEntity(5,1,"MCA003",1,"MCA6004");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity5);
            DepartmentTeacherEntity departmentTeacherEntity6 = new DepartmentTeacherEntity(6,1,"MCA005",2,"MCA3001");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity6);
            DepartmentTeacherEntity departmentTeacherEntity7 = new DepartmentTeacherEntity(7,1,"MCA001",2,"MCA3002");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity7);
            DepartmentTeacherEntity departmentTeacherEntity8 = new DepartmentTeacherEntity(8,1,"MCA004",2,"MCA3002");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity8);
            DepartmentTeacherEntity departmentTeacherEntity9 = new DepartmentTeacherEntity(9,1,"MCA002",2,"MCA3003");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity9);
            DepartmentTeacherEntity departmentTeacherEntity10 = new DepartmentTeacherEntity(10,1,"MCA003",2,"MCA3003");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity10);
            DepartmentTeacherEntity departmentTeacherEntity11 = new DepartmentTeacherEntity(11,1,"MCA005",2,"MCA3004");
            studentDatabase.departmentTeacherDao().insertDepartmentTeacher(departmentTeacherEntity11);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }
}

