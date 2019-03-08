package com.rtlabs.application1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ReminderViewModel extends AndroidViewModel{

        private ReminderRepository mRepository;

        private LiveData<List<Reminder>> mAllWords;

        public ReminderViewModel (Application application) {
            super(application);
            mRepository = new ReminderRepository(application);
            mAllWords = mRepository.getAllWords();
        }

        LiveData<List<Reminder>> getAllWords() { return mAllWords; }

        public void insert(Reminder word) { mRepository.insert(word); }

        public void deleteWord(Reminder word) {mRepository.deleteWord(word);}

        public void updateWord(Reminder word) {mRepository.updateWord(word);}

    }

