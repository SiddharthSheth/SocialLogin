package ln.sociallogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.Fabric;

public class LoginTwitterActivity extends AppCompatActivity {

    private static final String TWITTER_KEY = "Xq4SVoBIjyMnWv9kEdsvMfVjC";
    private static final String TWITTER_SECRET = "uiFgR0VpvXL9hsDkGWrzPLHa2t2cya6reAnU1mPml98xPYZRzx";
    private TwitterLoginButton btnLoginTwitter;
    private TextView txtUserName;
    private ImageView ivProfileImg;
    private String username;
    private String profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing TwitterAuthConfig, these two line will also
        // added automatically while configuration we did
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login_twitter);

        btnLoginTwitter = (TwitterLoginButton) findViewById(R.id.btn_login_twitter);
        txtUserName = (TextView) findViewById(R.id.txt_username);
        ivProfileImg = (ImageView) findViewById(R.id.iv_profileimg);

        btnLoginTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                login(result);
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Adding the login result back to the button
        btnLoginTwitter.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * The login function accepting the result object
     *
     * @param result contain user data
     */
    public void login(Result<TwitterSession> result) {

        //Creating a twitter session with result's data
        TwitterSession session = result.data;

        username = session.getUserName();


        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false).enqueue(new Callback<User>() {
            @Override
            public void success(Result<User> userResult) {
                try {
                    User user = userResult.data;

                    //replace is to get high resolution image
                    profileImage = user.profileImageUrl.replace("_normal","");

                    txtUserName.setText(username);

                    Glide.with(getApplicationContext())
                            .load(profileImage)
                            .thumbnail(0.5f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(ivProfileImg);

                    Log.d("Profile ", profileImage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(TwitterException e) {

            }

        });


        //This code will fetch the profile image URL
        //Getting the account service of the user logged in
        /*Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void failure(TwitterException e) {
                        //If any error occurs handle it here
                    }

                    @Override
                    public void success(Result<User> userResult) {
                        //If it succeeds creating a User object from userResult.data
                        User user = userResult.data;

                        //Getting the profile image url
                        profileImage = user.profileImageUrl;

                        *//*Glide.with(getApplicationContext())
                                .load(profileImage)
                                .thumbnail(0.5f)
                                .crossFade()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(ivProfileImg);*//*

                        txtUserName.setText(username);

                    }
                });*/


    }

}
