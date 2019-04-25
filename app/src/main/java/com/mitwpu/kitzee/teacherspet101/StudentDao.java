package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insertStudent(StudentEntity... studentEntities);

    @Query("SELECT * from student_entity where class_id = :classId order by roll_no ASC")
    List<StudentEntity> getAllStudents(int classId);

    @Query("SELECT COUNT(*) from student_entity where class_id = :classId")
    int getInsertedStudentsCound(int classId);
    //we dont need select * cause we added a 3rd column class.maybe thats why recycler view is not working
    //TODO: Check assumption
}
