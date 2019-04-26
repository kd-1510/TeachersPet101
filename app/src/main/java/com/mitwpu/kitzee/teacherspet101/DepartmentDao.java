package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

@Dao
public interface DepartmentDao {
    @Insert
    void insertDepartment(DepartmentEntity... departmentEntities);
}
