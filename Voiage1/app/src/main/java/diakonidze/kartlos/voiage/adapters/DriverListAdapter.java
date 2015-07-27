package diakonidze.kartlos.voiage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.models.DriverStatement;


/**
 * Created by k.diakonidze on 7/15/2015.
 */
public class DriverListAdapter extends BaseAdapter {

    private ArrayList<DriverStatement> data;
    private LayoutInflater inflater;
    private Context context;

    public DriverListAdapter(Context context, ArrayList<DriverStatement> data) {
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
        Viewholder viewholder;

        if(convertView == null) {
            viewholder = new Viewholder();
            itemToShow = inflater.inflate(R.layout.driver_statement_layout,null);

            viewholder.imig = (ImageView) itemToShow.findViewById(R.id.list1_image);
            viewholder.city = (TextView) itemToShow.findViewById(R.id.list1_city_text);
            viewholder.date = (TextView) itemToShow.findViewById(R.id.list1_date);
            viewholder.name = (TextView) itemToShow.findViewById(R.id.list1_name_text);
            viewholder.cost = (TextView) itemToShow.findViewById(R.id.list1_price);
            viewholder.freeSpace = (TextView) itemToShow.findViewById(R.id.list1_freespace_text);

            itemToShow.setTag(viewholder);

        }else {
            itemToShow = convertView;
            viewholder = (Viewholder) itemToShow.getTag();
        }

        DriverStatement currentStatement = (DriverStatement) getItem(position);
        Calendar c = currentStatement.getDate();

        viewholder.city.setText(currentStatement.getCityFrom()+" - "+currentStatement.getCityTo());
        viewholder.date.setText(c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DAY_OF_MONTH) );
        viewholder.name.setText(currentStatement.getName()+" "+currentStatement.getSurname());
        viewholder.cost.setText(String.valueOf(currentStatement.getPrice()));
        viewholder.freeSpace.setText(String.valueOf(currentStatement.getFreeSpace()));

        return itemToShow;
    }

    private class Viewholder {
        TextView city, date, name, cost, freeSpace;
        ImageView imig;
    }
}
