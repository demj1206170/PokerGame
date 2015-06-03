package com.demj.poker.util;
import java.util.Collection;
import java.util.Collections;



public class Gamer {

	
	private Card.CardList<Card> cards=new Card.CardList<Card>();
	private int currentCardLevel=PK.LEVEL_ERROR;
	private String name;
	private int winTimes=0;
	private int equalWinTimes=0;
	private int cardImagesListId;
	private int lostTimes=0;
	public Gamer(String name) {
		// TODO Auto-generated constructor stub
		this.name=name;
	}
	public Card.CardList<Card> getGamerCards()
	{
		Collections.sort(cards,cards);
		return cards;
	}
	public void setGamerCards(Collection<Card> cards)
	{
		if(this.cards==null)
			this.cards=new Card.CardList<Card>();
		this.cards.clear();
		this.cards.addAll(cards);
	}
	public void addGamerCard(Card card)
	{
		cards.add(card);
	}
	public void clearCards()
	{
		cards.clear();
	}
	public int getCurrentCardLevel()
	{
		return currentCardLevel;
	}
	public void setCurrentCardLevel(int level)
	{
		this.currentCardLevel=level;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public String getName()
	{
		return name;
	}
	public int getWinTimes() {
		return winTimes;
	}
	public void setWinTimes(int winTimes) {
		this.winTimes = winTimes;
	}
	public int getEqualWinTimes() {
		return equalWinTimes;
	}
	public void setEqualWinTimes(int equalWinTimes) {
		this.equalWinTimes = equalWinTimes;
	}
 
	public void addWinTimes()
	{
		this.winTimes++;
	}
	public void addEqualWinTimes()
	{
		this.equalWinTimes++;
	}
	public int getCardImagesListId() {
		return cardImagesListId;
	}
	public void setCardImagesListId(int cardImagesListId) {
		this.cardImagesListId = cardImagesListId;
	}
	public int getLoseTimes() {
		// TODO Auto-generated method stub
		return lostTimes;
	}
	public void setLoseTimes(int loseTimes) {
		// TODO Auto-generated method stub
		this.lostTimes=loseTimes;
	}
	public void addLostTimes()
	{
		this.lostTimes++;
	}
	

}
