package diakonidze.kartlos.voiage; // v 25-07-2015

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
import android.widget.Toast;

import diakonidze.kartlos.voiage.forTabs.SlidingTabLayout;
import diakonidze.kartlos.voiage.models.Constantebi;


public class MainActivity extends AppCompatActivity {


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
