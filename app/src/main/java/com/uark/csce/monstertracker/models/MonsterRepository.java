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
            InputStream in = assetManager.open("monster/card_classes.json");
            Reader reader = new InputStreamReader(in);

            cardClasses = gson.fromJson(reader, new TypeToken<List<CardClasses>>(){}.getType());

            reader.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }
}
