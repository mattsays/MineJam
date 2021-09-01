package org.minejam.commands;

import net.minestom.server.MinecraftServer;
import net.minestom.server.command.builder.Command;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class HelpCommand extends JamCommand {

    public HelpCommand() {
        super("help", "?");

        this.setDescription("Shows all command information");

        this.setDefaultExecutor((sender, context) -> {
            var commands = MinecraftServer.getCommandManager().getDispatcher().getCommands();

            sender.sendMessage("Command list: ");
            commands.forEach(command -> {
                for (var cmdName : command.getNames()) {
                    var helpCmd = " - /" + cmdName;
                    if(command instanceof JamCommand jamCommand && jamCommand.getDescription() != null) {
                        helpCmd = helpCmd + " | " + jamCommand.getDescription();
                    }
                    sender.sendMessage(helpCmd);
                }
            });
        });
    }

}
