package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    interface EditCityDialogListener {
        void editCity(City city, String updatedCityName, String updatedProvinceName);
    }
    private EditCityDialogListener listener;
    private City cityToEdit;

    static EditCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city); // Pass the city object to the fragment
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args); // Set arguments for the fragment
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        if (getArguments() != null) {
            cityToEdit = (City) getArguments().getSerializable("city");
        }

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        if (cityToEdit != null) {
            editCityName.setText(cityToEdit.getName());  // Set city name
            editProvinceName.setText(cityToEdit.getProvince());  // Set province name
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Edit", (dialog, which) -> {
                    String updatedCityName = editCityName.getText().toString();
                    String updatedProvinceName = editProvinceName.getText().toString();
                    listener.editCity(cityToEdit, updatedCityName, updatedProvinceName);
                })
                .create();
    }
}
