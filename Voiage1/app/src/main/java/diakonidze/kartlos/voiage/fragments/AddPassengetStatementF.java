package diakonidze.kartlos.voiage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import diakonidze.kartlos.voiage.R;

/**
 * Created by k.diakonidze on 7/17/2015.
 */
public class AddPassengetStatementF extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_passanger_statement_layout, container, false);
        return view;
    }
}
