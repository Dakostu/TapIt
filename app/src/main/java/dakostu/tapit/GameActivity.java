
package dakostu.tapit;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Tap It! by Daniel Kostuj
 *
 *
 * The class GameActivity provides methods for the game activity
 * which is being used as the screen for the actual game.
 *
 * @author Daniel Kostuj
 *
 */
public class GameActivity extends AppCompatActivity {
    long timeTotal, timeWhileGreen;
    boolean tooEarly = false;
    List<TextView> statsList;

    static Handler handler = new Handler();


    private void changeViewState(View v, boolean clickable, int vis) {
        v.setClickable(clickable);
        v.setVisibility(vis);
    }

    private void changeViewState(View v, boolean clickable) {
        changeViewState(v, clickable, v.getVisibility());
    }

    private void disableInput() {
        changeViewState(findViewById(R.id.redPlane), false);
        changeViewState(findViewById(R.id.greenPlane), false);
    }

    private TextView deliverEndTitle() {
        TextView textEndFail = findViewById(R.id.textEnd);
        Button btn = findViewById(R.id.restartButton);

        btn.setVisibility(View.VISIBLE);
        textEndFail.setVisibility(View.VISIBLE);

        return textEndFail;
    }

    public void failGame(View v) {
        tooEarly = true;
        disableInput();
        deliverEndTitle().setText(R.string.lose);
    }

    public void winGame(View v) {
        disableInput();

        // this calculates the time elasped while green plane was untouched
        timeWhileGreen = SystemClock.elapsedRealtime() - timeTotal;
        deliverEndTitle().setText(getString(R.string.win));

        // display time and rank statistics
        statsList = new ArrayList<>();
        statsList.add((TextView) findViewById(R.id.yourTime));
        statsList.add((TextView) findViewById(R.id.timePlayer));
        statsList.add((TextView) findViewById(R.id.yourRank));
        statsList.add((TextView) findViewById(R.id.rankPlayer));

        statsList.get(1).setText(totalTimeToString());
        statsList.get(3).setText(rankText());

        for (TextView temp: statsList)
            temp.setVisibility(View.VISIBLE);
    }

    public void restartGame(View v) {
        startActivity(new Intent(this, GameActivity.class));
        finish();
    }

    private String totalTimeToString() {
        return timeWhileGreen + " " + getString(R.string.time);
    }

    private String rankText() {
        // strings for ranks are saved in res/values/strings.xml
        if (timeWhileGreen <= 120)
            return getString(R.string.rankBest);
        else if (timeWhileGreen <= 180)
            return getString(R.string.rankSecondBest);
        else if (timeWhileGreen <= 350)
            return getString(R.string.rankThirdBest);
        else if (timeWhileGreen <= 550)
            return getString(R.string.rankMid1);
        else if (timeWhileGreen <= 760)
            return getString(R.string.rankMid2);
        else if (timeWhileGreen <= 970)
            return getString(R.string.rankMid3);
        else if (timeWhileGreen <= 1220)
            return getString(R.string.rankMid4);
        else if (timeWhileGreen <= 1550)
            return getString(R.string.rankMid5);
        else if (timeWhileGreen <= 1900)
            return getString(R.string.rankThirdWorst);
        else if (timeWhileGreen <= 2400)
            return getString(R.string.rankSecondWorst);
        else
            return getString(R.string.rankWorst);
    }

    /**
     * This Runnable is responsible for switching the red to the green plane.
     *
     * @return Runnable with switch
     */
    private Runnable createSwitchTimer() {
        return new Runnable() {
            @Override
            public void run() {
                // only enable green plane when red plane has not been pressed
                if (!tooEarly) {
                    // take a snapchat of current time for further subtraction
                    timeTotal = SystemClock.elapsedRealtime();
                    changeViewState(findViewById(R.id.greenPlane), true, View.VISIBLE);
                }
            }
        };
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // create random value between 2000 and 9000
        // This value will be used for the timed switch (in ms)
        long timeColorSwitch = ((long) (Math.random()*8)+2)*1000;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // create a timed switch
        handler.postDelayed(createSwitchTimer(), timeColorSwitch);
    }
}
