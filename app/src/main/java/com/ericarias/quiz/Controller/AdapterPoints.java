package com.ericarias.quiz.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ericarias.quiz.Model.Points;
import com.ericarias.quiz.R;

import java.util.ArrayList;

public class AdapterPoints extends RecyclerView.Adapter<AdapterPoints.ViewHolder> {

    private ArrayList<Points> points;
    private Context context;

    /**
     * Constructor
     * @param context
     * @param points
     */
    public AdapterPoints(Context context, ArrayList<Points> points){
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
    public AdapterPoints.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_points, parent, false);
        return new AdapterPoints.ViewHolder(view);
    }

    /**
     * Rellena la informacion del array a cada item
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull AdapterPoints.ViewHolder holder, int position) {
        holder.numPosition.setText(String.valueOf(position + 1));
        holder.username.setText(points.get(position).getName());
        holder.numPoints.setText(String.valueOf(points.get(position).getPoints()));
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            numPosition = itemView.findViewById(R.id.posItem);
            username = itemView.findViewById(R.id.userNameItem);
            numPoints = itemView.findViewById(R.id.pointsItem);

        }
    }
}
