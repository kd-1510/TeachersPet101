package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ClassDao {

    @Insert
    void insertClass(ClassEntity... classEntities);

    @Query("SELECT class_name from class_entity WHERE class_id IN " +
            "(SELECT class_id from department_teacher_entity WHERE teacher_id = :teacher_id)")
    List<String> getClassNames(String teacher_id);

   @Query("SELECT * from department_teacher_entity")
   List<DepartmentTeacherEntity> getClassIdsForTeacher();

//    @Query("SELECT course_name from subject_entity,department_teacher_entity " +
//            "WHERE subject_entity.course_code = department_teacher_entity.course_code " +
//            "AND class_id = :class_id " +
//            "AND teacher_id = :teacher_id")
//    List<String> getCourseNames(int class_id, String teacher_id);

    @Query("SELECT course_name from subject_entity " +
            "WHERE course_code IN " +
            "(SELECT course_code from department_teacher_entity " +
            "WHERE class_id = :class_id AND teacher_id = :teacher_id )")
    List<String> getCourseNames(int class_id, String teacher_id);

    @Query("SELECT class_id from class_entity where class_name = :class_name")
    int getClassId(String class_name);
    // Take selected value from drop-down list to get id for next query

    @Query("SELECT course_code from subject_entity where course_name = :course_name")
    String getCourseCode(String course_name);
    // Take selected value from drop-down list to get id for the attendance

    @Query("SELECT class_name from class_entity where class_teacher_id = :teacher_id")
    String getClassNameForClassTeacher(String teacher_id);

    @Query("SELECT class_strength from class_entity WHERE class_teacher_id = :teacher_id")
    int getClassStrength(String teacher_id);
}
