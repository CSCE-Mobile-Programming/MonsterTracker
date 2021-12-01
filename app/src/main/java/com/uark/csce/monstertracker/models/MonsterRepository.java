package com.uark.csce.monstertracker.models;

import android.content.Context;
import android.content.res.AssetManager;

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
    Map<String, CallbackContainer> firebaseCallbacks;
    private String roomCode;

    // Load-once informational data
    private List<Scenario> scenarios;
    private List<MonsterInfo> monsterInfos;

    private static volatile MonsterRepository INSTANCE;
    private Gson gson;

    //TODO Remove
    private Map<String, List<Monster>> monsterInstanceData;
    private Map<String, DeckUtil> currentCardData;

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

        //TODO Remove
        monsterInstanceData = new HashMap<String, List<Monster>>();
        currentCardData = new HashMap<String, DeckUtil>();
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
        firebaseCallbacks.put(id, container);

    }
    public void unregisterCallback(String id){
        CallbackContainer container = firebaseCallbacks.get(id);
        mDatabase.removeEventListener(container.getMonsterInfoListener());
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
        //TODO Remove
        monsterInstanceData = new HashMap<String, List<Monster>>();
        currentCardData = new HashMap<String, DeckUtil>();

        mDatabase.child(roomCode).child(MONSTER_INFO_REFERENCE).removeValue();
        mDatabase.child(roomCode).child(MONSTER_INSTANCE_REFERENCE).removeValue();
        mDatabase.child(roomCode).child(CARD_REFERENCE).removeValue();
    }

    public void addMonsterInfo(String monsterInfoName) {
        MonsterInfo info = getMonsterInfo(monsterInfoName);

        //TODO Remove
        // Preinitialize the list with null monsters
        ArrayList<Monster> list = new ArrayList<>(info.getMaxCount());
        for (int i = 0; i < info.getMaxCount(); i++) {
            list.add(null);
        }

        //TODO REMOVE
        monsterInstanceData.put(monsterInfoName, list);
        currentCardData.put(monsterInfoName, new DeckUtil(info.getDeck()));

        MainActivityInfo activityInfo = new MainActivityInfo();
        activityInfo.setMonsterInfoName(monsterInfoName);
        activityInfo.setInitiative(0);
        activityInfo.setNumMonsters(0);

        mDatabase.child(roomCode).child(MONSTER_INFO_REFERENCE).child(monsterInfoName).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                MainActivityInfo temp = currentData.getValue(MainActivityInfo.class);
                if(temp == null)
                {
                    currentData.setValue(activityInfo);
                    return Transaction.success(currentData);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }

    public List<Monster> getMonsters(String monsterInfoName) {
        return monsterInstanceData.get(monsterInfoName);
    }

    public void addMonster(String monsterInfoName, int level, boolean isElite) {
        MonsterInfo info = getMonsterInfo(monsterInfoName);
        List<Monster> monsters = monsterInstanceData.get(monsterInfoName);

        // When adding a monster we traverse the list and look for null entries. If one is found we
        // fill it. Otherwise, do nothing.
        for(int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i) == null) {
                Monster m = new Monster(info, level, isElite ? MonsterType.Elite : MonsterType.Normal);
                monsters.set(i, m);
                break;
            }
        }

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

            }
        });

    }
    public void removeMonster(String monsterInfoName, int position) {
        MonsterInfo info = getMonsterInfo(monsterInfoName);
        List<Monster> monsters = monsterInstanceData.get(monsterInfoName);
        if (position < 0 || position >= monsters.size()) {
            throw new IllegalArgumentException("Position out of range for monsterInfo: " + monsterInfoName);
        }

        // When removing a monster we go to that index and set the data to null. However, the entry
        // in the list is kept so that the indexes of other monsters are not changed. This is a
        // quality of life feature for GH players so they don't have to renumber their monsters
        // every time one of them dies.
        monsters.set(position, null);
        mDatabase.child(roomCode).child(MONSTER_INFO_REFERENCE).child(monsterInfoName).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                MainActivityInfo temp = currentData.getValue(MainActivityInfo.class);
                if(temp == null)
                {
                    return Transaction.success(currentData);
                }

                temp.setNumMonsters(temp.getNumMonsters() - 1);
                currentData.setValue(temp);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }

    public void addHealth(String monsterInfoName, int position) {
        Monster m = monsterInstanceData.get(monsterInfoName).get(position);
        if (m != null && m.getHealth() < m.getBaseHealth()) {
            m.setHealth(m.getHealth() + 1);
        }
    }
    public void subtractHealth(String monsterInfoName, int position) {
        Monster m = monsterInstanceData.get(monsterInfoName).get(position);
        if (m != null) {
            m.setHealth(m.getHealth() - 1);
            if (m.getHealth() <= 0) {
                removeMonster(monsterInfoName, position);
            }
        }
    }

    public boolean toggleMonsterStatus(String monsterInfoName, String statusName, int position) {
        Monster m = monsterInstanceData.get(monsterInfoName).get(position);
        // Toggle the relevant status
        if (statusName.equals("disarm")) {
            m.getAttributes().setDisarmed(!m.getAttributes().isDisarmed());
            return m.getAttributes().isDisarmed();
        }
        else if (statusName.equals("immobilize")) {
            m.getAttributes().setImmobilized(!m.getAttributes().isImmobilized());
            return m.getAttributes().isImmobilized();
        }
        else if (statusName.equals("invisible")) {
            m.getAttributes().setInvisible(!m.getAttributes().isInvisible());
            return m.getAttributes().isInvisible();
        }
        else if (statusName.equals("poison")) {
            m.getAttributes().setPoisoned(!m.getAttributes().isPoisoned());
            return m.getAttributes().isPoisoned();
        }
        else if (statusName.equals("muddle")) {
            m.getAttributes().setMuddled(!m.getAttributes().isMuddled());
            return m.getAttributes().isMuddled();
        }
        else if (statusName.equals("strengthen")) {
            m.getAttributes().setStrengthened(!m.getAttributes().isStrengthened());
            return m.getAttributes().isStrengthened();
        }
        else if (statusName.equals("stun")) {
            m.getAttributes().setStunned(!m.getAttributes().isStunned());
            return m.getAttributes().isStunned();
        }
        else if (statusName.equals("wound")) {
            m.getAttributes().setWounded(!m.getAttributes().isWounded());
            return m.getAttributes().isWounded();
        }
        else {
            throw new IllegalArgumentException("Unknown status.");
        }
    }

    public CardInfo getCurrentCard(String monsterInfoName) {
        return currentCardData.get(monsterInfoName).getCurrentCard();
    }
    public CardInfo drawNextCard(String monsterInfoName) {
        return currentCardData.get(monsterInfoName).drawNextCard();
    }
}
