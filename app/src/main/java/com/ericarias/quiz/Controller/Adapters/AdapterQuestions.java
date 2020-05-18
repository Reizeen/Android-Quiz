package com.ericarias.quiz.Controller.Adapters;

import android.content.ContentProvider;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterQuestions extends RecyclerView.Adapter<AdapterQuestions.ViewHolder> {

    private ArrayList<Question> questions;
    private OnItemClickListener listener;

    /**
     * Constructor
     * @param questions
     */
    public AdapterQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    /**
     * Interface para llamar a estos metodos en ViewQuestions
     */
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    /**
     * 'set' para poder utilizar los onClick en el constructor ViewQuestions
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    /**
     * Rellena la informacion del array a cada item
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public AdapterQuestions.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        ViewHolder vh = new ViewHolder(view, listener);
        return vh;
    }

    /**
     * Rellena la informacion del array a cada item del Recycler
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textItemQuest.setText(questions.get(position).getQuestion());
    }

    /**
     * Conteo de todas las preguntas
     * @return
     */
    @Override
    public int getItemCount() {
        return questions.size();
    }


    /**
     * Clase para cada item del RecyclerView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textItemQuest;
        private ImageView editQuest, deleteQuest;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            textItemQuest = itemView.findViewById(R.id.textItemQuest);
            editQuest = itemView.findViewById(R.id.editQuest);
            deleteQuest = itemView.findViewById(R.id.deleteQuest);

            editQuest.setOnClickListener(v -> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onEditClick(position);
                    }
                }
            });

            deleteQuest.setOnClickListener(v -> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onDeleteClick(position);
                    }
                }
            });
        }
    }
}
