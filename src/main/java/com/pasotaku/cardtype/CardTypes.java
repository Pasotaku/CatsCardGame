package com.pasotaku.cardtype;

public enum CardTypes {
	NORMAL_CARD("normal", true),
	SKIP("skip",false),
	ATTACK("attack",false),
	NOPE("nope",false),
    CAT("cat-boom", false),
    FAVOR("favor-for-me",false),
    FUTURE("future",false),
	DEFUSE("defuse",false);
	
	private String cardType;
	private boolean normalCard;
	CardTypes(String cardType, boolean normalCard) {
		this.cardType = cardType;
		this.normalCard = normalCard;
	}
	public String getCardType() {
		return cardType;
	}
	public boolean isNormalCard() {
		return normalCard;
	}
	
};
