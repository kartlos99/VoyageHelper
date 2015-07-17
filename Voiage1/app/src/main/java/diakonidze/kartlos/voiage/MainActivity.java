package diakonidze.kartlos.voiage; //1.0.2

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    ViewPager pager = null;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = (ViewPager) findViewById(R.id.pageView);
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            StatementListPagesAdapter myadapter = new StatementListPagesAdapter(fragmentManager);
        pager.setAdapter(myadapter);

//http://stackoverflow.com/questions/19238738/android-shape-with-bottom-stroke

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
