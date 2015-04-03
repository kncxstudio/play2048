package com.iboxshare.play2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Card extends FrameLayout {
	private TextView label=null;
	public Card(Context context) {
		super(context);
		
		//这一段没看懂
		
		label = new TextView(getContext());
		label.setTextSize(35);
		label.setGravity(Gravity.CENTER);
		label.setBackgroundColor(0x33ffffff);//半透明白色
		LayoutParams lp = new LayoutParams(-1,-1);
		lp.setMargins(10, 10, 0, 10);
		addView(label,lp);
		
		setNum(0);
	}
	

	private int num = 0;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
		if(num<=0){
			label.setText("");
		}else{
			label.setText(num+"");
		}
	}
	public boolean equals(Card card){
		return getNum()==card.getNum();
	}
}
