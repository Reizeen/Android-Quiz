package com.ericarias.quiz.Controller.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;
import com.ericarias.quiz.R;

public class ReportDialog extends AppCompatDialogFragment {
    private EditText textReporting;
    private DialogListener listener;
    private int idQuestion;

    /**
     * Constructor
     * @param idQuestion
     */
    public ReportDialog(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    /**
     * Creacion del AlertDialog
     * @param saveInstanceState
     * @return
     */
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.report_dialog, null);


        builder.setView(view)
                .setTitle("Reportar pregunta")
                .setNegativeButton("cancelar", (dialog, which) -> { })
                .setPositiveButton("Enviar", (dialog, which) -> {
                    listener.setReport(textReporting.getText().toString(), idQuestion);
                });

        textReporting = view.findViewById(R.id.textReporting);

        return builder.create();
    }

    /**
     * Modificar los colores de los botones del Dialog
     */
    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

    /**
     * Controlar excepcion
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() +
                    " Debes implementar DialogListener");
        }
    }

    /**
     * Obligar a utilizar el metodo applyText
     */
    public interface DialogListener {
        void setReport(String textReporting, int idQuestion);
    }

}
