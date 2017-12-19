package ru.start.strategygame.mobs;

import ru.start.strategygame.AbstractTeam;

public interface AbstractCharacter {

    void changeHp(int hp);
    String getName();
    int getHp();
    void primaryAction(AbstractCharacter character);
    void secondaryAction(AbstractCharacter character);
    void debuff();
    void buff();
    boolean isBuffed();
    AbstractTeam getAbstractTeam();
}
