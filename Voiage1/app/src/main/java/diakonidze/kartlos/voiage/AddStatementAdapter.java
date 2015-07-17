package diakonidze.kartlos.voiage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import diakonidze.kartlos.voiage.fragments.AddDriverStatementF;
import diakonidze.kartlos.voiage.fragments.AddPassengetStatementF;

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
        if(position == 0){
            fragment = new AddDriverStatementF();
        }
        if(position == 1){
            fragment = new AddPassengetStatementF();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
