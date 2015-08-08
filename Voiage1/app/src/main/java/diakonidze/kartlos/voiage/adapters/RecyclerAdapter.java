package diakonidze.kartlos.voiage.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import diakonidze.kartlos.voiage.R;

/**
 * Created by k.diakonidze on 8/8/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.VersionViewHolder> {

    private Context context;

    public RecyclerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test, parent, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    class VersionViewHolder extends RecyclerView.ViewHolder {

        public VersionViewHolder(View itemView) {
            super(itemView);


        }
    }
}
