package diakonidze.kartlos.voiage.fragments;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import diakonidze.kartlos.voiage.R;

/**
 * Created by k.diakonidze on 7/17/2015.
 */
public class AddDriverStatementF extends Fragment {
    private Calendar runTimeC;
    private TextView runDateText;
    private TextView runTimeText;
    private Button driverDonebtn;

    DatePickerDialog.OnDateSetListener datelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            runTimeC.set(Calendar.YEAR, year);
            runTimeC.set(Calendar.MONTH, monthOfYear);
            runTimeC.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            runDateText.setText(runTimeC.get(Calendar.YEAR)+"-"+runTimeC.get(Calendar.MONTH)+"-"+runTimeC.get(Calendar.DAY_OF_MONTH));
        }
    };

    TimePickerDialog.OnTimeSetListener timelistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            runTimeC.set(Calendar.HOUR, hourOfDay);
            runTimeC.set(Calendar.MINUTE, minute);
            runTimeText.setText(runTimeC.get(Calendar.HOUR_OF_DAY)+":"+runTimeC.get(Calendar.MINUTE));
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_driver_statement_layout, container, false);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        driverDonebtn = (Button) view.findViewById(R.id.done_driver);

        driverDonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("cityFrom", "TB-1");
                    jsonObject.put("cityTo", "BA-1");
                    jsonObject.put("cityPath", "A_B");
                    jsonObject.put("date", "2009-11-11");
                    jsonObject.put("time", "0");
                    jsonObject.put("freespace", 9);
                    jsonObject.put("price", 10);
                    jsonObject.put("mark", "1");
                    jsonObject.put("model", "1");
                    jsonObject.put("color", 1);
                    jsonObject.put("kondincioneri", 2);
                    jsonObject.put("sigareti", 2);
                    jsonObject.put("sabarguli", 2);
                    jsonObject.put("adgilzemisvla", 2);
                    jsonObject.put("cxoveli", 2);
                    jsonObject.put("placex", "555");
                    jsonObject.put("placey", "555");
                    jsonObject.put("ageFrom", 1);
                    jsonObject.put("ageTo", 100);
                    jsonObject.put("gender", 1);
                    jsonObject.put("comment", "cccooooommm");
                    jsonObject.put("firstname", "KARTLOS");
                    jsonObject.put("lastname", "DIAKO");
                    jsonObject.put("mobile", "577987006");
                    jsonObject.put("birth_date", "1999");
                    jsonObject.put("status", 1);
                    jsonObject.put("sex", 1);
                    jsonObject.put("photo", "NON");
                    jsonObject.put("statment_type", 1);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "http://back.meet.ge/index.php?type=INSERT&sub_type=1&json=";

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET ,url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Toast.makeText(getActivity(),"chawera", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(),volleyError.toString(), Toast.LENGTH_SHORT).show();
                    }
                }); // end


                queue.add(jsonRequest);

            }
        });


        runTimeC = Calendar.getInstance();
        Spinner cityFrom = (Spinner) view.findViewById(R.id.driver_cityfrom);
        Spinner cityTo = (Spinner) view.findViewById(R.id.driver_cityto);
        runDateText = (TextView) view.findViewById(R.id.driver_date_text);
        runTimeText = (TextView) view.findViewById(R.id.driver_time_text);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cityes, android.R.layout.simple_spinner_item);
        cityFrom.setAdapter(adapter);


// Tarigis archeva
        runDateText.setText(runTimeC.get(Calendar.YEAR)+"-"+runTimeC.get(Calendar.MONTH)+"-"+runTimeC.get(Calendar.DAY_OF_MONTH));
        runDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), datelistener, runTimeC.get(Calendar.YEAR), runTimeC.get(Calendar.MONTH), runTimeC.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

// drois archeva

        runTimeText.setText(runTimeC.get(Calendar.HOUR_OF_DAY)+":"+runTimeC.get(Calendar.MINUTE));
        runTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), timelistener, runTimeC.get(Calendar.HOUR), runTimeC.get(Calendar.MINUTE), true).show();
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
