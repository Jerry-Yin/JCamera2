package com.example.jerryyin.jcamera2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.jerryyin.jcamera2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void functionChoose(View v){
        switch (v.getId()){
            case R.id.btn_take_photo:
                Intent intent = new Intent(MainActivity.this, JCameraActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_beauty:
                Intent intent1 = new Intent(MainActivity.this, JBeautyImgActivity.class);
                startActivity(intent1);
                break;

            case R.id.btn_color_matrix:
                Intent intent2 = new Intent(MainActivity.this, ColorMatrixActivity.class);
                startActivity(intent2);
                break;

            default:
                break;
        }
    }

}
