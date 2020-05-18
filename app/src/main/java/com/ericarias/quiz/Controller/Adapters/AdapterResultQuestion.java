package com.ericarias.quiz.Controller.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.R;

import java.util.ArrayList;

public class AdapterResultQuestion extends RecyclerView.Adapter<AdapterResultQuestion.ViewHolder> {

    private ArrayList<Question> questions;
    private ArrayList<Integer> correctAnswers;
    private ArrayList<String> responseResult;
    private AdapterResultQuestion.OnItemClickListener listener;

    /**
     * Constructor
     * @param questions
     */
    public AdapterResultQuestion(ArrayList<Question> questions, ArrayList<Integer> correctAnswers, ArrayList<String> responseResult) {
        this.questions = questions;
        this.correctAnswers = correctAnswers;
        this.responseResult = responseResult;
    }

    /**
     * Interface para llamar a este metodo en EndQuiz
     */
    public interface OnItemClickListener {
        void onReportClick(int position);
    }

    /**
     * 'set' para poder utilizar el onClick en el constructor EndQuiz
     * @param onItemClickListener
     */
    public void setOnItemClickListener(AdapterResultQuestion.OnItemClickListener onItemClickListener) {
        listener = onItemClickListener;
    }

    /**
     * Rellena la informacion del array a cada item
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    public AdapterResultQuestion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result_question, parent, false);
        AdapterResultQuestion.ViewHolder vh = new AdapterResultQuestion.ViewHolder(view, listener);
        return vh;
    }

    /**
     * Rellena la informacion del array a cada item del Recycler
     * @param holder
     * @param position
     */
    public void onBindViewHolder(@NonNull AdapterResultQuestion.ViewHolder holder, int position) {
       holder.textQuestion.setText(questions.get(position).getQuestion());
       holder.textCorrect.setText(questions.get(position).getAnswers().get(0));

        if (correctAnswers.get(position) == 1){
            holder.iconResult.setBackgroundResource(R.drawable.ic_check);
            holder.cardIncorrect.setVisibility(TextView.GONE);
        } else {
            holder.iconResult.setBackgroundResource(R.drawable.ic_cross);
            holder.textIncorrect.setText(responseResult.get(position));
            holder.cardIncorrect.setVisibility(TextView.VISIBLE);
        }

    }

    /**
     * Conteo de todas las preguntas
     * @return
     */
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textQuestion, textCorrect, textIncorrect;
        private ImageView iconResult, btnReport;
        private CardView cardIncorrect;

        public ViewHolder(View itemView, final AdapterResultQuestion.OnItemClickListener listener) {
            super(itemView);

            textQuestion = itemView.findViewById(R.id.textQuestionResult);
            textCorrect = itemView.findViewById(R.id.textCorrectResult);
            textIncorrect = itemView.findViewById(R.id.textIncorrectResult);
            iconResult = itemView.findViewById(R.id.iconResult);
            btnReport = itemView.findViewById(R.id.btnReport);
            cardIncorrect = itemView.findViewById(R.id.cardIncorrect);

            btnReport.setOnClickListener(v -> {
                if (listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onReportClick(position);
                    }
                }
            });
        }
    }
}
