package com.artillexstudios.axkills.listeners;

import com.artillexstudios.axkills.utils.ColorUtils;
import com.artillexstudios.axkills.utils.ItemUtils;
import com.artillexstudios.axkills.utils.Placeholder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import static com.artillexstudios.axkills.AxKills.CONFIG;


public class DeathListener implements Listener {

    @EventHandler
    public void onDeath(@NotNull PlayerDeathEvent event) {
        final Player player = event.getEntity();

        Component message;

        if (player.getKiller() != null) {
            final Player killer = player.getKiller();

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
                                    killer.getInventory().getHelmet() == null ? Component.text("Yok.")
                                            : Component.translatable(killer.getInventory().getHelmet())
                            ).build()
                    ).replaceText(TextReplacementConfig.builder().matchLiteral("<chestplate>")
                            .replacement(
                                    killer.getInventory().getChestplate() == null ? Component.text("Yok.")
                                            : Component.translatable(killer.getInventory().getChestplate())
                            ).build()
                    ).replaceText(TextReplacementConfig.builder().matchLiteral("<leggings>")
                            .replacement(
                                    killer.getInventory().getLeggings() == null ? Component.text("Yok.")
                                            : Component.translatable(killer.getInventory().getLeggings())
                            ).build()
                    ).replaceText(TextReplacementConfig.builder().matchLiteral("<boots>")
                            .replacement(
                                    killer.getInventory().getBoots() == null ? Component.text("Yok.")
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
        } else if (event.getEntity().getLastDamageCause() != null && CONFIG.isString("death-messages." + event.getEntity().getLastDamageCause().getCause())) {
            message = ColorUtils.colorLegacy(CONFIG.getString("death-messages." + event.getEntity().getLastDamageCause().getCause()),
                    new Placeholder("<victim>", player.getName())
            );
        } else {
            message = Component.empty();
        }

        event.deathMessage(message);
    }
}
