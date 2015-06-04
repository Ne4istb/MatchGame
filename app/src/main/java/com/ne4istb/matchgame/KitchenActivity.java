package com.ne4istb.matchgame;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.HashMap;


public class KitchenActivity extends SceneActivity {

    private final float fieldWidth = 1280;
    private final float fieldHeight = 717;

    private enum Stuff {
        cake,
        pan,
        pot,
        wisp,
        pepper,
        board,
        plate,
        meat,
        none
    }

    private enum Area {
        Sink,
        Refrigerator,
        Stove,
        Shelf
    }

    Stuff selected = Stuff.none;

    private static final HashMap<Stuff, Area> matches;
    static {
        matches = new HashMap<>();
        matches.put(Stuff.cake, Area.Refrigerator);
        matches.put(Stuff.meat, Area.Refrigerator);
        matches.put(Stuff.pot, Area.Stove);
        matches.put(Stuff.pan, Area.Stove);
        matches.put(Stuff.board, Area.Shelf);
        matches.put(Stuff.pepper, Area.Shelf);
        matches.put(Stuff.plate, Area.Sink);
        matches.put(Stuff.wisp, Area.Sink);
    }

    private HashMap<Area, Zone> zones = new HashMap<>();

    private View rootView;
    private int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_kitchen);

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        initButtons();
        initRoom();
        initZones();

        startTime = System.currentTimeMillis();

    }

    private void initZones() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        float factor = width / fieldWidth;
        int offset = (int) ((height - fieldHeight / fieldWidth * width) / 2);

        zones.put(Area.Refrigerator, new Zone(950, 275, 1080, 435, factor, offset));
        zones.put(Area.Stove, new Zone(120, 395, 335, 610, factor, offset));
        zones.put(Area.Sink, new Zone(590, 330, 710, 460, factor, offset));
        zones.put(Area.Shelf, new Zone(735, 280, 900, 390, factor, offset));
    }

    private void initRoom() {

        ImageView kitchen = (ImageView) rootView.findViewById(R.id.kitchenView);

        kitchen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (selected == Stuff.none)
                    return true;

                Area area = matches.get(selected);
                Zone zone = zones.get(area);

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isTouchedInZone(event, zone)) {
                        HideSelectedButton();
                        state++;
                    } else {
                        Vibrate();
                    }

                    UnSelectButton();

                    if (state == 8) {
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

    private void UnSelectButton() {

        if (selected == Stuff.none)
            return;

        int focusedResourceId = getResources().getIdentifier(
                selected.toString(),
                "drawable",
                getPackageName());

        final ImageButton button = getImageButton(selected.toString());
        button.setImageResource(focusedResourceId);

        selected = Stuff.none;
    }

    private void HideSelectedButton() {
        final ImageButton button = getImageButton(selected.toString());
        button.setVisibility(View.GONE);
    }

    private void initButtons() {
        initButton("cake");
        initButton("wisp");
        initButton("pan");
        initButton("pepper");
        initButton("board");
        initButton("pot");
        initButton("plate");
        initButton("meat");
    }

    private void initButton(String name) {
        final ImageButton button = getImageButton(name);
        setOnMenuButtonClickListener(button, name);
    }

    private ImageButton getImageButton(String name) {
        int resourceId = getResources().getIdentifier(name, "id", getPackageName());
        return (ImageButton) rootView.findViewById(resourceId);
    }

    private void setOnMenuButtonClickListener(ImageButton button, final String name) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UnSelectButton();

                int focusedResourceId = getResources().getIdentifier(
                        name + "_focused",
                        "drawable",
                        getPackageName());

                ImageButton image = (ImageButton) view;
                image.setImageResource(focusedResourceId);

                selected = Stuff.valueOf(name);
            }
        });
    }

}