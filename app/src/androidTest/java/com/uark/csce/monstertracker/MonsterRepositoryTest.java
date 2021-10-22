package com.uark.csce.monstertracker;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.uark.csce.monstertracker.models.MonsterRepository;
import com.uark.csce.monstertracker.models.info.MonsterInfo;

@RunWith(AndroidJUnit4.class)
public class MonsterRepositoryTest {
    private MonsterRepository repository;
    private Context testContext;

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

    @Test
    public void BlackImpLoadsBaseInfo() {
        MonsterInfo blackImp = repository.getMonsterInfos().get(3);
        assertEquals("Black Imp", blackImp.getName());
        assertEquals(8, blackImp.getDeck().getCards().size());
        assertEquals(8, blackImp.getStats().size());
    }

    @Test
    public void BlackImpStatsLoad() {
        MonsterInfo blackImp = repository.getMonsterInfos().get(3);

        int[] levels = new int[] {0, 1, 2, 3, 4, 5, 6, 7 };
        int[] normalHealths = new int[] {3, 4, 5, 5, 7, 9, 10, 12};
        int[] eliteHealths = new int[] {4, 6, 8, 8, 11, 12, 14, 17};

        for(int i = 0; i < 8; i++) {
            assertEquals(levels[i], blackImp.getStats().get(i).getLevel());
            assertEquals(normalHealths[i], blackImp.getStats().get(i).getNormal().getHealth());
            assertEquals(eliteHealths[i], blackImp.getStats().get(i).getElite().getHealth());
        }
    }
}
