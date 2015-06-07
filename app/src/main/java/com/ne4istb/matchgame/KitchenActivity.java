package com.ne4istb.matchgame;

import android.os.Bundle;

import java.util.HashMap;


public class KitchenActivity extends RoomActivity {

    public KitchenActivity() {
        super("kitchen");

        stuff = new String[] {"cake", "pan", "pot", "wisp", "pepper", "board", "plate", "meat"};
        areas = new String[]{"Sink", "Refrigerator", "Stove", "Shelf"};

        numberOfStuff = stuff.length;
        fieldHeight = 717;

        matches = new HashMap<>();
        matches.put("cake", "Refrigerator");
        matches.put("meat", "Refrigerator");
        matches.put("pot", "Stove");
        matches.put("pan", "Stove");
        matches.put("board", "Shelf");
        matches.put("pepper", "Shelf");
        matches.put("plate", "Sink");
        matches.put("wisp", "Sink");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initZones();
    }

    private void initZones() {
        zones.put("Refrigerator", new Zone(950, 275, 1080, 435, factor, offset));
        zones.put("Stove", new Zone(120, 395, 335, 610, factor, offset));
        zones.put("Sink", new Zone(590, 330, 710, 460, factor, offset));
        zones.put("Shelf", new Zone(735, 280, 900, 390, factor, offset));
    }
}