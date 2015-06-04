package com.ne4istb.matchgame;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class DollActivity extends SceneActivity {

    int state = 0;
    long startTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_doll);

        final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        initButtons(rootView);

        startTime = System.currentTimeMillis();
    }

    private void initButtons(final View rootView) {

        final ImageButton doll1btn = (ImageButton) rootView.findViewById(R.id.doll1Button);
        final ImageButton doll2btn = (ImageButton) rootView.findViewById(R.id.doll2Button);
        final ImageButton doll3btn = (ImageButton) rootView.findViewById(R.id.doll3Button);
        final ImageButton doll4btn = (ImageButton) rootView.findViewById(R.id.doll4Button);
        final ImageButton doll5btn = (ImageButton) rootView.findViewById(R.id.doll5Button);

        setDollOnClickListener(rootView, doll1btn, 1);
        setDollOnClickListener(rootView, doll2btn, 2);
        setDollOnClickListener(rootView, doll3btn, 3);
        setDollOnClickListener(rootView, doll4btn, 4);
        setDollOnClickListener(rootView, doll5btn, 5);
    }

    private void setDollOnClickListener(final View rootView, ImageButton doll5btn, final int index) {

        doll5btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (index == state + 1) {
                    ShowStar();
                    state++;
                } else
                    Vibrate();

                if (state == 5) {
                    showCongratulationsDialog();
                }
            }

            private void ShowStar() {

                int starResourceId = getResources().getIdentifier(
                        "dollStar" + index,
                        "id",
                        getPackageName());

                final RelativeLayout starLayout = (RelativeLayout) rootView.findViewById(starResourceId);
                starLayout.setVisibility(View.VISIBLE);
            }
        });
    }

}
