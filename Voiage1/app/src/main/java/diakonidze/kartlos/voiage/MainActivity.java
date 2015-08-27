package diakonidze.kartlos.voiage; // v 25-07-2015

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        getCityPath();
                        break;

                    case R.id.manuPrivit:

                        PrivateInfo infoDialog = new PrivateInfo();
                        infoDialog.show(manager, "info");

                        Toast.makeText(getApplicationContext(), "info", Toast.LENGTH_SHORT).show();
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


    void getCityPath() {

        ArrayList<CityConnection> cityConnectionList = new ArrayList<>();

        cityConnectionList.add(new CityConnection("ბათუმი","ქობულეთი",38,29800));
        cityConnectionList.add(new CityConnection("ურეკი","ფოთი",23,23000));
        cityConnectionList.add(new CityConnection("ურეკი","ოზურგეთი",31,29000));
        cityConnectionList.add(new CityConnection("ურეკი","ლანჩხუთი",25,26700));
        cityConnectionList.add(new CityConnection("ქობულეთი","ურეკი",20,21400));
        cityConnectionList.add(new CityConnection("ქობულეთი","ოზურგეთი",34,26400));
        cityConnectionList.add(new CityConnection("ფოთი","ზუგდიდი",65,60000));
        cityConnectionList.add(new CityConnection("ფოთი","სენაკი",49,42300));
        cityConnectionList.add(new CityConnection("ფოთი","ლანჩხუთი",43,43900));
        cityConnectionList.add(new CityConnection("ფოთი","ოზურგეთი",53,50900));
        cityConnectionList.add(new CityConnection("ზუგდიდი","გალი",18,20900));
        cityConnectionList.add(new CityConnection("ზუგდიდი","ხაიში",61,68800));
        cityConnectionList.add(new CityConnection("ზუგდიდი","წალენჯიხა",29,25300));
        cityConnectionList.add(new CityConnection("ზუგდიდი","ჩხოროწყუ",32,29100));
        cityConnectionList.add(new CityConnection("ზუგდიდი","სენაკი",43,46700));
        cityConnectionList.add(new CityConnection("გალი","სოხუმი",75,81200));
        cityConnectionList.add(new CityConnection("სოხუმი","გაგრა",84,82500));
        cityConnectionList.add(new CityConnection("ხაიში","მესტია",53,60000));
        cityConnectionList.add(new CityConnection("ხაიში","წალენჯიხა",56,56700));
        cityConnectionList.add(new CityConnection("წალენჯიხა","ჩხოროწყუ",33,17300));
        cityConnectionList.add(new CityConnection("ჩხოროწყუ","სენაკი",35,35400));
        cityConnectionList.add(new CityConnection("ჩხოროწყუ","მარტვილი",42,42400));
        cityConnectionList.add(new CityConnection("სენაკი","მარტვილი",36,38500));
        cityConnectionList.add(new CityConnection("სენაკი","ხონი",36,41300));
        cityConnectionList.add(new CityConnection("სენაკი","სამტრედია",25,29200));
        cityConnectionList.add(new CityConnection("მარტვილი","ხონი",19,18900));
        cityConnectionList.add(new CityConnection("მარტვილი","სამტრედია",43,46800));
        cityConnectionList.add(new CityConnection("სამტრედია","ხონი",21,20500));
        cityConnectionList.add(new CityConnection("სამტრედია","ქუთაისი",33,34900));
        cityConnectionList.add(new CityConnection("სამტრედია","საირმე",87,77500));
        cityConnectionList.add(new CityConnection("სამტრედია","ჩოხატაური",28,28200));
        cityConnectionList.add(new CityConnection("სამტრედია","ლანჩხუთი",31,32200));
        cityConnectionList.add(new CityConnection("ჩოხატაური","ბახმარო",58,51200));
        cityConnectionList.add(new CityConnection("ჩოხატაური","ოზურგეთი",33,29000));
        cityConnectionList.add(new CityConnection("ჩოხატაური","ლანჩხუთი",45,36100));
        cityConnectionList.add(new CityConnection("საირმე","ქუთაისი",67,50700));
        cityConnectionList.add(new CityConnection("საირმე","ზესტაფონი",88,72000));
        cityConnectionList.add(new CityConnection("ქუთაისი","ხონი",34,32200));
        cityConnectionList.add(new CityConnection("ქუთაისი","ცაგერი",67,71900));
        cityConnectionList.add(new CityConnection("ქუთაისი","ტყიბული",49,40500));
        cityConnectionList.add(new CityConnection("ქუთაისი","ზესტაფონი",36,39900));
        cityConnectionList.add(new CityConnection("ცაგერი","ლენტეხი",21,22100));
        cityConnectionList.add(new CityConnection("ცაგერი","ამბროლაური",59,55200));
        cityConnectionList.add(new CityConnection("ამბროლაური","ონი",26,29400));
        cityConnectionList.add(new CityConnection("ამბროლაური","ტყიბული",34,39400));
        cityConnectionList.add(new CityConnection("ონი","შოვი",30,29000));
        cityConnectionList.add(new CityConnection("ტყიბული","ზესტაფონი",45,37100));
        cityConnectionList.add(new CityConnection("ზესტაფონი","ჭიათურა",37,38100));
        cityConnectionList.add(new CityConnection("ზესტაფონი","ხაშური",61,66300));
        cityConnectionList.add(new CityConnection("ზესტაფონი","სამტრედია",56,66900));
        cityConnectionList.add(new CityConnection("ჭიათურა","საჩხერე",12,13900));
        cityConnectionList.add(new CityConnection("საჩხერე","გომი",50,55800));
        cityConnectionList.add(new CityConnection("ხაშური","გომი",14,13200));
        cityConnectionList.add(new CityConnection("ხაშური","ბორჯომი",31,30000));
        cityConnectionList.add(new CityConnection("ბორჯომი","ბაკურიანი",39,30800));
        cityConnectionList.add(new CityConnection("ბორჯომი","ახალციხე",45,48500));
        cityConnectionList.add(new CityConnection("ახალციხე","აბასთუმანი",25,22100));
        cityConnectionList.add(new CityConnection("ახალციხე","ზარზმა",42,35000));
        cityConnectionList.add(new CityConnection("ახალციხე","ახალქალაქი",59,71400));
        cityConnectionList.add(new CityConnection("ზარზმა","აბასთუმანი",35,23600));
        cityConnectionList.add(new CityConnection("ზარზმა","ხულო",110,48500));
        cityConnectionList.add(new CityConnection("ხულო","შუახევი",25,16800));
        cityConnectionList.add(new CityConnection("შუახევი","ქედა",47,28600));
        cityConnectionList.add(new CityConnection("ქედა","ბათუმი",45,38300));
        cityConnectionList.add(new CityConnection("გომი","ცხინვალი",41,41700));
        cityConnectionList.add(new CityConnection("გომი","თბილისი",89,117000));
        cityConnectionList.add(new CityConnection("გომი","გორი",32,35500));
        cityConnectionList.add(new CityConnection("გორი","ცხინვალი",34,35000));
        cityConnectionList.add(new CityConnection("გორი","თბილისი",71,87200));
        cityConnectionList.add(new CityConnection("გორი","მცხეთა",66,72400));
        cityConnectionList.add(new CityConnection("თბილისი","ცხინვალი",92,115000));
        cityConnectionList.add(new CityConnection("თბილისი","ანანური",66,67500));
        cityConnectionList.add(new CityConnection("თბილისი","შატილი",190,149000));
        cityConnectionList.add(new CityConnection("თბილისი","თიანეთი",76,80500));
        cityConnectionList.add(new CityConnection("თბილისი","თელავი",100,95000));
        cityConnectionList.add(new CityConnection("თბილისი","საგარეჯო",51,50500));
        cityConnectionList.add(new CityConnection("თბილისი","რუსთავი",39,30000));
        cityConnectionList.add(new CityConnection("თბილისი","მარნეული",45,42700));
        cityConnectionList.add(new CityConnection("თბილისი","მანგლისი",65,54600));
        cityConnectionList.add(new CityConnection("თბილისი","მცხეთა",25,21500));
        cityConnectionList.add(new CityConnection("ანანური","გუდაური",56,54600));
        cityConnectionList.add(new CityConnection("გუდაური","სტეფანწმინდა",31,32300));
        cityConnectionList.add(new CityConnection("ანანური","შატილი",148,105000));
        cityConnectionList.add(new CityConnection("ანანური","თიანეთი",34,36500));
        cityConnectionList.add(new CityConnection("შატილი","თიანეთი",151,112000));
        cityConnectionList.add(new CityConnection("თიანეთი","ახმეტა",37,26500));
        cityConnectionList.add(new CityConnection("ახმეტა","ომალო",125,91200));
        cityConnectionList.add(new CityConnection("ახმეტა","თელავი",31,28000));
        cityConnectionList.add(new CityConnection("თელავი","ყვარელი",34,39000));
        cityConnectionList.add(new CityConnection("თელავი","გურჯაანი",37,36700));
        cityConnectionList.add(new CityConnection("თელავი","საგარეჯო",84,79000));
        cityConnectionList.add(new CityConnection("თელავი","რუსთავი",97,93800));
        cityConnectionList.add(new CityConnection("ყვარელი","ლაგოდეხი",44,45400));
        cityConnectionList.add(new CityConnection("ყვარელი","გურჯაანი",28,28600));
        cityConnectionList.add(new CityConnection("ლაგოდეხი","დედოფლისწყარო",55,66600));
        cityConnectionList.add(new CityConnection("ლაგოდეხი","სიღნაღი",42,46900));
        cityConnectionList.add(new CityConnection("ლაგოდეხი","საგარეჯო",90,107000));
        cityConnectionList.add(new CityConnection("ლაგოდეხი","გურჯაანი",51,47700));
        cityConnectionList.add(new CityConnection("დედოფლისწყარო","გურჯაანი",52,52600));
        cityConnectionList.add(new CityConnection("დედოფლისწყარო","სიღნაღი",33,34700));
        cityConnectionList.add(new CityConnection("დედოფლისწყარო","საგარეჯო",72,85000));
        cityConnectionList.add(new CityConnection("სიღნაღი","გურჯაანი",27,21000));
        cityConnectionList.add(new CityConnection("სიღნაღი","საგარეჯო",48,60500));
        cityConnectionList.add(new CityConnection("საგარეჯო","გურჯაანი",52,63700));
        cityConnectionList.add(new CityConnection("საგარეჯო","რუსთავი",48,49300));
        cityConnectionList.add(new CityConnection("რუსთავი","გარდაბანი",14,12400));
        cityConnectionList.add(new CityConnection("რუსთავი","მარნეული",58,54300));
        cityConnectionList.add(new CityConnection("რუსთავი","მანგლისი",93,90600));
        cityConnectionList.add(new CityConnection("მარნეული","ბოლნისი",25,23200));
        cityConnectionList.add(new CityConnection("მარნეული","წალკა",77,81500));
        cityConnectionList.add(new CityConnection("მარნეული","მანგლისი",61,61000));
        cityConnectionList.add(new CityConnection("წალკა","მანგლისი",33,37900));
        cityConnectionList.add(new CityConnection("წალკა","ნინოწმინდა",61,73200));
        cityConnectionList.add(new CityConnection("ნინოწმინდა","ახალქალაქი",17,18700));
        cityConnectionList.add(new CityConnection("ოზურგეთი","ლანჩხუთი",38,33500));
        cityConnectionList.add(new CityConnection("ქობულეთი","ბათუმი",38,29800));
        cityConnectionList.add(new CityConnection("ფოთი","ურეკი",23,23000));
        cityConnectionList.add(new CityConnection("ოზურგეთი","ურეკი",31,29000));
        cityConnectionList.add(new CityConnection("ლანჩხუთი","ურეკი",25,26700));
        cityConnectionList.add(new CityConnection("ურეკი","ქობულეთი",20,21400));
        cityConnectionList.add(new CityConnection("ოზურგეთი","ქობულეთი",34,26400));
        cityConnectionList.add(new CityConnection("ზუგდიდი","ფოთი",65,60000));
        cityConnectionList.add(new CityConnection("სენაკი","ფოთი",49,42300));
        cityConnectionList.add(new CityConnection("ლანჩხუთი","ფოთი",43,43900));
        cityConnectionList.add(new CityConnection("ოზურგეთი","ფოთი",53,50900));
        cityConnectionList.add(new CityConnection("გალი","ზუგდიდი",18,20900));
        cityConnectionList.add(new CityConnection("ხაიში","ზუგდიდი",61,68800));
        cityConnectionList.add(new CityConnection("წალენჯიხა","ზუგდიდი",29,25300));
        cityConnectionList.add(new CityConnection("ჩხოროწყუ","ზუგდიდი",32,29100));
        cityConnectionList.add(new CityConnection("სენაკი","ზუგდიდი",43,46700));
        cityConnectionList.add(new CityConnection("სოხუმი","გალი",75,81200));
        cityConnectionList.add(new CityConnection("გაგრა","სოხუმი",84,82500));
        cityConnectionList.add(new CityConnection("მესტია","ხაიში",53,60000));
        cityConnectionList.add(new CityConnection("წალენჯიხა","ხაიში",56,56700));
        cityConnectionList.add(new CityConnection("ჩხოროწყუ","წალენჯიხა",33,17300));
        cityConnectionList.add(new CityConnection("სენაკი","ჩხოროწყუ",35,35400));
        cityConnectionList.add(new CityConnection("მარტვილი","ჩხოროწყუ",42,42400));
        cityConnectionList.add(new CityConnection("მარტვილი","სენაკი",36,38500));
        cityConnectionList.add(new CityConnection("ხონი","სენაკი",36,41300));
        cityConnectionList.add(new CityConnection("სამტრედია","სენაკი",25,29200));
        cityConnectionList.add(new CityConnection("ხონი","მარტვილი",19,18900));
        cityConnectionList.add(new CityConnection("სამტრედია","მარტვილი",43,46800));
        cityConnectionList.add(new CityConnection("ხონი","სამტრედია",21,20500));
        cityConnectionList.add(new CityConnection("ქუთაისი","სამტრედია",33,34900));
        cityConnectionList.add(new CityConnection("საირმე","სამტრედია",87,77500));
        cityConnectionList.add(new CityConnection("ჩოხატაური","სამტრედია",28,28200));
        cityConnectionList.add(new CityConnection("ლანჩხუთი","სამტრედია",31,32200));
        cityConnectionList.add(new CityConnection("ბახმარო","ჩოხატაური",58,51200));
        cityConnectionList.add(new CityConnection("ოზურგეთი","ჩოხატაური",33,29000));
        cityConnectionList.add(new CityConnection("ლანჩხუთი","ჩოხატაური",45,36100));
        cityConnectionList.add(new CityConnection("ქუთაისი","საირმე",67,50700));
        cityConnectionList.add(new CityConnection("ზესტაფონი","საირმე",88,72000));
        cityConnectionList.add(new CityConnection("ხონი","ქუთაისი",34,32200));
        cityConnectionList.add(new CityConnection("ცაგერი","ქუთაისი",67,71900));
        cityConnectionList.add(new CityConnection("ტყიბული","ქუთაისი",49,40500));
        cityConnectionList.add(new CityConnection("ზესტაფონი","ქუთაისი",36,39900));
        cityConnectionList.add(new CityConnection("ლენტეხი","ცაგერი",21,22100));
        cityConnectionList.add(new CityConnection("ამბროლაური","ცაგერი",59,55200));
        cityConnectionList.add(new CityConnection("ონი","ამბროლაური",26,29400));
        cityConnectionList.add(new CityConnection("ტყიბული","ამბროლაური",34,39400));
        cityConnectionList.add(new CityConnection("შოვი","ონი",30,29000));
        cityConnectionList.add(new CityConnection("ზესტაფონი","ტყიბული",45,37100));
        cityConnectionList.add(new CityConnection("ჭიათურა","ზესტაფონი",37,38100));
        cityConnectionList.add(new CityConnection("ხაშური","ზესტაფონი",61,66300));
        cityConnectionList.add(new CityConnection("სამტრედია","ზესტაფონი",56,66900));
        cityConnectionList.add(new CityConnection("საჩხერე","ჭიათურა",12,13900));
        cityConnectionList.add(new CityConnection("გომი","საჩხერე",50,55800));
        cityConnectionList.add(new CityConnection("გომი","ხაშური",14,13200));
        cityConnectionList.add(new CityConnection("ბორჯომი","ხაშური",31,30000));
        cityConnectionList.add(new CityConnection("ბაკურიანი","ბორჯომი",39,30800));
        cityConnectionList.add(new CityConnection("ახალციხე","ბორჯომი",45,48500));
        cityConnectionList.add(new CityConnection("აბასთუმანი","ახალციხე",25,22100));
        cityConnectionList.add(new CityConnection("ზარზმა","ახალციხე",42,35000));
        cityConnectionList.add(new CityConnection("ახალქალაქი","ახალციხე",59,71400));
        cityConnectionList.add(new CityConnection("აბასთუმანი","ზარზმა",35,23600));
        cityConnectionList.add(new CityConnection("ხულო","ზარზმა",110,48500));
        cityConnectionList.add(new CityConnection("შუახევი","ხულო",25,16800));
        cityConnectionList.add(new CityConnection("ქედა","შუახევი",47,28600));
        cityConnectionList.add(new CityConnection("ბათუმი","ქედა",45,38300));
        cityConnectionList.add(new CityConnection("ცხინვალი","გომი",41,41700));
        cityConnectionList.add(new CityConnection("თბილისი","გომი",89,117000));
        cityConnectionList.add(new CityConnection("გორი","გომი",32,35500));
        cityConnectionList.add(new CityConnection("ცხინვალი","გორი",34,35000));
        cityConnectionList.add(new CityConnection("თბილისი","გორი",71,87200));
        cityConnectionList.add(new CityConnection("მცხეთა","გორი",66,72400));
        cityConnectionList.add(new CityConnection("ცხინვალი","თბილისი",92,115000));
        cityConnectionList.add(new CityConnection("ანანური","თბილისი",66,67500));
        cityConnectionList.add(new CityConnection("შატილი","თბილისი",190,149000));
        cityConnectionList.add(new CityConnection("თიანეთი","თბილისი",76,80500));
        cityConnectionList.add(new CityConnection("თელავი","თბილისი",100,95000));
        cityConnectionList.add(new CityConnection("საგარეჯო","თბილისი",51,50500));
        cityConnectionList.add(new CityConnection("რუსთავი","თბილისი",39,30000));
        cityConnectionList.add(new CityConnection("მარნეული","თბილისი",45,42700));
        cityConnectionList.add(new CityConnection("მანგლისი","თბილისი",65,54600));
        cityConnectionList.add(new CityConnection("მცხეთა","თბილისი",25,21500));
        cityConnectionList.add(new CityConnection("გუდაური","ანანური",56,54600));
        cityConnectionList.add(new CityConnection("სტეფანწმინდა","გუდაური",31,32300));
        cityConnectionList.add(new CityConnection("შატილი","ანანური",148,105000));
        cityConnectionList.add(new CityConnection("თიანეთი","ანანური",34,36500));
        cityConnectionList.add(new CityConnection("თიანეთი","შატილი",151,112000));
        cityConnectionList.add(new CityConnection("ახმეტა","თიანეთი",37,26500));
        cityConnectionList.add(new CityConnection("ომალო","ახმეტა",125,91200));
        cityConnectionList.add(new CityConnection("თელავი","ახმეტა",31,28000));
        cityConnectionList.add(new CityConnection("ყვარელი","თელავი",34,39000));
        cityConnectionList.add(new CityConnection("გურჯაანი","თელავი",37,36700));
        cityConnectionList.add(new CityConnection("საგარეჯო","თელავი",84,79000));
        cityConnectionList.add(new CityConnection("რუსთავი","თელავი",97,93800));
        cityConnectionList.add(new CityConnection("ლაგოდეხი","ყვარელი",44,45400));
        cityConnectionList.add(new CityConnection("გურჯაანი","ყვარელი",28,28600));
        cityConnectionList.add(new CityConnection("დედოფლისწყარო","ლაგოდეხი",55,66600));
        cityConnectionList.add(new CityConnection("სიღნაღი","ლაგოდეხი",42,46900));
        cityConnectionList.add(new CityConnection("საგარეჯო","ლაგოდეხი",90,107000));
        cityConnectionList.add(new CityConnection("გურჯაანი","ლაგოდეხი",51,47700));
        cityConnectionList.add(new CityConnection("გურჯაანი","დედოფლისწყარო",52,52600));
        cityConnectionList.add(new CityConnection("სიღნაღი","დედოფლისწყარო",33,34700));
        cityConnectionList.add(new CityConnection("საგარეჯო","დედოფლისწყარო",72,85000));
        cityConnectionList.add(new CityConnection("გურჯაანი","სიღნაღი",27,21000));
        cityConnectionList.add(new CityConnection("საგარეჯო","სიღნაღი",48,60500));
        cityConnectionList.add(new CityConnection("გურჯაანი","საგარეჯო",52,63700));
        cityConnectionList.add(new CityConnection("რუსთავი","საგარეჯო",48,49300));
        cityConnectionList.add(new CityConnection("გარდაბანი","რუსთავი",14,12400));
        cityConnectionList.add(new CityConnection("მარნეული","რუსთავი",58,54300));
        cityConnectionList.add(new CityConnection("მანგლისი","რუსთავი",93,90600));
        cityConnectionList.add(new CityConnection("ბოლნისი","მარნეული",25,23200));
        cityConnectionList.add(new CityConnection("წალკა","მარნეული",77,81500));
        cityConnectionList.add(new CityConnection("მანგლისი","მარნეული",61,61000));
        cityConnectionList.add(new CityConnection("მანგლისი","წალკა",33,37900));
        cityConnectionList.add(new CityConnection("ნინოწმინდა","წალკა",61,73200));
        cityConnectionList.add(new CityConnection("ახალქალაქი","ნინოწმინდა",17,18700));
        cityConnectionList.add(new CityConnection("ლანჩხუთი","ოზურგეთი",38,33500));

        HashMap<String, ArrayList<CityObj>> cityMap = new HashMap<>();

        for (int i = 0; i < Constantebi.cityList.size(); i++) {
            cityMap.put(Constantebi.cityList.get(i).getNameGE(), new ArrayList<CityObj>());
        }

        for (int i = 0; i < cityConnectionList.size(); i++) {
            cityMap.get(cityConnectionList.get(i).getCityA())
                    .add(new CityObj(
                            cityConnectionList.get(i).getCityB()
                            , ""
                            , cityConnectionList.get(i).getDistance()
                            , cityConnectionList.get(i).getTime()));
        }

        ArrayList<CityObj> visiBleCities1 = new ArrayList<>();
        ArrayList<CityObj> visiTedCities1 = new ArrayList<>();
        ArrayList<String> visiBleCitiesNames = new ArrayList<>();
        ArrayList<String> visiTedCitiesNames = new ArrayList<>();

        HashMap<String, CityObj> visiBleCities = new HashMap<>();
        HashMap<String, CityObj> visiTedCities = new HashMap<>();

        CityObj startCity = new CityObj("ქუთაისი", "", 0, 0);
        CityObj finishCity = new CityObj("თბილისი", "", 0, 0);

        CityObj currCity = startCity;
        visiTedCities.put(currCity.getName(), currCity);
        visiTedCitiesNames.add(currCity.getName());
        Boolean finishFounded = false;

        do {

            for (int i = 0; i < cityMap.get(currCity.getName()).size(); i++) {
                // mimdinare qalaqistvis avigot yvela mezobeli qalaqi da vamatebt xilvadi qalaqebis siashi

                if (visiBleCities.containsKey(cityMap.get(currCity.getName()).get(i).getName())) {
                    // tu mimdinare qalaqis i-uri mezobeli ukve aris xilvadobis areshi
                    // mashin gadavamowmot mandzili am qalaqamde da tu uketesia shevcvalot

                    if (visiBleCities.get(cityMap.get(currCity.getName()).get(i).getName()).getDistance() > currCity.getDistance() + cityMap.get(currCity.getName()).get(i).getDistance()) {
                        visiBleCities.get(cityMap.get(currCity.getName()).get(i).getName()).setDistance(currCity.getDistance() + cityMap.get(currCity.getName()).get(i).getDistance());
                        visiBleCities.get(cityMap.get(currCity.getName()).get(i).getName()).setPrevCity(currCity.getName());
                    }

                } else {
                    if (!visiTedCities.containsKey(cityMap.get(currCity.getName()).get(i).getName())) {
                        // tu mimdinare qalaqis i-uri mezobeli jer ar yofila chven xelshi mashin vamatebt mas
                        // xilvad qalaqebshi (tu ratqmaunda ukve napovni aragvaqvs umoklesi gza am qalaqamde NOT IN visiTed List)

                        visiBleCities.put(cityMap.get(currCity.getName()).get(i).getName(), cityMap.get(currCity.getName()).get(i));
                        visiBleCities.get(cityMap.get(currCity.getName()).get(i).getName()).setPrevCity(currCity.getName());
                        visiBleCities.get(cityMap.get(currCity.getName()).get(i).getName()).setDistance(currCity.getDistance() + cityMap.get(currCity.getName()).get(i).getDistance());

                        visiBleCitiesNames.add(cityMap.get(currCity.getName()).get(i).getName());

                    } else {
                        // ukve napovnia umoklesi gza - arafers ar vaketebt

                    }
                }
            }


            // xilvadi qalaqebidan varchevt ufro axlos romelia, rom gadavidet masze shemdegi iteraciistvis
            int minDistance = 10000000;
            String nextCity ="";
            for (int i = 0; i < visiBleCitiesNames.size(); i++){
                if(visiBleCities.get(visiBleCitiesNames.get(i)).getDistance() < minDistance){
                    minDistance = visiBleCities.get(visiBleCitiesNames.get(i)).getDistance();
                    nextCity = visiBleCitiesNames.get(i);
                }
            }

            currCity = visiBleCities.get(nextCity);
            visiBleCities.remove(nextCity);
            visiBleCitiesNames.remove(nextCity);

            visiTedCities.put(currCity.getName(), currCity);
            visiTedCitiesNames.add(currCity.getName());

            if(currCity.getName().equals(finishCity.getName())){
                finishFounded = true;
            }


        } while (visiBleCities.size() > 0 && !finishFounded);


        String result="";
        String tempCityName = finishCity.getName();


        while (!visiTedCities.get(tempCityName).getPrevCity().equals(startCity.getName())){
            result += visiTedCities.get(tempCityName).getPrevCity();
            result += " - ";

            tempCityName = visiTedCities.get(tempCityName).getPrevCity();
        }

        Toast.makeText(this, result + " km "+ String.valueOf(visiTedCities.get(finishCity.getName()).getDistance()), Toast.LENGTH_LONG).show();

    }

}
