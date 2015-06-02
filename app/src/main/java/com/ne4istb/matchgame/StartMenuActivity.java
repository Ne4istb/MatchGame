package com.ne4istb.matchgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


public class StartMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_start_menu);

        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        initButtons(rootView);
    }

    private void initButtons(View rootView) {

        final Button kitchenBtn = (Button) rootView.findViewById(R.id.kitchenButton);
        final Button bathRoomBtn = (Button) rootView.findViewById(R.id.bathRoomButton);
        final Button dollBtn = (Button) rootView.findViewById(R.id.dollButton);

        setOnMenuButtonTouchListener(kitchenBtn);
        setOnMenuButtonTouchListener(bathRoomBtn);
        setOnMenuButtonTouchListener(dollBtn);

        setOnMenuButtonClickListener(kitchenBtn, KitchenActivity.class);
        setOnMenuButtonClickListener(dollBtn, DollActivity.class);
    }

    private void setOnMenuButtonClickListener(Button button, final Class<?> activityClass) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartMenuActivity.this, activityClass));
            }
        });
    }

    private void setOnMenuButtonTouchListener(final Button button) {
        button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    layoutParams.setMargins(0, 0, 0, 0);
                    button.setLayoutParams(layoutParams);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    layoutParams.setMargins(20, 20, 20, 20);
                    button.setLayoutParams(layoutParams);
                }

                return false;
            }
        });
    }

}


