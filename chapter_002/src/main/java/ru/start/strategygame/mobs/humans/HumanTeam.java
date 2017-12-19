package ru.start.strategygame.mobs.humans;

import ru.start.strategygame.AbstractTeam;

public class HumanTeam extends AbstractTeam {

    public HumanTeam(String name) {
        super(name);
    }

    @Override
    public void fill(int number) {
        for (int i = 1; i <= number; i++) {
            this.addPlayer(new HumanShooter("Human Shooter " + i, this, 5, 3));
            this.addPlayer(new HumanWarrior("Human Warrior " + i, this, 18));
            this.addPlayer(new HumanSorcerer("Human Sorcerer " + i, this, 4));
        }
    }
}
