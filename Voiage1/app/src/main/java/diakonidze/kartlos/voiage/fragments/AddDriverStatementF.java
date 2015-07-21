package diakonidze.kartlos.voiage.fragments;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;

import diakonidze.kartlos.voiage.MainActivity;
import diakonidze.kartlos.voiage.AddStatement;
import diakonidze.kartlos.voiage.R;

/**
 * Created by k.diakonidze on 7/17/2015.
 */
public class AddDriverStatementF extends Fragment {
    Calendar runTimeC;
    TextView runDateT;

    DatePickerDialog.OnDateSetListener datelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            runTimeC.set(Calendar.YEAR, year);
            runTimeC.set(Calendar.MONTH, monthOfYear);
            runTimeC.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            runDateT.setText(runTimeC.getTime().toString());
        }
    };

    TimePickerDialog.OnTimeSetListener timelistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            runTimeC.set(Calendar.HOUR, hourOfDay);
            runTimeC.set(Calendar.MINUTE, minute);
            runDateT.setText(runTimeC.getTime().toString());
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_driver_statement_layout, container, false);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        runTimeC = Calendar.getInstance();
        Spinner cityFrom = (Spinner) view.findViewById(R.id.driver_cityfrom);
        Spinner cityTo = (Spinner) view.findViewById(R.id.driver_cityto);
        runDateT = (TextView) view.findViewById(R.id.driver_date_text);
        Spinner runTimeS = (Spinner) view.findViewById(R.id.driver_time_spiner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cityes, android.R.layout.simple_spinner_item);
        cityFrom.setAdapter(adapter);

        runTimeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView view1 = (TextView) view;
                if (position == 4)
                    new TimePickerDialog(getActivity(), timelistener, runTimeC.get(Calendar.HOUR), runTimeC.get(Calendar.MINUTE), false).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        runDateT.setText(runTimeC.getTime().toString());
        runDateT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), datelistener, runTimeC.get(Calendar.YEAR), runTimeC.get(Calendar.MONTH), runTimeC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        CheckBox pirobebi = (CheckBox) view.findViewById(R.id.driver_comfort_checkBox);
        final LinearLayout comfort1 = (LinearLayout) view.findViewById(R.id.comfort1);
        final LinearLayout comfort2 = (LinearLayout) view.findViewById(R.id.comfort2);

        pirobebi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    comfort1.setVisibility(View.VISIBLE);
                    comfort2.setVisibility(View.VISIBLE);
                } else {
                    comfort1.setVisibility(View.GONE);
                    comfort2.setVisibility(View.GONE);
                }
            }
        });


        return view;
    }
}
