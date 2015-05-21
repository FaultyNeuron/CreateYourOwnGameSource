package kinderuni.level.builder;

import kinderuni.*;
import kinderuni.System;

import java.util.Random;

/**
 * Created by Georg Plaz.
 */
public class Builder {
    private kinderuni.System system;
    private Random random;

    public Builder(System system, Random random) {
        this.system = system;
        this.random = random;
    }

    public System getSystem() {
        return system;
    }

    public Random getRandom() {
        return random;
    }
}
