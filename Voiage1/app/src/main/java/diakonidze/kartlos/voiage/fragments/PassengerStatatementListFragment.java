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
import diakonidze.kartlos.voiage.adapters.PassangerListAdapter;
import diakonidze.kartlos.voiage.models.PassangerStatement;

/**
 * Created by k.diakonidze on 7/13/2015.
 */
public class PassengerStatatementListFragment extends Fragment {

    private ArrayList<PassangerStatement> passangerStatements;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b, container,false);
        ListView listView = (ListView) v.findViewById(R.id.statement_2_list);
        passangerStatements = getStatementData();

        PassangerListAdapter passangerListAdapter = new PassangerListAdapter(getActivity(),passangerStatements);
        listView.setAdapter(passangerListAdapter);


        return v;
    }


    private ArrayList<PassangerStatement> getStatementData() {
        ArrayList<PassangerStatement> data = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        for (int i = 0; i < 14; i++)
        {
            PassangerStatement newStatment = new PassangerStatement(1, 1, 10, "ბორჯომი", "ბაკურიანი", Calendar.getInstance());
            newStatment.setName("მარგარიტა");
            newStatment.setSurname("აბდუშელაშვილი");
            newStatment.setNumber("577987__6");
            data.add(newStatment);
        }
        return data;
    }
}
