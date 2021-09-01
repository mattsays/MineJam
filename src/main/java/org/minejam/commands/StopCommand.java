package org.minejam.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.minejam.permissions.JamPermissions;

@ApiStatus.Internal
public class StopCommand extends Command {
    public StopCommand() {
        super("stop", "abort");

        this.setCondition((sender, commandString) -> JamPermissions.hasPermission(sender, "minejam.stop"));

        setDefaultExecutor(((sender, context) -> {
            var instances = MinecraftServer.getInstanceManager().getInstances();

            instances.forEach(instance -> {
                var players = instance.getPlayers();
                players.forEach(instancePlayer -> instancePlayer.kick(Component.text("Server stopped", NamedTextColor.RED)));
            });

            MinecraftServer.stopCleanly();
        }));
    }



}
