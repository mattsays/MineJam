package org.minejam.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.arguments.minecraft.registry.ArgumentEntityType;
import org.jetbrains.annotations.ApiStatus;
import org.minejam.permissions.JamPermissions;

@ApiStatus.Internal
public class PingCommand extends JamCommand {

    public PingCommand() {
        super("ping");

        setDefaultExecutor(((sender, context) -> {
            if(sender.isConsole()) {
                sender.sendMessage("Your ping is 0 because you are the host!");
                return;
            }

            var player = sender.asPlayer();

            player.sendMessage(Component
                    .text("Your ping is: " , NamedTextColor.GREEN)
                    .append(Component.text(player.getLatency(), NamedTextColor.GOLD))
            );

        }));

        var playerArgument = ArgumentType.Entity("otherPlayer");

        this.addConditionalSyntax(
                (sender, commandString) -> JamPermissions.hasPermission(sender, "minejam.ping.others"),
                (sender, context) -> {
                    var entityFinder = context.get(playerArgument);
                    var player = entityFinder.findFirstPlayer(sender);

                    if(player == null) {
                        sender.sendMessage(Component.text("Player not found", NamedTextColor.RED));
                        return;
                    }

                    sender.sendMessage(player.getName()
                            .append(Component.text("'s ping is: " , NamedTextColor.GREEN))
                            .append(Component.text(player.getLatency(), NamedTextColor.GOLD))
                    );
                },
                playerArgument);

    }

}
