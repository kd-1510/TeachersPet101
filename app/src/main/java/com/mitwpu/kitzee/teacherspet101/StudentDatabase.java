package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {StudentEntity.class, TeacherEntity.class, SubjectEntity.class, ClassEntity.class, DepartmentEntity.class, DepartmentTeacherEntity.class, AttendanceEntity.class}, version = 2)
public abstract class StudentDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();
    public abstract TeacherDao teacherDao();
    public abstract SubjectDao subjectDao();
    public abstract DepartmentDao departmentDao();
    public abstract ClassDao classDao();
    public abstract DepartmentTeacherDao departmentTeacherDao();
    public abstract AttendanceDao attendanceDao();
    // add more daos for every entity created
}
