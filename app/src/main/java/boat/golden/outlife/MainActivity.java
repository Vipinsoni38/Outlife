package boat.golden.outlife;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences=getSharedPreferences("login",MODE_PRIVATE);
        Boolean islogin=sharedPreferences.getBoolean("IsLogin",false);
        if (!islogin)
        {
        Intent i=new Intent(this,Login_main.class);
        startActivity(i);}






    }
}
