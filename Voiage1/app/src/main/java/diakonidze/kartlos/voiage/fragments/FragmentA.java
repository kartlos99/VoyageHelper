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
import java.util.zip.Inflater;

import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.adapters.StatementAdapter1;
import diakonidze.kartlos.voiage.models.Statement1;

/**
 * Created by k.diakonidze on 7/13/2015.
 */
public class FragmentA extends Fragment {

    private ArrayList<Statement1> statements1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a, container, false);
        ListView listViewStatements1 = (ListView) v.findViewById(R.id.statement_1_list);

        statements1 = getStatementData();

        StatementAdapter1 list1adapter = new StatementAdapter1(getActivity(), statements1);
        listViewStatements1.setAdapter(list1adapter);

        return v;
    }

    private ArrayList<Statement1> getStatementData() {
        ArrayList<Statement1> data = new ArrayList<>();
        Calendar now = Calendar.getInstance();
        for (int i = 0; i < 5; i++)
        {
            Statement1 newStatment = new Statement1(1, 3, 22, now, "tbilisi", "batumi");
            newStatment.setName("gela");
            newStatment.setSurname("ss");
            newStatment.setNumber("577987006");
            data.add(newStatment);
        }
        return data;
    }


}
