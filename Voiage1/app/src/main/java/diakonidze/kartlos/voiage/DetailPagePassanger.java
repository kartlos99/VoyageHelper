package diakonidze.kartlos.voiage;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import diakonidze.kartlos.voiage.adapters.RecyclerAdapter;


public class DetailPagePassanger extends ActionBarActivity {
    CollapsingToolbarLayout collapsingToolbar;
    int mutedColor = R.attr.colorPrimary;
    RecyclerAdapter simpleRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout_passanger);


        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("წამიყვანეთ");
        ImageView header = (ImageView) findViewById(R.id.header);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.scrollableview);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);

        if (simpleRecyclerAdapter == null) {
            simpleRecyclerAdapter = new RecyclerAdapter(this);
            recyclerView.setAdapter(simpleRecyclerAdapter);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_page_passanger, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
