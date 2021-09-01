package org.minejam.commands;

import net.kyori.adventure.permission.PermissionChecker;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.permission.Permission;
import org.minejam.permissions.JamPermissions;

public class OpCommand extends JamCommand {

    public OpCommand() {
        super("op");

        var playerArgument = ArgumentType.Entity("player");

        this.setCondition((sender, commandString) -> sender.isConsole() || JamPermissions.hasPermission(sender, "minejam.op"));

        this.addSyntax(
                (sender, context) -> {
                    var entityFinder = context.get(playerArgument);
                    var player = entityFinder.findFirstPlayer(sender);

                    if(player == null) {
                        sender.sendMessage(Component.text("Player not found", NamedTextColor.RED));
                        return;
                    }

                    sender.sendMessage(Component
                            .text(player.getUsername(), NamedTextColor.DARK_GREEN)
                            .append(Component.text(" is now an operator", NamedTextColor.GREEN)));
                },
                playerArgument);
    }

}
