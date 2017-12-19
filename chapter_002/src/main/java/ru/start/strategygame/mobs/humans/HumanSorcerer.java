package ru.start.strategygame.mobs.humans;

import ru.start.strategygame.AbstractTeam;
import ru.start.strategygame.mobs.AbstractCharacter;
import ru.start.strategygame.mobs.AbstractSorcerer;

public class HumanSorcerer extends AbstractSorcerer {

    public HumanSorcerer(String name, AbstractTeam abstractTeam, int dmg) {
        super(name, abstractTeam, dmg);
    }

    @Override
    public void primaryAction(AbstractCharacter character) {
        character.changeHp( - dmg);
        System.out.printf("%s (%s) attack %s (%s), and make %d damage. %s hp = %d  %n",
                this.getName(), this.abstractTeam.getName(), character.getName(), character.getAbstractTeam().getName(), this.dmg, character.getName(), character.getHp());
    }

    @Override
    public void secondaryAction(AbstractCharacter character) {
        System.out.printf("%s (%s) is casting spell. ", this.getName(), this.getAbstractTeam().getName() );
        this.abstractTeam.buffRandom();
    }
}
