package com.androidcode.imagegallery.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.androidcode.imagegallery.R;

public class Custom_dialog extends AppCompatDialogFragment {
    EditText edtx;

    Custom_DialogInterface dialogInterface;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.custom_dialog,null);

        builder.setView(view).setTitle("Nhập đường dẫn thư mục xong click thêm 1 lần nữa.").setPositiveButton("Có",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String textF = edtx.getText().toString();
                dialogInterface.applyText(textF);

            }
        }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        edtx = view.findViewById(R.id.Edittext);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialogInterface = (Custom_DialogInterface) context;
    }

    public interface Custom_DialogInterface{
        void applyText(String text);
    }
}
