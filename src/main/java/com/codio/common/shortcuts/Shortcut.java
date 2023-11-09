package com.codio.common.shortcuts;

import com.codio.common.HotKeys;
import com.codio.helpers.OsValidator;

public class Shortcut {
    private final String win;
    private final String mac;

    /**
     * Shortcut should be in format: mod + mod + mod + lastKey. + can be replaced
     * with -, spaces between are optional. Modifier is one of the list: Alt, Cmd,
     * Ctrl, Shift, and they are optional. LastKey is single letter (a, b, c, etc.,
     * except + and -), or special key name (BACK_SPACE, ADD, SUBTRACT, etc.)
     * Examples of legal shortcuts: 'alt + up', 'Ctrl-Shift-F', 'BACK_SPACE'.
     * Shortcuts don't work on macOs if input context locale not en_US
     */
    public Shortcut(String win, String mac) {
        this.win = win;
        this.mac = mac;
    }

    public String get() {
        return OsValidator.isMac() ? mac : win;
    }

    public void execute() {
        HotKeys.executeShortcut(get());
    }
}
