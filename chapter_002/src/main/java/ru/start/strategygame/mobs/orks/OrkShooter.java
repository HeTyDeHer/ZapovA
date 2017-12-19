package ru.start.strategygame.mobs.orks;

import ru.start.strategygame.AbstractTeam;
import ru.start.strategygame.mobs.AbstractShooter;

public class OrkShooter extends AbstractShooter {

    public OrkShooter(String name, AbstractTeam abstractTeam, int dmg, int rangeDmg) {
        super(name, abstractTeam, dmg, rangeDmg);
    }
}
