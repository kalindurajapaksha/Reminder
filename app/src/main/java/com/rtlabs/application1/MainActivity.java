package com.rtlabs.application1;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    int minute;
    int hour;
    int month;
    int dateOfMonth;
    int year;
    Calendar cal = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
    Spinner spinner;
    private ReminderViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = findViewById(R.id.fab);

        final EditText descriptiontxt = findViewById(R.id.description);
        final EditText datetxt = findViewById(R.id.date);
        final EditText timetxt = findViewById(R.id.time);

        timetxt.setOnClickListener(new View.OnClickListener() {
            int _hour;
            int _minute;

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                cal.set(Calendar.HOUR_OF_DAY, hour);
                cal.set(Calendar.MINUTE, minute);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timetxt.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }

        });


        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            EditText datetxt = findViewById(R.id.date);

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                datetxt.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, monthOfYear);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }

        };

        EditText edittext = findViewById(R.id.date);
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        spinner = findViewById(R.id.repeat);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.items, android.R.layout.simple_spinner_item);


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        final ReminderViewModel mWordViewModel;
        mWordViewModel = ViewModelProviders.of(this).get(ReminderViewModel.class);

        /*ImageButton button = findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mWordViewModel.insert(new Reminder(descriptiontxt.getText().toString(), timetxt.getText().toString(), datetxt.getText().toString()));
                setReminder();
                finish();
            }
        });*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (descriptiontxt.getText().toString().equals("") || timetxt.getText().toString().equals("")|| datetxt.getText().toString().equals("")){
                    Snackbar.make(view, "Please Fill all the fields", Snackbar.LENGTH_LONG)
                            .setAction("OK", null).show();
                    return;
                }
                int id = Calendar.getInstance().get(Calendar.MILLISECOND);
                mWordViewModel.insert(new Reminder(descriptiontxt.getText().toString(), timetxt.getText().toString(), datetxt.getText().toString(),id ));
                setReminder(id);
                finish();
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void setReminder(int id) {
        final EditText descriptiontxt = findViewById(R.id.description);
        String description = descriptiontxt.getText().toString();
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent;
        PendingIntent pendingIntent;

        intent = new Intent(MainActivity.this, ReminderNotificationReceiver.class);
        intent.putExtra("title", description);
        pendingIntent = PendingIntent.getBroadcast(this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        Calendar calendar = Calendar.getInstance();


        final EditText datetxt = findViewById(R.id.date);
        final EditText timetxt = findViewById(R.id.time);

        String[] time = timetxt.getText().toString().split(":");
        String[] date = datetxt.getText().toString().split("/");


        //Toast.makeText(getApplicationContext(),""+date[0]+" "+ date[1] + " " + date[2],Toast.LENGTH_LONG).show();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 2);
        cal.set(parseInt(date[2]), parseInt(date[1]), parseInt(date[0]), parseInt(time[0]), parseInt(time[1]), 0);
        //Toast.makeText(getApplicationContext(),"cal : " + cal.getTimeInMillis(),Toast.LENGTH_LONG).show();

        int s = spinner.getSelectedItemPosition();
        if (s==0){
            alarmManager.set(AlarmManager.RTC, cal.getTimeInMillis(), pendingIntent);
        }
        else if (s==1){
            alarmManager.setRepeating(AlarmManager.RTC,cal.getTimeInMillis(),86400000,pendingIntent);
        }
        else if (s==2){
            alarmManager.setRepeating(AlarmManager.RTC,cal.getTimeInMillis(),604800000,pendingIntent);
        }

    }
}
