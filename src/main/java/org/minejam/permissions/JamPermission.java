package org.minejam.permissions;

import net.minestom.server.permission.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.hephaistos.nbt.NBTCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class JamPermission extends Permission {
    protected JamPermission parent;
    protected List<JamPermission> children = new ArrayList<>();

    public JamPermission(@NotNull String permissionName, @Nullable NBTCompound data) {
        super(permissionName, data);
    }

    public JamPermission(@NotNull String permissionName) {
        super(permissionName);
    }

}
