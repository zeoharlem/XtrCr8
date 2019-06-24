package com.example.xtremecardz.DialogBox;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.xtremecardz.R;
import com.example.xtremecardz.Utils.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by Theophilus on 2/20/2018.
 */

public class MyFragNumDialogBox extends DialogFragment implements View.OnClickListener {

    private Button yes, no;
    Communicator communicator;
    Spinner typefaceDropDown, typefaceStyle, textSizeSpinner;
    ArrayList<String> dynamicString = new ArrayList<>();
    ArrayList<Typeface> typefaces   = new ArrayList<>();
    private EditText textTypeEditShow;
    private TextView tvProgressLabel;
    private TextView textTypeView;
    private int myDefaultColorPicked;
    private ImageButton imgButtonRow;
    private AmbilWarnaDialog colorPicker;

    private int viewId;
    private float getPointDx;
    private float getPointDy;
    private String textString;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator    = (Communicator) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.topup_submit_layout_fragment, container, false);

        yes                 = view.findViewById(R.id.acceptBtn);
        no                  = view.findViewById(R.id.cancelBtn);

        typefaceDropDown    = view.findViewById(R.id.typefaceDropDown);
        typefaceStyle       = view.findViewById(R.id.typefaceStyle);
        textTypeEditShow    = view.findViewById(R.id.textTypeEditShow);
        imgButtonRow        = view.findViewById(R.id.colorPickerButton);

        textTypeEditShow.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        setSpinnerTaskRow();
        setSpinnerTypefaceFormat();
        setTextTypeEditShowTask();

        // set a change listener on the SeekBar
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);

        int progress = seekBar.getProgress();
        tvProgressLabel = view.findViewById(R.id.textView);
        tvProgressLabel.setTextSize(12);

        tvProgressLabel.setText("TextSize: " + progress);

        //Assign typeface value to mTypefaceBlack
        Typeface mTypefaceBlack = Typeface.createFromAsset(view.getContext().getAssets(),"fonts/Ubuntu-Bold.ttf");
        myDefaultColorPicked    = ContextCompat.getColor(getContext(), R.color.colorPrimaryDark);
        tvProgressLabel.setTextColor(myDefaultColorPicked);

        //Set the typefaces for the Buttons
        yes.setTypeface(mTypefaceBlack);
        no.setTypeface(mTypefaceBlack);

        //Set the clickListener
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        imgButtonRow.setOnClickListener(this);

        setCancelable(true);
        communicator.onDialogGetTextView(this);
        if (getArguments() != null){
            viewId          = getArguments().getInt("viewId");
            getPointDx      = getArguments().getFloat("pointDx");
            getPointDy      = getArguments().getFloat("pointDy");
            textString      = getArguments().getString("nameKey");

            tvProgressLabel.setText(textString);
            textTypeEditShow.setText(textString);
        }
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog   = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.acceptBtn){
            if(tvProgressLabel.getParent() != null){
                ((ViewGroup)tvProgressLabel.getParent()).removeView(tvProgressLabel);
            }
            //TextView myTextView = tvProgressLabel;
            tvProgressLabel.setTextColor(myDefaultColorPicked);
            if(getPointDx != 0.0f) {
                tvProgressLabel.setX(getPointDx);
                tvProgressLabel.setY(getPointDy);
                //tvProgressLabel.setText(textString);
            }
            communicator.onDialogMsgTask(tvProgressLabel);
            L.l(getContext(), String.valueOf(getPointDx));
            dismiss();
        }
        else if(v.getId() == R.id.colorPickerButton){
            openColorPickerDialog();
        }
        else{
            dismiss();
        }
    }

    public void openColorPickerDialog(){
        colorPicker = new AmbilWarnaDialog(getContext(), myDefaultColorPicked, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                myDefaultColorPicked    = color;
                tvProgressLabel.setTextColor(myDefaultColorPicked);
            }
        });
        colorPicker.show();
    }

    public void setTextTypeEditShowTask(){
        textTypeEditShow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    tvProgressLabel.setText(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Place a call here after the editText looses focus
            }
        });
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb
            tvProgressLabel.setTextSize(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // called when the user first touches the SeekBar
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // called after the user finishes moving the SeekBar
        }
    };

    private void setSpinnerTaskRow(){
        final Typeface[] typefaceAction = new Typeface[1];

        for(Map.Entry<String, Typeface> typefaceEntry : getSystemFontType().entrySet()){
            dynamicString.add(typefaceEntry.getKey());
            typefaces.add(typefaceEntry.getValue());
        }

        ArrayAdapter<String> adapter    = new ArrayAdapter<String>(
                Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item,
                dynamicString
        ){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view   = super.getView(position, convertView, parent);
                ((TextView) view).setTypeface(typefaces.get(position));
                ((TextView) view).setTextSize(12);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view   = super.getDropDownView(position, convertView, parent);
                ((TextView) view).setTypeface(typefaces.get(position));
                ((TextView) view).setTextSize(12);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typefaceDropDown.setAdapter(adapter);
        typefaceDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvProgressLabel.setTypeface(typefaces.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        typefaceDropDown.setSelection(1);
    }

    private void setSpinnerTypefaceFormat(){
        String[] typeFaceString = new String[]{"regular","bold"};
        ArrayAdapter<String> adapter    = new ArrayAdapter<String>(
                Objects.requireNonNull(getContext()),
                android.R.layout.simple_spinner_dropdown_item,
                typeFaceString
        ){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view   = super.getView(position, convertView, parent);
                ((TextView) view).setTextSize(12);
                return view;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view   = super.getDropDownView(position, convertView, parent);
                ((TextView) view).setTextSize(12);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typefaceStyle.setAdapter(adapter);
        typefaceStyle.setSelection(0);
        typefaceStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemString   = parent.getItemAtPosition(position).toString();
                switch (selectedItemString){
                    case "regular":
                        tvProgressLabel.setTypeface(tvProgressLabel.getTypeface(), Typeface.NORMAL);
                        break;
                    case "bold":
                        tvProgressLabel.setTypeface(tvProgressLabel.getTypeface(), Typeface.BOLD);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private Map<String, Typeface> getSystemFontType(){
        Typeface daxlineProRegular  = Typeface.createFromAsset(getContext().getAssets(), "fonts/DaxlinePro-Regular.ttf");
        Typeface googleSansRegular  = Typeface.createFromAsset(getContext().getAssets(), "fonts/GoogleSans-Regular.ttf");
        Typeface proximaNovaRegular = Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Reg.ttf");
        Typeface pacificoRegular    = Typeface.createFromAsset(getContext().getAssets(), "fonts/Pacifico-Regular.ttf");
        Typeface ubuntuRegular      = Typeface.createFromAsset(getContext().getAssets(), "fonts/Ubuntu-Regular.ttf");

        Map<String, Typeface> typefaceList  = new HashMap<>();
        typefaceList.put("DaxlinePro", daxlineProRegular);
        typefaceList.put("GoogleSans", googleSansRegular);
        typefaceList.put("ProximaNova", proximaNovaRegular);
        typefaceList.put("Pacifico", pacificoRegular);
        typefaceList.put("Ubuntu", ubuntuRegular);
        return typefaceList;
    }

    public void callMyFragNumDialogBox(Communicator communicator){
        communicator.onDialogGetTextView(this);
    }

    public void setTvProgressLabel(TextView tvProgressLabel) {
        this.tvProgressLabel = tvProgressLabel;
    }

    public TextView getTvProgressLabel() {
        return tvProgressLabel;
    }

    public void setTextTypeEditShow(EditText textTypeEditShow) {
        this.textTypeEditShow = textTypeEditShow;
    }

    public EditText getTextTypeEditShow() {
        return textTypeEditShow;
    }

    public interface Communicator{
        void onDialogMsgTask(TextView textTypeView);
        void onDialogGetTextView(MyFragNumDialogBox fragNumDialogBox);
    }
}
