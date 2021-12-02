package com.uark.csce.monstertracker.models;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.uark.csce.monstertracker.models.FirebaseModels.CallbackContainer;
import com.uark.csce.monstertracker.models.FirebaseModels.CardState;
import com.uark.csce.monstertracker.models.FirebaseModels.FirebaseContract;
import com.uark.csce.monstertracker.models.FirebaseModels.MonsterState;
import com.uark.csce.monstertracker.models.info.CardInfo;
import com.uark.csce.monstertracker.models.info.MonsterInfo;
import com.uark.csce.monstertracker.models.info.Scenario;
import com.uark.csce.monstertracker.util.DeckUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonsterRepository {

    private DatabaseReference mDatabase;
    Map<String, CallbackContainer> firebaseCallbacks;
    private String roomCode;

    // Load-once informational data
    private List<Scenario> scenarios;
    private List<MonsterInfo> monsterInfos;

    private static volatile MonsterRepository INSTANCE;
    private Gson gson;

    //*************** Monster Repository Constructor ***************

    public static MonsterRepository getInstance(@NonNull Context context){
        if(INSTANCE == null){
            synchronized (MonsterRepository.class){
                if(INSTANCE == null){
                    INSTANCE = new MonsterRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    private MonsterRepository(@NonNull Context context) {
        gson = new GsonBuilder().create();
        AssetManager assetManager = context.getAssets();

        loadScenarios(assetManager);
        loadMonsterInfo(assetManager);

        for(MonsterInfo info : monsterInfos) {
            info.setup();
        }

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseCallbacks = new HashMap<>();
    }

    public void registerCallback(String id, String monsterName,  FirebaseContract.FirebaseCallback callback) {

        CallbackContainer container = new CallbackContainer();

        if(monsterName.isEmpty())
        {
            container.setGameStateListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Map<String, MonsterState> gameState = snapshot.getValue(new GenericTypeIndicator<Map<String, MonsterState>>() {});
                    if(gameState == null)
                    {
                        gameState = new HashMap<>();
                    }
                    callback.notifyGameStateChanged(gameState);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase.child(roomCode).addValueEventListener(container.getGameStateListener());
        }
        else
        {
            container.setMonsterStateListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<Monster> monsters = snapshot.getValue(new GenericTypeIndicator<List<Monster>>() {});
                    if(monsters == null)
                    {
                        monsters = new ArrayList<>();
                    }
                    callback.notifyMonsterStateChanged(monsters);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase.child(roomCode).child(monsterName).child("monsters").addValueEventListener(container.getMonsterStateListener());

            container.setCardStateListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CardInfo card = snapshot.getValue(CardInfo.class);
                    if(card == null)
                    {
                        card = new CardInfo();
                    }
                    callback.notifyCardStateChanged(card);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase.child(roomCode).child(monsterName).child("cardState").child("currentCard").addValueEventListener(container.getCardStateListener());

        }

        firebaseCallbacks.put(id, container);

    }
    public void unregisterCallback(String id) {

        CallbackContainer container = firebaseCallbacks.get(id);

        if(container.getGameStateListener() != null)
        {
            mDatabase.removeEventListener(container.getGameStateListener());
        }
        if(container.getMonsterStateListener() != null)
        {
            mDatabase.removeEventListener(container.getMonsterStateListener());
        }
        if(container.getCardStateListener() != null)
        {
            mDatabase.removeEventListener(container.getCardStateListener());
        }

        firebaseCallbacks.remove(id);
    }

    public void setRoomCode(String code){
        this.roomCode = code;
    }

    private void loadScenarios(@NonNull AssetManager assetManager) {
        try {
            InputStream in = assetManager.open("monster/scenarios.json");
            Reader reader = new InputStreamReader(in);

            scenarios = gson.fromJson(reader, new TypeToken<List<Scenario>>() {
            }.getType());

            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadMonsterInfo(@NonNull AssetManager assetManager) {
        try {
            InputStream in = assetManager.open("monster/monsters.json");
            Reader reader = new InputStreamReader(in);

            monsterInfos = gson.fromJson(reader, new TypeToken<List<MonsterInfo>>() { }.getType());
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //*************** Monster Info Data ***************
    public List<Scenario> getScenarios() {
        return scenarios;
    }
    public Scenario getScenario(String scenarioName) {
        // I miss Linq .Select()...
        for (int i = 0; i < scenarios.size(); i++) {
            if (scenarios.get(i).getName().equals(scenarioName)) {
                return scenarios.get(i);
            }
        }
        throw new IllegalArgumentException("Cannot find a scenario with name: " + scenarioName);
    }

    public List<MonsterInfo> getMonsterInfos() {
        return monsterInfos;
    }
    public MonsterInfo getMonsterInfo(String monsterName) {
        // ... still miss Linq .Select()...
        for (int i = 0; i < monsterInfos.size(); i++) {
            if (monsterInfos.get(i).getName().equals(monsterName)) {
                return monsterInfos.get(i);
            }
        }
        throw new IllegalArgumentException("Cannot find a monster with name: " + monsterName);
    }

    //*************** Monster Instance Data ***************
    public void clearInstanceData() {
        mDatabase.child(roomCode).removeValue();
    }

    public void addMonsterInfo(String monsterInfoName) {
        MonsterInfo info = getMonsterInfo(monsterInfoName);

        CardState cardState = new CardState();
        cardState.setCards(info.getDeck().getCards());
        cardState.setCurrentCard(new CardInfo());
        cardState.setNextCard(0);

        mDatabase.child(roomCode).child(monsterInfoName).child("cardState").setValue(cardState);

        drawNextCard(monsterInfoName);

    }

    public void addMonster(String monsterInfoName, int level, boolean isElite) {
        mDatabase.child(roomCode).child(monsterInfoName).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                MonsterState monsterState = currentData.getValue(MonsterState.class);
                if(monsterState == null)
                {
                    return Transaction.success(currentData);
                }

                MonsterInfo info = getMonsterInfo(monsterInfoName);

                Monster m = new Monster(info, level, isElite ? MonsterType.Elite : MonsterType.Normal);

                for(int i = 0; i<info.getMaxCount(); i++)
                {
                    if(i >= monsterState.getMonsters().size())
                    {
                        monsterState.getMonsters().add(m);
                        break;
                    }
                    if(monsterState.getMonsters().get(i) == null)
                    {
                        monsterState.getMonsters().set(i, m);
                        break;
                    }
                }

                currentData.setValue(monsterState);
                return Transaction.success(currentData);

            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }

    public void addHealth(String monsterInfoName, int position) {
        mDatabase.child(roomCode).child(monsterInfoName).child("monsters").child(Integer.toString(position)).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                Monster monster = currentData.getValue(Monster.class);
                if(monster ==  null) {
                    return Transaction.success(currentData);
                }

                if(monster.getHealth() < monster.getBaseHealth())
                {
                    monster.setHealth(monster.getHealth() + 1);
                }

                currentData.setValue(monster);

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }
    public void subtractHealth(String monsterInfoName, int position) {
        mDatabase.child(roomCode).child(monsterInfoName).child("monsters").child(Integer.toString(position)).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                Monster monster = currentData.getValue(Monster.class);
                if(monster ==  null) {
                    return Transaction.success(currentData);
                }

                monster.setHealth(monster.getHealth() - 1);

                if(monster.getHealth() == 0)
                {
                    currentData.setValue(null);
                }
                else
                {
                    currentData.setValue(monster);
                }

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }

    public void toggleMonsterStatus(String monsterInfoName, String statusName, int position) {
        mDatabase.child(roomCode).child(monsterInfoName).child("monsters").child(Integer.toString(position)).child("attributes").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                Attributes attribute = currentData.getValue(Attributes.class);
                if(attribute ==  null) {
                    return Transaction.success(currentData);
                }

                if (statusName.equals("disarm")) {
                    attribute.setDisarmed(!attribute.isDisarmed());
                }
                else if (statusName.equals("immobilize")) {
                    attribute.setImmobilized(!attribute.isImmobilized());
                }
                else if (statusName.equals("invisible")) {
                    attribute.setInvisible(!attribute.isInvisible());
                }
                else if (statusName.equals("poison")) {
                    attribute.setPoisoned(!attribute.isPoisoned());
                }
                else if (statusName.equals("muddle")) {
                    attribute.setMuddled(!attribute.isMuddled());
                }
                else if (statusName.equals("strengthen")) {
                    attribute.setStrengthened(!attribute.isStrengthened());
                }
                else if (statusName.equals("stun")) {
                    attribute.setStunned(!attribute.isStunned());
                }
                else if (statusName.equals("wound")) {
                    attribute.setWounded(!attribute.isWounded());
                }
                else {
                    throw new IllegalArgumentException("Unknown status.");
                }

                currentData.setValue(attribute);

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }

    public void drawNextCard(String monsterInfoName) {
        mDatabase.child(roomCode).child(monsterInfoName).child("cardState").runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                CardState cardState = currentData.getValue(CardState.class);
                if(cardState == null)
                {
                    return Transaction.success(currentData);
                }

                CardInfo card = cardState.getCards().get(cardState.getNextCard());
                cardState.setCurrentCard(card);
                cardState.setNextCard(cardState.getNextCard() + 1);

                if(cardState.getNextCard() >= cardState.getCards().size() || card.isShuffle())
                {
                    cardState.setNextCard(0);
                    cardState.setCards(DeckUtil.shuffleDeck(cardState.getCards()));
                }

                currentData.setValue(cardState);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }
    public void drawAll() {
        mDatabase.child(roomCode).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                Map<String, MonsterState> gameState = currentData.getValue(new GenericTypeIndicator<Map<String, MonsterState>>() {});
                if(gameState == null)
                {
                    return Transaction.success(currentData);
                }

                for(String key : gameState.keySet()) {
                    CardState cardState = gameState.get(key).getCardState();

                    if(cardState == null)
                    {
                        continue;
                    }

                    CardInfo card = cardState.getCards().get(cardState.getNextCard());
                    cardState.setCurrentCard(card);
                    cardState.setNextCard(cardState.getNextCard() + 1);

                    if(cardState.getNextCard() >= cardState.getCards().size() || card.isShuffle())
                    {
                        cardState.setNextCard(0);
                        cardState.setCards(DeckUtil.shuffleDeck(cardState.getCards()));
                    }

                    gameState.get(key).setCardState(cardState);
                }

                currentData.setValue(gameState);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }
}
