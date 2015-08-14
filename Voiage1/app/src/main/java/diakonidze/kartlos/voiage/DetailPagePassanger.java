package diakonidze.kartlos.voiage;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        ImageView callerImage = (ImageView) findViewById(R.id.caller);
        callerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:577987006"));
                startActivity(callIntent);
            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("diakonidze kartlos");
        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.ColorPrimary));
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);

        ImageView header = (ImageView) findViewById(R.id.header);


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
