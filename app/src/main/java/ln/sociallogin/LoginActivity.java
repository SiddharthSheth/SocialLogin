package ln.sociallogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private CallbackManager mFacebookCallbackManager;
    private FacebookCallback<LoginResult> mfacebookCallback;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private AccessToken accessToken;
    private Profile profile;

    LoginButton loginButton;
    private TextView txtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        mFacebookCallbackManager = CallbackManager.Factory.create();

        // This MUST be placed after the above two lines.

        setContentView(R.layout.activity_login);

        profile = Profile.getCurrentProfile();

        loginButton = (LoginButton)findViewById(R.id.btn_facebooklogin);
        txtUserName = (TextView) findViewById(R.id.txt_username);

        //use this line if you want to require user permission
        loginButton.setReadPermissions("user_friends");

        loginButton.registerCallback(mFacebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                accessToken = loginResult.getAccessToken();

                profile = Profile.getCurrentProfile();

                displayUsername(profile);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {



            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {

                profile = currentProfile;

                displayUsername(currentProfile);

            }
        };

        accessTokenTracker.startTracking();

        profileTracker.startTracking();

    }

    public void displayUsername(Profile profile1)
    {
        if (profile1 != null)
        {
            txtUserName.setText("Welcome  :  "+profile1.getName());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStop() {
        super.onStop();

        accessTokenTracker.stopTracking();

        profileTracker.stopTracking();
    }


    //we override onresume because when the orientation change contents remain as it is.
    @Override
    protected void onResume() {
        super.onResume();

        profile = Profile.getCurrentProfile();
        displayUsername(profile);
    }
}
