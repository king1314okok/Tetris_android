package com.sues.wangyi.tetris;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button startGameBtn;
    private Button quitGameBtn;
    private Button turnOffMusicBtn;
    //private Button webView;
    private MediaPlayer bgmusic;
    private static boolean bgmflag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        bgmflag = true;
        bgmusic = MediaPlayer.create(this,R.raw.bgm01);
        //bgmusic.start();

        startGameBtn = (Button) findViewById(R.id.startGameBtn);
        quitGameBtn = (Button) findViewById(R.id.quitGameBtn);
        turnOffMusicBtn = (Button) findViewById(R.id.turnOffMusicBtn);
        //webView = (Button) findViewById(R.id.webViewPageBtn);

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,TetrisActivityAW.class);
                startActivity(intent);
            }
        });

        quitGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bgmusic.stop();
                bgmusic.release();
                finish();
            }
        });

        turnOffMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBgmDialog();
            }
        });

//        webView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this,WebActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    private void showBgmDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("背景音乐").setMessage("是否关闭音乐");
        dialog.setPositiveButton("打开", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bgmflag = true;
                bgmusic.start();
            }
        });
        dialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                bgmflag = false;
                bgmusic.pause();
            }
        });
        dialog.show();
    }


}
