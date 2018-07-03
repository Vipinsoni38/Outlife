package boat.golden.outlife;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login_main extends AppCompatActivity {
    LoginButton flogin;
    CallbackManager mCallbackManager;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login_main);
        mAuth=FirebaseAuth.getInstance();

//--fb Key Hash
/*
            try {
                PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    String hashKey = new String(Base64.encode(md.digest(), 0));
                    Log.e("fb key hash", "printHashKey() Hash Key: " + hashKey);
                }
            } catch (NoSuchAlgorithmException e) {
            } catch (Exception e) {
            }

*/


        flogin=findViewById(R.id.login_button);
        flogin.setReadPermissions("email", "public_profile");
        mCallbackManager=CallbackManager.Factory.create();
        flogin.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("error check", "001");

                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                // ...
                Toast.makeText(Login_main.this,"Cancled",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Login_main.this,"Unexceptional Error",Toast.LENGTH_SHORT).show();
                // ...
            }
        });


    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.e("error check", "002");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Yup Baby Logging In");
        progressDialog.setCancelable(false);
        progressDialog.show();



        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Log.e( "error check",credential.toString());

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
                            SharedPreferences.Editor editor=sharedPreferences.edit();
                            editor.putBoolean("IsLogin",true);
                            editor.commit();
                            progressDialog.dismiss();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login_main.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }

                });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("error check", "003");

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        //super.onActivityResult(requestCode, resultCode, data);
    }

}
