package com.iimbvista.iimbvista;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.iimbvista.iimbvista.MainActivity;

public class SplashActivity extends AppCompatActivity {

  ImageView splash_image;

  /* Duration of wait */
  private final int SPLASH_DISPLAY_LENGTH = 3000;

  @Override
  protected void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    this.setContentView(R.layout.activity_splash);

    splash_image=findViewById(R.id.splash_image);
    Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
    splash_image.startAnimation(myFadeInAnimation);


    /* New Handler to start the Menu-Activity
     * and close this Splash-Screen after some seconds.*/
    new Handler().postDelayed(new Runnable(){
      @Override
      public void run() {
        /* Create an Intent that will start the Menu-Activity. */
        Intent intent =new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
      }

    }, SPLASH_DISPLAY_LENGTH);



  }

}