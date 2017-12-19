package ru.start.strategygame.mobs;

import ru.start.strategygame.AbstractTeam;

public abstract class AbstractSorcerer implements AbstractCharacter {
    public String name;
    public AbstractTeam abstractTeam;
    public int hp = 100;
    public int dmg;
    public boolean buffed = false;

    public AbstractSorcerer(String name, AbstractTeam abstractTeam, int dmg) {
        this.name = name;
        this.abstractTeam = abstractTeam;
        this.dmg = dmg;
    }

    @Override
    public void changeHp(int hp) {
        this.hp += hp;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getHp() {
        return this.hp < 0 ? 0 : this.hp;
    }


    public abstract void primaryAction(AbstractCharacter character);

    public abstract void secondaryAction(AbstractCharacter character);

    @Override
    public void debuff() {
        if(buffed) {
            buffed = false;
            this.dmg /= 2;
        }
    }

    @Override
    public void buff() {
        if(!buffed) {
            this.buffed = true;
            this.dmg *= 2;
        }
    }

    @Override
    public AbstractTeam getAbstractTeam() {
        return this.abstractTeam;
    }

    @Override
    public boolean isBuffed() {
        return buffed;
    }
}
