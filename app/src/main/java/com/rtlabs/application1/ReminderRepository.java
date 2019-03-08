package com.rtlabs.application1;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ReminderRepository {

    private ReminderDao mWordDao;
    private LiveData<List<Reminder>> mAllWords;

    ReminderRepository(Application application) {
        ReminderRoomDatabase db = ReminderRoomDatabase.getDatabase(application);
        mWordDao = db.reminderDao();
        mAllWords = mWordDao.getAllWords();
    }

    LiveData<List<Reminder>> getAllWords() {
        return mAllWords;
    }


    public void insert (Reminder word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Reminder, Void, Void> {

        private ReminderDao mAsyncTaskDao;

        insertAsyncTask(ReminderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Reminder... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteWordAsyncTask extends AsyncTask<Reminder, Void, Void> {
        private ReminderDao mAsyncTaskDao;

        deleteWordAsyncTask(ReminderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Reminder... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
    public void deleteWord(Reminder word)  {
        new deleteWordAsyncTask(mWordDao).execute(word);
    }

    private static class updateWordAsyncTask extends AsyncTask<Reminder, Void, Void> {
        private ReminderDao mAsyncTaskDao;

        updateWordAsyncTask(ReminderDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Reminder... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }

    public void updateWord(Reminder word)  {
        new updateWordAsyncTask(mWordDao).execute(word);
    }

}
