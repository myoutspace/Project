package main.taskmanager.javaActions;

import android.view.KeyEvent;
import android.view.View;

/**
 * Class SimpleAction.
 * Used for little changes.
 */

public class SimpleAction {
    public static String capitalizeString(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
