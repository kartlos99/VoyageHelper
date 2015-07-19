package diakonidze.kartlos.voiage;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;

import diakonidze.kartlos.voiage.models.DriverStatement;


public class DetailPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        DriverStatement driverStatement = (DriverStatement) getIntent().getSerializableExtra("driver_st");

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

        return super.onOptionsItemSelected(item);
    }
}
