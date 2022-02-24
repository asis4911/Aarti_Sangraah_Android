package ashish.xdroid.aartisangraah.player;

import static ashish.xdroid.aartisangraah.MainActivity.gridView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.concurrent.TimeUnit;

import ashish.xdroid.aartisangraah.MainActivity;
import ashish.xdroid.aartisangraah.R;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    static MediaPlayer player;
    final int[] resID = {R.raw.god_ganesha, R.raw.god_hanuman_lala_ki, R.raw.hanuman_chalisa, R.raw.god_krishna_aarti,
            R.raw.god_ramchandra_krupalu_bhajman, R.raw.god_sai_baba, R.raw.god_satyanarayn,
            R.raw.god_shiva_onkara, R.raw.goddess_radha, R.raw.godess_durga, R.raw.godess_gayatri,
            R.raw.godess_jag_janani, R.raw.godess_kali, R.raw.godess_saraswati, R.raw.godess_tulsi,
            R.raw.om_jai_jagdish_hare, R.raw.sukh_karta_dukh_harta};
    Bitmap bitmap;
    GridView musicFiles;
    TextView song_name, artist_name, duration_played, duration_total;
    ImageView cover_art, nextBtn, prevBtn, backBtn, shufflBtn, repeatBtn, menubtn;
    FloatingActionButton playPauseBtn;
    SeekBar seekBar;
    int position = -1;
    private String[] data;
    private Thread playThread, prevThread, nextThread;
    private Handler handler = new Handler();
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        initViews();
        getIntentMethod();


        //Advertiesment
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.app_inter));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        player.setOnCompletionListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (player != null && fromUser) {
                    player.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (player != null) {
                    int mCurrentPosition = player.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    duration_played.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private String formattedTime(int mCurrentPosition) {
        String totalout = "";
        String totalnew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalout = minutes + ":" + seconds;
        totalnew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1) {
            return totalnew;
        } else return totalout;
    }

    private void initViews() {
        song_name = findViewById(R.id.song_name);
        artist_name = findViewById(R.id.song_artist);
        duration_played = findViewById(R.id.duration_played);
        duration_total = findViewById(R.id.duration_total);

        cover_art = findViewById(R.id.cover_art);
        nextBtn = findViewById(R.id.next_btn);
        prevBtn = findViewById(R.id.prev_btn);
        backBtn = findViewById(R.id.back_btn);
        shufflBtn = findViewById(R.id.shuffle);
        repeatBtn = findViewById(R.id.id_repeat);
        playPauseBtn = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seekBar);
        menubtn = findViewById(R.id.menu_btn);
    }


    private void setPictureInBooksNext(int i) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        position = i;
        switch (position) {
            case 0:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ganesha);
                ImageAnimation(this, cover_art, bitmap);
                String music1 = getResources().getString(R.string.arti1);
                song_name.setText(music1);
                palleteApi();
                break;
            case 1:
                artist_name.setText(R.string.app_name);
                //String path1 = "android.resource://" + getPackageName() + "/" + R.raw.music2;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hanuman);
                ImageAnimation(this, cover_art, bitmap);
                String music2 = getResources().getString(R.string.arti2);
                song_name.setText(music2);
                palleteApi();
                break;
            case 2:
                artist_name.setText(R.string.app_name);
                //String path1 = "android.resource://" + getPackageName() + "/" + R.raw.music2;
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hanuman_ji);
                ImageAnimation(this, cover_art, bitmap);
                String music2_1 = getResources().getString(R.string.arti2_1);
                song_name.setText(music2_1);
                palleteApi();
                break;
            case 3:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.krishna);
                ImageAnimation(this, cover_art, bitmap);
                String music3 = getResources().getString(R.string.arti3);
                song_name.setText(music3);
                palleteApi();
                break;
            case 4:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ram);
                ImageAnimation(this, cover_art, bitmap);
                String music4 = getResources().getString(R.string.arti4);
                song_name.setText(music4);
                palleteApi();
                break;
            case 5:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sai_baba);
                ImageAnimation(this, cover_art, bitmap);
                String music5 = getResources().getString(R.string.arti5);
                song_name.setText(music5);
                palleteApi();
                break;
            case 6:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.satyanarayan);
                ImageAnimation(this, cover_art, bitmap);
                String music6 = getResources().getString(R.string.arti6);
                song_name.setText(music6);
                palleteApi();
                break;

            case 7:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shiva);
                ImageAnimation(this, cover_art, bitmap);
                String music7 = getResources().getString(R.string.arti7);
                song_name.setText(music7);
                palleteApi();
                break;

            case 8:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.radha);
                ImageAnimation(this, cover_art, bitmap);
                String music8 = getResources().getString(R.string.arti8);
                song_name.setText(music8);
                palleteApi();
                break;

            case 9:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.durga_ma);
                ImageAnimation(this, cover_art, bitmap);
                String music9 = getResources().getString(R.string.arti9);
                song_name.setText(music9);
                palleteApi();
                break;

            case 10:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gayatri_ma);
                ImageAnimation(this, cover_art, bitmap);
                String music10 = getResources().getString(R.string.arti10);
                song_name.setText(music10);
                palleteApi();
                break;

            case 11:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.jag_janani);
                ImageAnimation(this, cover_art, bitmap);
                String music11 = getResources().getString(R.string.arti11);
                song_name.setText(music11);
                palleteApi();
                break;

            case 12:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kali_ma);
                ImageAnimation(this, cover_art, bitmap);
                String music12 = getResources().getString(R.string.arti12);
                song_name.setText(music12);
                palleteApi();
                break;

            case 13:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sarawati_ma);
                ImageAnimation(this, cover_art, bitmap);
                String music13 = getResources().getString(R.string.arti13);
                song_name.setText(music13);
                palleteApi();
                break;

            case 14:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tulsi_ma);
                ImageAnimation(this, cover_art, bitmap);
                String music14 = getResources().getString(R.string.arti14);
                song_name.setText(music14);
                palleteApi();
                break;

            case 15:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.om_jai_jagdish);
                ImageAnimation(this, cover_art, bitmap);
                String music15 = getResources().getString(R.string.arti15);
                song_name.setText(music15);
                palleteApi();
                break;

            case 16:
                artist_name.setText(R.string.app_name);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sukh_harta);
                ImageAnimation(this, cover_art, bitmap);
                String music16 = getResources().getString(R.string.arti6);
                song_name.setText(music16);
                palleteApi();
                break;
        }

    }

    public void palleteApi() {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                Palette.Swatch swatch = palette.getDominantSwatch();
                if (swatch != null) {
                    ImageView gradient = findViewById(R.id.imageViewGradient);
                    RelativeLayout mContainer = findViewById(R.id.mContainer);
                    gradient.setBackgroundResource(R.drawable.gradient_bg);
                    mContainer.setBackgroundResource(R.drawable.main_bg);
                    GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP
                            , new int[]{swatch.getRgb(), 0x00000000});
                    gradient.setBackground(gradientDrawable);
                    GradientDrawable gradientDrawableBg = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP
                            , new int[]{swatch.getRgb(), swatch.getRgb()});
                    mContainer.setBackground(gradientDrawableBg);
                    gradient.setBackground(gradientDrawable);
                    song_name.setTextColor(swatch.getTitleTextColor());
                    artist_name.setTextColor(swatch.getBodyTextColor());
                } else {
                    ImageView gradient = findViewById(R.id.imageViewGradient);
                    RelativeLayout mContainer = findViewById(R.id.mContainer);
                    gradient.setBackgroundResource(R.drawable.gradient_bg);
                    mContainer.setBackgroundResource(R.drawable.main_bg);
                    GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP
                            , new int[]{0xff000000, 0x00000000});
                    gradient.setBackground(gradientDrawable);
                    GradientDrawable gradientDrawableBg = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP
                            , new int[]{0xff000000, 0xff000000});
                    mContainer.setBackground(gradientDrawableBg);
                    gradient.setBackground(gradientDrawable);
                    song_name.setTextColor(Color.WHITE);
                    artist_name.setTextColor(Color.DKGRAY);
                }
            }
        });

    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position", -1);
        musicFiles = gridView;
        if (musicFiles != null) {
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            setPictureInBooksNext(position);
        }
        if (player != null) {
            player.stop();
            player.release();
            player = MediaPlayer.create(this, resID[position]);
            player.start();
            int duration = player.getDuration();
            String time = String.format("%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            );
            duration_total.setText(time);

        } else {
            player = MediaPlayer.create(this, resID[position]);
            player.start();
            int duration = player.getDuration();
            String time = String.format("%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            );
            duration_total.setText(time);
        }
        seekBar.setMax(player.getDuration() / 1000);
    }

    @Override
    protected void onResume() {
        playThreadBtn();
        prevThreadBtn();
        nextThreadBtn();
        super.onResume();
    }

    private void nextThreadBtn() {
        nextThread = new Thread() {
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
            position = ((position + 1) % musicFiles.getCount());
            player = MediaPlayer.create(this, resID[position]);
            setPictureInBooksNext(position);

            seekBar.setMax(player.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (player != null) {
                        int mCurrentPosition = player.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            player.setOnCompletionListener(this);
            playPauseBtn.setBackgroundResource(R.drawable.ic_pause);
            player.start();
            int duration = player.getDuration();
            String time = String.format("%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            );
            duration_total.setText(time);
        } else {
            player.stop();
            player.release();
            position = (position + 1);
            player = MediaPlayer.create(this, resID[position]);
            setPictureInBooksNext(position);

            seekBar.setMax(player.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (player != null) {
                        int mCurrentPosition = player.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            player.setOnCompletionListener(this);
            playPauseBtn.setBackgroundResource(R.drawable.ic_play);
            int duration = player.getDuration();
            String time = String.format("%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            );
            duration_total.setText(time);

        }
    }

    private void prevThreadBtn() {
        prevThread = new Thread() {
            @Override
            public void run() {
                super.run();
                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void prevBtnClicked() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
            position = ((position - 1) < 0 ? (musicFiles.getCount() - 1) : (position - 1));
            player = MediaPlayer.create(this, resID[position]);
            setPictureInBooksNext(position);

            seekBar.setMax(player.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (player != null) {
                        int mCurrentPosition = player.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            player.setOnCompletionListener(this);
            playPauseBtn.setBackgroundResource(R.drawable.ic_pause);
            player.start();
            int duration = player.getDuration();
            String time = String.format("%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            );
            duration_total.setText(time);
        } else {
            player.stop();
            player.release();
            position = ((position - 1) < 0 ? (musicFiles.getCount() - 1) : (position - 1));
            player = MediaPlayer.create(this, resID[position]);
            setPictureInBooksNext(position);

            seekBar.setMax(player.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (player != null) {
                        int mCurrentPosition = player.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            player.setOnCompletionListener(this);
            playPauseBtn.setBackgroundResource(R.drawable.ic_play);
            int duration = player.getDuration();
            String time = String.format("%02d : %02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            );
            duration_total.setText(time);

        }
    }

    private void playThreadBtn() {
        playThread = new Thread() {
            @Override
            public void run() {
                super.run();
                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playPauseBtnClicked() {
        if (player.isPlaying()) {
            playPauseBtn.setImageResource(R.drawable.ic_play);
            player.pause();
            seekBar.setMax(player.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (player != null) {
                        int mCurrentPosition = player.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        } else {
            playPauseBtn.setImageResource(R.drawable.ic_pause);
            player.start();
            seekBar.setMax(player.getDuration() / 1000);

            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (player != null) {
                        int mCurrentPosition = player.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                    }
                    handler.postDelayed(this, 1000);
                }
            });
        }
    }

    public void ImageAnimation(final Context context, final ImageView imageView, final Bitmap bitmap) {
        Animation animOut = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
        final Animation animoIn = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        animOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Glide.with(context).load(bitmap).into(imageView);
                animoIn.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imageView.startAnimation(animoIn);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(animOut);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        nextBtnClicked();
        if (player != null) {
            player = MediaPlayer.create(this, resID[position]);
            player.start();
            player.setOnCompletionListener(this);
        }
    }

    public void backButtonAction(View v) {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            super.onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            super.onBackPressed();
        }

    }
}