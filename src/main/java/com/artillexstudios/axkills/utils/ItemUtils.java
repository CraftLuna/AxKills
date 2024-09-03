package com.artillexstudios.axkills.utils;

import com.artillexstudios.axkills.AxKills;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static Component getReplacement(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().isAir()) {
            return Component.text(AxKills.CONFIG.getString("empty-hand-text"));
        }

        return Component.translatable(itemStack);
    }

}
