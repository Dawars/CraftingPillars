package me.dawars.craftingpillars.util.helpers;

import net.minecraft.world.World;

/**
 * Contains various helper functions to assist with determining Server/Client status.
 *
 * @author King Lemming
 */
public final class ServerHelper {

    private ServerHelper() {

    }

    public static boolean isClientWorld(World world) {

        return world.isRemote;
    }

    public static boolean isServerWorld(World world) {

        return !world.isRemote;
    }
}