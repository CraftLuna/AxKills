package com.artillexstudios.axkills.listeners;

import com.artillexstudios.axkills.utils.ColorUtils;
import com.artillexstudios.axkills.utils.ItemUtils;
import com.artillexstudios.axkills.utils.Placeholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import static com.artillexstudios.axkills.AxKills.CONFIG;

public class DeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(@NotNull PlayerDeathEvent event) {
        final Player player = event.getEntity();

        Component message;
        final Player killer = player.getKiller();

        if (killer != null && !killer.getName().equals(player.getName())) {
            Material weapon = killer.getInventory().getItemInMainHand().getType();
            if (weapon != Material.NETHERITE_SWORD && weapon != Material.DIAMOND_SWORD &&
                    weapon != Material.NETHERITE_AXE && weapon != Material.DIAMOND_AXE) {
                event.deathMessage(null);
                return;
            }

            Component hover = Component.join(Component.newline(), ColorUtils.colorLegacy(CONFIG.getStringList("death-messages.HOVER"),
                    new Placeholder("<attacker>", killer.getName()),
                    new Placeholder("<victim>", player.getName()),
                    new Placeholder("<health>", String.format("%.1f", killer.getHealth() / 2))
            ));

            hover = hover
                    .replaceText(TextReplacementConfig.builder().matchLiteral("<item>")
                            .replacement(ItemUtils.getReplacement(killer.getInventory().getItemInMainHand())).build())
                    .replaceText(TextReplacementConfig.builder().matchLiteral("<helmet>")
                            .replacement(
                                    killer.getInventory().getHelmet() == null ? Component.text("Kafalık bulunmuyor")
                                            : Component.translatable(killer.getInventory().getHelmet())
                            ).build()
                    ).replaceText(TextReplacementConfig.builder().matchLiteral("<chestplate>")
                            .replacement(
                                    killer.getInventory().getChestplate() == null ? Component.text("Göğüslük bulunmuyor")
                                            : Component.translatable(killer.getInventory().getChestplate())
                            ).build()
                    ).replaceText(TextReplacementConfig.builder().matchLiteral("<leggings>")
                            .replacement(
                                    killer.getInventory().getLeggings() == null ? Component.text("Pantalon bulunmuyor")
                                            : Component.translatable(killer.getInventory().getLeggings())
                            ).build()
                    ).replaceText(TextReplacementConfig.builder().matchLiteral("<boots>")
                            .replacement(
                                    killer.getInventory().getBoots() == null ? Component.text("Bot bulunmuyor")
                                            : Component.translatable(killer.getInventory().getBoots())
                            ).build()
                    );

            Component component = ColorUtils.colorLegacy(CONFIG.getString("death-messages.KILLED"),
                    new Placeholder("<attacker>", killer.getName()),
                    new Placeholder("<victim>", player.getName()),
                    new Placeholder("<health>", String.format("%.1f", killer.getHealth() / 2))
            ).replaceText(TextReplacementConfig.builder().matchLiteral("<item>")
                    .replacement(ItemUtils.getReplacement(killer.getInventory().getItemInMainHand())).build());

            message = component.hoverEvent(hover);
            event.deathMessage(message);
        } else {
            event.deathMessage(null);
        }
    }
}
