package com.iboxshare.play2048;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

public class GameView extends GridLayout {

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		startGame();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		startGame();
	}

	public GameView(Context context) {
		super(context);
		startGame();
	}

	private void startGame() {
		
		setColumnCount(4);//設置4列
		setBackgroundColor(0xffEEE0C6);//設置背景色
		
		setOnTouchListener(new OnTouchListener() {
			float startX, startY, offsetX, offsetY;

			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				switch (arg1.getAction()) {
				case MotionEvent.ACTION_DOWN:
					startX = arg1.getX();
					startY = arg1.getY();
					break;
				case MotionEvent.ACTION_UP:
					offsetX = arg1.getX() - startX;
					offsetY = arg1.getY() - startY;
					
				default:
					break;
				}
				return true;// 如果return false只能监听到touch_DOWN
			}
		});
	}

	private void addRandomNum(){
		emptyPoint.clear();//清空随机数
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if(cardMap[i][j].getNum()<=0){
					emptyPoint.add(new Point(i,j));
				}
			}
		}
		Point p = emptyPoint.remove((int)(Math.random()*emptyPoint.size()));//??????????????????????????????????????
		//2和4生成概率9:1
		cardMap[p.x][p.y].setNum(Math.random()>0.1?2:4);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int cardSize=((Math.min(w, h))-10)/4;
		addCards(cardSize, cardSize);
		GoGoGo();
	}
	
	private void addCards(int cardWidth,int cardHeight){
		Card c=null;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				c=new Card(getContext());
				c.setNum(0);
				addView(c, cardWidth,cardHeight);//addView?????????
				cardMap[i][j]=c;
			}
		}
	}
	private void GoGoGo(){
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 0; j++) {
				emptyPoint.add(new Point(i,j));
			}
		}
		addRandomNum();
		addRandomNum();
	}
	private Card[][] cardMap=new Card[4][4];
	private List<Point> emptyPoint=new ArrayList<Point>();
}
