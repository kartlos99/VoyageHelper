package diakonidze.kartlos.voiage.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.internal.widget.DialogTitle;
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

    EditText mobileText;
    Button buttonOk;
    Button buttonCancel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog d =  super.onCreateDialog(savedInstanceState);
        d.setTitle("ტელეფონის ნომერი");

        return d;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.private_info_dialog, container, false);

        mobileText = (EditText) view.findViewById(R.id.edit_mobile);
        buttonOk = (Button) view.findViewById(R.id.okMobile);
        buttonCancel = (Button) view.findViewById(R.id.cancelMobile);

        mobileText.setText(Constantebi.MY_MOBILE);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constantebi.MY_MOBILE = mobileText.getText().toString();
                dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        return view;
    }


}
