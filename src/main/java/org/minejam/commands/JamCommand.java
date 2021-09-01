package org.minejam.commands;

import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.OverrideOnly
public class JamCommand extends Command {

    private String description;

    public JamCommand(@NotNull String name, @Nullable String... aliases) {
        super(name, aliases);
    }

    public JamCommand(@NotNull String name) {
        super(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public void addPermissions() {

    }
}
