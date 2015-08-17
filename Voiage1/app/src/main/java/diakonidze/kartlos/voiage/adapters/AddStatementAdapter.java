package diakonidze.kartlos.voiage.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import diakonidze.kartlos.voiage.fragments.AddDriverStatementF;
import diakonidze.kartlos.voiage.fragments.AddPassengetStatementF;
import diakonidze.kartlos.voiage.utils.Constantebi;
import diakonidze.kartlos.voiage.models.DriverStatement;
import diakonidze.kartlos.voiage.models.PassangerStatement;

/**
 * Created by k.diakonidze on 7/17/2015.
 */
public class AddStatementAdapter extends FragmentStatePagerAdapter {

    public AddStatementAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString("action", Constantebi.REASON_ADD);

        if (position == 0) {
            DriverStatement statement = new DriverStatement(Constantebi.MY_ID,0,0,"","","");
            bundle.putSerializable("statement", statement);
            fragment = new AddDriverStatementF();

            fragment.setArguments(bundle);
        }
        if (position == 1) {
            PassangerStatement statement = new PassangerStatement(Constantebi.MY_ID,0,0,"","","");
            bundle.putSerializable("statement", statement);
            fragment = new AddPassengetStatementF();
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "ვეძებ მგზავრს";
        } else {
            return "ვეძებ ტრანსპორტს";
        }
    }
}
