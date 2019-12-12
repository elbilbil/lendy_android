package com.lendy.Utils.custom_views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.lendy.R;
import com.lendy.Utils.DialogResult;

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
                calendarView.getDate();
                dateString = String.format("%d/%d/%d", i2, i1 + 1, i);
            }
        });

        validate.setOnClickListener(v -> {
            if (dateString != null && index != null) {
                dialogResult.getDialogResult(dateString, index, calendarView.getDate() / 1000);
                this.alertDialog.dismiss();
            }
        });
        return alertDialog;
    }
}