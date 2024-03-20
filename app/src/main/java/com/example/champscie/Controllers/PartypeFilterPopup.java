package com.example.champscie.Controllers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.champscie.R;

public class PartypeFilterPopup {
    RadioButton mana;
    RadioButton energy;
    RadioButton bloodyWell;
    RadioButton rage;
    RadioButton courage;
    RadioButton shield;
    RadioButton fury;
    RadioButton ferocity;
    RadioButton vapor;
    RadioButton agressivity;
    RadioButton bloodFlow;
    RadioButton impulse;
    RadioButton noOne;
    private PartypeFilterListener partypeFilterListener;
    private Dialog mDialog;
    private RadioButton selectedRadioButton;

    public PartypeFilterPopup(Context context, PartypeFilterListener partypeFilterListener) {
        this.partypeFilterListener = partypeFilterListener;
        mDialog = new Dialog(context);
        mDialog.setContentView(R.layout.partype_filter);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mana = mDialog.findViewById(R.id.mana);
        energy = mDialog.findViewById(R.id.energy);
        bloodyWell = mDialog.findViewById(R.id.bloodyWell);
        rage = mDialog.findViewById(R.id.rage);
        courage = mDialog.findViewById(R.id.courage);
        shield = mDialog.findViewById(R.id.shield);
        fury = mDialog.findViewById(R.id.fury);
        ferocity = mDialog.findViewById(R.id.ferocity);
        vapor = mDialog.findViewById(R.id.vapor);
        agressivity = mDialog.findViewById(R.id.agressivity);
        bloodFlow = mDialog.findViewById(R.id.bloodFlow);
        impulse = mDialog.findViewById(R.id.impulse);
        noOne = mDialog.findViewById(R.id.noOne);

        ImageView closePopup = mDialog.findViewById(R.id.closePopup);
        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        mana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onManaClicked();
                }
                mDialog.dismiss();
            }
        });

        energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onEnergyClicked();
                }
                mDialog.dismiss();
            }
        });

        bloodyWell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onBloodyWellClicked();
                }
                mDialog.dismiss();
            }
        });

        rage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onRageClicked();
                }
                mDialog.dismiss();
            }
        });

        courage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onCourageClicked();
                }
                mDialog.dismiss();
            }
        });

        shield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onShieldClicked();
                }
                mDialog.dismiss();
            }
        });

        fury.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onFuryClicked();
                }
                mDialog.dismiss();
            }
        });

        ferocity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onFerocityClicked();
                }
                mDialog.dismiss();
            }
        });

        vapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onVaporClicked();
                }
                mDialog.dismiss();
            }
        });

        agressivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onAgressivityClicked();
                }
                mDialog.dismiss();
            }
        });

        bloodFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onBloodFlowClicked();
                }
                mDialog.dismiss();
            }
        });

        impulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onImpulseClicked();
                }
                mDialog.dismiss();
            }
        });

        noOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(partypeFilterListener != null) {
                    partypeFilterListener.onNoOneClicked();
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

    public void clearFilters() {
        selectedRadioButton = noOne;
        noOne.setChecked(true);
    }
}
