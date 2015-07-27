package diakonidze.kartlos.voiage;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import diakonidze.kartlos.voiage.models.DriverStatement;


public class DetailPageDriver extends ActionBarActivity {

    private DriverStatement driverStatement;
    private String whereFrom="";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout_driver);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.GONE);

        whereFrom = getIntent().getStringExtra("from");
        if(whereFrom.equals(MainActivity.MY_OWN_STAT)){
            toolbar.setVisibility(View.VISIBLE);
        }
        driverStatement = (DriverStatement) getIntent().getSerializableExtra("driver_st");

        TextView nameT = (TextView) findViewById(R.id.detiles_name_text);
        TextView cityT = (TextView) findViewById(R.id.detiles_city_text);
        TextView timeT = (TextView) findViewById(R.id.detiles_time_text);
        TextView freespaceT = (TextView) findViewById(R.id.detiles_freespace_text);
        TextView priceT = (TextView) findViewById(R.id.detiles_price_text);
        TextView carT = (TextView) findViewById(R.id.detiles_car_text);
        TextView limitT = (TextView) findViewById(R.id.detiles_limit_text);
        TextView commentT = (TextView) findViewById(R.id.detiles_comment_text);

        nameT.setText(driverStatement.getName()+" "+driverStatement.getSurname());
        cityT.setText(driverStatement.getCityFrom()+" - "+driverStatement.getCityTo());
        timeT.setText(driverStatement.getDate().get(Calendar.YEAR)+"/"+(driverStatement.getDate().get(Calendar.MONTH)+1)+"/"+driverStatement.getDate().get(Calendar.DAY_OF_MONTH)+" "+driverStatement.getTime());
        freespaceT.setText(String.valueOf(driverStatement.getFreeSpace()));
        priceT.setText(String.valueOf(driverStatement.getPrice()));
        carT.setText(driverStatement.getMarka()+" "+driverStatement.getModeli()+" "+driverStatement.getColor());
        limitT.setText(driverStatement.getAgeTo()+"წ. სქესი "+driverStatement.getGender());
        commentT.setText(driverStatement.getComment());



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_page, menu);


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
        if (id == R.id.del_dr_manu) {
            // serverze gaushvebt gancx ID-s wasashlelad
            Toast.makeText(getApplicationContext(),"ar unda waishalos es gancxadeba", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.edit_dr_manu) {
            // aq unda gamovidzaxot gancxadebis Sesavsebi forma, romelic
            // shevsebuli iqneba redaqtirebadi gancxadebis parametrebiT
            // amitom intentshi vatant gancxadebas
            Intent intent = new Intent(getApplicationContext(), AddStatement.class);

            intent.putExtra("driver_st", driverStatement);
            intent.putExtra("reason","edit");
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
