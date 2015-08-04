package diakonidze.kartlos.voiage.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import diakonidze.kartlos.voiage.R;

/**
 * Created by k.diakonidze on 8/4/2015.
 */
public class FilterDialog extends DialogFragment {

    Button filterBtn;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.setTitle("ფილტრი");

        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_layout, container, false);
        filterBtn = (Button) view.findViewById(R.id.filter_Btn);

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // aq vagzavnit servewrze motxovnas chveni kiteriumebiT

                dismiss();
            }
        });


        return view;

    }
}
