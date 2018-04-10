package com.sues.wangyi.tetris;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sues.wangyi.tetris.tetris.BlockUnit;
import com.sues.wangyi.tetris.tetris.NextBlockView;
import com.sues.wangyi.tetris.tetris.TetrisViewAW;

import java.util.List;

public class TetrisActivityAW extends Activity {
    private static final int SHOW_SCORE = 1;
    private static int finalScore = 0;
    private NextBlockView nextBlockView;
    private TetrisViewAW tetrisViewAW;
    //private TextView gameStatusTip;
    public TextView score;
    private static boolean pauseFlag;
    public RelativeLayout relativeLayout;
    private static int bgNum = 0;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SHOW_SCORE:
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification.Builder builder = new Notification.Builder(TetrisActivityAW.this);
                    PendingIntent contentIntent = PendingIntent.getActivities(TetrisActivityAW.this,0,new Intent[]{
                            new Intent(TetrisActivityAW.this,TetrisActivityAW.class)},PendingIntent.FLAG_CANCEL_CURRENT);
                    builder.setContentIntent(contentIntent).setSmallIcon(R.drawable.ic_launcher)
                            .setContentTitle("分数为:")
                            .setContentText(Integer.toString(finalScore));
                    Notification notification = builder.build();
                    manager.notify(1,notification);
                    //Log.d("1","Handle started");
                    break;
                default: break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        relativeLayout = (RelativeLayout)this.findViewById(R.id.bgLayout);
        nextBlockView = (NextBlockView) findViewById(R.id.nextBlockView1);
        tetrisViewAW = (TetrisViewAW) findViewById(R.id.tetrisViewAW1);
        tetrisViewAW.setFather(this);
        score = (TextView) findViewById(R.id.score);
        changeBg();
    }

    public void showMessage(){
        new Runnable(){
            @Override
            public void run() {
                Message message = new Message();
                message.what = SHOW_SCORE;
                handler.sendMessage(message);
            }
        }.run();
    }

    public void setNextBlockView(List<BlockUnit> blockUnits, int div_x) {
        nextBlockView.setBlockUnits(blockUnits, div_x);
    }

    public void changeBg(){
        if (bgNum>3){
            bgNum = 0;
        }
        switch (bgNum){
            case 0: relativeLayout.setBackgroundResource(R.drawable.bgi00); break;
            case 1: relativeLayout.setBackgroundResource(R.drawable.bgi01); break;
            case 2: relativeLayout.setBackgroundResource(R.drawable.bgi02); break;
            case 3: relativeLayout.setBackgroundResource(R.drawable.bgi03); break;
        }
        bgNum ++;
    }

    /**
     * 开始游戏
     *
     * @param view
     */

    public void startGame(View view) {
        pauseFlag = false;
        tetrisViewAW.startGame();
        changeBg();
        //gameStatusTip.setText("游戏运行中");
    }

    /**
     * 暂停游戏
     */
    public void pauseGame(View view) {
        if (pauseFlag){
            pauseFlag = false;
            tetrisViewAW.continueGame();
        }
        else{
            pauseFlag = true;
            tetrisViewAW.pauseGame();
        }

        //gameStatusTip.setText("游戏已暂停");
    }


    /**
     * 停止游戏
     */
    public void stopGame(View view) {
        finalScore = tetrisViewAW.stopGame();
        Log.d("score:",Integer.toString(finalScore)+"分");
        showMessage();
        score.setText("" + 0);
        //gameStatusTip.setText("游戏已停止");
    }

    /**
     * 向左滑动
     */
    public void toLeft(View view) {
        tetrisViewAW.toLeft();
    }

    /**
     * 向右滑动
     */
    public void toRight(View view) {
        tetrisViewAW.toRight();
    }

    /**
     * 向右滑动
     */
    public void toRoute(View view) {
        tetrisViewAW.route();
    }

    public void toDown(View view){
        TetrisViewAW.speedUpFlag = 1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tetrisViewAW != null) {
            finalScore = tetrisViewAW.stopGame();
        }
    }



}
