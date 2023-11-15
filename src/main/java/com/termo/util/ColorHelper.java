package com.termo.util;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ansi.ANSIComponentSerializer;
import net.kyori.ansi.ColorLevel;

public class ColorHelper {

    private ColorHelper() {
        throw new IllegalStateException("Utility class");
    }

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    private static final ANSIComponentSerializer ansi = ANSIComponentSerializer.builder()
            .colorLevel(ColorLevel.TRUE_COLOR)
            .build();

    public static String colorize(String text) {
        try {
            return ansi.serialize(miniMessage.deserialize(text));
        } catch (Exception e) {
            System.err.println("Error parsing text: " + text);
            return text;
        }
    }

    public static void console(String text) {
        System.out.println(colorize(text));
    }

    public static void console(String text, Object... format) {
        System.out.println(colorize(String.format(text, format)));
    }

}