package ru.start.strategygame.mobs.orks;

import ru.start.strategygame.AbstractTeam;
import ru.start.strategygame.mobs.AbstractCharacter;
import ru.start.strategygame.mobs.AbstractSorcerer;

public class OrkSorcerer extends AbstractSorcerer {

    public OrkSorcerer(String name, AbstractTeam abstractTeam, int dmg) {
        super(name, abstractTeam, dmg);
    }

    @Override
    public void primaryAction(AbstractCharacter character) {
        System.out.printf("%s (%s) is casting spell. %n", this.getName(), this.getAbstractTeam().getName() );
        character.getAbstractTeam().debuffRandom();
    }

    @Override
    public void secondaryAction(AbstractCharacter character) {
        System.out.printf("%s (%s) is casting spell. ", this.getName(), this.getAbstractTeam().getName() );
        this.abstractTeam.buffRandom();
    }
}
