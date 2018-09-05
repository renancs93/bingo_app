package br.edu.ifsp.scl.sdm.bingoapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Spinner;

public class CustomDialogSettings extends DialogFragment{

    private int valor;
    private Spinner spinner;

    public CustomDialogSettings(){
    }

    @SuppressLint("ValidFragment")
    public CustomDialogSettings(int valor){
        this.valor = valor;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface CustomDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, int val);
        public void onDialogNegativeClick(DialogFragment dialog, int val);
    }

    // Use this instance of the interface to deliver action events
    CustomDialogListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View converView = inflater.inflate(R.layout.dialog_custom_settings, null);
        spinner = converView.findViewById(R.id.spinner_qtd_numbers_bingo);

        setCurrentNumberSpinner();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(converView);
            // Add action buttons
            builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // Alterar quantidade de números do Bingo
                    valor = Integer.parseInt(spinner.getSelectedItem().toString());
                    mListener.onDialogPositiveClick(CustomDialogSettings.this, valor);
                    //CustomDialogSettings.this.getDialog().cancel();
                }
            })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    mListener.onDialogNegativeClick(CustomDialogSettings.this, valor);
                    //CustomDialogSettings.this.getDialog().cancel();
                }
            });
        return builder.create();

    }

    private void setCurrentNumberSpinner() {

        for (int i=0 ; i<spinner.getCount() ; i++ ){

            int val = Integer.parseInt(spinner.getItemAtPosition(i).toString());

            if (val == valor){
                spinner.setSelection(i);
            }

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mListener = (CustomDialogListener) context;

        }
        catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context.toString()
                    + " must implement CustomDialogListener");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
