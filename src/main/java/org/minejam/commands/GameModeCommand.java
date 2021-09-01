package org.minejam.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;
import net.minestom.server.entity.GameMode;
import org.jetbrains.annotations.ApiStatus;
import org.minejam.permissions.JamPermissions;

import java.util.List;

@ApiStatus.Internal
public class GameModeCommand extends JamCommand {

    public GameModeCommand() {
        super("gamemode", "gm");

        this.setDescription("Changes player's gamemode");

        var gamemodeType = ArgumentType.Enum("gamemodeType", GameMode.class);

        this.setCondition((sender, commandString) -> {
            if(sender.isConsole()) {
                sender.sendMessage(Component.text("You must be a player to change gamemode!", NamedTextColor.RED));
                return false;
            }

            if(commandString == null)
                return false;

            var args = commandString.split(" ");

            if(args.length == 2 && !args[1].isEmpty()) {
                return JamPermissions.hasPermission(sender, "minejam.gamemode." + args[1]);
            } else {
                return JamPermissions.hasPermission(sender, "minejam.gamemode");
            }
        });

        this.addSyntax(
                (sender, context) -> {
                    var gamemode = context.get(gamemodeType);
                    var player = sender.asPlayer();
                    player.setGameMode(gamemode);
                    player.setAllowFlying(gamemode == GameMode.CREATIVE || gamemode == GameMode.SPECTATOR);
                },
                gamemodeType);
    }

}
