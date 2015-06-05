package com.ne4istb.matchgame;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomActivity extends SceneActivity {

    private View rootView;
    private int state = 0;
    private String selected;
    private ArrayList<String> resolved = new ArrayList<>();

    protected static int numberOfStuff;
    protected static HashMap<String, String> matches;
    protected static float fieldWidth;
    protected static float fieldHeight;
    protected static String[] Stuff = new String[]{};
    protected static String[] Areas = new String[]{};

    protected HashMap<String, Zone> zones = new HashMap<>();

    protected float factor;
    protected int offset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        calculateDimensions();

        selected = "none";
    }

    protected void initButton(String name) {
        final ImageButton button = getImageButton(name);
        setOnMenuButtonClickListener(button, name);
    }

    protected void initRoom(String name) {

        int viewResourceId = getResources().getIdentifier(
                name + "View",
                "id",
                getPackageName());

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
                return event.getX() > zone.getLeft() && event.getX() < zone.getRight()
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

        int focusedResourceId = getResources().getIdentifier(
                selected,
                "drawable",
                getPackageName());

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
        int resourceId = getResources().getIdentifier(name, "id", getPackageName());
        return (ImageButton) rootView.findViewById(resourceId);
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
        int focusedResourceId = getResources().getIdentifier(
                name + "_focused",
                "drawable",
                getPackageName());

        ImageButton image = view;
        image.setImageResource(focusedResourceId);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("State", state);
        savedInstanceState.putStringArrayList("Resolved", resolved);
        savedInstanceState.putString("Selected", selected);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        state = savedInstanceState.getInt("State");

        ArrayList<String> resolvedButtons = savedInstanceState.getStringArrayList("Resolved");
        for(String name: resolvedButtons){
            hideSelectedButton(name);
        }

        String selectedButton = savedInstanceState.getString("Selected");
        if (selectedButton != null && selectedButton != "none") {
            selected = selectedButton;
            ImageButton button = getImageButton(selected);
            selectButton(button, selected);
        }
    }
}
