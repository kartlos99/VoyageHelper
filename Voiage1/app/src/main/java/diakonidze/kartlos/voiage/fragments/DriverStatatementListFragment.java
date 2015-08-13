package diakonidze.kartlos.voiage.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import diakonidze.kartlos.voiage.DetailPageDriver;
import diakonidze.kartlos.voiage.MainActivity;
import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.adapters.DriverListAdapter;
import diakonidze.kartlos.voiage.adapters.DriverListAdapterRc;
import diakonidze.kartlos.voiage.datebase.DBmanager;
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
//    private ListView driverStatementList;
    private String location = "";
    private SwipeRefreshLayout swRefresh;
    private RecyclerView statementListView;
    private DriverListAdapterRc driverListAdapterRc;

    int dataStartPoint = 0, dataPageSize = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a, container, false);

        statementListView = (RecyclerView) v.findViewById(R.id.recyclerList1);
        swRefresh = (SwipeRefreshLayout) v.findViewById(R.id.driverRefresh);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        driverStatements = new ArrayList<>();
        swRefresh.setColorSchemeColors(getResources().getColor(R.color.fab_color));
        final View v;

        // vin gamoiZaxa es forma
        location = getArguments().getString("location");

        switch (location) {
            case Constantebi.MY_OWN_STAT:
                DBmanager.initialaize(getActivity());
                DBmanager.openReadable();
                driverStatements = DBmanager.getDriverList(Constantebi.MY_STATEMENT);
                DBmanager.close();
                v = statementListView;
                break;
            case Constantebi.FAVORIT_STAT:
                DBmanager.initialaize(getActivity());
                DBmanager.openReadable();
                driverStatements = DBmanager.getDriverList(Constantebi.FAV_STATEMENT);
                DBmanager.close();
                v = statementListView;
                break;
            case Constantebi.ALL_STAT:
                getDriversStatements();
                v = getActivity().findViewById(R.id.main_content);
                break;
            default:
                Toast.makeText(getActivity(), "Nothing to show", Toast.LENGTH_LONG).show();
                v = statementListView;
        }

        // snakbarma fab Button -i rom ar dafaros


        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                dataStartPoint = 0;
                getDriversStatements();
                Snackbar.make(v, "OK refresh", Snackbar.LENGTH_LONG).show();
            }
        });



        // Listis gaketeba
        statementListView.setHasFixedSize(true);
        driverListAdapterRc = new DriverListAdapterRc(driverStatements, getActivity(), location);
        statementListView.setAdapter(driverListAdapterRc);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        statementListView.setLayoutManager(linearLayoutManager);

linearLayoutManager.onItemsChanged(statementListView);

        statementListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition()==driverStatements.size()-1){
                    Snackbar.make(v, "BOLOSHIA!!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        statementListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition()==driverStatements.size()-1){
                    Snackbar.make(v, "BOLOSHIA!!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                Snackbar.make(v, "BOLOSHIA!!", Snackbar.LENGTH_LONG).show();
            }
        });


    }

    private void getDriversStatements() {

        String url = "";
        // romeli info wamovigo serveridan
        switch (location) {
//            http://back.meet.ge/get.php?type=PAGE&sub_type=1&start=0&end=10

            case Constantebi.ALL_STAT:
//            url = "http://back.meet.ge/get.php?type=1";
                url = "http://back.meet.ge/get.php?type=PAGE&sub_type=1&start=" + dataStartPoint + "&end=" + dataPageSize;

                break;
            case Constantebi.MY_OWN_STAT:
                url = "http://back.meet.ge/get.php?type=1";
                break;
            case Constantebi.FAVORIT_STAT:
                url = "http://back.meet.ge/get.php?type=1";
                break;
        }


        RequestQueue queue = Volley.newRequestQueue(getActivity());

        DefaultRetryPolicy myPolicy = new DefaultRetryPolicy(5000, 3, 1.0f);

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        ArrayList<DriverStatement> newData = new ArrayList<>();

                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                try {

                                    DriverStatement newDriverStatement = new DriverStatement(1,
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

                                    newDriverStatement.setId(jsonArray.getJSONObject(i).getInt("s_id"));
                                    newDriverStatement.setUserID(jsonArray.getJSONObject(i).getInt("user_id"));
                                    newDriverStatement.setCarpicture(jsonArray.getJSONObject(i).getString("image"));

                                    newData.add(newDriverStatement);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (newData.size() > 0) {
                            if(location.equals(Constantebi.ALL_STAT)) {
                                for (int i = 0; i< newData.size(); i++){
                                    driverStatements.add(newData.get(i));
                                }
                                dataStartPoint += dataPageSize;
                            }
                            if(location.equals(Constantebi.FAVORIT_STAT)) {
                                driverStatements = newData;
                            }

                            statementListView.setHasFixedSize(true);
                            driverListAdapterRc = new DriverListAdapterRc(driverStatements, getActivity(), location);
                            statementListView.setAdapter(driverListAdapterRc);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            statementListView.setLayoutManager(linearLayoutManager);
//                            driverStatements = newData;
//                            driverListAdapter = new DriverListAdapter(getActivity(), driverStatements);
//                            driverStatementList.setAdapter(driverListAdapter);
                        }
                        swRefresh.setRefreshing(false);
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

        request.setRetryPolicy(myPolicy);

        if(!swRefresh.isRefreshing()) {
            progress = ProgressDialog.show(getActivity(), "ჩამოტვირთვა1", "გთხოვთ დაიცადოთ");
        }
        queue.add(request);
    }
}
