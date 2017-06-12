package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Luis on 03/05/2017.
 */


/**
 * When using an AlertDialog, it is a good idea to wrap it in an instance of DialogFragment, a subclass
 of Fragment. It is possible to display an AlertDialog without a DialogFragment, but it is not
 recommended. Having the dialog managed by the FragmentManager gives you more options for
 presenting the dialog */

public class DatePickerFragment extends Fragment {//DialogFragment {

    private static final String ARG_DATE = "date";
    public static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";
    private DatePicker mDatePicker;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, Date date)
    {
        //if(getTargetFragment() == null){
           Intent intent = new Intent();
           intent.putExtra(EXTRA_DATE, date);
           getActivity().setResult(Activity.RESULT_OK, intent);
           getActivity().finish();

        //}
/*
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);


        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);*/
    }

/**
    Also, notice that the selected date in the DatePicker is automatically preserved across rotation
    How does this happen? Remember that Views can save state across configuration changes,
    but only if they have an ID attribute. When you created the DatePicker in dialog_date.xml you also
    asked the build tools to generate a unique ID value for that DatePicker
 */
 /*
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
          return new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.date_picker_title)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year, month, day).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                        })
                        .setView(view)
                        .create();
    }
 */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year= calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour= calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        //DatePicker datePicker = new DatePicker(getActivity());
        //View view = inflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        View view = inflater.inflate(R.layout.dialog_date, container, false);
        //return super.onCreateDialog(savedInstanceState);

        mDatePicker = (DatePicker) view.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        Button btnOK = (Button) view.findViewById(R.id.id_btn_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();
                Date date = new GregorianCalendar(year, month, day).getTime();
                sendResult(Activity.RESULT_OK, date);
                //dismiss();
            }
        });


        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}


