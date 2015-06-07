package com.ne4istb.matchgame;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class DollActivity extends SceneActivity {

    public static final int OFFSET = 1000;
    int state = 0;
    private View rootView;

    private ArrayList<Integer> shuffled;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_doll);

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        initButtons();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("State", state);
        savedInstanceState.putIntegerArrayList("Shuffled", shuffled);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState == null)
            return;

        state = savedInstanceState.getInt("State");

        shuffled = savedInstanceState.getIntegerArrayList("Shuffled");
        initButtons();

        for (int i=1; i <=state; i++)
        {
            showStar(i);
        }
    }

    private void initButtons() {

        if (shuffled == null) {
            shuffled = new ArrayList<>(Arrays.asList(new Integer[]{1,2,3,4,5}));
            Collections.shuffle(shuffled);
        }

        int dolLayoutId = getViewResourceId("id", "dollLayout");
        LinearLayout dollLayout = (LinearLayout) rootView.findViewById(dolLayoutId);

        dollLayout.removeAllViews();

        for (Integer index : shuffled) {
            ImageButton button = createDollImageButton(index);
            dollLayout.addView(button);
        }

        int starLayoutId = getViewResourceId("id", "starLayout");
        LinearLayout starLayout = (LinearLayout) rootView.findViewById(starLayoutId);

        starLayout.removeAllViews();

        for (Integer index : shuffled) {
            RelativeLayout starView = createStarView(index);
            starLayout.addView(starView);
        }
    }

    private ImageButton createDollImageButton(int index) {

        ImageButton button = new ImageButton(this);

        button.setBackgroundColor(Color.TRANSPARENT);
        button.setScaleType(ImageView.ScaleType.FIT_XY);
        button.setImageResource(getViewResourceId("drawable", "doll" + index));
        button.setId(10000 + index);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        layoutParams.weight = 1;
        button.setLayoutParams(layoutParams);

        setDollOnClickListener(button, index);

        return button;
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

    private RelativeLayout createStarView(Integer index) {

        RelativeLayout layout = new RelativeLayout(DollActivity.this);

        layout.setId(OFFSET + index);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.weight = 1;
        layout.setLayoutParams(layoutParams);
        layout.setVisibility(View.INVISIBLE);

        ImageView starImage = new ImageView(DollActivity.this);
        starImage.setImageResource(R.drawable.star);

        layout.addView(starImage);

        TextView textView = new TextView(DollActivity.this);
        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        textLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textView.setLayoutParams(textLayoutParams);
        textView.setText(String.valueOf(index));
        textView.setTextAppearance(DollActivity.this, android.R.style.TextAppearance_Small);
        textView.setTextColor(Color.BLACK);
        textView.setTypeface(Typeface.SERIF, Typeface.BOLD);

        layout.addView(textView);

        return layout;
    }


    private void showStar(int index) {

        final RelativeLayout starLayout = (RelativeLayout) rootView.findViewById(OFFSET + index);
        starLayout.setVisibility(View.VISIBLE);
    }

}
