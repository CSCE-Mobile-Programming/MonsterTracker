package com.uark.csce.monstertracker.models;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class MonsterRepository {

    private static volatile MonsterRepository INSTANCE;
    Gson gson;

    private List<CardClasses> cardClasses;
    private List<Scenario> scenarios;
    private List<CardContent> cardContents;

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

        try {
            //Get a map between monster and card deck
            InputStream in = assetManager.open("monster/card_classes.json");
            Reader reader = new InputStreamReader(in);

            cardClasses = gson.fromJson(reader, new TypeToken<List<CardClasses>>(){}.getType());

            reader.close();

            //Load the map between scenarios and monsters
            in = assetManager.open("monster/scenarios.json");
            reader = new InputStreamReader(in);

            scenarios = gson.fromJson(reader, new TypeToken<List<Scenario>>(){}.getType());

            reader.close();

            //Load the cardContents for each deck
            in = assetManager.open("monster/card_content.json");
            reader = new InputStreamReader(in);

            cardContents = gson.fromJson(reader, new TypeToken<List<CardContent>>(){}.getType());

            reader.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
