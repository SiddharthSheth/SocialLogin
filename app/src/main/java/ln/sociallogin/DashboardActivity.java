package ln.sociallogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "sfddvgdfvdfv"; //your twitter key
    private static final String TWITTER_SECRET = "fdgdvdfvbdfvbfdbvf" //your twitter secret key;


    private Button btnSigninFb, btnSigninGoogle, btnSigninTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_dashboard);

        btnSigninFb = (Button) findViewById(R.id.btn_signinwithfacebook);
        btnSigninGoogle = (Button) findViewById(R.id.btn_signinwithgoogle);
        btnSigninTwitter = (Button) findViewById(R.id.btn_signinwithtwitter);

        btnSigninFb.setOnClickListener(this);
        btnSigninGoogle.setOnClickListener(this);
        btnSigninTwitter.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn_signinwithfacebook:

                Intent intentFacebook = new Intent(DashboardActivity.this,LoginActivity.class);
                startActivity(intentFacebook);

                break;

            case R.id.btn_signinwithgoogle:

                Intent intentGoogle = new Intent(DashboardActivity.this,LoginGoogleActivity.class);
                startActivity(intentGoogle);

                break;

            case R.id.btn_signinwithtwitter:

                Intent intentTwitter = new Intent(DashboardActivity.this,LoginTwitterActivity.class);
                startActivity(intentTwitter);

                break;

        }

    }
}
