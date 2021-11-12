package com.uark.csce.monstertracker.util;

import com.uark.csce.monstertracker.models.info.CardInfo;
import com.uark.csce.monstertracker.models.info.DeckInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class DeckUtil {

    private boolean shuffle;
    private int nextCard;
    Random rand;
    ArrayList<CardInfo> cards;


    public DeckUtil(DeckInfo deck)
    {
        cards = new ArrayList<>(deck.getCards());
        shuffle = true;
        nextCard = 0;

        Date now = new Date();
        rand = new Random(now.getTime());
    }

    public void shuffleDeck()
    {
        nextCard = 0;
        for(int i = cards.size()-1; i>0; i--)
        {
            int index = rand.nextInt(i+1);

            CardInfo c1 = cards.get(index);
            CardInfo c2 = cards.get(i);

            cards.set(index, c2);
            cards.set(i, c1);
        }
    }

    public CardInfo getCard()
    {
        if(shuffle)
            shuffleDeck();

        CardInfo c = cards.get(nextCard);

        nextCard++;
        if(nextCard >= cards.size() || c.isShuffle())
            shuffle = true;

        return c;
    }

}