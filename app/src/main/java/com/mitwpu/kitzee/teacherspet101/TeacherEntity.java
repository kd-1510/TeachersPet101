package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "teacher_entity")
public class TeacherEntity {
    @PrimaryKey
    @NonNull
    public String teacher_id;

    @ColumnInfo
    public String teacher_name;

    @ColumnInfo
    public int class_teacher_status;
    // 1: Class teacher, 0: Not class teacher

    public TeacherEntity(@NonNull String teacher_id, String teacher_name, int class_teacher_status) {
        this.teacher_id = teacher_id;
        this.teacher_name = teacher_name;
        this.class_teacher_status = class_teacher_status;
    }

    @NonNull
    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(@NonNull String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public int getClass_teacher_status() {
        return class_teacher_status;
    }

    public void setClass_teacher_status(int class_teacher_status) {
        this.class_teacher_status = class_teacher_status;
    }

}

