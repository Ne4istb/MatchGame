package com.ne4istb.matchgame;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class RoomActivity extends SceneActivity {

    private final String activityName;
    private View rootView;
    private int state = 0;
    private String selected;
    private ArrayList<String> resolved = new ArrayList<>();

    protected static int numberOfStuff;
    protected static HashMap<String, String> matches;
    protected static float fieldWidth;
    protected static float fieldHeight;
    protected static String[] stuff = new String[]{};
    protected static String[] areas = new String[]{};

    protected HashMap<String, Zone> zones = new HashMap<>();

    protected float factor;
    protected int offset;
    private ArrayList<String> shuffled;

    public RoomActivity(String activityName) {
        super();
        this.activityName = activityName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        int activityId = getViewResourceId("layout", "activity_" + activityName);
        setContentView(activityId);

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        initRoom(activityName);

        calculateDimensions();

        shuffleButtons();

        selected = "none";

    }

    private void shuffleButtons() {

        if (shuffled == null) {
            shuffled = new ArrayList<>(Arrays.asList(stuff));
                        Collections.shuffle(shuffled);
        }

        int layoutId = getViewResourceId("id", activityName + "Layout");
        LinearLayout layout = (LinearLayout) rootView.findViewById(layoutId);

        layout.removeAllViews();

        for (String thing : shuffled) {
            ImageButton button = createImageButton(thing);
            layout.addView(button);
        }
    }

    private ImageButton createImageButton(String name) {

        ImageButton button = new ImageButton(this);
        button.setBackgroundColor(Color.TRANSPARENT);
        button.setScaleType(ImageView.ScaleType.FIT_XY);
        button.setImageResource(getViewResourceId("drawable", name));
        button.setId(Math.abs(name.hashCode()));
        setOnMenuButtonClickListener(button, name);

        return button;
    }

    private void initRoom(String name) {

        int viewResourceId = getViewResourceId("id", name + "View");

        ImageView room = (ImageView) rootView.findViewById(viewResourceId);

        room.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (selected == "none")
                    return true;

                String area = matches.get(selected);
                Zone zone = zones.get(area);

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isTouchedInZone(event, zone)) {
                        hideSelectedButton(selected);
                        state++;
                    } else {
                        vibrate();
                    }

                    unSelectButton();

                    if (state == numberOfStuff) {
                        showCongratulationsDialog();
                    }
                }
                return true;
            }

            private boolean isTouchedInZone(MotionEvent event, Zone zone) {
                return zone != null && event.getX() > zone.getLeft() && event.getX() < zone.getRight()
                        && event.getY() > zone.getTop() && event.getY() < zone.getBottom();
            }
        });
    }

    private void calculateDimensions() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        factor = width / fieldWidth;
        offset = (int) ((height - fieldHeight / fieldWidth * width) / 2);
    }

    private void unSelectButton() {

        if (selected == "none")
            return;

        int focusedResourceId = getViewResourceId("drawable", selected);

        final ImageButton button = getImageButton(selected);
        button.setImageResource(focusedResourceId);

        selected = "none";
    }

    private void hideSelectedButton(String name) {
        final ImageButton button = getImageButton(name);
        button.setVisibility(View.GONE);

        resolved.add(name);
    }

    private ImageButton getImageButton(String name) {
        return (ImageButton) rootView.findViewById(Math.abs(name.hashCode()));
    }

    private void setOnMenuButtonClickListener(ImageButton button, final String name) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unSelectButton();
                selectButton((ImageButton) view, name);
                selected = name;
            }
        });
    }

    private void selectButton(ImageButton view, String name) {
        int focusedResourceId = getViewResourceId("drawable", name + "_focused");
        view.setImageResource(focusedResourceId);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("State", state);
        savedInstanceState.putStringArrayList("Resolved", resolved);
        savedInstanceState.putStringArrayList("Shuffled", shuffled);
        savedInstanceState.putString("Selected", selected);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState == null)
            return;

        state = savedInstanceState.getInt("State");

        shuffled = savedInstanceState.getStringArrayList("Shuffled");
        shuffleButtons();

        ArrayList<String> resolvedButtons = savedInstanceState.getStringArrayList("Resolved");
        for (String name : resolvedButtons) {
            hideSelectedButton(name);
        }

        String selectedButton = savedInstanceState.getString("Selected");
        if (selectedButton != null && selectedButton != "none") {
            selected = selectedButton;
            selectButton(getImageButton(selected), selected);
        }
    }
}
