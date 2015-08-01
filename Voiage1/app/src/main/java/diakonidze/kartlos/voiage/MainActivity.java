package diakonidze.kartlos.voiage; // v 25-07-2015

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import diakonidze.kartlos.voiage.adapters.StatementListPagesAdapter;
import diakonidze.kartlos.voiage.datebase.DBmanager;
import diakonidze.kartlos.voiage.forTabs.SlidingTabLayout;
import diakonidze.kartlos.voiage.models.CarBrend;
import diakonidze.kartlos.voiage.models.CarModel;
import diakonidze.kartlos.voiage.models.DriverStatement;
import diakonidze.kartlos.voiage.utils.Constantebi;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;
    public final Context mainctx = this;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private ViewPager pager = null;
    SlidingTabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

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
                        Toast.makeText(getApplicationContext(),"filtri",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getApplicationContext(),"exit",Toast.LENGTH_SHORT).show();
                        return true;
                }

                return false;
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(getApplicationContext(),"daiketa",Toast.LENGTH_SHORT).show();
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Toast.makeText(getApplicationContext(),"gaigo",Toast.LENGTH_SHORT).show();
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
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


        // meniu gancxadebis monishvnaze (didi xnit dacheraze)


//        Constantebi.brendList.add(new CarBrend(1, "mers"));
//        Constantebi.brendList.add(new CarBrend(2, "toyota"));
//        Constantebi.brendList.add(new CarBrend(3, "BMW"));
//        Constantebi.modelList.add(new CarModel(1, 1, "cls"));
//        Constantebi.modelList.add(new CarModel(2, 1, "E class"));
//        Constantebi.modelList.add(new CarModel(3, 2, "prado"));
//        Constantebi.modelList.add(new CarModel(4, 2, "camry"));
//        Constantebi.modelList.add(new CarModel(5, 3, "730i"));

        LoadVehicles();

    }

    private void LoadVehicles() {

//        http://back.meet.ge/get.php?type=mark
//        http://back.meet.ge/get.php?type=model

        DBmanager.initialaize(this);
        DBmanager.openReadable();
        Constantebi.brendList = DBmanager.getMarkaList();
        Constantebi.modelList = DBmanager.getModelList();
        DBmanager.close();

        if(Constantebi.brendList.size() == 0 ) {

            RequestQueue queue = Volley.newRequestQueue(this);

            String url = "http://back.meet.ge/get.php?type=mark";
            JsonArrayRequest requestMarka = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {

                            if (jsonArray.length() > 0) {
                                Constantebi.brendList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        CarBrend carBrend = new CarBrend(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getString("name"));
                                        Constantebi.brendList.add(carBrend);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            progress.dismiss();

                            if (Constantebi.brendList.size() > 0) {           // bazashi shebivaxot
                                DBmanager.initialaize(getApplication());
                                DBmanager.openWritable();
                                for (int i = 0 ; i< Constantebi.brendList.size(); i++){
                                    DBmanager.insertToMarka(Constantebi.brendList.get(i));
                                }
                                DBmanager.close();
                            }
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

            url = "http://back.meet.ge/get.php?type=model";
            JsonArrayRequest requestModel = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray jsonArray) {

                            if (jsonArray.length() > 0) {
                                Constantebi.modelList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    try {
                                        CarModel carModel = new CarModel(jsonArray.getJSONObject(i).getInt("id"), jsonArray.getJSONObject(i).getInt("id_mark"), jsonArray.getJSONObject(i).getString("name"));
                                        Constantebi.modelList.add(carModel);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            progress.dismiss();

                            if (Constantebi.modelList.size() > 0) {           // bazashi shebivaxot
                                DBmanager.initialaize(getApplication());
                                DBmanager.openWritable();
                                for (int i = 0 ; i< Constantebi.modelList.size(); i++){
                                    DBmanager.insertToModel(Constantebi.modelList.get(i));
                                }
                                DBmanager.close();
                            }
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

            progress = ProgressDialog.show(this, "ჩამოტვირთვა", "გთხოვთ დაიცადოთ");
            queue.add(requestMarka);
            queue.add(requestModel);

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
        if(id == R.id.add_statement){

            Intent intent = new Intent(getApplicationContext(), AddStatement.class);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
