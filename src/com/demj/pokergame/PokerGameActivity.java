package com.demj.pokergame;

import java.text.MessageFormat;
import java.util.LinkedList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demj.poker.util.Card;
import com.demj.poker.util.CardProvider;
import com.demj.poker.util.Gamer;
import com.demj.poker.util.PK;

public class PokerGameActivity extends FullScreenActivity {

	private ImageButton pk;
	Gamer apple,android;
	CardProvider provider;
	GamerInfoThread gamerThreadAndroid;
	GamerInfoThread gamerThreadApple;
	MyHandler handler;
	Object o1=new Object();
	Object o2=new Object();
	TextView providerinfo;
	TextView whoWin;
    LinearLayout gamerAndroidScoreInfo;
    LinearLayout gamerAppleScoreInfo;
    private int pkTimes=0;
    private TextView winOrDrawInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poker_game);
		pk=(ImageButton)findViewById(R.id.pk);
		winOrDrawInfo=(TextView)findViewById(R.id.winordrawinfo);
		pk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pk();
			}
		});
		 gamerAndroidScoreInfo=(LinearLayout)findViewById(R.id.android_score_detail);
		 gamerAppleScoreInfo=(LinearLayout)findViewById(R.id.apple_score_detail);
		 providerinfo=(TextView)findViewById(R.id.cardproviderinfo);
		 whoWin=(TextView)findViewById(R.id.whowin);
		apple=new Gamer("苹果");
		android=new Gamer("安卓");
		provider=new CardProvider(2,3);
		handler=new MyHandler();
		android.setCardImagesListId(R.id.android_cards);
		apple.setCardImagesListId(R.id.apple_cards);
		gamerThreadAndroid=new GamerInfoThread(android, handler);
		gamerThreadApple=new GamerInfoThread(apple, handler);
		gamerThreadApple.start();
		gamerThreadAndroid.start();
		
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.poker_game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	private void pk()
	{
		pkTimes++;
		LinkedList<Card> list=provider.getCards();
		apple.clearCards();
		android.clearCards();
		for(int i=0;i<3;i++)
		{
			apple.addGamerCard(list.get(i));
			android.addGamerCard(list.get(i+3));
		}
		
		Gamer winner=PK.pk(apple, android);
		synchronized (gamerThreadApple) {
			gamerThreadApple.notify();
		}
		synchronized (gamerThreadAndroid) {
			gamerThreadAndroid.notify();
		}
		if(winner==null)
		{
			provider.addPerWinTypeTimes(apple,CardProvider.TYPE_DRAW);
			whoWin.setText("\n第"+pkTimes+"次 "+"平局"+"\n平局类型为:"+PK.getLevelName(apple.getCurrentCardLevel()));
		}
		else
		{
			provider.addPerWinTypeTimes(winner,CardProvider.TYPE_WIN);
			whoWin.setText("\n第"+pkTimes+"次 "+winner.getName()+" 赢"+"\n赢牌类型为:"+PK.getLevelName(winner.getCurrentCardLevel()));
		}
		
		winOrDrawInfo.setText(provider.getTotalWinOrDrawTimesInfo());
		Message msg=new Message();
		msg.what=MyHandler.MSG_WHAT_UPDATE_GAMERS_SCORE_AND_PROVIDERINFO;
		handler.sendMessage(msg);
	}
	public class GamerInfoThread extends Thread
	{
		public final Object lock=new Object();
		public String TAG="GamerInfoThread: ";
		Gamer gamer;
		MyHandler handler;
		public GamerInfoThread(Gamer gamer,MyHandler handler)
		{
			this.gamer=gamer;
			this.handler=handler;
		}
		@Override
		 public void run()
		{
			while(true)
			{
				try {
					 synchronized(this){this.wait();}
						
					
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Card.CardList<Card> list=gamer.getGamerCards();
				LinearLayout cardList=(LinearLayout)findViewById(gamer.getCardImagesListId());
				if(cardList==null)
				{
					Log.e(TAG, "have you set the card list id in gamer?");
					return ;
				}
				ImageView image;
				int[] cardImageResIds=new int[list.size()];
				for(int i=0;i<list.size();i++)
				{
					 
					 cardImageResIds[i]=Card.getImageIdByCard(list.get(i));
				}
				Message msg=new Message();
				 msg.what=MyHandler.MSG_WHAT_LOOK_CARD;
				 Bundle data=new Bundle();
				 data.putIntArray(MyHandler.cardImageResId, cardImageResIds);
				 data.putInt(MyHandler.cardListId, gamer.getCardImagesListId());
				 msg.setData(data);
				 handler.sendMessage(msg);
			}
		}
	}
	public class MyHandler extends Handler
	{

		public static final int MSG_WHAT_LOOK_CARD=0x1;
		public static final int MSG_WHAT_COVER_CARD=0x2;
		public static final int MSG_WHAT_UPDATE_GAMERS_SCORE_AND_PROVIDERINFO=0x3;
		public String TAG="my handler: ";
		public static final String cardListId="cardListId";
		public static final String cardImageResId="cardImageResId";
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what)
			{
				case MSG_WHAT_LOOK_CARD: lookCard(msg);break;
				case MSG_WHAT_COVER_CARD:coverCard(msg);break;
				case MSG_WHAT_UPDATE_GAMERS_SCORE_AND_PROVIDERINFO:updateGamersScoreAndProviderInfo();break;
			}
			
		}
		private void updateGamersScoreAndProviderInfo() {
			// TODO Auto-generated method stub
			providerinfo.setText(MessageFormat.format(getApplicationContext()
					.getResources().getString(R.string.providerinfotext), ""+provider.getShuffledTimes(),""+provider.getLeftCards()));
			((TextView)gamerAndroidScoreInfo.findViewById(R.id.wintimes)).setText(""+android.getWinTimes());
			((TextView)gamerAndroidScoreInfo.findViewById(R.id.losetimes)).setText(""+android.getLoseTimes());
			((TextView)gamerAndroidScoreInfo.findViewById(R.id.equaltimes)).setText(""+android.getEqualWinTimes());
			
			((TextView)gamerAppleScoreInfo.findViewById(R.id.wintimes)).setText(""+apple.getWinTimes());
			((TextView)gamerAppleScoreInfo.findViewById(R.id.losetimes)).setText(""+apple.getLoseTimes());
			((TextView)gamerAppleScoreInfo.findViewById(R.id.equaltimes)).setText(""+apple.getEqualWinTimes());
			
		}
		
		private void coverCard(Message msg) {
			// TODO Auto-generated method stub
			unpdateCards(msg,OPTION_COVER_CARD);
		}
		private void lookCard(Message msg) {
			// TODO Auto-generated method stub
			unpdateCards(msg,OPTION_LOOK_CARD);
			
		}
		public static final int OPTION_LOOK_CARD=0X0;
		public static final int OPTION_COVER_CARD=0X1;
		private void unpdateCards(Message msg,int option)
		{
			Bundle b=msg.getData();
			int cardList=b.getInt(cardListId, -1);
			int[] cardImageResArray=b.getIntArray(cardImageResId);
			if(cardList==-1||cardImageResArray==null)
			{
				Log.e(TAG, "have you set the cardListId or cardImageResId in this message?");
				return;
			}
			ImageView image;
			LinearLayout cards=(LinearLayout)findViewById(cardList);
			switch(option)
			{
				case OPTION_LOOK_CARD:
					
					for(int i=0;i<cardImageResArray.length;i++)
					{
						 
						
						image=(ImageView)cards.findViewById(R.id.card_1+i);
						image.setImageResource(cardImageResArray[i]);
					}
					break;
				case OPTION_COVER_CARD:
					for(int i=0;i<cardImageResArray.length;i++)
					{
						image=(ImageView)cards.findViewById(R.id.card_1+i);
						image.setImageResource(R.drawable.card_back);
					}
					break;
		}
	}
		
	};
	
}
