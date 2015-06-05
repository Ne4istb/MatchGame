package com.ne4istb.matchgame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Home on 04.06.2015.
 */
public class SceneActivity extends Activity {
    long startTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startTime = System.currentTimeMillis();
    }

    protected void showCongratulationsDialog(){
        String timeString = getTimeDifferenceString();
        showCongratulationDialog(timeString);
    }

    private String getTimeDifferenceString() {

        long timeDifference = getTimeDifference();

        int totalSecs = (int) (timeDifference / 1000);
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;

        return String.format("%02d:%02d", minutes, seconds);
    }

    private long getTimeDifference() {
        long finishTime = System.currentTimeMillis();
        return finishTime - startTime;
    }

    private void showCongratulationDialog(String timeString) {

        final Dialog dialog = new Dialog(SceneActivity.this);

        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle(getString(R.string.congratilatuons));

        TextView timeText = (TextView) dialog.findViewById(R.id.time);
        timeText.setText(timeString);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

        dialog.show();
    }

    protected void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putLong("TimeDiff", getTimeDifference());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        long timeDiff = savedInstanceState.getLong("TimeDiff");
        startTime -= timeDiff;
    }
}
