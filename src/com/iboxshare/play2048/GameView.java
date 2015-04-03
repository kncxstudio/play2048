package com.iboxshare.play2048;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
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

		setColumnCount(4);// 設置4列
		setBackgroundColor(0xffEEE0C6);// 設置背景色

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
					if (Math.abs(offsetX) > Math.abs(offsetY)) {
						if (offsetX < -5) {
							swipeLeft();
						} else if (offsetX > 5) {
							swipeRight();
						}
					} else {
						if (offsetY < -5) {
							swipeUp();
						} else if (offsetY > 5) {
							swipeDown();
						}
					}
					break;
				default:
					break;
				}

				return true;// 如果return false只能监听到touch_DOWN
			}
		});
	}

	//添加随机数
	private void addRandomNum() {
		emptyPoint.clear();// 清空随机数
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (cardMap[i][j].getNum() <= 0) {
					emptyPoint.add(new Point(i, j));
				}
			}
		}
		Point p = emptyPoint.remove((int) (Math.random() * emptyPoint.size()));// Point没有搞懂？？？？？？？？？？？
		// 2和4生成概率9:1
		cardMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);
	}

	//调整屏幕比例
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		int cardSize = ((Math.min(w, h)) - 10) / 4;
		addCards(cardSize, cardSize);
		GoGoGo();
	}

	//添加卡片
	private void addCards(int cardWidth, int cardHeight) {
		Card c = null;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				c = new Card(getContext());
				c.setNum(0);
				addView(c, cardWidth, cardHeight);// addView没有搞懂??????????????????????????????
				cardMap[i][j] = c;
			}
		}
	}

	private void GoGoGo() {
		MainActivity.getMainActivity().clearScore();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 0; j++) {
				emptyPoint.add(new Point(i, j));
			}
		}
		addRandomNum();
		addRandomNum();
	}

	// 四个滑动动作
	private void swipeUp() {
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				for (int x1 = x + 1; x1 < 4; x1++) {
					if (cardMap[x1][y].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);
							x--;
							break;
						} else if (cardMap[x][y].equals(cardMap[x1][y])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							cardMap[x1][y].setNum(0);
							checkOver();
							break;
						}

					}
				}
			}
		}
		addRandomNum();
	}

	private void swipeRight() {
		for (int x = 0; x < 4; x++) {
			for (int y = 3; y >= 0; y--) {
				for (int y1 = y - 1; y1 >= 0; y1--) {
					if (cardMap[x][y1].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);
							
							y--;
							break;
						} else if (cardMap[x][y].equals(cardMap[x][y1])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							cardMap[x][y1].setNum(0);
							checkOver();
							break;
						}

					}
				}
			}
		}
		addRandomNum();
	}

	private void swipeLeft() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				for (int y1 = y + 1; y1 < 4; y1++) {
					if (cardMap[x][y1].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x][y1].getNum());
							cardMap[x][y1].setNum(0);
							y--;
							break;
						} else if (cardMap[x][y].equals(cardMap[x][y1])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							
							cardMap[x][y1].setNum(0);
							checkOver();
							break;
						}

					}
				}
			}
		}
		addRandomNum();
	}

	private void swipeDown() {
		for (int y = 0; y < 4; y++) {
			for (int x = 3; x >= 0; x--) {
				for (int x1 = x - 1; x1 >= 0; x1--) {
					if (cardMap[x1][y].getNum() > 0) {
						if (cardMap[x][y].getNum() <= 0) {
							cardMap[x][y].setNum(cardMap[x1][y].getNum());
							cardMap[x1][y].setNum(0);
							x--;
							break;
						} else if (cardMap[x][y].equals(cardMap[x1][y])) {
							cardMap[x][y].setNum(cardMap[x][y].getNum() * 2);
							MainActivity.getMainActivity().addScore(cardMap[x][y].getNum());
							cardMap[x1][y].setNum(0);
							checkOver();
							break;
						}

					}
				}
			}
		}
		addRandomNum();
	}
	//判断游戏是否结束
	private void checkOver(){
		boolean over = true;
		//标签,类似于GOTO语句
		ALL:
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (    (cardMap[x][y].getNum()==0)   ||
						(x>0&&cardMap[x][y].equals(cardMap[x-1][y])   ||
								(x<3&&cardMap[x][y].equals(cardMap[x+1][y]))   ||
										(y>0&&cardMap[x][y].equals(cardMap[x][y-1]))   ||
												(y<3&&cardMap[x][y].equals(cardMap[x][y+1])))) {
					over = false;
					break ALL;
				}
			}
		}
		if(over){
			new AlertDialog.Builder(getContext()).setTitle("Game Over!").setMessage("游戏结束，您的总得分为"+MainActivity.getMainActivity().showScore());
		}
	}
	private Card[][] cardMap = new Card[4][4];
	private List<Point> emptyPoint = new ArrayList<Point>();
}
