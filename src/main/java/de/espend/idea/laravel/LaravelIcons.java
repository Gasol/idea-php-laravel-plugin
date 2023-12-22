package de.espend.idea.laravel;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.util.Calendar;

/**
 * @author Daniel Espendiller <daniel@espendiller.net>
 */
public class LaravelIcons {

    public static final Icon LARAVEL;

    static {
        if(Calendar.getInstance().get(Calendar.MONTH) == Calendar.DECEMBER && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > 7) {
            LARAVEL = IconLoader.getIcon("/icons/laravel_dec.png", LaravelIcons.class);
        } else {
            LARAVEL = IconLoader.getIcon("/icons/laravel.png", LaravelIcons.class);
        }
    }

    public static final Icon CONFIG = IconLoader.getIcon("/icons/config_value.png", LaravelIcons.class);
    public static final Icon ROUTE = IconLoader.getIcon("/icons/route.png", LaravelIcons.class);
    public static final Icon TRANSLATION = IconLoader.getIcon("/icons/translation.png", LaravelIcons.class);
    public static final Icon TEMPLATE_CONTROLLER_LINE_MARKER = IconLoader.getIcon("/icons/controller_line_marker.png", LaravelIcons.class);
}
