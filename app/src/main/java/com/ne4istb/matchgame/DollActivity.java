package com.ne4istb.matchgame;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class DollActivity extends SceneActivity {

    int state = 0;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_doll);

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        initButtons(rootView);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("State", state);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        state = savedInstanceState.getInt("State");

        for (int i=1; i <=state; i++)
        {
            showStar(i);
        }
    }

    private void initButtons(final View rootView) {

        final ImageButton doll1btn = (ImageButton) rootView.findViewById(R.id.doll1Button);
        final ImageButton doll2btn = (ImageButton) rootView.findViewById(R.id.doll2Button);
        final ImageButton doll3btn = (ImageButton) rootView.findViewById(R.id.doll3Button);
        final ImageButton doll4btn = (ImageButton) rootView.findViewById(R.id.doll4Button);
        final ImageButton doll5btn = (ImageButton) rootView.findViewById(R.id.doll5Button);

        setDollOnClickListener(doll1btn, 1);
        setDollOnClickListener(doll2btn, 2);
        setDollOnClickListener(doll3btn, 3);
        setDollOnClickListener(doll4btn, 4);
        setDollOnClickListener(doll5btn, 5);
    }

    private void setDollOnClickListener(ImageButton doll5btn, final int index) {

        doll5btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (index == state + 1) {
                    showStar(index);
                    state++;
                } else
                    vibrate();

                if (state == 5) {
                    showCongratulationsDialog();
                }
            }


        });
    }

    private void showStar(int index) {

        int starResourceId = getResources().getIdentifier(
                "dollStar" + index,
                "id",
                getPackageName());

        final RelativeLayout starLayout = (RelativeLayout) rootView.findViewById(starResourceId);
        starLayout.setVisibility(View.VISIBLE);
    }

}
