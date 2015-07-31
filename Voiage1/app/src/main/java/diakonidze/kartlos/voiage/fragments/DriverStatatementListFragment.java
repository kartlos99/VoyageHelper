package diakonidze.kartlos.voiage.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import diakonidze.kartlos.voiage.DetailPageDriver;
import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.adapters.DriverListAdapter;
import diakonidze.kartlos.voiage.utils.Constantebi;
import diakonidze.kartlos.voiage.models.DriverStatement;

/**
 * Created by k.diakonidze on 7/13/2015.
 */
public class DriverStatatementListFragment extends Fragment {

    private JSONObject myobj;
    private ProgressDialog progress;
    private ArrayList<DriverStatement> driverStatements;
    private DriverListAdapter driverListAdapter;
    private ListView driverStatementList;
    private String location = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a, container, false);

        driverStatementList = (ListView) v.findViewById(R.id.statement_1_list);
        driverStatementList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupMenu pManu = new PopupMenu(getActivity(), view);
                pManu.getMenuInflater().inflate(R.menu.popup_manu, pManu.getMenu());
                pManu.show();
                return false;
            }
        });

        location = getArguments().getString("location");

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        driverStatements = new ArrayList<>();
        getDriversStatements();

        driverListAdapter = new DriverListAdapter(getActivity(), driverStatements);
        driverStatementList.setAdapter(driverListAdapter);
        driverStatementList.setDivider(null);

        driverStatementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailPageDriver.class);

                DriverStatement currStatement = (DriverStatement) parent.getItemAtPosition(position);
                intent.putExtra("driver_st", currStatement);
                intent.putExtra("from", location);

                startActivity(intent);
            }
        });
    }

    private void getDriversStatements() {

        String url="";
// romeli info wamovigo serveridan
        switch (location){
            case Constantebi.ALL_STAT:  url = "http://back.meet.ge/get.php?type=1";
                break;
            case Constantebi.MY_OWN_STAT:  url = "http://back.meet.ge/get.php?type=1";
                break;
            case Constantebi.FAVORIT_STAT:  url = "http://back.meet.ge/get.php?type=1";
                break;
        }


        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        ArrayList<DriverStatement> newData = new ArrayList<>();


                        if(jsonArray.length()>0){
                            for(int i=0; i<jsonArray.length(); i++){
                                try {
//                                    String stringDate = jsonArray.getJSONObject(i).getString("date");
//                                    Calendar calendar = Calendar.getInstance();
//                                    try {
//                                        calendar.setTime(format.parse(stringDate));
//                                    } catch (ParseException e) {
//                                        e.printStackTrace();
//                                    }

                                    DriverStatement newDriverStatement = new DriverStatement( 1,
                                            jsonArray.getJSONObject(i).getInt("freespace"),
                                            jsonArray.getJSONObject(i).getInt("price"),
                                            jsonArray.getJSONObject(i).getString("date"),
                                            jsonArray.getJSONObject(i).getString("cityFrom"),
                                            jsonArray.getJSONObject(i).getString("cityTo"));

                                    newDriverStatement.setCityPath(jsonArray.getJSONObject(i).getString("cityPath"));
                                    newDriverStatement.setTime(jsonArray.getJSONObject(i).getString("time"));
                                    newDriverStatement.setMarka(1); //int
                                    newDriverStatement.setModeli(1); //int
                                    newDriverStatement.setColor(jsonArray.getJSONObject(i).getInt("color"));
                                    newDriverStatement.setCarpicture(jsonArray.getJSONObject(i).getString("photo"));
                                    newDriverStatement.setKondencioneri(jsonArray.getJSONObject(i).getInt("kondincioneri"));
                                    newDriverStatement.setSigareti(jsonArray.getJSONObject(i).getInt("sigareti"));
                                    newDriverStatement.setSabarguli(jsonArray.getJSONObject(i).getInt("sabarguli"));
                                    newDriverStatement.setAtHome(jsonArray.getJSONObject(i).getInt("adgilzemisvla"));
                                    newDriverStatement.setCxovelebi(jsonArray.getJSONObject(i).getInt("cxoveli"));
                                    newDriverStatement.setPlaceX(jsonArray.getJSONObject(i).getLong("placex"));
                                    newDriverStatement.setPlaceY(jsonArray.getJSONObject(i).getLong("placey"));
                                    newDriverStatement.setAgeTo(jsonArray.getJSONObject(i).getInt("ageTo"));
                                    newDriverStatement.setGender(jsonArray.getJSONObject(i).getInt("gender"));
                                    newDriverStatement.setComment(jsonArray.getJSONObject(i).getString("comment"));

                                    newDriverStatement.setName(jsonArray.getJSONObject(i).getString("firstname"));
                                    newDriverStatement.setSurname(jsonArray.getJSONObject(i).getString("lastname"));
                                    newDriverStatement.setNumber(jsonArray.getJSONObject(i).getString("mobile"));

                                    newData.add(newDriverStatement);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (newData.size() > 0) {           // tu erti mowyobiloba mainc aris mashin vaxarisxebt lists
                            driverStatements = newData;
                            driverListAdapter = new DriverListAdapter(getActivity(), driverStatements);
                            driverStatementList.setAdapter(driverListAdapter);
                        }
                        progress.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                      //  Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
                }
        );

        progress = ProgressDialog.show(getActivity(), "ჩამოტვირთვა", "გთხოვთ დაიცადოთ");
        queue.add(request);
    }



    private ArrayList<DriverStatement> getStatementData() {
        ArrayList<DriverStatement> data = new ArrayList<>();
//        Calendar now = Calendar.getInstance();
//        for (int i = 0; i < 14; i++)
//        {
//            DriverStatement newStatment = new DriverStatement(1, 3, 12+i, "", "ქუთაისი", "ზესტაფონი");
//            newStatment.setName("მალზახ");
//            newStatment.setSurname("აბდუშელაშვილი");
//            newStatment.setNumber("577987006");
//            newStatment.setMarka(1+i);
//            newStatment.setAgeTo(55);
//
//            data.add(newStatment);
//        }
        return data;
    }


}
