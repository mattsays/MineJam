package org.minejam.permissions;

import net.minestom.server.command.CommandSender;
import net.minestom.server.permission.PermissionHandler;

public class JamPermissions {

    private JamPermission rootPermission;

    public static boolean hasPermission(PermissionHandler target, String permission) {
        if(target.hasPermission("*")) {
            return true;
        }

        return target.hasPermission(permission);
    }

}
