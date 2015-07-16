package diakonidze.kartlos.voiage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.adapters.DriverListAdapter;
import diakonidze.kartlos.voiage.models.DriverStatement;

/**
 * Created by k.diakonidze on 7/13/2015.
 */
public class DriverStatatementListFragment extends Fragment {

    private ArrayList<DriverStatement> driverStatements;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a, container, false);
        ListView listViewStatements1 = (ListView) v.findViewById(R.id.statement_1_list);

        driverStatements = getStatementData();

        DriverListAdapter driverListAdapter = new DriverListAdapter(getActivity(), driverStatements);
        listViewStatements1.setAdapter(driverListAdapter);

        return v;
    }

    private ArrayList<DriverStatement> getStatementData() {
        ArrayList<DriverStatement> data = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        for (int i = 0; i < 14; i++)
        {
            DriverStatement newStatment = new DriverStatement(1, 3, 12+i, now, "ქუთაისი", "ზესტაფონი");
            newStatment.setName("მალზახ");
            newStatment.setSurname("აბდუშელაშვილი");
            newStatment.setNumber("577987006");
            data.add(newStatment);
        }
        return data;
    }


}
