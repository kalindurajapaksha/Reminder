package com.rtlabs.application1;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert
    void insert(Reminder reminder);

    @Query("DELETE FROM reminder")
    void deleteAll();

    @Query("SELECT * from reminder")
    LiveData<List<Reminder>> getAllWords();

    @Delete
    void delete(Reminder reminder);

    @Update
    void update(Reminder reminder);
}
