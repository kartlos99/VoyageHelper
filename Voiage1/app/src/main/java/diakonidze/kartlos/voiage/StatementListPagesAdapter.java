package diakonidze.kartlos.voiage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import diakonidze.kartlos.voiage.fragments.DriverStatatementListFragment;
import diakonidze.kartlos.voiage.fragments.PassengerStatatementListFragment;

/**
 * Created by k.diakonidze on 7/14/2015.
 */
public class StatementListPagesAdapter extends FragmentPagerAdapter {

    private String location="";

    public StatementListPagesAdapter(FragmentManager fm, String location) {
        super(fm);
        this.location = location;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("location",location);

        Fragment fragment = null;
        if(position == 0){
            fragment = new DriverStatatementListFragment();
            fragment.setArguments(bundle);
        }
        if(position == 1){
            fragment = new PassengerStatatementListFragment();
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
        if(position == 0){
            return "წავიყვან";
        }else{
            return "წამიყვანეთ";
        }
    }
}
