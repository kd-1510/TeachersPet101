package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "department_entity")
public class DepartmentEntity {
    @PrimaryKey
    @NonNull
    public int dept_id;

    @ColumnInfo
    @NonNull
    public String dept_name;

    public DepartmentEntity(@NonNull int dept_id, @NonNull String dept_name) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
    }

    @NonNull
    public int getDept_id() {
        return dept_id;
    }

    public void setDept_id(@NonNull int dept_id) {
        this.dept_id = dept_id;
    }

    @NonNull
    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(@NonNull String dept_name) {
        this.dept_name = dept_name;
    }
}
