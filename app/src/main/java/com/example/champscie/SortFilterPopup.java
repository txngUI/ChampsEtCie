package com.example.champscie;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.champscie.R;

public class SortFilterPopup {
    private Dialog mDialog;
    private RadioButton alphaAsc;
    private RadioButton alphaDesc;
    private RadioButton newestToOldest;
    private RadioButton oldestToNewest;
    public SortFilterPopup(Context context) {
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.sort_filter);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView closePopup = mDialog.findViewById(R.id.closePopup);
        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }
    public void show() {
        mDialog.show();
    }
}
