package com.uark.csce.monstertracker.models;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.uark.csce.monstertracker.models.info.MonsterInfo;
import com.uark.csce.monstertracker.models.info.Scenario;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class MonsterRepository {

    private List<Scenario> scenarios;
    private List<MonsterInfo> monsterInfos;

    private static volatile MonsterRepository INSTANCE;
    Gson gson;

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
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public List<MonsterInfo> getMonsterInfos() {
        return monsterInfos;
    }

    private void loadScenarios(AssetManager assetManager) {
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

    private void loadMonsterInfo(AssetManager assetManager) {
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
}