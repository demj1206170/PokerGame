package com.demj.poker.util;

import java.util.Collections;

public class PK {
	public static final int LEVEL_ERROR=-1;
	public static final int LEVEL_1=4;
	public static final int LEVEL_2=3;
	public static final int LEVEL_3=2;
	public static final int LEVEL_4=1;
	public static final int LEVEL_5=0;
	public static final String[] LEVEL_NAME= {"杂牌","对子","同点","顺子","同花"};

	public static String getLevelName(int level)
	{
		String levelName="level error";
		if(level<LEVEL_5||level>LEVEL_1)
			return levelName;
		else
		return LEVEL_NAME[level];
	}
	public static Gamer pk(Gamer gamer1,Gamer gamer2)
	{
//		int result=0;
		Gamer winner=null;
//		Card.CardList<Card> GamerCards1=gamer1.getGamerCards();
//		Card.CardList<Card> GamerCards2=gamer2.getGamerCards();
		matchLevel(gamer1);
		matchLevel(gamer2);
//		System.out.println("level name = level "+getLevelName(gamer1.getCurrentCardLevel()));
//		System.out.println("level name = level "+getLevelName(gamer2.getCurrentCardLevel()));
		
		 
		winner=whoWin(gamer1,gamer2);
		
		if(winner!=null)
			System.out.println("winner is "+winner.getName());
		else
			System.out.println("nobody win");
//		for(int i=0;i<3;i++)
//		{
//			System.out.println("g1 "+GamerCards1.get(i));
//		}
//		for(int i=0;i<3;i++)
//		{
//			System.out.println("g2 "+GamerCards2.get(i));
//		}
		
		return winner;
	}
	private static int matchLevel(Gamer gamer/*Card.CardList<Card> list*/)
	{
		Card.CardList<Card> list=gamer.getGamerCards();
		int level=LEVEL_ERROR;
		int size=list.size();
		if(size!=3){
			System.out.println("poker's quantity no equal 3! is "+size);
			return level;
		}
		int[] cardNums=new int[size];
		int[] cardTypes=new int[size];
		Collections.sort(list,list);
		for(int i=0;i<size;i++)
		{
			Card c=list.get(i);
			cardNums[i]=c.getCardNum();
			cardTypes[i]=c.getType();
		}
		if(cardTypes[0]==cardTypes[1]&&cardTypes[0]==cardTypes[2])
			level=LEVEL_1;
		else if( (cardNums[1]-cardNums[0])==1 &&(cardNums[2]-cardNums[1])==1)
			level=LEVEL_2;
		else if(cardNums[0]==cardNums[1]&&cardNums[1]==cardNums[2])
				level=LEVEL_3;
			else if((cardNums[0]==cardNums[1]||cardNums[1]==cardNums[2]))
					level=LEVEL_4;
			else level=LEVEL_5;
		gamer.setCurrentCardLevel(level);
		return level;
				
		}
	private static Gamer whoWin(Gamer gamer1,Gamer gamer2)
	{
		
		int level1=gamer1.getCurrentCardLevel();
		int level2=gamer2.getCurrentCardLevel();
		Gamer winner=null;
//		System.out.println(" level1 "+getLevelName(level1)+" level2 "+getLevelName(level2));
		if(level1!=level2)
		{
//			System.out.println("level !" +
//					"e");
			if(level1>level2)
			{
				gamer1.addWinTimes();
				gamer2.addLostTimes();
				winner=gamer1;
			}
			else if(level1<level2)
			{
				gamer1.addLostTimes();
				gamer2.addWinTimes();
				winner=gamer2;
			}
		}
		else 
		{
//			System.out.println("level e");
			int sumNum1=0,sumNum2=0;
			Card.CardList<Card> cards1=gamer1.getGamerCards();
			Card.CardList<Card> cards2=gamer2.getGamerCards();
			for(int i=0;i<3;i++)
			{
				sumNum1+=cards1.get(i).getCardNum();
				sumNum2+=cards2.get(i).getCardNum();
			}
//			System.out.println("sn1 "+sumNum1);
//			System.out.println("sn2 "+sumNum2);
			if(sumNum1>sumNum2)
			{
				gamer1.addWinTimes();
				gamer2.addLostTimes();
				winner=gamer1;
			}
			else if(sumNum1<sumNum2)
			{
				gamer1.addLostTimes();
				gamer2.addWinTimes();
				winner=gamer2;
			}
			else {
				winner=null;
				gamer1.addEqualWinTimes();
				gamer2.addEqualWinTimes();
				}
		 
		}
		
		return winner;
	}
	
}
