package org.minejam;

import com.google.common.reflect.ClassPath;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.MinecraftServer;
import net.minestom.server.adventure.audience.Audiences;
import net.minestom.server.command.builder.Command;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.PlayerSkin;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.permission.Permission;
import org.minejam.commands.JamCommand;
import org.minejam.generators.DefaultChunkGenerator;
import java.io.IOException;

public class Entrypoint {

    public static void main(String[] args) {
        var server = MinecraftServer.init();
        MinecraftServer.setBrandName("MineJam");

        MinecraftServer.getCommandManager().getConsoleSender().addPermission(new Permission("*"));

        var instanceManager = MinecraftServer.getInstanceManager();

        var instanceContainer = instanceManager.createInstanceContainer();
        instanceContainer.setChunkGenerator(new DefaultChunkGenerator());


        // Set default spawn
        var globalEventHandler = MinecraftServer.getGlobalEventHandler();
        globalEventHandler.addListener(PlayerLoginEvent.class, event -> {
           var player = event.getPlayer();
           event.setSpawningInstance(instanceContainer);
           player.setSkin(PlayerSkin.fromUsername(player.getUsername()));
           player.setRespawnPoint(new Pos(8, 42 , 8));
           player.setGameMode(GameMode.CREATIVE);
           player.setAllowFlying(true);
           Audiences.all().sendMessage(Component.text(player.getUsername() + " joined the game", NamedTextColor.YELLOW));
        });

        // Add commands
        loadCommands();

        server.start("0.0.0.0", 25565);
    }


    private static void loadCommands() {
        var commandManager = MinecraftServer.getCommandManager();
        var classLoader = Thread.currentThread().getContextClassLoader();
        try {
            var commandClasses = ClassPath.from(classLoader).getTopLevelClasses("org.minejam.commands");
            commandClasses.forEach(commandClassInfo -> {
                if(commandClassInfo.getName().contains("JamCommand")) {
                    return;
                }
                var commandClass = commandClassInfo.load();

                try {
                    var commandObject = commandClass.getConstructor().newInstance();
                    commandManager.register(((Command) commandObject));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
