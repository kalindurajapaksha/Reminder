package com.rtlabs.application1;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Reminder.class}, version = 1, exportSchema = false)
public abstract class ReminderRoomDatabase extends RoomDatabase {
    public abstract ReminderDao reminderDao();

    private static volatile ReminderRoomDatabase INSTANCE;

    static ReminderRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ReminderRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ReminderRoomDatabase.class, "reminder_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ReminderDao mDao;

        PopulateDbAsync(ReminderRoomDatabase db) {
            mDao = db.reminderDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            return null;
        }
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
}