package ru.start.strategygame.mobs.humans;

import ru.start.strategygame.AbstractTeam;
import ru.start.strategygame.mobs.AbstractShooter;

public class HumanShooter extends AbstractShooter {


    public HumanShooter(String name, AbstractTeam abstractTeam, int dmg, int rangeDmg) {
        super(name, abstractTeam, dmg, rangeDmg);
    }

}
