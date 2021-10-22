package com.uark.csce.monstertracker;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.uark.csce.monstertracker.models.MonsterRepository;

@RunWith(AndroidJUnit4.class)
public class MonsterRepositoryTest {
    private MonsterRepository repository;
    private Context testContext = ApplicationProvider.getApplicationContext();

    public MonsterRepositoryTest() {
        testContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        repository = MonsterRepository.getInstance(testContext);
    }

    @Test
    public void repositoryCanLoad() {
        // Just need the constructor to run, so check that 0 = 0
        assertEquals(0, 0);
    }

    @Test
    public void countScenarios() {
        // There should be 122 scenarios
        assertEquals(122, repository.getScenarios().size());
    }

    @Test
    public void countMonsters() {
        assertEquals(34, repository.getMonsterInfos().size());
    }
}
