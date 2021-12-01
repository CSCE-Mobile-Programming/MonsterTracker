package com.uark.csce.monstertracker.models.info;

import java.util.List;

public class DeckInfo {
    private String Name;
    private List<CardInfo> Cards;
    private String Path;

    public void setup() {
        for (int i = 0; i < Cards.size(); i++) {
            Cards.get(i).setup(Path.replace("$", Integer.toString(i + 1)));
        }
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }

    public List<CardInfo> getCards() {
        return Cards;
    }
    public void setCards(List<CardInfo> cards) {
        this.Cards = cards;
    }

    public String getPath() {
        return Path;
    }
    public void setPath(String path) {
        Path = path;
    }
}
