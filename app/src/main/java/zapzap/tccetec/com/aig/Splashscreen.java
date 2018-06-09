package zapzap.tccetec.com.aig;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import zapzap.tccetec.com.aig.R;

public class Splashscreen extends Activity {

    private ImageView logo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

    logo = findViewById(R.id.logoAig);

        final Intent intent = new Intent(Splashscreen.this, LoginActivity.class);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransition);
        logo.startAnimation(myanim);
        Thread timer = new Thread(){

            public void run(){

                try{
                    sleep(2000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    startActivity(intent);
                    finish();
                }
            }

        };
        timer.start();
    }

}

