package diakonidze.kartlos.voiage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


public class AddStatement extends ActionBarActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_statement);

        viewPager = (ViewPager) findViewById(R.id.add_statement_pager);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        AddStatementAdapter addStatementAdapter = new AddStatementAdapter(fragmentManager);
        viewPager.setAdapter(addStatementAdapter);


//
//        driverDonebtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//

//            }
//        });


//        Spinner spinner = (Spinner) findViewById(R.id.spiner1);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cityes, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                TextView v = (TextView) view;
//                TextView textView = (TextView) findViewById(R.id.textView);
//                textView.setText(v.getText()+" "+position);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                TextView textView = (TextView) findViewById(R.id.textView);
//                textView.setText("rame airchie");
//            }
//        });
//
//
//        spinner.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_statement, menu);
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
