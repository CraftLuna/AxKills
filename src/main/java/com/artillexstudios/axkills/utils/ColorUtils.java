package com.artillexstudios.axkills.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public class ColorUtils {

    private final static MiniMessage MINI_MESSAGE = MiniMessage.builder()
            .tags(TagResolver.standard())
            .postProcessor(component -> component.decoration(TextDecoration.ITALIC, false))
            .build();

    @NotNull
    public static Component format(@NotNull String message, TagResolver... placeholders) {
        return MINI_MESSAGE.deserialize(message, placeholders);
    }

}