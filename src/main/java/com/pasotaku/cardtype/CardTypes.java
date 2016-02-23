package com.pasotaku.cardtype;

public enum CardTypes {
	NORMAL_CARD("normal", true),
	SKIP("skip",false),
	ATTACK("attack",false);
	
	private String cardType;
	private boolean normalCard;
	private CardTypes(String cardType, boolean normalCard) {
		this.cardType = cardType;
		this.normalCard = normalCard;
	}
	
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public boolean isNormalCard() {
		return normalCard;
	}
	public void setNormalCard(boolean normalCard) {
		this.normalCard = normalCard;
	}
	
};
