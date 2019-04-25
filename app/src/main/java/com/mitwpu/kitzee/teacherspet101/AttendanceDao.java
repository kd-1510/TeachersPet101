package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AttendanceDao {
    @Insert
    void insertAttendance(AttendanceEntity... attendanceEntities);

    @Query("SELECT * FROM student_entity WHERE class_id = :class_id ")
    List<StudentEntity> getAllStudentsForAttendance(int class_id);

    @Query("SELECT COUNT(*) FROM student_entity")
    int getCountOfAttendanceRecords();
}
