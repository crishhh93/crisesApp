package transportapp.cris.com.transportapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Timer;

public class IntroActivity extends AppCompatActivity {

    private final Context context = IntroActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //startService(new Intent(this, SyncInformationService.class));
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        getIntent().setFlags(getIntent().getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);

        final int interval = 1500;
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){

            public void run() {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);
    }
}
