package com.rtlabs.application1;

import android.app.Activity;
import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import static android.arch.lifecycle.ViewModelProviders.of;


public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.WordViewHolder> {

    private View.OnClickListener onItemClickListener;
    private ReminderViewModel mWordViewModel;
    private Application application;

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView des;
        private final TextView date;
        private final TextView time;
        View view;

        private WordViewHolder(View itemView) {
            super(itemView);
            des = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            view = itemView;
        }
    }

    private final LayoutInflater mInflater;
    private List<Reminder> mWords; // Cached copy of words

    RemindersAdapter(Context context, Application application) {
        mInflater = LayoutInflater.from(context);
        mWordViewModel = new ReminderViewModel(application);
        this.application = application;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.list_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WordViewHolder holder, int position) {
        if (mWords != null) {
            final Reminder current = mWords.get(position);
            holder.des.setText(current.getDescription());
            holder.date.setText("Date : " + current.getDate());
            holder.time.setText("Time : " +current.getTime());
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(application,Main3Activity.class);
                    intent.putExtra("object",current);
                    application.startActivity(intent);
                    //mWordViewModel.deleteWord(current);
                }
            });

        } else {
            // Covers the case of data not being ready yet.
            holder.des.setText("No Word");
            holder.date.setText("No Word");
            holder.time.setText("No Word");
        }
    }

    void setWords(List<Reminder> words) {
        mWords = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }
}
