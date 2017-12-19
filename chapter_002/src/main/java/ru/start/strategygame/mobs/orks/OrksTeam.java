package ru.start.strategygame.mobs.orks;

import ru.start.strategygame.AbstractTeam;
import ru.start.strategygame.mobs.AbstractCharacter;


public class OrksTeam extends AbstractTeam {

    public OrksTeam(String name) {
        super(name);
    }

    @Override
    public void fill(int number) {
        for (int i = 1; i <= number; i++) {
            this.addPlayer(new OrkShooter("Ork Shooter " + i, this, 2, 3));
            this.addPlayer(new OrkWarrior("Ork Warrior " + i, this, 20));
            this.addPlayer(new OrkSorcerer("Ork Sorcerer " + i, this, 0));
        }
    }

    @Override
    public void buffRandom() {
        AbstractCharacter ac;
        do {
            ac = this.team.get(rnd.nextInt(team.size()));
        } while (ac.getClass().getName().contains("Ork Sorcerer"));
        ac.buff();
        buffed.add(ac);
        System.out.printf("%s from %s has been buffed %n", ac.getName(), this.getName());
    }
}
