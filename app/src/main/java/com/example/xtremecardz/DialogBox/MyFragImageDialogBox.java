package com.example.xtremecardz.DialogBox;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.example.xtremecardz.R;
import com.example.xtremecardz.Utils.L;
import com.github.siyamed.shapeimageview.CircularImageView;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.Objects;

public class MyFragImageDialogBox extends DialogFragment {
    Communicator communicator;
    Spinner typefaceDropDown, typefaceStyle;
    private RoundedImageView roundedImageView;
    private CircularImageView circularImageView;
    private int myDefaultColorPicked;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator    = (Communicator) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.image_dialog_layout_fragment, container, false);
        Button yes  = view.findViewById(R.id.acceptBtn);
        Button no   = view.findViewById(R.id.cancelBtn);

        AppCompatRadioButton circularType       = view.findViewById(R.id.circularTypeImg);
        AppCompatRadioButton rectangularType    = view.findViewById(R.id.rectangularTypeImg);
        AppCompatRadioButton roundedType        = view.findViewById(R.id.roundedTypeImg);

        roundedImageView    = view.findViewById(R.id.imgPhotoBox);
        circularImageView   = view.findViewById(R.id.imgPhotoBox2);

        Typeface mTypefaceBlack = Typeface.createFromAsset(view.getContext().getAssets(),"fonts/Ubuntu-Bold.ttf");
        Typeface mTypeBoldBlack = Typeface.createFromAsset(view.getContext().getAssets(),"fonts/GoogleSans-Bold.ttf");
        myDefaultColorPicked    = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimaryDark);

        //Set the typefaces for the Buttons
        no.setTypeface(mTypefaceBlack);
        yes.setTypeface(mTypefaceBlack);

        circularType.setTypeface(mTypeBoldBlack);
        rectangularType.setTypeface(mTypeBoldBlack);
        roundedType.setTypeface(mTypeBoldBlack);

        // set a change listener on the SeekBar
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener);
        int progress = seekBar.getProgress();

        //Set the radio button for actions
        setRadioButtonChecked(view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog   = super.onCreateDialog(savedInstanceState);
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void setRadioButtonChecked(View view){
        RadioGroup radioGroup   = view.findViewById(R.id.radioGroupImgShapeType);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.circularTypeImg:
                        roundedImageView.setVisibility(View.GONE);
                        circularImageView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rectangularTypeImg:
                        break;
                    case R.id.roundedTypeImg:
                        roundedImageView.setVisibility(View.VISIBLE);
                        circularImageView.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // updated continuously as the user slides the thumb

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

    public interface Communicator{
        void onDialogImageTask(ImageView imageTypeView);
    }
}
