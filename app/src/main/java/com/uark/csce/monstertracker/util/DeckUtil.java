package com.uark.csce.monstertracker.util;

import com.uark.csce.monstertracker.models.info.CardInfo;
import com.uark.csce.monstertracker.models.info.DeckInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DeckUtil {

    static Date now = new Date();
    static Random rand = new Random(now.getTime());

    public DeckUtil()
    {

    }

    public static List<CardInfo> shuffleDeck(List<CardInfo> cards)
    {
        for(int i = cards.size()-1; i>0; i--)
        {
            int index = rand.nextInt(i+1);

            CardInfo c1 = cards.get(index);
            CardInfo c2 = cards.get(i);

            cards.set(index, c2);
            cards.set(i, c1);
        }

        return cards;
    }
}
