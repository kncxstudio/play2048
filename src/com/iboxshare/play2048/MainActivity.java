package com.iboxshare.play2048;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView scoreTextView=null;
	private int score = 0;
	private static MainActivity mainActivity=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		scoreTextView=(TextView)findViewById(R.id.score);
	}
	public MainActivity(){
		mainActivity=this;
	}
	public static MainActivity getMainActivity(){
		return mainActivity;
	}
	public void clearScore(){
		score = 0;
	}
	public int showScore(){
		scoreTextView.setText(score+"");
		return score;
	}
	public void addScore(int s){
		score+=s;
		showScore();
	}

}
