package com.lendy.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.lendy.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChooseDateDialog extends AlertDialog.Builder {
    private View view;
    private AlertDialog alertDialog;
    private String dateString;
    private Integer index;
    private DialogResult dialogResult;

    public ChooseDateDialog(@NonNull Context context, DialogResult dialogResult1, int index1) {
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.calendar_day, null);
        dialogResult = dialogResult1;
        index = index1;
        this.setView(view);
    }

    @Override
    public AlertDialog show() {
        alertDialog = super.show();
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        TextView validate = view.findViewById(R.id.validate);

        calendarView.setMinDate(System.currentTimeMillis() - 1000);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                dateString = String.format("%d/%d/%d", i2, i1 + 1, i);
            }
        });

        validate.setOnClickListener(v -> {
            dialogResult.getDialogResult(dateString, index);
            this.alertDialog.dismiss();
        });
        return alertDialog;
    }
}