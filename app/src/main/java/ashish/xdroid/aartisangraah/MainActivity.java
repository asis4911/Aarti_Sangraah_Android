package ashish.xdroid.aartisangraah;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ashish.xdroid.aartisangraah.player.PlayerActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static GridView gridView;
    String[] Book;
    int[] imageID = {R.drawable.ganesha, R.drawable.hanuman, R.drawable.hanuman_ji, R.drawable.krishna, R.drawable.ram,
            R.drawable.sai_baba, R.drawable.satyanarayan, R.drawable.shiva, R.drawable.radha,
            R.drawable.durga_ma, R.drawable.gayatri_ma, R.drawable.jag_janani, R.drawable.kali_ma, R.drawable.sarawati_ma,
            R.drawable.tulsi_ma, R.drawable.om_jai_jagdish, R.drawable.sukh_harta};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        initialize();
        Book = getResources().getStringArray(R.array.Book_name);
        CustomGrid adapter = new CustomGrid(MainActivity.this, Book, imageID);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PlayerActivity.class);
                intent.putExtra("position", i);
                startActivity(intent);
            }
        });
    }

    private void initialize() {
        gridView = findViewById(R.id.gridView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_linkedin:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.linkedin.com/in/ashish-kumar-5948b4a8/")));
                break;

            case R.id.nav_rateus:
                Toast.makeText(this, "Rate me", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)));
                break;

            case R.id.nav_feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "asis4911@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback app development");
                startActivity(Intent.createChooser(emailIntent, null));
                break;

            case R.id.nav_sendlink:
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
                break;

            case R.id.nav_otherapps:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/collection/cluster?clp=igNIChkKEzgxODUyNTc3NDI1NzE2MTk4MTcQCBgDEikKI2FzaGlzaC54ZHJvaWQuc2VsZW5pdW1qYXZhaW50ZXJ2aWV3EAEYAxgB:S:ANO1ljI6w1A&gsr=CkuKA0gKGQoTODE4NTI1Nzc0MjU3MTYxOTgxNxAIGAMSKQojYXNoaXNoLnhkcm9pZC5zZWxlbml1bWphdmFpbnRlcnZpZXcQARgDGAE%3D:S:ANO1ljKmTQI&hl=en_IN&gl=US")));
                break;

            case R.id.nav_developer:
                Intent aboutIntent = new Intent(MainActivity.this, About.class);
                startActivity(aboutIntent);
                break;

            case R.id.nav_privacy:
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://xdroid-privacy-policy.blogspot.com/2020/04/android-apps-from-xdroid-this-privacy.html")));
                break;
        }
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}