package ru.start.strategygame;

import ru.start.strategygame.mobs.AbstractCharacter;

import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractTeam {
    private String name;
    public Random rnd = new Random();
    public ArrayList<AbstractCharacter> team = new ArrayList<>();
    public ArrayList<AbstractCharacter> buffed = new ArrayList<>();

    public AbstractTeam(String name) {
        this.name = name;
    }

    public abstract void fill(int number);

    public void addPlayer(AbstractCharacter player) {
        this.team.add(player);
    }

    public void makeMove(AbstractCharacter character) {
        AbstractCharacter toMove;
        if (hasBuffed()) {
            toMove = buffed.get(rnd.nextInt(buffed.size()));
        } else {
            toMove = team.get(rnd.nextInt(team.size()));
        }
        if (rnd.nextBoolean()) {
            toMove.primaryAction(character);
        } else {
            toMove.secondaryAction(character);
        }
        if (toMove.isBuffed()) {
            toMove.debuff();
            buffed.remove(toMove);
        }
        if (character.getHp() == 0) {
            System.out.printf("%s from team %s has died.%n", character.getName(), character.getAbstractTeam().getName());
            character.getAbstractTeam().removeCharacter(character);
        }
    }

    public AbstractCharacter getRandomCharacter() {
        return team.get(rnd.nextInt(team.size()));
    }

    public boolean hasPlayers() {
        return team.size() != 0;
    }

    public void buffRandom() {
        AbstractCharacter ac = this.team.get(rnd.nextInt(team.size()));
        ac.buff();
        buffed.add(ac);
        System.out.printf("%s from %s has been buffed %n", ac.getName(), this.getName());
    }

    public void debuffRandom() {
        if (hasBuffed()) {
            AbstractCharacter ac = this.buffed.get(rnd.nextInt(buffed.size()));
            ac.debuff();
            buffed.remove(ac);
            System.out.printf("%s from %s has been debuffed %n", ac.getName(), this.getName());
        }
    }

    private boolean hasBuffed() {
        return !buffed.isEmpty();
    }

    private void removeCharacter(AbstractCharacter character) {
        if (character.isBuffed()) {
            buffed.remove(character);
        }
        team.remove(character);
    }

    public String getName() {
        return name;
    }
}
