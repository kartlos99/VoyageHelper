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

import diakonidze.kartlos.voiage.MainActivity;
import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.models.DriverStatement;

/**
 * Created by k.diakonidze on 7/17/2015.
 */
public class AddDriverStatementF extends Fragment {
    private Calendar runTimeC;
    private TextView runDateText;
    private TextView runTimeText;
    private Button driverDonebtn;
    private DriverStatement addDriverStatement, getetDriverStatement;

    AutoCompleteTextView cityFrom;
    AutoCompleteTextView cityTo;
    private ArrayAdapter<String> cityFromAdapret;
    private ArrayAdapter<String> cityToAdapret;

    private ArrayAdapter<String> dateSpinnerAdapter;
    private ArrayAdapter<String> timeSpinnerAdapter;
    private List<String> timelist = new ArrayList<>();
    private List<String> datelist = new ArrayList<>();
    private List<String> citylist = new ArrayList<>();
    private Spinner runDateSpinner;
    private Spinner runTimeSpinner;

    String setedDate, setedtime;


    DatePickerDialog.OnDateSetListener datelistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            runTimeC.set(Calendar.YEAR, year);
            runTimeC.set(Calendar.MONTH, monthOfYear);
            runTimeC.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setedDate = runTimeC.get(Calendar.YEAR)+"-"+(runTimeC.get(Calendar.MONTH)+1)+"-"+runTimeC.get(Calendar.DAY_OF_MONTH);

            datelist.add(setedDate);
            dateSpinnerAdapter.notifyDataSetChanged();
            runDateSpinner.setAdapter(dateSpinnerAdapter);

            runDateSpinner.setSelection(getIndexInSpinner(runDateSpinner,setedDate));
//            runDateText.setText();
        }
    };

    TimePickerDialog.OnTimeSetListener timelistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            runTimeC.set(Calendar.HOUR_OF_DAY, hourOfDay);
            runTimeC.set(Calendar.MINUTE, minute);
            setedtime = runTimeC.get(Calendar.HOUR_OF_DAY)+":"+runTimeC.get(Calendar.MINUTE);

            timelist.add(setedtime);
            timeSpinnerAdapter.notifyDataSetChanged();
            runTimeSpinner.setAdapter(timeSpinnerAdapter);

            runTimeSpinner.setSelection(getIndexInSpinner(runTimeSpinner, setedtime));

//            runTimeText.setText());
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_driver_statement_layout, container, false);

        initialazeAll();


//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");


        runTimeC = Calendar.getInstance();

        cityFrom = (AutoCompleteTextView) view.findViewById(R.id.driver_cityfrom);
        cityTo = (AutoCompleteTextView) view.findViewById(R.id.driver_cityto);

        cityFromAdapret = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, citylist);
        cityToAdapret = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, citylist);

        cityFrom.setAdapter(cityFromAdapret);
        cityFrom.setThreshold(0);
        cityTo.setAdapter(cityToAdapret);
        cityTo.setThreshold(0);

        cityFrom.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    cityFrom.showDropDown();
                    cityFrom.setTextColor(Color.BLACK);
                }else{
                    Boolean inCityes = false;
                    for (int i =0; i< citylist.size(); i++){

                        if( cityFrom.getText().toString().equalsIgnoreCase(citylist.get(i)) )   {
                            inCityes = true;
                        }

                    }
                    if(!inCityes) cityFrom.setTextColor(Color.RED);
                }
            }
        });

        cityTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b) {
                    cityTo.showDropDown();
                    cityTo.setTextColor(Color.BLACK);
                }else{
                    Boolean inCityes = false;
                    for (int i =0; i< citylist.size(); i++){

                        if( cityTo.getText().toString().equalsIgnoreCase(citylist.get(i)) )   {
                            inCityes = true;
                        }

                    }
                    if(!inCityes) cityTo.setTextColor(Color.RED);
                }
            }
        });

        // დროის დაყენება
        runDateSpinner = (Spinner) view.findViewById(R.id.driver_date_spiner);
        runTimeSpinner = (Spinner) view.findViewById(R.id.driver_time_spiner);

        dateSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datelist);
        timeSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, timelist);

        runDateSpinner.setAdapter(dateSpinnerAdapter);
        runTimeSpinner.setAdapter(timeSpinnerAdapter);

        runDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // tu aavirchiet "sxva" romelic me4 poziciaa gamova dayepikeri
                if(i == 3){
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
                if(i == 3) {
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

        final CheckBox pirobebi = (CheckBox) view.findViewById(R.id.driver_comfort_checkBox);
        final RelativeLayout comfort1 = (RelativeLayout) view.findViewById(R.id.comfort1);

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
        final CheckBox passengerLimit = (CheckBox) view.findViewById(R.id.driver_passanger_restrict_checkBox);
        final LinearLayout passangerLimitBox = (LinearLayout) view.findViewById(R.id.driver_passanger_restrict_box);

        passengerLimit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    passangerLimitBox.setVisibility(View.VISIBLE);
                }else{
                    passangerLimitBox.setVisibility(View.GONE);
                }
            }
        });

        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.driver_pass_age_seek);
        seekBar.setMax(80);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView textView = (TextView) view.findViewById(R.id.driver_pass_age_text);
                textView.setText("მაქს. ასაკი "+i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


// chawera / gagzavna bazashi
        driverDonebtn = (Button) view.findViewById(R.id.done_driver);

        driverDonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();

                Spinner freeSpaceSpinner = (Spinner) view.findViewById(R.id.driver_freespace_spiner);
                Spinner priceSpinner = (Spinner) view.findViewById(R.id.driver_price_spiner);
                Spinner markaSpinner = (Spinner) view.findViewById(R.id.driver_marka_spiner);
                Spinner modelSpinner = (Spinner) view.findViewById(R.id.driver_model_spiner);
                CheckBox condicionerCK = (CheckBox) view.findViewById(R.id.driver_conditioner_checkBox);
                CheckBox atplaceCK = (CheckBox) view.findViewById(R.id.driver_atplace_checkBox);
                CheckBox cigarCK = (CheckBox) view.findViewById(R.id.driver_cigar_checkBox);
                CheckBox baggageCK = (CheckBox) view.findViewById(R.id.driver_baggage_checkBox);
                CheckBox animalCK = (CheckBox) view.findViewById(R.id.driver_animal_checkBox);
                EditText commentText = (EditText) view.findViewById(R.id.driver_comment);

                Spinner genderSpinner = (Spinner) view.findViewById(R.id.driver_sex_spiner);

                if(runDateSpinner.getSelectedItemPosition() < 3){
                    setedDate = calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+(calendar.get(Calendar.DAY_OF_MONTH)+ runDateSpinner.getSelectedItemPosition() );

                }


                addDriverStatement = new DriverStatement(1,freeSpaceSpinner.getSelectedItemPosition()+1, Integer.valueOf(priceSpinner.getSelectedItem().toString()), runTimeC,cityFrom.getText().toString(), cityTo.getText().toString());
                addDriverStatement.setMarka(markaSpinner.getSelectedItemPosition());
                addDriverStatement.setModeli(modelSpinner.getSelectedItemPosition());
                if(pirobebi.isChecked()) {
                    addDriverStatement.setKondencioneri(BoolToInt(condicionerCK.isChecked()));
                    addDriverStatement.setAtHome(BoolToInt(atplaceCK.isChecked()));
                    addDriverStatement.setSigareti(BoolToInt(cigarCK.isChecked()));
                    addDriverStatement.setSabarguli(BoolToInt(baggageCK.isChecked()));
                    addDriverStatement.setCxovelebi(BoolToInt(animalCK.isChecked()));
                }else{
                    addDriverStatement.setKondencioneri(-1);
                    addDriverStatement.setAtHome(-1);
                    addDriverStatement.setSigareti(-1);
                    addDriverStatement.setSabarguli(-1);
                    addDriverStatement.setCxovelebi(-1);
                }

                if(passengerLimit.isChecked()){
                    addDriverStatement.setAgeTo(seekBar.getProgress());
                    addDriverStatement.setGender(genderSpinner.getSelectedItemPosition());
                }else{
                    addDriverStatement.setAgeTo(1000);
                    addDriverStatement.setGender(-1);
                }
                addDriverStatement.setComment(commentText.getText().toString());




                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("cityFrom", addDriverStatement.getCityFrom());
                    jsonObject.put("cityTo", addDriverStatement.getCityTo());
                    jsonObject.put("cityPath", "AA__BB");
                    jsonObject.put("date", setedDate);
                    jsonObject.put("time", setedtime);
                    jsonObject.put("freespace", addDriverStatement.getFreeSpace());
                    jsonObject.put("price", addDriverStatement.getPrice());
                    jsonObject.put("mark", addDriverStatement.getMarka());
                    jsonObject.put("model", addDriverStatement.getModeli());
                    jsonObject.put("color", 1);
                    jsonObject.put("kondincioneri", addDriverStatement.getKondencioneri());
                    jsonObject.put("sigareti", addDriverStatement.getSigareti());
                    jsonObject.put("sabarguli", addDriverStatement.getSabarguli());
                    jsonObject.put("adgilzemisvla", addDriverStatement.getAtHome());
                    jsonObject.put("cxoveli", addDriverStatement.getCxovelebi());
                    jsonObject.put("placex", "555");
                    jsonObject.put("placey", "555");
                    jsonObject.put("ageFrom", 1);
                    jsonObject.put("ageTo", addDriverStatement.getAgeTo());
                    jsonObject.put("gender", addDriverStatement.getGender());
                    jsonObject.put("comment", addDriverStatement.getComment());
                    jsonObject.put("firstname", "KARTLOS");
                    jsonObject.put("lastname", "DIAKO");
                    jsonObject.put("mobile", "577987006");
                    jsonObject.put("birth_date", "1999");
                    jsonObject.put("status", 1);
                    jsonObject.put("sex", 1);
                    jsonObject.put("photo", "NON");
                    jsonObject.put("user_id", MainActivity.MY_ID);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "http://back.meet.ge/get.php?type=INSERT&sub_type=1&json";

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST ,url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Toast.makeText(getActivity(),"OK "+jsonObject.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(),volleyError.toString()+"   -   "+volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsonRequest);

            }
        });

        return view;
    }

    private int BoolToInt(boolean checked) {
        if(checked){
            return 1;
        }else {
            return 0;
        }
    }

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

        setedDate="";
        setedtime="";

    }

    private int getIndexInSpinner(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }
}
