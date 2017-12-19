package ru.start.strategygame;


import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import ru.start.strategygame.mobs.humans.HumanTeam;
import ru.start.strategygame.mobs.orks.OrksTeam;

import java.io.IOException;


public class Battle {
    private AbstractTeam team1;
    private AbstractTeam team2;
    private int players;
    final static Logger logger = Logger.getLogger("logger");




    public Battle(AbstractTeam team1, AbstractTeam team2, int players) {
        this.team1 = team1;
        this.team2 = team2;
        this.players = players;

/*        {
            BasicConfigurator.configure();
            logger.info("loggerConfHere");
        }*/
        Appender appender = new ConsoleAppender();
        appender.setName("name");
//        logger.addAppender(appender);

    }

    private void startBattle() {
        team1.fill(players);
        team2.fill(players);
        boolean firstTeamMove = true;
        AbstractTeam attackersTeam;
        AbstractTeam attackedTeam;
        while (team1.hasPlayers() && team2.hasPlayers()) {
            if (firstTeamMove) {
                attackersTeam = team1;
                attackedTeam = team2;
            } else {
                attackersTeam = team2;
                attackedTeam = team1;
            }
            firstTeamMove = !firstTeamMove;
            attackersTeam.makeMove(attackedTeam.getRandomCharacter());

        }
        System.out.println("GameOver");
        if (team1.hasPlayers()) {

            System.out.println(team1.getName() + " won!");
        } else {
            System.out.println(team2.getName() + " won!");
        }
        logger.info("logger here");
    }

    public static void main(String[] args) throws IOException {
        Battle m = new Battle(new HumanTeam("HumansTeam"), new OrksTeam("OrksTeam"), 3);
        m.startBattle();
    }
}
