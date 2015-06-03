package com.demj.poker.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import android.util.Log;

public class CardProvider {

	public static final int TYPE_DRAW = 0;
	public static final int TYPE_WIN = 1;
	public static LinkedList<Card> cards = new LinkedList<Card>();
	private ArrayList<Card> shuffledCards;
	private int gamersNum = 0;
	private final int CARD_NUMBERS = 52;
	private int cards_per_gamer = 0;
	private int shuffledTimes = 0;
	private int gamerWinTypeTimes[] = new int[5];
	private int[] gamerDrawTimes = new int[5];

	private int pkTimes=0;
	public CardProvider(int initGamerNumber, int initCardsPerGamer) {
		// TODO Auto-generated constructor stub
		this.cards_per_gamer = initCardsPerGamer;
		this.gamersNum = initGamerNumber;
		generateCards();
		this.shuffledCards = shuffleCards();

	}

	private void generateCards() {
		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 4; j++) {
				Card card = new Card(Card.CARD_2 + i, Card.TYPE_SQUARE + j);
				cards.add(card);
			}
		}
	}

	public int getLeftCards() {
		if (shuffledCards != null)
			return shuffledCards.size();
		else
			return 0;
	}

	private ArrayList<Card> shuffleCards() {
		ArrayList<Card> shuffledCardsArrayList = new ArrayList<Card>();
		LinkedList<Card> copy = new LinkedList<Card>(cards);
		Random random = new Random();
		for (int i = 0; i < CARD_NUMBERS; i++) {
			int position = random.nextInt(copy.size());
			// System.out.println("Random generate:"+position);
			shuffledCardsArrayList.add(copy.remove(position));

		}
		// for(int i=0;i<52;i++)
		// {
		// System.out.println(cards.get(i)+"  position="+i);
		// }
		// for(int i=0;i<52;i++)
		// {
		// System.out.println(shuffledCardsArrayList.get(i)+"  position="+i);
		// }
		shuffledTimes++;
		return shuffledCardsArrayList;
	}

	public int getShuffledTimes() {
		return shuffledTimes;
	}

	public boolean setGamers(int number) {
		if (number <= 0) {
			System.out.println("gamer number <= 0");
			return false;
		} else if (number > CARD_NUMBERS / cards_per_gamer) {
			System.out.println("gamer number out of max, current max is "
					+ CARD_NUMBERS / cards_per_gamer);
			return false;
		} else {
			{
				this.gamersNum = number;
			}
		}
		return true;
	}

	public LinkedList<Card> getCards() throws NullPointerException {
		if (gamersNum <= 1) {
			System.out.println("error: gamer's number is " + gamersNum);
			return null;
		}
		int needCards = cards_per_gamer * gamersNum;
		if (getLeftCards() < needCards)
			shuffledCards = shuffleCards();
		LinkedList<Card> getedCards = new LinkedList<Card>();
		for (int i = 0; i < needCards; i++) {
			getedCards.add(shuffledCards.remove(0));
			// System.out.println(""+i);
		}
		return getedCards;
	}

	public void addPerWinTypeTimes(Gamer gamer,int winOrDraw) {
		if (gamer != null)
		{
			int type=gamer.getCurrentCardLevel();
			switch(winOrDraw)
			{
				case TYPE_WIN:gamerWinTypeTimes[type]++;break;
				case TYPE_DRAW:gamerDrawTimes[type]++;break;
			}
			pkTimes++;
		}
		

	}
	public String getTotalWinOrDrawTimesInfo()
	{
		StringBuffer info=new StringBuffer();
		info.append("\n\t赢    \t\t平局\n");
		for(int i=0;i<gamerWinTypeTimes.length;i++)
		{
			info.append(PK.getLevelName(i)+": \t"+gamerWinTypeTimes[i]+", \t"+gamerDrawTimes[i]+"\n");
		}
		info.append("----------------");
		info.append("\n共计:"+pkTimes);
		info.append("\n概率:\n");
		float fpktimes=(float)pkTimes;
		
		for(int i=0;i<gamerWinTypeTimes.length;i++)
		{
			float fwin=gamerWinTypeTimes[i]/fpktimes;
			BigDecimal b=new BigDecimal(fwin);
			float fwinreal=b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();

			float fdraw=gamerDrawTimes[i]/fpktimes;
			BigDecimal b1=new BigDecimal(fdraw);
			float fdrawreal=b1.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
			info.append(PK.getLevelName(i)+": \t"+fwinreal+", \t"+fdrawreal+"\n");
		}
		
		return info.toString();
	}
}
