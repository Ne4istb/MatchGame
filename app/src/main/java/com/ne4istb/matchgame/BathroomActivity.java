package com.ne4istb.matchgame;

import android.os.Bundle;

import java.util.HashMap;


public class BathroomActivity extends RoomActivity {

    public BathroomActivity() {
        super("bathroom");

        stuff = new String[]{"brush", "bast", "comb", "soap", "shower", "gel"};
        areas = new String[]{"sink", "bath", "shelf", "mirror"};

        numberOfStuff = stuff.length;

        fieldWidth = 1280;
        fieldHeight = 717;

        matches = new HashMap<>();
        matches.put("brush", "sink");
        matches.put("soap", "sink");
        matches.put("comb", "mirror");
        matches.put("shower", "bath");
        matches.put("bast", "bath");
        matches.put("gel", "shelf");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initZones();
    }

    private void initZones() {
        zones.put("sink", new Zone(820, 345, 1200, 600, factor, offset));
        zones.put("bath", new Zone(30, 440, 810, 660, factor, offset));
        zones.put("shelf", new Zone(90, 80, 400, 400, factor, offset));
        zones.put("mirror", new Zone(760, 160, 1200, 340, factor, offset));
    }
}