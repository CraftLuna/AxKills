package com.artillexstudios.axkills.utils;

import org.bukkit.Material;

public class ItemUtils {

    public static String getKey(Material material) {
        return (!material.isBlock() ? "item." : "block.") + "minecraft." + material.getKey().getKey();
    }

}
