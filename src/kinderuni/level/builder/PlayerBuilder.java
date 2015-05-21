package kinderuni.level.builder;

import kinderuni.gameLogic.objects.Player;
import kinderuni.settings.PlayerSettings;

import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class PlayerBuilder extends LivingObjectBuilder {
//    private DropFactory dropFactory;
    public PlayerBuilder(kinderuni.System system, Random random) {
        super(system, random);
    }

    public Player build(PlayerSettings playerSettings) {
        return build(playerSettings, new PlayerSettings());
    }

    public Player build(PlayerSettings playerSettings, PlayerSettings defaultSettings) {
        int life = playerSettings.hasLife()?playerSettings.getLife():defaultSettings.getLife();
        double enemyThrowBack = playerSettings.hasEnemyThrowbackPower()?playerSettings.getEnemyThrowbackPower():defaultSettings.getEnemyThrowbackPower();

        Player toReturn = new Player(enemyThrowBack, life);
        attach(toReturn, playerSettings);
        return toReturn;
    }

//    public void setDropFactory(DropFactory dropFactory) {
//        this.dropFactory = dropFactory;
//    }
}
