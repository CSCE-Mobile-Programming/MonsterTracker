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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.uark.csce.monstertracker.models.FirebaseModels.CallbackContainer;
import com.uark.csce.monstertracker.models.FirebaseModels.CardDeckModel;
import com.uark.csce.monstertracker.models.FirebaseModels.FirebaseContract;
import com.uark.csce.monstertracker.models.FirebaseModels.MainActivityInfo;
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
import java.util.Set;

public class MonsterRepository {

    private DatabaseReference mDatabase;
    final String MONSTER_INSTANCE_REFERENCE = "monster_instance";
    final String MONSTER_INFO_REFERENCE = "monster_info";
    final String CARD_REFERENCE = "cards";
    final String DECK_REFERENCE = "deck";
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

        FirebaseDatabase.getInstance().setPersistenceEnabled(false);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseCallbacks = new HashMap<>();
    }

    public void registerCallback(String id, String monsterName,  FirebaseContract.FirebaseCallback callback) {

        CallbackContainer container = new CallbackContainer();

         container.setMonsterInfoListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<MainActivityInfo> infos = new ArrayList<>();
                for(DataSnapshot infoSnapshot : snapshot.getChildren() )
                {
                    infos.add(infoSnapshot.getValue(MainActivityInfo.class));
                }
                callback.notifyMonsterInfoChanged(infos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mDatabase.child(roomCode).child(MONSTER_INFO_REFERENCE).addValueEventListener(container.getMonsterInfoListener());

        if(!monsterName.isEmpty()){
            container.setMonsterInstanceListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    List<Monster> monsters = new ArrayList<>();
                    MonsterInfo info = getMonsterInfo(monsterName);
                    for(int i = 0; i<info.getMaxCount(); i++)
                    {
                        Monster monster = snapshot.child(Integer.toString(i)).getValue(Monster.class);
                        monsters.add(monster);
                    }
                    callback.notifyMonsterInstanceChanged(monsters);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase.child(roomCode).child(MONSTER_INSTANCE_REFERENCE).child(monsterName).addValueEventListener(container.getMonsterInstanceListener());

            container.setMonsterCardListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    CardInfo card = new CardInfo();
                    card.setImagePath("");
                    callback.notifyMonsterCardsChanged(card);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            mDatabase.child(roomCode).child(CARD_REFERENCE).child(monsterName).child("current_card").addValueEventListener(container.getMonsterCardListener());
        }

        firebaseCallbacks.put(id, container);

    }
    public void unregisterCallback(String id){
        CallbackContainer container = firebaseCallbacks.get(id);
        mDatabase.removeEventListener(container.getMonsterInfoListener());

        if(container.getMonsterInstanceListener() != null && container.getMonsterCardListener() != null)
        {
            mDatabase.removeEventListener(container.getMonsterInstanceListener());
            mDatabase.removeEventListener(container.getMonsterCardListener());
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
        mDatabase.child(roomCode).child(MONSTER_INFO_REFERENCE).removeValue();
        mDatabase.child(roomCode).child(MONSTER_INSTANCE_REFERENCE).removeValue();
        mDatabase.child(roomCode).child(CARD_REFERENCE).removeValue();
    }

    public void addMonsterInfo(String monsterInfoName) {
        MonsterInfo info = getMonsterInfo(monsterInfoName);

        MainActivityInfo activityInfo = new MainActivityInfo();
        activityInfo.setMonsterInfoName(monsterInfoName);
        activityInfo.setInitiative(0);
        activityInfo.setNumMonsters(0);

        mDatabase.child(roomCode).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                MainActivityInfo temp = currentData.child(MONSTER_INFO_REFERENCE).child(monsterInfoName).getValue(MainActivityInfo.class);
                if(temp == null)
                {
                    currentData.child(MONSTER_INFO_REFERENCE).child(monsterInfoName).setValue(activityInfo);
                    return Transaction.success(currentData);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                Log.d("Monster Repository", "addMonsterINfo:onComplete:" + error);
            }
        });
    }

    public void addMonster(String monsterInfoName, int level, boolean isElite) {
        MonsterInfo info = getMonsterInfo(monsterInfoName);

        mDatabase.child(roomCode).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                MainActivityInfo temp = currentData.child(MONSTER_INFO_REFERENCE).child(monsterInfoName).getValue(MainActivityInfo.class);

                if(temp == null) {
                    return Transaction.success(currentData);
                }

                for(int i = 0; i < info.getMaxCount(); i++) {

                    Monster monster = currentData.child(MONSTER_INSTANCE_REFERENCE).child(monsterInfoName).child(Integer.toString(i)).getValue(Monster.class);

                    if (monster == null) {

                        Monster m = new Monster(info, level, isElite ? MonsterType.Elite : MonsterType.Normal);
                        temp.setNumMonsters(temp.getNumMonsters() + 1);

                        currentData.child(MONSTER_INSTANCE_REFERENCE).child(monsterInfoName).child(Integer.toString(i)).setValue(m);
                        currentData.child(MONSTER_INFO_REFERENCE).child(monsterInfoName).setValue(temp);

                        break;
                    }
                }

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                Log.d("Monster Repository", "addMonster:onComplete:" + error);
            }
        });

    }

    public void addHealth(String monsterInfoName, int position) {
        mDatabase.child(roomCode).child(MONSTER_INSTANCE_REFERENCE).child(monsterInfoName).child(Integer.toString(position))
                .runTransaction(new Transaction.Handler() {
                    @NonNull
                    @Override
                    public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                        Monster monster = currentData.getValue(Monster.class);
                        if(monster == null && monster.getHealth() >= monster.getBaseHealth())
                        {
                            return Transaction.success(currentData);
                        }
                        monster.setHealth(monster.getHealth() + 1);
                        currentData.setValue(monster);
                        return Transaction.success(currentData);
                    }

                    @Override
                    public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                        Log.d("Monster Repository", "addHealth:onComplete:" + error);
                    }
                });

    }
    public void subtractHealth(String monsterInfoName, int position) {
        mDatabase.child(roomCode).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                MainActivityInfo temp = currentData.child(MONSTER_INFO_REFERENCE).child(monsterInfoName).getValue(MainActivityInfo.class);
                Monster monster = currentData.child(MONSTER_INSTANCE_REFERENCE).child(monsterInfoName).child(Integer.toString(position)).getValue(Monster.class);

                if(monster == null || temp == null)
                {
                    return Transaction.success(currentData);
                }
                else if(temp != null && monster.getHealth() == 1)
                {
                    temp.setNumMonsters(temp.getNumMonsters() - 1);
                    currentData.child(MONSTER_INFO_REFERENCE).child(monsterInfoName).setValue(temp);
                    currentData.child(MONSTER_INSTANCE_REFERENCE).child(monsterInfoName).child(Integer.toString(position)).setValue(null);

                    return Transaction.success(currentData);
                }

                monster.setHealth(monster.getHealth() - 1);
                currentData.child(MONSTER_INSTANCE_REFERENCE).child(monsterInfoName).child(Integer.toString(position)).setValue(monster);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                Log.d("Monster Repository", "subtractHealth:onComplete:" + error);
            }
        });

    }

    public void toggleMonsterStatus(String monsterInfoName, String statusName, int position) {

        mDatabase.child(roomCode).child(MONSTER_INSTANCE_REFERENCE).child(monsterInfoName).child(Integer.toString(position))
                .child("attributes").child(statusName).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Boolean status = true;
                if(status == null)
                {
                    return Transaction.success(currentData);
                }
                status = !status;
                currentData.setValue(status);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }

    public void drawNextCard(String monsterInfoName) {
        mDatabase.child(roomCode).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {

                MainActivityInfo temp = currentData.child(MONSTER_INFO_REFERENCE).child(monsterInfoName).getValue(MainActivityInfo.class);
                CardDeckModel deck = currentData.child(CARD_REFERENCE).child(monsterInfoName).child(DECK_REFERENCE).getValue(CardDeckModel.class);

                if(temp != null &&  deck != null)
                {
                    CardInfo card = deck.getCards().get(deck.getNextCard());

                    if(card.isShuffle())
                    {
                        deck.setCards(DeckUtil.shuffleDeck(deck.getCards()));
                        deck.setNextCard(0);
                        currentData.child(CARD_REFERENCE).child(monsterInfoName).child(DECK_REFERENCE).setValue(deck);
                    }

                    temp.setInitiative(card.getInitiative());
                    deck.setNextCard(deck.getNextCard() + 1);

                    currentData.child(CARD_REFERENCE).child(monsterInfoName).child("current_card").setValue(card);
                    currentData.child(MONSTER_INFO_REFERENCE).child(monsterInfoName).setValue(temp);

                    return Transaction.success(currentData);
                }

                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                Log.d("Monster Repository", "drawCard:onComplete:" + error);
            }
        });
    }
}
