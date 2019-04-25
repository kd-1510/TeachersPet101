package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DepartmentTeacherDao {
    @Insert
    void insertDepartmentTeacher(DepartmentTeacherEntity... departmentTeacherEntities);
}
