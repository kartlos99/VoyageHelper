package diakonidze.kartlos.voiage.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.datebase.DBmanager;
import diakonidze.kartlos.voiage.models.DriverStatement;
import diakonidze.kartlos.voiage.models.PassangerStatement;
import diakonidze.kartlos.voiage.utils.Constantebi;

/**
 * Created by k.diakonidze on 7/17/2015.
 */
public class AddPassengetStatementF extends Fragment {

    private Calendar runTimeC;
    private Button passangerDonebtn, pirobebiBtn;

    private PassangerStatement passangerStatement;


    Spinner freeSpaceSpinner, priceSpinner;
    CheckBox condicionerCK, atplaceCK, cigarCK, baggageCK, animalCK;
    EditText commentText;
    RelativeLayout comfort1;
    LinearLayout passangerLimitBox;
    AutoCompleteTextView cityFrom, cityTo;
    SeekBar seekBar;

    private ArrayAdapter<String> cityFromAdapret;
    private ArrayAdapter<String> cityToAdapret;

    private ArrayAdapter<String> dateSpinnerAdapter;
    private ArrayAdapter<String> timeSpinnerAdapter;
    private ArrayAdapter<String> freeSpaceSpinnerAdapter;
    private ArrayAdapter<String> priceSpinnerAdapter;

    private List<String> brendlist = new ArrayList<>();
    private List<String> modellist = new ArrayList<>();
    private List<String> timelist = new ArrayList<>();
    private List<String> datelist = new ArrayList<>();
    private List<String> citylist = new ArrayList<>();
    private List<String> freespacelist = new ArrayList<>();
    private List<String> pricelist = new ArrayList<>();
    private Spinner runDateSpinner;
    private Spinner runTimeSpinner;

    String setedDate, setedtime, workState;
    Boolean pirobebi, passengerLimit;



    DatePickerDialog.OnDateSetListener datelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            runTimeC.set(Calendar.YEAR, year);
            runTimeC.set(Calendar.MONTH, monthOfYear);
            runTimeC.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            setedDate = dateFormat.format(runTimeC.getTime());

            if (!datelist.contains(setedDate)) {
                datelist.add(setedDate);
                ((ArrayAdapter<String>) runDateSpinner.getAdapter()).notifyDataSetChanged();
            }
            runDateSpinner.setSelection(getIndexInSpinner(runDateSpinner, setedDate));
        }
    };

    TimePickerDialog.OnTimeSetListener timelistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            runTimeC.set(Calendar.HOUR_OF_DAY, hourOfDay);
            runTimeC.set(Calendar.MINUTE, minute);
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            setedtime = dateFormat.format(runTimeC.getTime());

            if (!timelist.contains(setedtime)) {
                timelist.add(setedtime);
                ((ArrayAdapter<String>) runTimeSpinner.getAdapter()).notifyDataSetChanged();
            }
            runTimeSpinner.setSelection(getIndexInSpinner(runTimeSpinner, setedtime));
        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialazeAll();
        workState = getArguments().getString("action");

        if (savedInstanceState != null) {
            // tu reCreate moxda grafebshi vabrumebt ra mdgomareobac iyo
            passangerStatement = (PassangerStatement) savedInstanceState.getSerializable("statement");
            fillForm(passangerStatement);
        } else {  // pirvelad chaitvirta es forma
            passangerStatement = (PassangerStatement) getArguments().getSerializable("statement");
            // chemi gancxadebis gaxsna redaqtirebisatvis
            if (workState.equals(Constantebi.REASON_EDIT)) {
                fillForm(passangerStatement);
            }
            // axali gancxadebis chawera
            if (getArguments().getString("action").equals(Constantebi.REASON_ADD)) {

            }
        }


//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        // დროის დაყენება
        runDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // tu aavirchiet "sxva" romelic me4 poziciaa gamova dayepikeri
                if (i == 3) {
                    DatePickerDialog dialog = new DatePickerDialog(getActivity(), datelistener, runTimeC.get(Calendar.YEAR), runTimeC.get(Calendar.MONTH), runTimeC.get(Calendar.DAY_OF_MONTH));
                    dialog.setCancelable(false);
                    dialog.show();

                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            runDateSpinner.setSelection(0);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        runTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 3) {
                    TimePickerDialog dialog = new TimePickerDialog(getActivity(), timelistener, runTimeC.get(Calendar.HOUR_OF_DAY), runTimeC.get(Calendar.MINUTE), false);
                    dialog.setCancelable(false);
                    dialog.show();

                    dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            runTimeSpinner.setSelection(0);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    
        // damatebiti pirobebis manipulaciebi

        pirobebiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pirobebi = !pirobebi;
                if (pirobebi) {
                    comfort1.setVisibility(View.VISIBLE);
                    pirobebiBtn.setBackgroundResource(R.drawable.greenbtn_lite);
                } else {
                    comfort1.setVisibility(View.GONE);
                    pirobebiBtn.setBackgroundResource(R.drawable.greenbtn_dark);
                }
            }
        });


        // chawera / gagzavna bazashi
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_passanger_statement_layout, container, false);

        freeSpaceSpinner = (Spinner) view.findViewById(R.id.passanger_freespace_spiner);
        priceSpinner = (Spinner) view.findViewById(R.id.passanger_price_spiner);
        condicionerCK = (CheckBox) view.findViewById(R.id.passanger_conditioner_checkBox);
        atplaceCK = (CheckBox) view.findViewById(R.id.passanger_atplace_checkBox);
        cigarCK = (CheckBox) view.findViewById(R.id.passanger_cigar_checkBox);
        baggageCK = (CheckBox) view.findViewById(R.id.passanger_baggage_checkBox);
        animalCK = (CheckBox) view.findViewById(R.id.passanger_animal_checkBox);
        commentText = (EditText) view.findViewById(R.id.passanger_comment);
        cityFrom = (AutoCompleteTextView) view.findViewById(R.id.passanger_cityfrom);
        cityTo = (AutoCompleteTextView) view.findViewById(R.id.passanger_cityto);
        runDateSpinner = (Spinner) view.findViewById(R.id.passanger_date_spiner);
        runTimeSpinner = (Spinner) view.findViewById(R.id.passanger_time_spiner);
        comfort1 = (RelativeLayout) view.findViewById(R.id.comfort1_passanger);
        passangerDonebtn = (Button) view.findViewById(R.id.done_passanger);
        pirobebiBtn = (Button) view.findViewById(R.id.passanger_pirobebi_btn);

        passangerDonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!citylist.contains(cityFrom.getText().toString()) || !citylist.contains(cityTo.getText().toString())) {
                    Toast.makeText(getActivity(), "ქალაქი არასწორადაა მითითებული!", Toast.LENGTH_LONG).show();
                } else {

                    passangerStatement = readForm();
                    passangerStatement.setUserID(Constantebi.MY_ID);

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("CityFrom", passangerStatement.getCityFrom());
                        jsonObject.put("CityTo", passangerStatement.getCityTo());
                        jsonObject.put("date", passangerStatement.getDate());
                        jsonObject.put("time", passangerStatement.getTime());
                        jsonObject.put("freespace", passangerStatement.getFreeSpace());
                        jsonObject.put("price", passangerStatement.getPrice());
                        jsonObject.put("kondincioneri", passangerStatement.getKondencioneri());
                        jsonObject.put("sigareti", passangerStatement.getSigareti());
                        jsonObject.put("sabarguli", passangerStatement.getSabarguli());
                        jsonObject.put("adgilzemisvla", passangerStatement.getAtHome());
                        jsonObject.put("cxovelebi", passangerStatement.getCxovelebi());
                        jsonObject.put("placex", "555");
                        jsonObject.put("placey", "555");
                        jsonObject.put("comment", passangerStatement.getComment());
                        jsonObject.put("status", 1);
                        jsonObject.put("sex", 1);
                        jsonObject.put("photo", "NON");
                        jsonObject.put("user_id", passangerStatement.getUserID());
//
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    if (workState.equals(Constantebi.REASON_ADD)) {
                        String url = "http://back.meet.ge/get.php?type=INSERT&sub_type=2&json";
//                String url = "http://back.meet.ge/get.php?type=my&sub_type=2";

                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Toast.makeText(getActivity(), "OK " + jsonObject.toString(), Toast.LENGTH_SHORT).show();

                                try {
                                    int id = jsonObject.getInt("insert_id");
                                    passangerStatement.setId(id);

                                    DBmanager.initialaize(getActivity());
                                    DBmanager.openWritable();
                                    DBmanager.insertIntoPassanger(passangerStatement, Constantebi.MY_STATEMENT);
                                    DBmanager.close();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getActivity(), volleyError.toString() + "   -   " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        queue.add(jsonRequest);
                    } else {
                        String url = "http://back.meet.ge/get.php?type=INSERT&sub_type=2&json";
//                String url = "http://back.meet.ge/get.php?type=my&sub_type=2";
                        try {
                            jsonObject.put("s_id", passangerStatement.getId());
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "mgzavris gancxadebis ganaxleba - id aramaqvs rom gavagzavno serverze", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                                Toast.makeText(getActivity(), "OK " + jsonObject.toString(), Toast.LENGTH_SHORT).show();

                                try {
                                    int id = jsonObject.getInt("insert_id");
                                    passangerStatement.setId(id);

                                    DBmanager.initialaize(getActivity());
                                    DBmanager.openWritable();
                                    DBmanager.insertIntoPassanger(passangerStatement, Constantebi.MY_STATEMENT);
                                    DBmanager.close();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(getActivity(), volleyError.toString() + "   -   " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        queue.add(jsonRequest);
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cityFromAdapret = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, citylist);
        cityToAdapret = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, citylist);

        cityFrom.setAdapter(cityFromAdapret);
        cityFrom.setThreshold(0);
        cityTo.setAdapter(cityToAdapret);
        cityTo.setThreshold(0);

        cityFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    cityFrom.showDropDown();
                    cityFrom.setTextColor(Color.BLACK);
                } else {
                    if (!citylist.contains(cityFrom.getText().toString()))
                        cityFrom.setTextColor(Color.RED);
                }
            }
        });

        cityTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    cityTo.showDropDown();
                    cityTo.setTextColor(Color.BLACK);
                } else {
                    if (!citylist.contains(cityTo.getText().toString()))
                        cityTo.setTextColor(Color.RED);
                }
            }
        });
    }

    private void fillForm(PassangerStatement statement) {

        cityFrom.setText(statement.getCityFrom());
        cityTo.setText(statement.getCityTo());

        if (!datelist.contains(statement.getDate())) {
            datelist.add(statement.getDate());
            ((ArrayAdapter<String>) runDateSpinner.getAdapter()).notifyDataSetChanged();
            setedDate = statement.getDate();
        }
        runDateSpinner.setSelection(getIndexInSpinner(runDateSpinner, statement.getDate()));

        if (!timelist.contains(statement.getTime())) {
            timelist.add(statement.getTime());
            ((ArrayAdapter<String>) runTimeSpinner.getAdapter()).notifyDataSetChanged();
        }
        runTimeSpinner.setSelection(getIndexInSpinner(runTimeSpinner, statement.getTime()));

        freeSpaceSpinner.setSelection(statement.getFreeSpace() - 1);
        priceSpinner.setSelection(statement.getPrice());

        if (statement.getAtHome() != 2) {
            pirobebi= true;
            pirobebiBtn.setBackgroundResource(R.drawable.greenbtn_lite);
            comfort1.setVisibility(View.VISIBLE);
            if (statement.getKondencioneri() == 1) condicionerCK.setChecked(true);
            else condicionerCK.setChecked(false);
            if (statement.getSigareti() == 1) cigarCK.setChecked(true);
            else cigarCK.setChecked(false);
            if (statement.getAtHome() == 1) atplaceCK.setChecked(true);
            else atplaceCK.setChecked(false);
            if (statement.getCxovelebi() == 1) animalCK.setChecked(true);
            else animalCK.setChecked(false);
            if (statement.getSabarguli() == 1) baggageCK.setChecked(true);
            else baggageCK.setChecked(false);
        } else {
            pirobebi = false;
            pirobebiBtn.setBackgroundResource(R.drawable.greenbtn_dark);
            comfort1.setVisibility(View.GONE);
        }

        commentText.setText(statement.getComment());
    }


    private int BoolToInt(boolean checked) {
        if (checked) {
            return 1;
        } else {
            return 0;
        }
    }

    // formistvis sachiro monacemebis chatvirtva
    private void initialazeAll() {

        pirobebi = false;

        runTimeC = Calendar.getInstance();

        citylist.add("თბილისი");
        citylist.add("ქუთაისი");
        citylist.add("ბათუმი");
        citylist.add("ფოთი");
        citylist.add("სამტრედია");
        citylist.add("ბორჯომი");
        citylist.add("სტეფანწმინდა");

        timelist.add("დილით");
        timelist.add("შუადღეს");
        timelist.add("საღამოს");
        timelist.add("სხვა");

        datelist.add("დღეს");
        datelist.add("ხვალ");
        datelist.add("ზეგ");
        datelist.add("სხვა");

        setedDate = "";
        setedtime = "";

        for (int i = 1; i < 10; i++) {
            freespacelist.add(String.valueOf(i));
        }
        for (int i = 0; i < 51; i++) {
            pricelist.add(String.valueOf(i));
        }


        dateSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datelist);
        timeSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, timelist);
        freeSpaceSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, freespacelist);
        priceSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, pricelist);


        freeSpaceSpinner.setAdapter(freeSpaceSpinnerAdapter);
        priceSpinner.setAdapter(priceSpinnerAdapter);
        runDateSpinner.setAdapter(dateSpinnerAdapter);
        runTimeSpinner.setAdapter(timeSpinnerAdapter);

    }

    private int getIndexInSpinner(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        passangerStatement = readForm();

        outState.putSerializable("statement", passangerStatement);
    }

    private PassangerStatement readForm() {

        if (runDateSpinner.getSelectedItemPosition() < 3) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, runDateSpinner.getSelectedItemPosition());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            setedDate = dateFormat.format(runTimeC.getTime());
        }

        PassangerStatement statement = new PassangerStatement(Constantebi.MY_ID,
                freeSpaceSpinner.getSelectedItemPosition() + 1,
                Integer.valueOf(priceSpinner.getSelectedItem().toString()),
                cityFrom.getText().toString(),
                cityTo.getText().toString(),
                setedDate);

        statement.setTime(runTimeSpinner.getSelectedItem().toString());

        if (pirobebi) {
            statement.setKondencioneri(BoolToInt(condicionerCK.isChecked()));
            statement.setAtHome(BoolToInt(atplaceCK.isChecked()));
            statement.setSigareti(BoolToInt(cigarCK.isChecked()));
            statement.setSabarguli(BoolToInt(baggageCK.isChecked()));
            statement.setCxovelebi(BoolToInt(animalCK.isChecked()));
        } else {
            statement.setKondencioneri(2);
            statement.setAtHome(2);
            statement.setSigareti(2);
            statement.setSabarguli(2);
            statement.setCxovelebi(2);
        }

        statement.setComment(commentText.getText().toString());

        return statement;
    }



}
