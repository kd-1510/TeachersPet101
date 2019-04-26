package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.sql.Time;
import java.util.Date;

@Entity(tableName = "attendance_entity",
foreignKeys = {@ForeignKey(entity = TeacherEntity.class, parentColumns = "teacher_id", childColumns = "teacher_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = ClassEntity.class, parentColumns = "class_id", childColumns = "class_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = SubjectEntity.class, parentColumns = "course_code", childColumns = "course_code", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = StudentEntity.class, parentColumns = "roll_no", childColumns = "roll_no", onDelete = ForeignKey.CASCADE)})
public class AttendanceEntity {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    public int attendance_id;

    @ColumnInfo
    @NonNull
    public long attendance_date;

    @ColumnInfo
    public String teacher_id;

    @ColumnInfo
    public String course_code;

    @ColumnInfo
    public int class_id;

    @ColumnInfo
    public String roll_no;

    @ColumnInfo
    @NonNull
    public int attendance_status; //0 = absent and 1 = present

    public AttendanceEntity(@NonNull int attendance_id, @NonNull long attendance_date, String teacher_id, String course_code, int class_id, String roll_no, @NonNull int attendance_status) {
        this.attendance_id = attendance_id;
        this.attendance_date = attendance_date;
        this.teacher_id = teacher_id;
        this.course_code = course_code;
        this.class_id = class_id;
        this.roll_no = roll_no;
        this.attendance_status = attendance_status;
    }

    @NonNull
    public int getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(@NonNull int attendance_id) {
        this.attendance_id = attendance_id;
    }

    @NonNull
    public long getAttendance_date() {
        return attendance_date;
    }

    public void setAttendance_date(@NonNull long attendance_date) {
        this.attendance_date = attendance_date;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    @NonNull
    public int getAttendance_status() {
        return attendance_status;
    }

    public void setAttendance_status(@NonNull int attendance_status) {
        this.attendance_status = attendance_status;
    }
}
