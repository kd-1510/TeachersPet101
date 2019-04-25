package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "student_entity",
foreignKeys = @ForeignKey(entity = ClassEntity.class, parentColumns = "class_id", childColumns = "class_id", onDelete = ForeignKey.CASCADE))
public class StudentEntity {
    @PrimaryKey
    @NonNull
    public String roll_no;

    @ColumnInfo (name = "name")
    public String name;

    @ColumnInfo
    public int class_id;


    public StudentEntity(String roll_no, String name, int class_id) {
        this.roll_no = roll_no;
        this.name = name;
        this.class_id = class_id;
    }

    @NonNull
    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(@NonNull String roll_no) {
        this.roll_no = roll_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }
}
