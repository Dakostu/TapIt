
package dakostu.tapit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Tap It! by Daniel Kostuj
 *
 *
 * The class MainActivity provides methods for the main activity
 * which is being used as an introduction screen.
 *
 * @author Daniel Kostuj
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * switch from MainActivity to GameActivity
     * in other words: Switch to game screen
     * @param v current View
     */
    public void startGame(View v) {
        startActivity(new Intent(this, GameActivity.class));
    }
}
