package diakonidze.kartlos.voiage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import diakonidze.kartlos.voiage.R;

/**
 * Created by k.diakonidze on 7/17/2015.
 */
public class AddDriverStatementF extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_driver_statement_layout, container, false);
        Spinner cityFrom = (Spinner) view.findViewById(R.id.driver_cityfrom);
        Spinner cityTo = (Spinner) view.findViewById(R.id.driver_cityto);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cityes, android.R.layout.simple_spinner_item);
        cityFrom.setAdapter(adapter);

        CheckBox pirobebi = (CheckBox) view.findViewById(R.id.driver_comfort_checkBox);
        final LinearLayout comfort1 = (LinearLayout) view.findViewById(R.id.comfort1);
        final LinearLayout comfort2 = (LinearLayout) view.findViewById(R.id.comfort2);


        pirobebi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    comfort1.setVisibility(View.VISIBLE);
                    comfort2.setVisibility(View.VISIBLE);
                }else{
                    comfort1.setVisibility(View.GONE);
                    comfort2.setVisibility(View.GONE);
                }
            }
        });


        return view;
    }
}
