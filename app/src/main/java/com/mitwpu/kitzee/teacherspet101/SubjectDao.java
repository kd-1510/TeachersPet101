package com.mitwpu.kitzee.teacherspet101;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface SubjectDao {
    @Insert
    void insertSubject(SubjectEntity... subjectEntities);
}
