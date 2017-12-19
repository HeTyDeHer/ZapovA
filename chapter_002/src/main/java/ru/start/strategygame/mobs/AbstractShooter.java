package ru.start.strategygame.mobs;

import ru.start.strategygame.AbstractTeam;

public abstract class AbstractShooter implements AbstractCharacter {

    private String name;
    private AbstractTeam abstractTeam;
    private int hp = 100;
    private int dmg;
    private int rangeDmg;
    private boolean buffed = false;

    public AbstractShooter(String name, AbstractTeam abstractTeam, int dmg, int rangeDmg) {
        this.name = name;
        this.abstractTeam = abstractTeam;
        this.dmg = dmg;
        this.rangeDmg = rangeDmg;
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

    @Override
    public void primaryAction(AbstractCharacter character) {
        character.changeHp( - rangeDmg);
        System.out.printf("%s (%s) attack %s (%s), and make %d damage. %s hp = %d  %n",
                this.getName(), this.abstractTeam.getName(), character.getName(), character.getAbstractTeam().getName(), this.rangeDmg, character.getName(), character.getHp());
    }

    @Override
    public void secondaryAction(AbstractCharacter character) {
        character.changeHp( - dmg);
        System.out.printf("%s (%s) attack %s (%s), and make %d damage. %s hp = %d  %n",
                this.getName(), this.abstractTeam.getName(), character.getName(), character.getAbstractTeam().getName(), this.dmg, character.getName(), character.getHp());
    }

    @Override
    public void debuff() {
        if(buffed) {
            buffed = false;
            this.rangeDmg /= 2;
            this.dmg /= 2;
        }
    }

    @Override
    public void buff() {
        if(!buffed) {
            this.buffed = true;
            this.rangeDmg *= 2;
            this.dmg *= 2;
        }
    }

    @Override
    public boolean isBuffed() {
        return buffed;
    }

    @Override
    public AbstractTeam getAbstractTeam() {
        return this.abstractTeam;
    }


}
