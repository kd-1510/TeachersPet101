package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "subject_entity")
public class SubjectEntity {
    @PrimaryKey
    @NonNull
    public String course_code;

    @ColumnInfo
    public String course_name;

    public SubjectEntity(@NonNull String course_code, String course_name) {
        this.course_code = course_code;
        this.course_name = course_name;
    }

    @NonNull
    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(@NonNull String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }
}

