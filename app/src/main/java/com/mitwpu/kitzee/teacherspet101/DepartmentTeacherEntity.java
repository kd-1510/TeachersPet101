package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "department_teacher_entity",
foreignKeys = {@ForeignKey(entity = DepartmentEntity.class, parentColumns = "dept_id", childColumns = "dept_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = TeacherEntity.class, parentColumns = "teacher_id", childColumns = "teacher_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = ClassEntity.class, parentColumns = "class_id", childColumns = "class_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = SubjectEntity.class, parentColumns = "course_code", childColumns = "course_code", onDelete = ForeignKey.CASCADE)})
public class DepartmentTeacherEntity {
    @PrimaryKey
    @NonNull
    public int dept_tech_id;

    @ColumnInfo
    @NonNull
    public int dept_id;

    @ColumnInfo
    @NonNull
    public String teacher_id;

    @ColumnInfo
    @NonNull
    public int class_id;

    @ColumnInfo
    @NonNull
    public String course_code;

    public DepartmentTeacherEntity() {
    }

    public DepartmentTeacherEntity(@NonNull int dept_tech_id, @NonNull int dept_id, @NonNull String teacher_id, @NonNull int class_id, @NonNull String course_code) {
        this.dept_tech_id = dept_tech_id;
        this.dept_id = dept_id;
        this.teacher_id = teacher_id;
        this.class_id = class_id;
        this.course_code = course_code;
    }

    @NonNull
    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(@NonNull String course_code) {
        this.course_code = course_code;
    }

    @NonNull
    public int getDept_tech_id() {
        return dept_tech_id;
    }

    public void setDept_tech_id(@NonNull int dept_tech_id) {
        this.dept_tech_id = dept_tech_id;
    }

    @NonNull
    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(@NonNull int dept_id) {
        this.dept_id = dept_id;
    }

    @NonNull
    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(@NonNull String teacher_id) {
        this.teacher_id = teacher_id;
    }

    @NonNull
    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(@NonNull int class_id) {
        this.class_id = class_id;
    }
}

