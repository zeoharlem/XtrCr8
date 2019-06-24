package com.example.xtremecardz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xtremecardz.Activities.BackgroundTaskActivity;
import com.example.xtremecardz.DialogBox.MyFragImageDialogBox;
import com.example.xtremecardz.DialogBox.MyFragNumDialogBox;
import com.example.xtremecardz.Utils.L;
import com.github.siyamed.shapeimageview.RoundedImageView;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateDesignActivity extends AppCompatActivity implements MyFragNumDialogBox.Communicator, View.OnTouchListener, MyFragImageDialogBox.Communicator {

    private TextView textView;
    private MyFragNumDialogBox myFragDialogBox;
    private ArrayList<TextView> objectArrayList;
    private FrameLayout frameLayout;
    private float dX, dY;
    private int lastAction;

    final static float move = 100;
    float ratio             = 1.0f;
    int baseDist;
    float baseRatio;

    long startTime;
    static final int MAX_DURATION   = 200;

    private int myDefaultColorPicked;
    private ImageButton imgButtonRow;
    private AmbilWarnaDialog colorPicker;
    private RoundedImageView roundedImageView;

    public static final int PICK_IMAGE = 1;
    public static final int LOGO_PICKER = 2;
    private ImageView logoBoxLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_design);

        Toolbar toolbar = findViewById(R.id.toolbar);
        frameLayout     = findViewById(R.id.canvasRack);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Create Design");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        objectArrayList = new ArrayList<>();
        setButtonRowAction();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setButtonRowAction(){
        roundedImageView            = findViewById(R.id.imgPhotoBox);
        ImageButton bgTabLayoutBtn  = findViewById(R.id.imageBackgroundTab);
        ImageButton galleryImagePicker  = findViewById(R.id.galleryImagePicker);
        ImageButton textLayoutBtn   = findViewById(R.id.textButtonRow);
        ImageButton colorBgPicker   = findViewById(R.id.colorBgPicker);
        ImageButton myLogoPicker    = findViewById(R.id.myLogoPicker);
        logoBoxLayout               = findViewById(R.id.logoFrameBox);

        bgTabLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent   = new Intent(getApplicationContext(), BackgroundTaskActivity.class);
                startActivity(intent);
            }
        });

        textLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogMyFragDialogBox(v);
            }
        });

        colorBgPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPickerDialog();
            }
        });

        galleryImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myLogoPickerIntent   = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(myLogoPickerIntent,"Select Image"), PICK_IMAGE);
            }
        });

        myLogoPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myLogoPickerIntent   = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(Intent.createChooser(myLogoPickerIntent,"Select Logo"), LOGO_PICKER);
            }
        });

        //Set on touch Listener for the PassPort Image
        roundedImageView.setOnTouchListener(onTouchListener);
        logoBoxLayout.setOnTouchListener(onTouchListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE){
            if(data != null){
                String picturePath  = handleDataResActivityResult(data);
                roundedImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                //roundedImageView.setImageURI(selectedImage);
            }
        }
        if(resultCode == Activity.RESULT_OK && requestCode == LOGO_PICKER){
            if(data != null){
                String picturePath  = handleDataResActivityResult(data);
                logoBoxLayout.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }

    //Refactored response handling on onActivityResult
    private String handleDataResActivityResult(Intent data){
        Uri selectedImage       = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        assert selectedImage != null;
        Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        assert cursor != null;
        cursor.moveToFirst();
        int columnIndex     = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath  = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if(id == R.id.action_save){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onDialogMsgTask(TextView textTypeView) {
        LinearLayout.LayoutParams layoutParams   = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        textTypeView.setLayoutParams(layoutParams);
        textTypeView.setGravity(Gravity.CENTER_VERTICAL);

        //Text Being underLined with AutoLinks(REMOVED)
        textTypeView.setPaintFlags(textTypeView.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        L.l(getApplicationContext(), "Create:"+textTypeView.getText().toString());
        objectArrayList.add(textTypeView);

        for(int i=0; i < objectArrayList.size(); i++) {

            if(objectArrayList.get(i).getParent() != null){
                ((ViewGroup) objectArrayList.get(i).getParent()).removeView(objectArrayList.get(i));
            }
            textView    = objectArrayList.get(i);
            frameLayout.addView(objectArrayList.get(i));
            objectArrayList.get(i).setId(View.generateViewId());
            objectArrayList.get(i).setOnTouchListener(this);
        }
    }

    @Override
    public void onDialogGetTextView(MyFragNumDialogBox fragNumDialogBox) {

    }


    public void showDialogMyFragDialogBox(View view){
        FragmentManager manager = getSupportFragmentManager();
        myFragDialogBox         = new MyFragNumDialogBox();
        myFragDialogBox.show(manager, "MyDetailFragDialogBox");
    }

    public void showDialogBoxMyFagEditAction(final View view){
        FragmentManager manager   = getSupportFragmentManager();
        myFragDialogBox           = new MyFragNumDialogBox();
        myFragDialogBox.callMyFragNumDialogBox(new MyFragNumDialogBox.Communicator() {
            @Override
            public void onDialogMsgTask(TextView textTypeView) {

            }

            @Override
            public void onDialogGetTextView(MyFragNumDialogBox fragNumDialogBox) {
                //fragNumDialogBox.setTvProgressLabel();
                Bundle bundle   = new Bundle();
                bundle.putInt("viewId", view.getId());
                bundle.putFloat("pointDx", view.getX());
                bundle.putFloat("pointDy", view.getY()+400);
                bundle.putString("nameKey", ((TextView)view).getText().toString());
                fragNumDialogBox.setArguments(bundle);
                L.l(getApplicationContext(), "Users: "+((TextView)view).getText().toString());
                view.setVisibility(View.GONE);
            }
        });
        myFragDialogBox.show(manager, "MyDetailFragDialogBox2");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getPointerCount() == 2){
            int action  = event.getAction();
            int mainAction  = action & MotionEvent.ACTION_MASK;
            if(mainAction == MotionEvent.ACTION_POINTER_DOWN){
                baseDist    = getDistance(event);
                baseRatio   = ratio;
            }
            else{
                float scale = (getDistance(event) - baseDist)/move;
                float factor    = (float) Math.pow(2, scale);
                ratio           = Math.min(1023.0f, Math.max(0.1f, baseRatio*factor));
                textView.setTextSize(ratio+10);
            }
        }
        return false;
    }

    private void openColorPickerDialog(){
        colorPicker = new AmbilWarnaDialog(CreateDesignActivity.this, myDefaultColorPicked, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                myDefaultColorPicked    = color;
                frameLayout.setBackgroundColor(myDefaultColorPicked);
            }
        });
        colorPicker.show();
    }

    private int getDistance(MotionEvent event) {
        int dx  = (int) (event.getX(0)-event.getX(1));
        int dy  = (int) (event.getY(0)-event.getY(1));
        return (int)(Math.sqrt(dx*dx+dy+dy));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            lastAction  = MotionEvent.ACTION_DOWN;
            dX  = v.getX() - event.getRawX();
            dY  = v.getY() - event.getRawY();
            if(System.currentTimeMillis() - startTime <= MAX_DURATION){
                //textView    = ((TextView)v);
                showDialogBoxMyFagEditAction(v);
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE) {
            lastAction  = MotionEvent.ACTION_MOVE;
            v.setX(event.getRawX() + dX);
            v.setY(event.getRawY() + dY);
            v.performClick();
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            startTime   = System.currentTimeMillis();
            if(lastAction == MotionEvent.ACTION_DOWN){

            }
        }
        return true;
    }

    public MyFragNumDialogBox getMyFragDialogBox() {
        return myFragDialogBox;
    }

    public void showDialogFragImageBox(View view){
        FragmentManager manager = getSupportFragmentManager();
        MyFragImageDialogBox mFragImageDialogBox    = new MyFragImageDialogBox();
        mFragImageDialogBox.show(manager, "MyFragImageDialogBox");
    }

    View.OnClickListener onClickListener    = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            L.l(getApplicationContext(), String.valueOf(v.getId()));
        }
    };

    //This onTouchListener is for Passport Photo Box Space
    View.OnTouchListener onTouchListener    = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    dX  = v.getX() - event.getRawX();
                    dY  = v.getY() - event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    v.animate()
                            .x(event.getRawX()+dX).y(event.getRawY()+dY)
                            .setDuration(0).start();
                    break;
                case MotionEvent.ACTION_UP:
                    if(lastAction == MotionEvent.ACTION_DOWN){
                        //showDialogFragImageBox(v);
                    }
                    break;
                default:
                    return false;
            }
            v.performClick();
            return true;
        }
    };

    @Override
    public void onDialogImageTask(ImageView imageTypeView) {

    }
}
