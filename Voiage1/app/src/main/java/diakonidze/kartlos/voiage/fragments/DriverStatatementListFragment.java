package diakonidze.kartlos.voiage.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import diakonidze.kartlos.voiage.DetailPage;
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
        ListView driverStatementList = (ListView) v.findViewById(R.id.statement_1_list);

        driverStatements = getStatementData();

        DriverListAdapter driverListAdapter = new DriverListAdapter(getActivity(), driverStatements);
        driverStatementList.setAdapter(driverListAdapter);

        driverStatementList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailPage.class);

                DriverStatement currStatement = (DriverStatement) parent.getItemAtPosition(position);
                intent.putExtra("driver_st",currStatement);

                startActivity(intent);

            }
        });

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
