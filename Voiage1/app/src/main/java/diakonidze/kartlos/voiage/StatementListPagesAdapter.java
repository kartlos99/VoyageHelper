package diakonidze.kartlos.voiage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import diakonidze.kartlos.voiage.fragments.DriverStatatementListFragment;
import diakonidze.kartlos.voiage.fragments.PassengerStatatementListFragment;

/**
 * Created by k.diakonidze on 7/14/2015.
 */
public class StatementListPagesAdapter extends FragmentPagerAdapter {
    public StatementListPagesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if(position == 0){
            fragment = new DriverStatatementListFragment();
        }
        if(position == 1){
            fragment = new PassengerStatatementListFragment();
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
