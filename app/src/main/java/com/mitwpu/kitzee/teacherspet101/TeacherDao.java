package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface TeacherDao {
    @Insert
    void insertTeacher(TeacherEntity... teacherEntities);

    @Query("select COUNT(*) from teacher_entity WHERE teacher_id = :teacher_id ")
    public int isValidTeacher(String teacher_id);

    @Query("select * from teacher_entity WHERE teacher_id = :teacher_id ")
    public TeacherEntity getTeacher(String teacher_id);

    @Query("select teacher_name from teacher_entity WHERE teacher_id = :teacher_id ")
    public String getTeacherName(String teacher_id);

}
