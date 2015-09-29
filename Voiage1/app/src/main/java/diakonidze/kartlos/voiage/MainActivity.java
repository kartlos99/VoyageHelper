package diakonidze.kartlos.voiage; // v 30-08-2015
//C:\Users\kartlos\AppData\Local\GitHub\PortableGit_c2ba306e536fdf878271f7fe636a147ff37326ad\bin\git.exe

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import diakonidze.kartlos.voiage.adapters.StatementListPagesAdapter;
import diakonidze.kartlos.voiage.datebase.DBhelper;
import diakonidze.kartlos.voiage.datebase.DBmanager;
import diakonidze.kartlos.voiage.datebase.DBscheme;
import diakonidze.kartlos.voiage.dialogs.FilterDialog;
import diakonidze.kartlos.voiage.dialogs.PrivateInfo;
import diakonidze.kartlos.voiage.models.Cities;
import diakonidze.kartlos.voiage.models.CityConnection;
import diakonidze.kartlos.voiage.models.CityObj;
import diakonidze.kartlos.voiage.models.DriverStatement;
import diakonidze.kartlos.voiage.models.PassangerStatement;
import diakonidze.kartlos.voiage.utils.Constantebi;


public class MainActivity extends AppCompatActivity {

    private int movida = 0;
    FragmentManager manager;
    private ProgressDialog progress;
    public final Context mainctx = this;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ViewPager pager = null;
    private TabLayout tabs;

    void printHK(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "diakonidze.kartlos.voiage",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(Constantebi.accessToken == null){
            Intent intent = new Intent(getApplication(), LoginActivity.class);

            startActivity(intent);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printHK();



        final CoordinatorLayout coordLayout = (CoordinatorLayout) findViewById(R.id.main_content);
        manager = getSupportFragmentManager();

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("PeckMeApp");


        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddStatement.class);
                startActivity(intent);
            }
        });

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.manuFilter:

                        FilterDialog filterDialog = new FilterDialog();
                        filterDialog.show(manager, "filter");

                        Toast.makeText(getApplicationContext(), "filtri", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.manuAdd:
                        Intent intent = new Intent(getApplicationContext(), AddStatement.class);
                        startActivity(intent);
                        return true;

                    case R.id.manuMy:
                        Intent toMyPage = new Intent(getApplicationContext(), MyStatements.class);
                        startActivity(toMyPage);
                        return true;

                    case R.id.manuFav:
                        Intent toMyfav = new Intent(getApplicationContext(), FavoriteStatements.class);
                        startActivity(toMyfav);
                        return true;

                    case R.id.manuExit:
                        break;

                    case R.id.manuPrivit:

                        PrivateInfo infoDialog = new PrivateInfo();
                        infoDialog.show(manager, "info");

                        return true;
                }

                return false;
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
//                Toast.makeText(getApplicationContext(),"daiketa",Toast.LENGTH_SHORT).show();
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);


            }

            @Override
            public void onDrawerOpened(View drawerView) {
//                Toast.makeText(getApplicationContext(),"gaigo",Toast.LENGTH_SHORT).show();
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);

                TextView myname = (TextView) findViewById(R.id.username);
                myname.setText(Constantebi.MY_NAME);

                de.hdodenhof.circleimageview.CircleImageView profImage = (CircleImageView) findViewById(R.id.profile_image);

                Uri imageUri = Constantebi.profile.getProfilePictureUri(200, 200);

                Picasso.with(getApplicationContext())
                        .load(imageUri)
                        .resize(200,200)
                        .centerCrop()
                        .into(profImage);

            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();

        pager = (ViewPager) findViewById(R.id.pageView);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        StatementListPagesAdapter myadapter = new StatementListPagesAdapter(fragmentManager, Constantebi.ALL_STAT);
        pager.setAdapter(myadapter);

        tabs = (TabLayout) findViewById(R.id.tabs_mainpage);
        tabs.setupWithViewPager(pager);

//        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabs.setTabTextColors(Color.WHITE, getResources().getColor(R.color.fab_color));

        LoadCities();

        getFavoriteStatements();

    }

    private void getFavoriteStatements() {
        DBmanager.initialaize(this);
        DBmanager.openReadable();
        ArrayList<DriverStatement> driverStatements = DBmanager.getDriverList(Constantebi.FAV_STATEMENT);
        ArrayList<PassangerStatement> passangerStatements = DBmanager.getPassangerList(Constantebi.FAV_STATEMENT);
        DBmanager.close();

        for (int i = 0; i < driverStatements.size(); i++) {
            Constantebi.FAV_STAT_DRIVER.add(driverStatements.get(i).getId());
        }
        for (int i = 0; i < passangerStatements.size(); i++) {
            Constantebi.FAV_STAT_PASSANGER.add(passangerStatements.get(i).getId());
        }
    }

    private void LoadCities() {

//        http://back.meet.ge/get.php?type=mark
//        http://back.meet.ge/get.php?type=model


        DBmanager.initialaize(this);
        DBmanager.openReadable();
        Constantebi.cityList = DBmanager.getCityList();
//        Constantebi.brendList = DBmanager.getMarkaList();
//        Constantebi.modelList = DBmanager.getModelList();
        DBmanager.close();

        // aq sxva kriteriumia chasasmeli, es droebitia
        if (Constantebi.cityList.size() == 0) {

            RequestQueue queue = Volley.newRequestQueue(this);

            String url = "";
//            String url = "http://back.meet.ge/get.php?type=mark";
//            JsonArrayRequest requestMarka = new JsonArrayRequest(url,
//                    new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray jsonArray) {
//
//                            if (jsonArray.length() > 0) {
//                                Constantebi.brendList.clear();
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    try {
//                                        CarBrend carBrend = new CarBrend(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getString("name"));
//                                        Constantebi.brendList.add(carBrend);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                            movida++;
//                            writeToDB();
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//                            progress.dismiss();
//                            Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//            );
//
//            url = "http://back.meet.ge/get.php?type=model";
//            JsonArrayRequest requestModel = new JsonArrayRequest(url,
//                    new Response.Listener<JSONArray>() {
//                        @Override
//                        public void onResponse(JSONArray jsonArray) {
//
//                            if (jsonArray.length() > 0) {
//                                Constantebi.modelList.clear();
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    try {
//                                        CarModel carModel = new CarModel(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getInt("id_mark"), jsonArray.getJSONObject(i).getString("name"));
//                                        Constantebi.modelList.add(carModel);
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            }
//                            movida++;
//                            writeToDB();
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError volleyError) {
//                            progress.dismiss();
//                            Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    }
//            );


            url = "http://back.meet.ge/get.php?type=cities";
            JsonArrayRequest requestCities = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {

                            if (jsonArray.length() > 0) {
                                Constantebi.cityList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        Cities newCity = new Cities(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getString("nameGE"));
                                        newCity.setNameEN(jsonArray.getJSONObject(i).getString("nameEN"));
                                        newCity.setImage(jsonArray.getJSONObject(i).getString("image"));
                                        Constantebi.cityList.add(newCity);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            movida++;
                            writeToDB();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            progress.dismiss();
                            Toast.makeText(MainActivity.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
            );

            progress = ProgressDialog.show(this, "ჩამოტვირთვა", "გთხოვთ დაიცადოთ");
//            queue.add(requestMarka);
//            queue.add(requestModel);
            queue.add(requestCities);

            Toast.makeText(this, "INtidan chamotvirtva", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.add_statement) {
            Intent intent = new Intent(getApplicationContext(), AddStatement.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.manu_filter) {
            FilterDialog filterDialog = new FilterDialog();
            filterDialog.show(manager, "filter");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void writeToDB() {
        if (movida == 1) {

            progress.dismiss();

            AsyncWrite writedb = new AsyncWrite(new DBhelper(this));
            writedb.execute();

//            DBmanager.initialaize(getApplication());
//            DBmanager.openWritable();
//            for (int i = 0; i < Constantebi.brendList.size(); i++) {
//                DBmanager.insertToMarka(Constantebi.brendList.get(i));
//            }
//            for (int i = 0; i < Constantebi.modelList.size(); i++) {
//                DBmanager.insertToModel(Constantebi.modelList.get(i));
//            }
//            for (int i = 0; i < Constantebi.cityList.size(); i++) {
//                DBmanager.insertCity(Constantebi.cityList.get(i));
//            }
//            DBmanager.close();

        }
    }

    private class AsyncWrite extends AsyncTask<DBhelper, String, String> {
        private DBhelper dbhelper;
        private SQLiteDatabase db;

        public AsyncWrite(DBhelper dbhelper) {
            this.dbhelper = dbhelper;
        }

        @Override
        protected String doInBackground(DBhelper... params) {

            db = dbhelper.getWritableDatabase();

            for (int i = 0; i < Constantebi.cityList.size(); i++) {
                ContentValues values = new ContentValues();
                values.put(DBscheme.CITY_ID, Constantebi.cityList.get(i).getC_id());
                values.put(DBscheme.NAME, Constantebi.cityList.get(i).getNameGE());
                values.put(DBscheme.NAME_EN, Constantebi.cityList.get(i).getNameEN());
                values.put(DBscheme.CITY_PHOTO, Constantebi.cityList.get(i).getImage());
                db.insert(DBscheme.CITY_TABLE_NAME, null, values);
            }

//            for (int i = 0; i < Constantebi.modelList.size(); i++) {
//                ContentValues values = new ContentValues();
//                values.put(DBscheme.MODEL_ID, Constantebi.modelList.get(i).getId());
//                values.put(DBscheme.MARKA_ID, Constantebi.modelList.get(i).getBrendID());
//                values.put(DBscheme.NAME, Constantebi.modelList.get(i).getModel());
//                db.insert(DBscheme.MODEL_TABLE_NAME, null, values);
//            }
//
//            for (int i = 0; i < Constantebi.brendList.size(); i++) {
//                ContentValues values = new ContentValues();
//                values.put(DBscheme.MARKA_ID, Constantebi.brendList.get(i).getId());
//                values.put(DBscheme.NAME, Constantebi.brendList.get(i).getMarka());
//                db.insert(DBscheme.MARKA_TABLE_NAME, null, values);
//            }

            db.close();

            return "saved in DB";
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
        }
    }

}
