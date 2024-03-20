package com.example.champscie.Controllers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.champscie.R;

public class SortFilterPopup {
    private SortListener listener;
    private Dialog mDialog;
    private RadioButton alphaAsc;
    private RadioButton alphaDesc;
    private RadioButton selectedRadioButton;
    public SortFilterPopup(Context context, SortListener listener) {
        this.listener = listener;
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.sort_filter);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alphaAsc = mDialog.findViewById(R.id.alphaAsc);
        alphaDesc = mDialog.findViewById(R.id.alphaDesc);

        ImageView closePopup = mDialog.findViewById(R.id.closePopup);
        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        alphaAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = alphaAsc;
                if(listener != null) {
                    listener.onAlphaAscClicked();
                }
                mDialog.dismiss();
            }
        });
        alphaDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedRadioButton = alphaDesc;
                if(listener != null) {
                    listener.onAlphaDescClicked();
                }
                mDialog.dismiss();
            }
        });
    }
    public void show() {
        mDialog.show();
    }

    public void setSelectedRadioButton(RadioButton radioButton) {
        selectedRadioButton = radioButton;
        if (selectedRadioButton != null) {
            selectedRadioButton.setChecked(true);
        }
    }
}
