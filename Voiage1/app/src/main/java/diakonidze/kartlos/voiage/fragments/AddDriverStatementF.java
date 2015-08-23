package diakonidze.kartlos.voiage.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.datebase.DBhelper;
import diakonidze.kartlos.voiage.datebase.DBmanager;
import diakonidze.kartlos.voiage.utils.Constantebi;
import diakonidze.kartlos.voiage.models.DriverStatement;

/**
 * Created by k.diakonidze on 7/17/2015.
 */
public class AddDriverStatementF extends Fragment implements View.OnClickListener {
    private Calendar runTimeC;
    private TextView passAgeText;
    private Button driverDonebtn, pirobebiBtn, limitBtn, takePhotoBtn;
    private DriverStatement driverStatement;
    private ImageView carImageView;
    private ImageView carType1;
    private ImageView carType2;
    private ImageView carType3;
    private ImageView carType4;
    private ImageView carType5;

    private int carType = 0;

    private Uri uri;
    private File imagefile;

    Spinner freeSpaceSpinner, priceSpinner, markaSpinner, modelSpinner, genderSpinner, colorSpinner;
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

    public String convertImigToSrt(String teUri) {
//        System.out.print("______________");
        Bitmap bitmap = BitmapFactory.decodeFile(teUri);

        if (bitmap == null) {
            return "";
        } else {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 2, outputStream);

            byte[] bytes = outputStream.toByteArray();

            return Base64.encodeToString(bytes, Base64.DEFAULT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                if (imagefile.exists()) {
                    Toast.makeText(getActivity(), "sheinaxa " + imagefile.getAbsolutePath(), Toast.LENGTH_LONG).show();
//                    imageView.setImageURI(uri);
                    carImageView.clearAnimation();
                    Picasso.with(getActivity())
                            .load(imagefile)
                            .resize(500, 200)
                            .centerCrop()
                            .into(carImageView);
                } else {
                    Toast.makeText(getActivity(), "imagefile Error", Toast.LENGTH_LONG).show();
                }

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "uari fotoze", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initialazeAll();
        workState = getArguments().getString("action");

        if (savedInstanceState != null) {
            // tu reCreate moxda grafebshi vabrumebt ra mdgomareobac iyo
            driverStatement = (DriverStatement) savedInstanceState.getSerializable("statement");
            fillForm(driverStatement);
        } else {  // pirvelad chaitvirta es forma
            driverStatement = (DriverStatement) getArguments().getSerializable("statement");
            // chemi gancxadebis gaxsna redaqtirebisatvis
            if (workState.equals(Constantebi.REASON_EDIT)) {
                fillForm(driverStatement);
            }
            // axali gancxadebis chawera
            if (getArguments().getString("action").equals(Constantebi.REASON_ADD)) {

            }
        }


//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        // suratis gadageba ************************************************

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imagefile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "my1photo.jpg");

                uri = Uri.fromFile(imagefile);
                imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                imageIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.1);
                startActivityForResult(imageIntent, 0);
            }
        });

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
                modelebisListisDayeneba(position);
//                modelSpinner.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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


        // mgzavrze shezgudvebis dayeneba

        limitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passengerLimit = !passengerLimit;
                if (passengerLimit) {
                    passangerLimitBox.setVisibility(View.VISIBLE);
                    limitBtn.setBackgroundResource(R.drawable.greenbtn_lite);
                } else {
                    passangerLimitBox.setVisibility(View.GONE);
                    limitBtn.setBackgroundResource(R.drawable.greenbtn_dark);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                passAgeText.setText("მაქს. ასაკი " + i);
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


                if (!citylist.contains(cityFrom.getText().toString()) || !citylist.contains(cityTo.getText().toString())) {
                    Toast.makeText(getActivity(), "ქალაქი არასწორადაა მითითებული!", Toast.LENGTH_LONG).show();
                } else {
                    driverStatement = readForm();
                    driverStatement.setUserID(Constantebi.MY_ID);

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("cityFrom", driverStatement.getCityFrom());
                        jsonObject.put("cityTo", driverStatement.getCityTo());
                        jsonObject.put("cityPath", "AA__BB");
                        jsonObject.put("date", driverStatement.getDate());
                        jsonObject.put("time", driverStatement.getTime());
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
                        jsonObject.put("user_id", driverStatement.getUserID());

                        if (uri != null) {
                            jsonObject.put("image", convertImigToSrt(uri.getPath()));
                        } else {
                            jsonObject.put("image", "");
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    if (workState.equals(Constantebi.REASON_ADD)) {

                        String url = "http://back.meet.ge/get.php?type=INSERT&sub_type=1&json";

                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

//                                Toast.makeText(getActivity(), "OK insert" + jsonObject.toString(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(), "განცხადება დამატებულია!", Toast.LENGTH_SHORT).show();

                                // tu warmatebit ganxorcielda bazashi chawera, chventanac vinaxavt localurad
                                try {
                                    int id = jsonObject.getInt("insert_id");
                                    driverStatement.setId(id);

                                    DBmanager.initialaize(getActivity());
                                    DBmanager.openWritable();
                                    DBmanager.insertIntoDriver(driverStatement, Constantebi.MY_STATEMENT);
                                    DBmanager.close();

                                    getActivity().onBackPressed();

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
                        // **** aq modis ganaxlebis dros ******

                        try {
                            jsonObject.put("s_id", driverStatement.getId());
                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "mdzgolis gancxadebis ganaxleba - id aramaqvs rom gavagzavno serverze", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        // აქ უპდატეს ლინკია ცჰასაწერი!!!!!!!!!!!!!!!!!!*************************************
                        String url = "http://back.meet.ge/get.php?type=UPDATE&sub_type=1";

                        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {

                                Toast.makeText(getActivity(), "ცვლილებები შენახულია!", Toast.LENGTH_SHORT).show();
                                // tu warmatebit ganxorcielda bazashi chawera, chventanac vinaxavt localurad
                                DBmanager.initialaize(getActivity());
                                DBmanager.openWritable();
                                DBmanager.updateDriverStatement(driverStatement);
                                DBmanager.close();

                                getActivity().onBackPressed();

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
                    if (cityFrom.getText().toString().equals(""))
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
                    if (cityTo.getText().toString().equals(""))
                        cityTo.showDropDown();
                    cityTo.setTextColor(Color.BLACK);
                } else {
                    if (!citylist.contains(cityTo.getText().toString()))
                        cityTo.setTextColor(Color.RED);
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
        comfort1 = (RelativeLayout) view.findViewById(R.id.comfort1);
        passangerLimitBox = (LinearLayout) view.findViewById(R.id.driver_passanger_restrict_box);
        seekBar = (SeekBar) view.findViewById(R.id.driver_pass_age_seek);
        driverDonebtn = (Button) view.findViewById(R.id.done_driver);
        colorSpinner = (Spinner) view.findViewById(R.id.driver_color_spiner);
        passAgeText = (TextView) view.findViewById(R.id.driver_pass_age_text);
        limitBtn = (Button) view.findViewById(R.id.driver_limit_btn);
        pirobebiBtn = (Button) view.findViewById(R.id.driver_pirobebi_btn);
        takePhotoBtn = (Button) view.findViewById(R.id.take_photo_btn);
        carImageView = (ImageView) view.findViewById(R.id.car_image);
        carType1 = (ImageView) view.findViewById(R.id.car_type_1);
        carType2 = (ImageView) view.findViewById(R.id.car_type_2);
        carType3 = (ImageView) view.findViewById(R.id.car_type_3);
        carType4 = (ImageView) view.findViewById(R.id.car_type_4);
        carType5 = (ImageView) view.findViewById(R.id.car_type_5);

        carType1.setOnClickListener(this);
        carType2.setOnClickListener(this);
        carType3.setOnClickListener(this);
        carType4.setOnClickListener(this);
        carType5.setOnClickListener(this);

        return view;
    }

    private void fillForm(DriverStatement statement) {

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

//        markaSpinner.setSelection(statement.getMarka());
//        modelebisListisDayeneba(statement.getMarka());
//        modelSpinner.setSelection(statement.getModeli());
//        colorSpinner.setSelection(statement.getColor());

        carType1.setBackground(null);
        carType2.setBackground(null);
        carType3.setBackground(null);
        carType4.setBackground(null);
        carType5.setBackground(null);

        switch (statement.getMarka()) {
            case 0:
                carType1.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
                carType = 0;
                break;
            case 1:
                carType2.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
                carType = 1;
                break;
            case 2:
                carType3.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
                carType = 2;
                break;
            case 3:
                carType4.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
                carType = 3;
                break;
            case 4:
                carType5.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
                carType = 4;
                break;
        }

        if (statement.getAtHome() != 2) {
            pirobebi = true;
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

        if (statement.getAgeTo() != 1000) {
            seekBar.setProgress(statement.getAgeTo());
            genderSpinner.setSelection(statement.getGender());
            passAgeText.setText("მაქს. ასაკი " + statement.getAgeTo());
            passangerLimitBox.setVisibility(View.VISIBLE);
            limitBtn.setBackgroundResource(R.drawable.greenbtn_lite);
            passengerLimit = true;
        } else {
            seekBar.setProgress(1);
            genderSpinner.setSelection(0);
            passangerLimitBox.setVisibility(View.GONE);
            limitBtn.setBackgroundResource(R.drawable.greenbtn_dark);
            passengerLimit = false;
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
        passengerLimit = false;

        seekBar.setMax(80);
        runTimeC = Calendar.getInstance();

        citylist.clear();
        for (int i = 0; i < Constantebi.cityList.size(); i++) {
            citylist.add(Constantebi.cityList.get(i).getNameGE());
        }


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

//        brendlist.clear();
//        modellist.clear();
//        for (int i = 0; i < Constantebi.brendList.size(); i++) {
//            brendlist.add(Constantebi.brendList.get(i).getMarka());
//        }
//
//        for (int i = 0; i < Constantebi.modelList.size(); i++) {
//            if (Constantebi.modelList.get(i).getBrendID() == 1) {
//                modellist.add(Constantebi.modelList.get(i).getModel());
//            }
//        }
        carType2.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
        carType = 1;


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

    // formidan informaciis wakitxva ***************************************
    private DriverStatement readForm() {

        if (runDateSpinner.getSelectedItemPosition() < 3) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, runDateSpinner.getSelectedItemPosition());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            setedDate = dateFormat.format(runTimeC.getTime());
        }

        DriverStatement statement = new DriverStatement(Constantebi.MY_ID,
                freeSpaceSpinner.getSelectedItemPosition() + 1,
                Integer.valueOf(priceSpinner.getSelectedItem().toString()),
                setedDate,
                cityFrom.getText().toString(),
                cityTo.getText().toString());

        statement.setTime(runTimeSpinner.getSelectedItem().toString());

        statement.setMarka(carType);
//        statement.setMarka(markaSpinner.getSelectedItemPosition());
//        statement.setModeli(modelSpinner.getSelectedItemPosition());
//        statement.setColor(colorSpinner.getSelectedItemPosition());

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

        if (passengerLimit) {
            statement.setAgeTo(seekBar.getProgress());
            statement.setGender(genderSpinner.getSelectedItemPosition());
        } else {
            statement.setAgeTo(1000);
            statement.setGender(2);
        }
        statement.setComment(commentText.getText().toString());

        statement.setId(driverStatement.getId());
        statement.setUserID(driverStatement.getUserID());

        return statement;
    }

    private void modelebisListisDayeneba(int position) {
        String br = brendlist.get(position).toString();
        int br_id = 0;
        for (int i = 0; i < Constantebi.brendList.size(); i++) {
            if (br.equals(Constantebi.brendList.get(i).getMarka())) {
                br_id = Constantebi.brendList.get(i).getId();
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
        modelSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, modellist);
        ((ArrayAdapter<String>) modelSpinner.getAdapter()).notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        carType1.setBackground(null);
        carType2.setBackground(null);
        carType3.setBackground(null);
        carType4.setBackground(null);
        carType5.setBackground(null);

        switch (view.getId()) {
            case R.id.car_type_1:
                carType1.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
                carType = 0;
                break;
            case R.id.car_type_2:
                carType2.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
                carType = 1;
                break;
            case R.id.car_type_3:
                carType3.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
                carType = 2;
                break;
            case R.id.car_type_4:
                carType4.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
                carType = 3;
                break;
            case R.id.car_type_5:
                carType5.setBackground(getResources().getDrawable(R.drawable.abc_tab_indicator_mtrl_alpha));
                carType = 4;
                break;
        }
    }
}
