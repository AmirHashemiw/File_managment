package com.example.filemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddNewFolderDialog extends DialogFragment {

    private AddNewFolderCallback addNewFolderCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        addNewFolderCallback = (AddNewFolderCallback) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_add_new_folder, null);
        TextInputEditText editText = view.findViewById(R.id.et_addNewFolder);
        TextInputLayout etl = view.findViewById(R.id.etl_addNewFolder);
        view.findViewById(R.id.btn_addNewFolder_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editText.length() > 0) {
                    addNewFolderCallback.onCreatefolderButtonClick(editText.getText().toString());
                    dismiss();
                }else
                    etl.setError("Folder Name cannot be Empty");

            }
        });
        return builder.setView(view).create();
    }

    public interface AddNewFolderCallback {
        void onCreatefolderButtonClick(String folderName);
    }

}
