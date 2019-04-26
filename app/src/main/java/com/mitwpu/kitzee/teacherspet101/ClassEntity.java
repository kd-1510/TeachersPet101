package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "class_entity",
foreignKeys = @ForeignKey(entity = TeacherEntity.class, parentColumns = "teacher_id", childColumns = "class_teacher_id", onDelete = ForeignKey.CASCADE))
public class ClassEntity {
    @PrimaryKey
    @NonNull
    public int class_id;

    @ColumnInfo
    @NonNull
    public int start_year;

    @ColumnInfo
    @NonNull
    public int end_year;

    @ColumnInfo
    @NonNull
    public String class_name;

    @ColumnInfo
    @NonNull
    public int class_strength;

    @ColumnInfo
    @NonNull
    public String class_teacher_id;
    // this is a foreign key jiska real name is teacher_id


    public ClassEntity(@NonNull int class_id, @NonNull int start_year, @NonNull int end_year, @NonNull String class_name, @NonNull int class_strength, @NonNull String class_teacher_id) {
        this.class_id = class_id;
        this.start_year = start_year;
        this.end_year = end_year;
        this.class_name = class_name;
        this.class_strength = class_strength;
        this.class_teacher_id = class_teacher_id;
    }

    @NonNull
    public String getClass_teacher_id() {
        return class_teacher_id;
    }

    public void setClass_teacher_id(@NonNull String class_teacher_id) {
        this.class_teacher_id = class_teacher_id;
    }

    @NonNull
    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(@NonNull int class_id) {
        this.class_id = class_id;
    }

    @NonNull
    public int getStart_year() {
        return start_year;
    }

    public void setStart_year(@NonNull int start_year) {
        this.start_year = start_year;
    }

    public ClassEntity() {
    }

    @NonNull
    public int getEnd_year() {
        return end_year;
    }

    public void setEnd_year(@NonNull int end_year) {
        this.end_year = end_year;
    }

    @NonNull
    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(@NonNull String class_name) {
        this.class_name = class_name;
    }


    @NonNull
    public int getClass_strength() {
        return class_strength;
    }

    public void setClass_strength(@NonNull int class_strength) {
        this.class_strength = class_strength;
    }
}

