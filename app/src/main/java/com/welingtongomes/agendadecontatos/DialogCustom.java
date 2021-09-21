package com.welingtongomes.agendadecontatos;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogCustom extends AppCompatDialogFragment {

    private EditText nomeEdt;
    private EditText numeroEdt;
    private DialogListeners listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);

        builder.setView(view)
                .setTitle(R.string.novo_ct)
                .setNegativeButton(R.string.cancelar, null)
                .setPositiveButton(R.string.salvar, (dialog, which) -> {
                    String nome = nomeEdt.getText().toString();
                    String numero = numeroEdt.getText().toString();
                    listener.aplyTexts(nome, numero);
                });
        nomeEdt = view.findViewById(R.id.nomeEdt);
        numeroEdt = view.findViewById(R.id.numeroEdt);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListeners) context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }
}
