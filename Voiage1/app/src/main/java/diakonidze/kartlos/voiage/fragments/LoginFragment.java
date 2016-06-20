package diakonidze.kartlos.voiage.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginBehavior;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import diakonidze.kartlos.voiage.AddStatement;
import diakonidze.kartlos.voiage.MainActivity;
//import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.datebase.DBmanager;
import diakonidze.kartlos.voiage.utils.Constantebi;

/**
 * Created by kartlos on 9/29/2015.
 */
public class LoginFragment extends android.support.v4.app.Fragment {


//        String ss;
//    ImageView imageView, imig2;
//    TextView textView;
//    private CallbackManager callbackManager;
//
//    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {
//        @Override
//        public void onSuccess(LoginResult loginResult) {
//            Constantebi.accessToken = loginResult.getAccessToken();
//            Constantebi.profile = Profile.getCurrentProfile();
//            if (Constantebi.profile != null) {
//
//                String url = "back.meet.ge/reg.php";
//                RequestQueue queue = Volley.newRequestQueue(getActivity());
//
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("firstname", Constantebi.profile.getName());
//                    jsonObject.put("fb_id",  Constantebi.profile.getId());
//                    jsonObject.put("mobile",  "123456789");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject jsonObject) {
//
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError volleyError) {
//                        Toast.makeText(getActivity(), volleyError.toString() + "   -   " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                queue.add(jsonRequest);
//
////                gotoapp();
//            }
//
//        }
//
//        @Override
//        public void onCancel() {
//
//            Toast.makeText(getActivity(), "Canseled", Toast.LENGTH_LONG).show();
//            Intent intent = new Intent(getActivity(), MainActivity.class);
//            intent.putExtra("logedin",false);
//            startActivity(intent);
//        }
//
//        @Override
//        public void onError(FacebookException e) {
//            Toast.makeText(getActivity(), "Errorio", Toast.LENGTH_LONG).show();
//
//        }
//    };

//    void gotoapp(){
//        Constantebi.MY_NAME = Constantebi.profile.getName();
//        Constantebi.MY_ID = Constantebi.profile.getId();
//        getActivity().onBackPressed();
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        LoginButton loginButton = (LoginButton) view.findViewById(R.id.FB_btn);
//        loginButton.setReadPermissions("user_friends");
//        loginButton.setFragment(this);
//        loginButton.registerCallback(callbackManager, mCallback);
//
//        AccessToken ac = AccessToken.getCurrentAccessToken();
//
//
//        if(ac != null){
//            Constantebi.accessToken = ac;
//            Constantebi.profile = Profile.getCurrentProfile();
//
//            gotoapp();
//        }
//
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
//
//        callbackManager = CallbackManager.Factory.create();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        return inflater.inflate(R.layout.login_f, container, false);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//    public boolean isLoggedIn() {
//        AccessToken ac = AccessToken.getCurrentAccessToken();
//        return ac != null;
//    }
}
