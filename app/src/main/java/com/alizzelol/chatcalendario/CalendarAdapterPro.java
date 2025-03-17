package com.alizzelol.chatcalendario;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarAdapterPro extends BaseAdapter {

    private Context context;
    private List<Date> days;
    private Calendar currentCalendar;

    public CalendarAdapterPro(Context context, List<Date> days, Calendar currentCalendar) {
        this.context = context;
        this.days = days;
        this.currentCalendar = currentCalendar;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout;
        if (convertView == null) {
            linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_calendar_day, parent, false);
        } else {
            linearLayout = (LinearLayout) convertView;
        }

        TextView dayOfMonthTextView = linearLayout.findViewById(R.id.dayOfMonth);

        Date day = days.get(position);
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(day);
        int dayOfMonth = tempCalendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.getDefault());
        String dayString = sdf.format(day);
        dayOfMonthTextView.setText(dayString);

        if (isSameMonth(day, currentCalendar.getTime())) {
            linearLayout.setBackgroundColor(Color.WHITE);
            dayOfMonthTextView.setTextColor(Color.BLACK);
        } else {
            linearLayout.setBackgroundColor(Color.LTGRAY);
            dayOfMonthTextView.setTextColor(Color.GRAY);
        }

        return linearLayout;
    }

    private boolean isSameMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }
}
