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
import android.widget.CompoundButton;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.utils.Constantebi;
import diakonidze.kartlos.voiage.models.DriverStatement;

/**
 * Created by k.diakonidze on 7/17/2015.
 */
public class AddDriverStatementF extends Fragment {
    private Calendar runTimeC;
    private TextView runDateText;
    private TextView runTimeText;
    private Button driverDonebtn;
    private DriverStatement driverStatement, getetDriverStatement;

    Spinner freeSpaceSpinner, priceSpinner, markaSpinner, modelSpinner, genderSpinner, colorSpinner;
    CheckBox pirobebi, passengerLimit, condicionerCK, atplaceCK, cigarCK, baggageCK, animalCK;
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
    private ArrayAdapter<String> brendSpinerAdapter;
    private ArrayAdapter<String> modelSpinnerAdapter;

    private List<String> brendlist = new ArrayList<>();
    private List<String> modellist = new ArrayList<>();
    private List<String> timelist = new ArrayList<>();
    private List<String> datelist = new ArrayList<>();
    private List<String> citylist = new ArrayList<>();
    private List<String> freespacelist = new ArrayList<>();
    private List<String> pricelist = new ArrayList<>();
    private Spinner runDateSpinner;
    private Spinner runTimeSpinner;

    String setedDate, setedtime;


    DatePickerDialog.OnDateSetListener datelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            runTimeC.set(Calendar.YEAR, year);
            runTimeC.set(Calendar.MONTH, monthOfYear);
            runTimeC.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setedDate = runTimeC.get(Calendar.YEAR) + "-" + (runTimeC.get(Calendar.MONTH) + 1) + "-" + runTimeC.get(Calendar.DAY_OF_MONTH);

            if (!datelist.contains(setedDate)) {
                datelist.add(setedDate);
                //            dateSpinnerAdapter.notifyDataSetChanged();
                //            runDateSpinner.setAdapter(dateSpinnerAdapter);
                ((ArrayAdapter<String>) runDateSpinner.getAdapter()).notifyDataSetChanged();
                runDateSpinner.setSelection(getIndexInSpinner(runDateSpinner, setedDate));
            }
        }
    };

    TimePickerDialog.OnTimeSetListener timelistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            runTimeC.set(Calendar.HOUR_OF_DAY, hourOfDay);
            runTimeC.set(Calendar.MINUTE, minute);
            setedtime = runTimeC.get(Calendar.HOUR_OF_DAY) + ":" + runTimeC.get(Calendar.MINUTE);

            if (!timelist.contains(setedtime)) {
                timelist.add(setedtime);
//            timeSpinnerAdapter.notifyDataSetChanged();
//            runTimeSpinner.setAdapter(timeSpinnerAdapter);
                ((ArrayAdapter<String>) runTimeSpinner.getAdapter()).notifyDataSetChanged();
                runTimeSpinner.setSelection(getIndexInSpinner(runTimeSpinner, setedtime));
            }
        }
    };

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
                    Boolean inCityes = false;
                    for (int i = 0; i < citylist.size(); i++) {

                        if (cityFrom.getText().toString().equalsIgnoreCase(citylist.get(i))) {
                            inCityes = true;
                        }
                    }
                    if (!inCityes) cityFrom.setTextColor(Color.RED);
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
                    Boolean inCityes = false;

                    for (int i = 0; i < citylist.size(); i++) {

                        if (cityTo.getText().toString().equalsIgnoreCase(citylist.get(i))) {
                            inCityes = true;
                        }
                    }
                    if (!inCityes) cityTo.setTextColor(Color.RED);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_driver_statement_layout, container, false);

        freeSpaceSpinner = (Spinner) view.findViewById(R.id.driver_freespace_spiner);
        priceSpinner = (Spinner) view.findViewById(R.id.driver_price_spiner);
        markaSpinner = (Spinner) view.findViewById(R.id.driver_marka_spiner);
        modelSpinner = (Spinner) view.findViewById(R.id.driver_model_spiner);
        condicionerCK = (CheckBox) view.findViewById(R.id.driver_conditioner_checkBox);
        atplaceCK = (CheckBox) view.findViewById(R.id.driver_atplace_checkBox);
        cigarCK = (CheckBox) view.findViewById(R.id.driver_cigar_checkBox);
        baggageCK = (CheckBox) view.findViewById(R.id.driver_baggage_checkBox);
        animalCK = (CheckBox) view.findViewById(R.id.driver_animal_checkBox);
        commentText = (EditText) view.findViewById(R.id.driver_comment);
        genderSpinner = (Spinner) view.findViewById(R.id.driver_sex_spiner);
        cityFrom = (AutoCompleteTextView) view.findViewById(R.id.driver_cityfrom);
        cityTo = (AutoCompleteTextView) view.findViewById(R.id.driver_cityto);
        runDateSpinner = (Spinner) view.findViewById(R.id.driver_date_spiner);
        runTimeSpinner = (Spinner) view.findViewById(R.id.driver_time_spiner);
        pirobebi = (CheckBox) view.findViewById(R.id.driver_comfort_checkBox);
        comfort1 = (RelativeLayout) view.findViewById(R.id.comfort1);
        passengerLimit = (CheckBox) view.findViewById(R.id.driver_passanger_restrict_checkBox);
        passangerLimitBox = (LinearLayout) view.findViewById(R.id.driver_passanger_restrict_box);
        seekBar = (SeekBar) view.findViewById(R.id.driver_pass_age_seek);
        driverDonebtn = (Button) view.findViewById(R.id.done_driver);
        colorSpinner = (Spinner) view.findViewById(R.id.driver_color_spiner);

        seekBar.setMax(80);

        runTimeC = Calendar.getInstance();
        initialazeAll();

        if (savedInstanceState != null) {
            // tu reCreate moxda grafebshi vabrumebt ra mdgomareobac iyo
            driverStatement = (DriverStatement) savedInstanceState.getSerializable("statement");
            fillForm(driverStatement);
        } else {  // pirvelad chaitvirta es forma
            driverStatement = (DriverStatement) getArguments().getSerializable("statement");
            // chemi gancxadebis gaxsna redaqtirebisatvis
            if (getArguments().getString("action").equals(Constantebi.REASON_EDIT)) {
                fillForm(driverStatement);
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

        // modelebis gafiltvra brendebis mixedvit
        markaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String br = brendlist.get(position).toString();
                int br_id = 0;
                for (int i=0; i<Constantebi.brendList.size(); i++){
                    if(br.equals(Constantebi.brendList.get(i).getMarka())){
                        br_id=Constantebi.brendList.get(i).getId();
                    }
                }
                modellist.clear();
                for (int i = 0; i < Constantebi.modelList.size(); i++) {
                    if (Constantebi.modelList.get(i).getBrendID() == br_id) {
                        if (!Constantebi.modelList.get(i).getModel().equals(""))
                            modellist.add(Constantebi.modelList.get(i).getModel());
                    }
                }
                if (modellist.size() == 0) {
                    modellist.add("-");
                }
                ((ArrayAdapter<String>) modelSpinner.getAdapter()).notifyDataSetChanged();
                modelSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


// damatebiti pirobebis manipulaciebi


        pirobebi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    comfort1.setVisibility(View.VISIBLE);
                } else {
                    comfort1.setVisibility(View.GONE);
                }
            }
        });

// mgzavrze shezgudvebis dayeneba


        passengerLimit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    passangerLimitBox.setVisibility(View.VISIBLE);
                } else {
                    passangerLimitBox.setVisibility(View.GONE);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView textView = (TextView) view.findViewById(R.id.driver_pass_age_text);
                textView.setText("მაქს. ასაკი " + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


// chawera / gagzavna bazashi


        driverDonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                driverStatement = readForm();


                if (runDateSpinner.getSelectedItemPosition() < 3) {
                    Calendar calendar = Calendar.getInstance();
                    setedDate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH) + runDateSpinner.getSelectedItemPosition());
                }


                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("cityFrom", driverStatement.getCityFrom());
                    jsonObject.put("cityTo", driverStatement.getCityTo());
                    jsonObject.put("cityPath", "AA__BB");
                    jsonObject.put("date", setedDate);
                    jsonObject.put("time", setedtime);
                    jsonObject.put("freespace", driverStatement.getFreeSpace());
                    jsonObject.put("price", driverStatement.getPrice());
                    jsonObject.put("mark", driverStatement.getMarka());
                    jsonObject.put("model", driverStatement.getModeli());
                    jsonObject.put("color", driverStatement.getColor());
                    jsonObject.put("kondincioneri", driverStatement.getKondencioneri());
                    jsonObject.put("sigareti", driverStatement.getSigareti());
                    jsonObject.put("sabarguli", driverStatement.getSabarguli());
                    jsonObject.put("adgilzemisvla", driverStatement.getAtHome());
                    jsonObject.put("cxoveli", driverStatement.getCxovelebi());
                    jsonObject.put("placex", "555");
                    jsonObject.put("placey", "555");
                    jsonObject.put("ageFrom", 1);
                    jsonObject.put("ageTo", driverStatement.getAgeTo());
                    jsonObject.put("gender", driverStatement.getGender());
                    jsonObject.put("comment", driverStatement.getComment());
                    jsonObject.put("firstname", "KARTLOS");
                    jsonObject.put("lastname", "DIAKO");
                    jsonObject.put("mobile", "577987006");
                    jsonObject.put("birth_date", "1999");
                    jsonObject.put("status", 1);
                    jsonObject.put("sex", 1);
                    jsonObject.put("photo", "NON");
                    jsonObject.put("user_id", Constantebi.MY_ID);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "http://back.meet.ge/get.php?type=INSERT&sub_type=1&json";

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Toast.makeText(getActivity(), "OK " + jsonObject.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), volleyError.toString() + "   -   " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsonRequest);

            }
        });

        return view;
    }


    private void fillForm(DriverStatement statement) {

        cityFrom.setText(statement.getCityFrom());
        cityTo.setText(statement.getCityTo());

        if (!datelist.contains(statement.getDate())) {
            datelist.add(statement.getDate());
            ((ArrayAdapter<String>) runDateSpinner.getAdapter()).notifyDataSetChanged();
        }
        runDateSpinner.setSelection(getIndexInSpinner(runDateSpinner, statement.getDate()));

        if (!timelist.contains(statement.getTime())) {
            timelist.add(statement.getTime());
            ((ArrayAdapter<String>) runTimeSpinner.getAdapter()).notifyDataSetChanged();
        }
        runTimeSpinner.setSelection(getIndexInSpinner(runTimeSpinner, statement.getTime()));

        freeSpaceSpinner.setSelection(statement.getFreeSpace() - 1);
        priceSpinner.setSelection(statement.getPrice());

        markaSpinner.setSelection(statement.getMarka());
        modelSpinner.setSelection(statement.getModeli());
        colorSpinner.setSelection(statement.getColor());


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

        brendlist.clear();
        modellist.clear();
        for (int i = 0; i < Constantebi.brendList.size(); i++) {
            brendlist.add(Constantebi.brendList.get(i).getMarka());
        }

        for (int i = 0; i < Constantebi.modelList.size(); i++) {
            if (Constantebi.modelList.get(i).getBrendID() == 1) {
                modellist.add(Constantebi.modelList.get(i).getModel());
            }
        }

        modelSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, modellist);
        brendSpinerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, brendlist);
        dateSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datelist);
        timeSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, timelist);
        freeSpaceSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, freespacelist);
        priceSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, pricelist);


        modelSpinner.setAdapter(modelSpinnerAdapter);
        markaSpinner.setAdapter(brendSpinerAdapter);
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

        driverStatement = readForm();

        outState.putSerializable("statement", driverStatement);
    }

    private DriverStatement readForm() {


        DriverStatement statement = new DriverStatement(Constantebi.MY_ID,
                freeSpaceSpinner.getSelectedItemPosition() + 1,
                Integer.valueOf(priceSpinner.getSelectedItem().toString()),
                setedDate,
                cityFrom.getText().toString(),
                cityTo.getText().toString());


        statement.setMarka(markaSpinner.getSelectedItemPosition());
        statement.setModeli(modelSpinner.getSelectedItemPosition());
        statement.setColor(colorSpinner.getSelectedItemPosition());
        if (pirobebi.isChecked()) {
            statement.setKondencioneri(BoolToInt(condicionerCK.isChecked()));
            statement.setAtHome(BoolToInt(atplaceCK.isChecked()));
            statement.setSigareti(BoolToInt(cigarCK.isChecked()));
            statement.setSabarguli(BoolToInt(baggageCK.isChecked()));
            statement.setCxovelebi(BoolToInt(animalCK.isChecked()));
        } else {
            statement.setKondencioneri(-1);
            statement.setAtHome(-1);
            statement.setSigareti(-1);
            statement.setSabarguli(-1);
            statement.setCxovelebi(-1);
        }

        if (passengerLimit.isChecked()) {
            statement.setAgeTo(seekBar.getProgress());
            statement.setGender(genderSpinner.getSelectedItemPosition());
        } else {
            statement.setAgeTo(1000);
            statement.setGender(-1);
        }
        statement.setComment(commentText.getText().toString());


        return statement;
    }
}
