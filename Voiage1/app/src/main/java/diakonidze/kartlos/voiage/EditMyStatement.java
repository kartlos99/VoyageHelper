package diakonidze.kartlos.voiage;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import diakonidze.kartlos.voiage.fragments.AddDriverStatementF;
import diakonidze.kartlos.voiage.models.Constantebi;
import diakonidze.kartlos.voiage.models.DriverStatement;
import diakonidze.kartlos.voiage.models.PassangerStatement;


public class EditMyStatement extends ActionBarActivity {

    String stType="";
    DriverStatement driverStatement;
    PassangerStatement passangerStatement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_my_statement);

        android.support.v4.app.Fragment fragment = null;

        if(savedInstanceState != null){
            // recreate
            stType = savedInstanceState.getString("type");

        }else {
            // pirveladi chartva
            stType = getIntent().getStringExtra("type");
            if(stType.equals(Constantebi.STAT_TYPE_DRIVER)){
                driverStatement = (DriverStatement) getIntent().getSerializableExtra("driver_st");
                Bundle bundle = new Bundle();
                bundle.putString("action", Constantebi.REASON_EDIT);
                bundle.putSerializable("statement",driverStatement);
                fragment = new AddDriverStatementF();
                fragment.setArguments(bundle);

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.replace(R.id.conteiner, fragment, "driverFR");
                ft.commit();
            }
        }




        if(stType.equals("passanger")){
            // meore
        }



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("type", stType);
        if(stType.equals(Constantebi.STAT_TYPE_DRIVER)) {
            outState.putSerializable("statement", driverStatement);
        }
        if(stType.equals(Constantebi.STAT_TYPE_PASSANGER)) {
            outState.putSerializable("statement", passangerStatement);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_my_statement, menu);
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
