package diakonidze.kartlos.voiage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.models.Statement1;

/**
 * Created by k.diakonidze on 7/15/2015.
 */
public class StatementAdapter1 extends BaseAdapter {

    private ArrayList<Statement1> data;
    private LayoutInflater inflater;
    private Context context;

    public StatementAdapter1(Context context, ArrayList<Statement1> data) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemToShow;

        if(convertView == null) {
            itemToShow = inflater.inflate(R.layout.statement1_layout,null);

        }else {
            itemToShow = convertView;
        }



        return itemToShow;
    }
}
