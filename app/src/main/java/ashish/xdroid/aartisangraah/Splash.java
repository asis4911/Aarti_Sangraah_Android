package ashish.xdroid.aartisangraah;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity{
    private int RUN_TIMEOUT = 2000;
    TextView heading1,heading2,version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        heading2 = findViewById(R.id.tv2);
        version = findViewById(R.id.version);

        Typeface head2 = Typeface.createFromAsset(getAssets(), "fonts/vesper.ttf");
        heading2.setTypeface(head2);

        Typeface face1 = Typeface.createFromAsset(getAssets(), "fonts/vesper.ttf");
        version.setTypeface(face1);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent Splash = new Intent(Splash.this,MainActivity.class);
                startActivity(Splash);
                finish();
            }

        },RUN_TIMEOUT);

    }
}