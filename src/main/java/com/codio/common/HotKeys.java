package com.codio.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.codeborne.selenide.Selenide;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import com.codio.common.shortcuts.Shortcut;
import com.codio.helpers.Driver;

public class HotKeys {

    /**
     * Executes shortcut. Shortcut should be in format: mod + mod + mod + lastKey. +
     * can be replaced with -, spaces between are optional. Modifier is one of the
     * list: Alt, Cmd, Ctrl, Shift, and they are optional. LastKey is single letter
     * (a, b, c, etc., except + and -), or special key name (BACK_SPACE, ADD,
     * SUBTRACT, etc.) Examples of legal shortcuts: 'alt + up', 'Ctrl-Shift-F',
     * 'BACK_SPACE'. Shortcuts don't work on macOs if input context locale not en_US
     */
    public static void executeShortcut(String shortcut) {
        List<String> keys = shortcutStringToList(shortcut);
        press(getSpecKeysFromList(keys), getLastKeyFromList(keys));
    }

    private static List<String> shortcutStringToList(String shortcut) {
        return Arrays.asList(shortcut.toUpperCase().replaceAll("\\s", "").replaceAll("-", "+").split("\\+"));
    }

    private static List<Keys> getSpecKeysFromList(List<String> keys) {
        List<Keys> spec = new ArrayList<>();
        if (keys.contains("ALT") || keys.contains("⌥")) {
            spec.add(Keys.ALT);
        }
        if (keys.contains("CTRL") || keys.contains("^")) {
            spec.add(Keys.CONTROL);
        }
        if (keys.contains("CMD") || keys.contains("⌘")) {
            spec.add(Keys.COMMAND);
        }
        if (keys.contains("SHIFT") || keys.contains("⇧")) {
            spec.add(Keys.SHIFT);
        }
        if (spec.size() == keys.size()) {
            Driver
                .exitTestWithError("Shortcut should contain additional key (except Alt, Ctrl, Cmd, Shift) in the end");
        }
        return spec;
    }

    private static CharSequence getLastKeyFromList(List<String> keys) {
        String lastKey = keys.get(keys.size() - 1);
        return lastKey.length() == 1 ? lastKey.toLowerCase() : Keys.valueOf(lastKey.toUpperCase());
    }

    private static void press(List<Keys> spec, CharSequence lastKey) {
        Actions actions = Selenide.actions();
        for (Keys k : spec) {
            actions.keyDown(k);
        }
        actions.sendKeys(lastKey);
        for (Keys k : spec) {
            actions.keyUp(k);
        }
        actions.perform();
    }

    public static void pressKeys(CharSequence keys) {
        Selenide.actions().sendKeys(keys).perform();
    }

    public static void deleteAll() {
        selectAll();
        pressKeys(Keys.DELETE);
    }

    public static void selectAll() {
        new Shortcut("Ctrl+A", "Cmd+A").execute();
    }
}
