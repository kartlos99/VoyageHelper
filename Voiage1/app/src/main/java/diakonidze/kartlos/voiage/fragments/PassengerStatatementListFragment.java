package diakonidze.kartlos.voiage.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

import diakonidze.kartlos.voiage.DetailPagePassanger;
import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.adapters.PassangerListAdapter;
import diakonidze.kartlos.voiage.datebase.DBmanager;
import diakonidze.kartlos.voiage.utils.Constantebi;
import diakonidze.kartlos.voiage.models.PassangerStatement;

/**
 * Created by k.diakonidze on 7/13/2015.
 */
public class PassengerStatatementListFragment extends Fragment {

    private ArrayList<PassangerStatement> passangerStatements;
    private ListView passangerStatementList;
    private PassangerListAdapter passangerListAdapter;
    private ProgressDialog progress;
    private JSONObject myobj;
    private String location = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b, container,false);
        passangerStatementList = (ListView) v.findViewById(R.id.statement_2_list);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        location = getArguments().getString("location"); // vin gamoiZaxa es forma
        passangerStatements = new ArrayList<>();

        if(location.equals(Constantebi.MY_OWN_STAT)){
            DBmanager.initialaize(getActivity());
            DBmanager.openReadable();
            passangerStatements = DBmanager.getPassangerList(Constantebi.MY_STATEMENT);
            DBmanager.close();
        }else {
            getPassengersStatements();
        }

        passangerListAdapter = new PassangerListAdapter(getActivity(), passangerStatements);
        passangerStatementList.setAdapter(passangerListAdapter);
        passangerStatementList.setDivider(null);

        passangerStatementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailPagePassanger.class);

                PassangerStatement currStatement = (PassangerStatement) parent.getItemAtPosition(position);
                intent.putExtra("driver_st", currStatement);
                intent.putExtra("from", location);

                startActivity(intent);
            }
        });
    }

    private void getPassengersStatements() {

        String url = "";
        // romeli info wamovigo serveridan
        switch (location){
            case Constantebi.ALL_STAT:
//                url = "http://back.meet.ge/get.php?type=2";
                url = "http://back.meet.ge/get.php?type=PAGE&sub_type=2&start=0&end=50";
                break;
            case Constantebi.MY_OWN_STAT:  url = "http://back.meet.ge/get.php?type=2";
                break;
            case Constantebi.FAVORIT_STAT:  url = "http://back.meet.ge/get.php?type=2";
                break;
        }

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {


                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        ArrayList<PassangerStatement> newData = new ArrayList<>();

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

                                    PassangerStatement newPassangerStatement = new PassangerStatement( 1,
                                            jsonArray.getJSONObject(i).getInt("freespace"),
                                            jsonArray.getJSONObject(i).getInt("price"),
                                            jsonArray.getJSONObject(i).getString("cityFrom"),
                                            jsonArray.getJSONObject(i).getString("cityTo"),
                                            jsonArray.getJSONObject(i).getString("date"));

                                    newPassangerStatement.setTime(jsonArray.getJSONObject(i).getString("time"));
                                    newPassangerStatement.setKondencioneri(jsonArray.getJSONObject(i).getInt("kondincioneri"));
                                    newPassangerStatement.setSigareti(jsonArray.getJSONObject(i).getInt("sigareti"));
                                    newPassangerStatement.setSabarguli(jsonArray.getJSONObject(i).getInt("sabarguli"));
                                    newPassangerStatement.setAtHome(jsonArray.getJSONObject(i).getInt("adgilzemisvla"));
                                    newPassangerStatement.setCxovelebi(jsonArray.getJSONObject(i).getInt("cxoveli"));
                                    newPassangerStatement.setPlaceX(jsonArray.getJSONObject(i).getLong("placex"));
                                    newPassangerStatement.setPlaceY(jsonArray.getJSONObject(i).getLong("placey"));
                                    newPassangerStatement.setComment(jsonArray.getJSONObject(i).getString("comment"));

                                    newPassangerStatement.setName(jsonArray.getJSONObject(i).getString("firstname"));
                                    newPassangerStatement.setSurname(jsonArray.getJSONObject(i).getString("lastname"));
                                    newPassangerStatement.setNumber(jsonArray.getJSONObject(i).getString("mobile"));

                                    newPassangerStatement.setId(jsonArray.getJSONObject(i).getInt("s_id"));
                                    newPassangerStatement.setUserID(jsonArray.getJSONObject(i).getInt("user_id"));

                                    newData.add(newPassangerStatement);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (newData.size() > 0) {           // tu erti mowyobiloba mainc aris mashin vaxarisxebt lists
                            passangerStatements = newData;
                            passangerListAdapter = new PassangerListAdapter(getActivity(),passangerStatements);
                            passangerStatementList.setAdapter(passangerListAdapter);
                        }
                        progress.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                          Toast.makeText(getActivity(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }
                }
        );

        progress = ProgressDialog.show(getActivity(), "ჩამოტვირთვა2", "გთხოვთ დაიცადოთ");
        queue.add(request);
    }

//    private ArrayList<PassangerStatement> getStatementData() {
//        ArrayList<PassangerStatement> data = new ArrayList<>();
////        Calendar now = Calendar.getInstance();
////        for (int i = 0; i < 14; i++)
////        {
////            PassangerStatement newStatment = new PassangerStatement(1, 1, 10, "ბორჯომი", "ბაკურიანი", "");
////            newStatment.setName("მარგარიტა");
////            newStatment.setSurname("აბდუშელაშვილი");
////            newStatment.setNumber("577987__6");
////            data.add(newStatment);
////        }
//        return data;
//    }
}
