package com.artillexstudios.axkills.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ColorUtils {

    private final static Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");
    private final static LegacyComponentSerializer LEGACY_COMPONENT_SERIALIZER = LegacyComponentSerializer.builder().hexColors().useUnusualXRepeatedCharacterHexFormat().build();

    public static Component colorLegacy(String string, Placeholder... placeholders) {
        for (Placeholder placeholder : placeholders) {
            string = string.replace(placeholder.key(), placeholder.value());
        }

        Matcher matcher = HEX_PATTERN.matcher(string);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of(matcher.group()).toString());
        }

        return LEGACY_COMPONENT_SERIALIZER.deserialize(ChatColor.translateAlternateColorCodes('&', matcher.appendTail(buffer).toString()));
    }

    public static List<Component> colorLegacy(List<String> list, Placeholder... placeholders) {
        return list.stream().map(s -> colorLegacy(s, placeholders)).collect(Collectors.toList());
    }

}