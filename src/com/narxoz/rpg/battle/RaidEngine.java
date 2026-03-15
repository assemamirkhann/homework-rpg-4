package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        // TODO: Validate inputs (null checks, alive checks, required skills).
        // TODO: Implement round-based simulation:
        // 1) Team A casts on Team B
        // 2) Team B casts on Team A (if still alive)
        // 3) Track rounds and log each step
        // 4) Stop when one team is defeated (or max rounds reached)
        //
        // Optional extension:
        // Use random for critical strikes or other deterministic events.
        // Example: boolean critA = random.nextInt(100) < 10;
        RaidResult result = new RaidResult();
        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            result.setWinner("Invalid input");
            result.addLine("Raid cannot start: null parameter");
            return result;
        }
        if (!teamA.isAlive() || !teamB.isAlive()) {
            result.setWinner("Invalid state");
            result.addLine("Raid cannot start: one of the teams is already defeated");
            return result;
        }
        int rounds = 0;
        int maxRounds = 30;
        result.addLine("Raid started between " + teamA.getName() + " and " + teamB.getName());
        while (teamA.isAlive() && teamB.isAlive() && rounds < maxRounds) {
            rounds++;
            result.addLine("Round " + rounds);
            result.addLine(teamA.getName() + " casts skill on " + teamB.getName());
            teamASkill.cast(teamB);
            if (!teamB.isAlive()) {
                result.addLine(teamB.getName() + " has been defeated");
                break;
            }

            result.addLine(teamB.getName() + " casts skill on " + teamA.getName());
            teamBSkill.cast(teamA);
            if (!teamA.isAlive()) {
                result.addLine(teamA.getName() + " has been defeated");
                break;
            }
        }
        result.setRounds(rounds);
        if (teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner(teamA.getName());
        } else if (teamB.isAlive() && !teamA.isAlive()) {
            result.setWinner(teamB.getName());
        } else {
            result.setWinner("Draw");
        }
        result.addLine("Raid finished in " + rounds + " rounds");
        return result;
    }
}
