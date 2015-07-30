package diakonidze.kartlos.voiage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import diakonidze.kartlos.voiage.R;

/**
 * Created by k.diakonidze on 7/17/2015.
 */
public class AddPassengetStatementF extends Fragment {

    private Button passangerDoneBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_passanger_statement_layout, container, false);


        passangerDoneBtn = (Button) view.findViewById(R.id.done_passanger);
        passangerDoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("CityFrom", "TB-1");
                    jsonObject.put("CityTo", "BA-1");
                    jsonObject.put("date", "2009-11-11");
                    jsonObject.put("time", "0");
                    jsonObject.put("freespace", 9);
                    jsonObject.put("price", 10);
                    jsonObject.put("kondincioneri", 2);
                    jsonObject.put("sigareti", 2);
                    jsonObject.put("sabarguli", 2);
                    jsonObject.put("adgilzemisvla", 2);
                    jsonObject.put("cxovelebi", 2);
                    jsonObject.put("placex", "555");
                    jsonObject.put("placey", "555");
                    jsonObject.put("comment", "cccooooommm");
                    jsonObject.put("status", 1);
                    jsonObject.put("sex", 1);
                    jsonObject.put("photo", "NON");
                    jsonObject.put("user_id", "1");
//

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestQueue queue = Volley.newRequestQueue(getActivity());
                String url = "http://back.meet.ge/get.php?type=INSERT&sub_type=2&json";
//                String url = "http://back.meet.ge/get.php?type=my&sub_type=2";


                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST ,url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        Toast.makeText(getActivity(), "OK " + jsonObject.toString(), Toast.LENGTH_SHORT).show();
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
}
