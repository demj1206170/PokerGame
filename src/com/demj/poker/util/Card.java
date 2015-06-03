package com.demj.poker.util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import android.util.Log;

import com.demj.pokergame.R;



public class Card {

	public static final String TAG="Card: ";
	public static final int TYPE_SQUARE=0;   //·½¿é
	public static final int TYPE_QUINCUNX=1;//Ã·»¨
	public static final int TYPE_HEARTS=2; //ºìÌÒ
	public static final int TYPE_SPADE=3; //ºÚÌÒ
	public static final int CARD_2=0;
	public static final int CARD_3=1;
	public static final int CARD_4=2;
	public static final int CARD_5=3;
	public static final int CARD_6=4;
	public static final int CARD_7=5;
	public static final int CARD_8=6;
	public static final int CARD_9=7;
	public static final int CARD_10=8;
	public static final int CARD_J=9;
	public static final int CARD_Q=10;
	public static final int CARD_K=11;
	public static final int CARD_A=12;
	public static final String[] CARD_NAME={"2","3","4","5","6","7","8","9","10",
											 "J","Q","K","A"};
	public static final String[] TYPE_NAME={"·½¿é","Ã·»¨","ºìÌÒ","ºÚÌÒ"};
	
	public static final int[] SQUARES={R.drawable.square_2,R.drawable.square_3,R.drawable.square_4,
		R.drawable.square_5,R.drawable.square_6,R.drawable.square_7,R.drawable.square_8,R.drawable.square_9,
		R.drawable.square_10,R.drawable.square_11,R.drawable.square_12,R.drawable.square_12,R.drawable.square_14};
	
	public static final int[] QUINCUNXs={R.drawable.quincunx_2,R.drawable.quincunx_3,R.drawable.quincunx_4,
		R.drawable.quincunx_5,R.drawable.quincunx_6,R.drawable.quincunx_7,R.drawable.quincunx_8,R.drawable.quincunx_9,
		R.drawable.quincunx_10,R.drawable.quincunx_11,R.drawable.quincunx_12,R.drawable.quincunx_12,R.drawable.quincunx_14};
	
	public static final int[] HEARTS={R.drawable.hearts_2,R.drawable.hearts_3,R.drawable.hearts_4,
		R.drawable.hearts_5,R.drawable.hearts_6,R.drawable.hearts_7,R.drawable.hearts_8,R.drawable.hearts_9,
		R.drawable.hearts_10,R.drawable.hearts_11,R.drawable.hearts_12,R.drawable.hearts_12,R.drawable.hearts_14};
	
	public static final int[] SPADES={R.drawable.spade_2,R.drawable.spade_3,R.drawable.spade_4,
		R.drawable.spade_5,R.drawable.spade_6,R.drawable.spade_7,R.drawable.spade_8,R.drawable.spade_9,
		R.drawable.spade_10,R.drawable.spade_11,R.drawable.spade_12,R.drawable.spade_12,R.drawable.spade_14};
	
	private final int type;
	private final int card;
	private int cardImageResId=-1;
	private boolean isAvailable=true;
	public Card(int card, int type) {
		// TODO Auto-generated constructor stub
		if(type>=TYPE_SQUARE&&type<=TYPE_SPADE)
		{
			if(card>=CARD_2&&card<=CARD_A)
			{
				this.card=card;
				this.type=type;
			}
			else {
				this.card=-1;
				this.type=-1;
				System.out.println("card out of area");
				return;
			}
		}
		else {
			this.card=-1;
			this.type=-1;
			System.out.println("type is wrong");
			return;
		}
		
	
	}
	public void setUnAvailable()
	{
		this.isAvailable=false;
	}
	public boolean getIsAvailable()
	{
		return isAvailable;
	}
	public int[] getCardAndType()
	{
		return new int[]{card,type};
	}
	public int getCardNum()
	{
		return card;
	}
	public int getType()
	{
		return type;
	}
	@Override
	public String toString()
	{
		return "card "+CARD_NAME[card]+" , "+TYPE_NAME[type]; 
	}
	public static class CardList<T> extends ArrayList<T> implements Comparator<T>
	{
		@Override
		public int compare(T t1, T t2) {
			// TODO Auto-generated method stub
			Card card1=(Card)t1;
			Card card2=(Card)t2;
			int result=0;
			if(card1.getCardNum()<card2.getCardNum()) result=-1;
			else if(card1.getCardNum()==card2.getCardNum()) result=0;
			else result=1;
			return result;
		}
		@Override
		public boolean addAll(Collection c)
		{
			boolean state=super.addAll(c);
			if(state)
			  Collections.sort(this,this);
			return state;
		}
	}
	public void setCardImageResId(int id)
	{
		this.cardImageResId=id;
	}
	public int getCardImageResId()
	{
		return cardImageResId;
	}
	public  static int getImageIdByCard(Card card)
	{
		synchronized (TAG) {
		
			int id = 0;
			Log.e(TAG, "card num and type is "+card);
			switch(card.getType())
			{
				case TYPE_HEARTS:id=HEARTS[card.getCardNum()];break;
				case TYPE_QUINCUNX:id=QUINCUNXs[card.getCardNum()];break;
				case TYPE_SPADE:id=SPADES[card.getCardNum()];break;
				case TYPE_SQUARE:id=SQUARES[card.getCardNum()];break;
				
			}
			 
			Log.e(TAG," id is "+Integer.toHexString(id));
			
			return id;
		}
		}
 
	
}
