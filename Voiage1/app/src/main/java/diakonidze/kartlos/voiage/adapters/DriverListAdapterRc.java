package diakonidze.kartlos.voiage.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import diakonidze.kartlos.voiage.DetailPageDriver;
import diakonidze.kartlos.voiage.MainActivity;
import diakonidze.kartlos.voiage.R;
import diakonidze.kartlos.voiage.fragments.DriverStatatementListFragment;
import diakonidze.kartlos.voiage.models.DriverStatement;

/**
 * Created by k.diakonidze on 8/12/2015.
 */
public class DriverListAdapterRc extends RecyclerView.Adapter<DriverListAdapterRc.DriverViewHolder> {
    private ArrayList<DriverStatement> data;
    private LayoutInflater inflater;
    private Context context;
    String location="";

    public DriverListAdapterRc(ArrayList<DriverStatement> data, Context context, String location) {
        this.data = data;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.location = location;
    }

    @Override
    public DriverViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.driver_statement_layout, parent, false);
        DriverViewHolder viewHolder = new DriverViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DriverViewHolder holder, int position) {
        DriverStatement currentStatement = data.get(position);

        holder.city.setText(currentStatement.getCityFrom() + " - " + currentStatement.getCityTo());
        holder.date.setText(currentStatement.getDate());
        holder.name.setText(currentStatement.getName() + " " + currentStatement.getSurname());
        holder.cost.setText(String.valueOf(currentStatement.getPrice()));

        holder.kacunebiConteiner.removeAllViews();
        ImageView[] imageView = new ImageView[currentStatement.getFreeSpace()];
        // kacunebi ra zomis amochdnen, sxvanairad arvici rogor miutito zoma
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(50, ViewGroup.LayoutParams.MATCH_PARENT); //(Constantebi.MAN_WIDTH, Constantebi.MAN_HIEGHT);
        for (int i = 0; i < currentStatement.getFreeSpace(); i++) {
            imageView[i] = new ImageView(context);
            imageView[i].setImageResource(R.drawable.man_empty);
            imageView[i].setLayoutParams(lp);
            imageView[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            holder.kacunebiConteiner.addView(imageView[i]);
        }
    }

    public void deleteItem(int position){
        this.data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    class DriverViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView city, date, name, cost;
        ImageView imig;
        LinearLayout kacunebiConteiner;

        public DriverViewHolder(View itemToShow) {
            super(itemToShow);
            imig = (ImageView) itemToShow.findViewById(R.id.list1_image);
            city = (TextView) itemToShow.findViewById(R.id.list1_city_text);
            date = (TextView) itemToShow.findViewById(R.id.list1_date);
            name = (TextView) itemToShow.findViewById(R.id.list1_name_text);
            cost = (TextView) itemToShow.findViewById(R.id.list1_price);
            kacunebiConteiner = (LinearLayout) itemToShow.findViewById(R.id.list1_kacunebi);

            itemToShow.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DetailPageDriver.class);
            intent.putExtra("driver_st", data.get(getAdapterPosition()));
            intent.putExtra("from", location);
            context.startActivity(intent);

        }
    }
}
