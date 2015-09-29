package diakonidze.kartlos.voiage.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.internal.widget.DialogTitle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import diakonidze.kartlos.voiage.utils.Constantebi;

/**
 * Created by k.diakonidze on 8/3/2015.
 */
public class PrivateInfo extends DialogFragment {

    EditText mobileText;
    Button buttonOk;
    Button buttonCancel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog d =  super.onCreateDialog(savedInstanceState);
        d.setTitle("ტელეფონის ნომერი");

        return d;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.private_info_dialog, container, false);

        mobileText = (EditText) view.findViewById(R.id.edit_mobile);
        buttonOk = (Button) view.findViewById(R.id.okMobile);
        buttonCancel = (Button) view.findViewById(R.id.cancelMobile);

        mobileText.setText(Constantebi.MY_MOBILE);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constantebi.MY_MOBILE = mobileText.getText().toString();


                String url = "back.meet.ge/mobile.php";
                RequestQueue queue = Volley.newRequestQueue(getActivity());

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("fb_id",  Constantebi.profile.getId());
                    jsonObject.put("mobile", Constantebi.MY_MOBILE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getActivity(), volleyError.toString() + "   -   " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                queue.add(jsonRequest);

                dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;
    }


}
