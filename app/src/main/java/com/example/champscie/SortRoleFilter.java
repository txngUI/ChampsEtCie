package com.example.champscie;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;

public class SortRoleFilter {
    private RadioButton all;
    private RadioButton mage;
    private RadioButton assassin;
    private RadioButton marksman;
    private RadioButton tank;
    private RadioButton support;
    private RadioButton fighter;
    private SortRoleListener sortRoleListener;
    private Dialog mDialog;
    private RadioButton selectedCheckBox;
    public SortRoleFilter(Context context, SortRoleListener sortRoleListener) {
        this.sortRoleListener = sortRoleListener;
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.roles_filter);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mage = mDialog.findViewById(R.id.mage);
        assassin = mDialog.findViewById(R.id.assassin);
        marksman = mDialog.findViewById(R.id.marksman);
        tank = mDialog.findViewById(R.id.tank);
        support = mDialog.findViewById(R.id.support);
        fighter = mDialog.findViewById(R.id.fighter);
        all = mDialog.findViewById(R.id.all);

        ImageView closePopup = mDialog.findViewById(R.id.closePopup);
        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckBox = mage;
                if(sortRoleListener != null) {
                    sortRoleListener.onMageClicked();
                }
                mDialog.dismiss();
            }
        });

        assassin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckBox = assassin;
                if(sortRoleListener != null) {
                    sortRoleListener.onAssassinClicked();
                }
                mDialog.dismiss();
            }
        });

        marksman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckBox = marksman;
                if(sortRoleListener != null) {
                    sortRoleListener.onMarksmanClicked();
                }
                mDialog.dismiss();
            }
        });

        tank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckBox = tank;
                if(sortRoleListener != null) {
                    sortRoleListener.onTankClicked();
                }
                mDialog.dismiss();
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckBox = support;
                if(sortRoleListener != null) {
                    sortRoleListener.onSupportClicked();
                }
                mDialog.dismiss();
            }
        });

        fighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckBox = fighter;
                if(sortRoleListener != null) {
                    sortRoleListener.onFighterClicked();
                }
                mDialog.dismiss();
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckBox = all;
                if(sortRoleListener != null) {
                    sortRoleListener.onAllClicked();
                }
                mDialog.dismiss();
            }
        });
    }

    public void show() {
        mDialog.show();
    }
}
