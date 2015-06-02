package com.ne4istb.matchgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.util.HashMap;


public class KitchenActivity extends Activity {


    private enum Stuff{
        Cake
    }

    private enum Area{
        Table,
        Refrigerator
    }

    Stuff selected;

    private static final HashMap<Stuff, Area> matches;
    static
    {
        matches = new HashMap<Stuff, Area>();
        matches.put(Stuff.Cake, Area.Refrigerator);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_kitchen);

        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        initButtons(rootView);
    }

    private void initButtons(View rootView) {

        final ImageButton cake = (ImageButton) rootView.findViewById(R.id.cake);

        setOnMenuButtonClickListener(cake, KitchenActivity.class);
    }

    private void setOnMenuButtonClickListener(ImageButton button, final Class<?> activityClass) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageButton image = (ImageButton) view;
                image.setImageResource(R.drawable.cake_focused2);
            }
        });
    }

}