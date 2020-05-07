package com.ericarias.quiz.Controller.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ericarias.quiz.Model.Points;
import com.ericarias.quiz.R;

import java.util.ArrayList;

public class AdapterRanking extends RecyclerView.Adapter<AdapterRanking.ViewHolder> {

    private ArrayList<Points> points;
    private Context context;

    /**
     * Constructor
     * @param context
     * @param points
     */
    public AdapterRanking(Context context, ArrayList<Points> points){
        this.context = context;
        this.points = points;
    }

    /**
     * Infla la vista del adapatador con cada item.
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public AdapterRanking.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_points, parent, false);
        return new AdapterRanking.ViewHolder(view);
    }

    /**
     * Rellena la informacion del array a cada item del Recycler
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterRanking.ViewHolder holder, int position) {

        int positionRanking = position + 1;

        holder.numPosition.setText(String.valueOf(positionRanking));
        holder.username.setText(points.get(position).getName());
        holder.numPoints.setText(String.valueOf(points.get(position).getPoints()));

        Log.e(null, "onBindViewHolder: " + positionRanking);
        if (positionRanking == 1)
            holder.cardViewItem.setCardBackgroundColor(ContextCompat.getColor(context, R.color.oro));
        else if (positionRanking == 2)
            holder.cardViewItem.setCardBackgroundColor(ContextCompat.getColor(context, R.color.plata));
        else if (positionRanking == 3)
            holder.cardViewItem.setCardBackgroundColor(ContextCompat.getColor(context, R.color.bronce));
        else
            holder.cardViewItem.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent));

    }

    /**
     * Conteo de todos los puntos.
     * @return
     */
    @Override
    public int getItemCount() {
        return points.size();
    }

    /**
     * Clase para cada item del RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView numPosition, username, numPoints;
        private CardView cardViewItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            numPosition = itemView.findViewById(R.id.posItem);
            username = itemView.findViewById(R.id.userNameItem);
            numPoints = itemView.findViewById(R.id.pointsItem);
            cardViewItem = itemView.findViewById(R.id.cardViewItem);

        }
    }
}
