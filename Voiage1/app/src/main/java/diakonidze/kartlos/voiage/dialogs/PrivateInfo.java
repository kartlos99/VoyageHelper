package diakonidze.kartlos.voiage.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.utils.Constantebi;

/**
 * Created by k.diakonidze on 8/3/2015.
 */
public class PrivateInfo extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.private_info_dialog, container, false);

        Button button = (Button) view.findViewById(R.id.okMobile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText text = (EditText) view.findViewById(R.id.edit_mobile);
                Constantebi.MY_MOBILE = text.getText().toString();
            }
        });


        return view;
    }



}
