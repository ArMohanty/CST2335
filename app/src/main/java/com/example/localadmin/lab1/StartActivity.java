package com.example.localadmin.lab1;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Log.i(ACTIVITY_NAME, "In onCreate()");
        Button b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                // startActivity(intent);
                startActivityForResult(intent, 5);

            }



        } );
        Button button3 = (Button) findViewById(R.id.b3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(ACTIVITY_NAME, "User clicked Start Chat");


                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 5) {
            Log.i(ACTIVITY_NAME, "Returns to StartActivity.onActivityResult");
        }
        if(resultCode== Activity.RESULT_OK){
            String messgPassed = data.getStringExtra("Response");
            CharSequence text = "ListItemsActivity Passed:My Information to Share";
            Toast toast = Toast.makeText(StartActivity.this,text,Toast.LENGTH_LONG);
            toast.show();
            Log.i(ACTIVITY_NAME,messgPassed);
        }
    }
    protected void onResume()
    {
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");
    }
    protected void onStart()
    {
        super.onStart();

        Log.i(ACTIVITY_NAME, "In onStart()");
    }
    protected void onPause()
    {
        super.onPause();

        Log.i(ACTIVITY_NAME, "In onPause()");
    }
    protected void onStop()
    {
        super.onStop();

        Log.i(ACTIVITY_NAME, "In onStop()");

    }
    protected void onDestroy()
    {
        super.onDestroy();

        Log.i(ACTIVITY_NAME, "In onDestroy()");

    }






}
