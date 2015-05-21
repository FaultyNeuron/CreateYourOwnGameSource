package kinderuni.level.builder;

import kinderuni.gameLogic.objects.Player;
import kinderuni.gameLogic.objects.ProjectileGun;
import kinderuni.settings.PlayerSettings;
import kinderuni.settings.levelSettings.objectSettings.GraphicsSettings;

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
        return build(playerSettings, PlayerSettings.DEFAULT, false);
    }

    public Player build(PlayerSettings playerSettings, PlayerSettings defaultSettings) {
        return build(playerSettings, defaultSettings, true);
    }

    public Player build(PlayerSettings playerSettings, PlayerSettings defaultSettings, boolean keepDefault) {
        int life = playerSettings.hasLife()?playerSettings.getLife():defaultSettings.getLife();
        double enemyThrowBack = playerSettings.hasEnemyThrowbackPower()?playerSettings.getEnemyThrowbackPower():defaultSettings.getEnemyThrowbackPower();

        Player toReturn = new Player(enemyThrowBack, life);
//        ProjectileGun gun = new ProjectileGun(getSystem(), "black_hole");
//        gun.setInitialShootCoolDown(200);
//        toReturn.setGun(gun);
        if(keepDefault){
            attach(toReturn, playerSettings, defaultSettings);
        }else{
            attach(toReturn, playerSettings);
        }
        return toReturn;
    }

//    public void setDropFactory(DropFactory dropFactory) {
//        this.dropFactory = dropFactory;
//    }
}
