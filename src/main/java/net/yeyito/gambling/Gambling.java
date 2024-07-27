package net.yeyito.gambling;

import net.yeyito.gambling.games.Blackjack;
import net.yeyito.gambling.util.Chair;
import org.bukkit.*;
import org.bukkit.block.BlockFace;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.command.brigadier.argument.ArgumentTypes;
import io.papermc.paper.command.brigadier.argument.resolvers.ArgumentResolver;
import io.papermc.paper.math.Position;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;
import java.util.List;

/** @noinspection UnstableApiUsage*/
public final class Gambling extends JavaPlugin {
    private static Plugin instance;
    private static World OVERWORLD;

    public static World getOverworld() {
        return OVERWORLD;
    }

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Gambling enabled!");
        OVERWORLD = this.getServer().getWorlds().get(0);
        Server server = this.getServer();
        server.getPluginManager().registerEvents(new Chair(),this);

        LifecycleEventManager<Plugin> manager = this.getLifecycleManager();
        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();
            commands.register(
                    Commands.literal("chair")
                            .requires(sender -> sender.getSender().hasPermission("chair.summon"))
                            .executes(this::summonChair)
                            .build(),
                    "Summons a chair at the player's location",
                    List.of("sitdown")
            );

            commands.register(
                    Commands.literal("blackjack")
                            .requires(sender -> sender.getSender().hasPermission("chair.summon"))
                            .executes(ctx -> {
                                new Blackjack(((Player) ctx.getSource().getSender()).getLocation());
                                return Command.SINGLE_SUCCESS;
                            })
                            .build(),
                    "Summons a chair at the player's location",
                    List.of("sitdown")
            );
        });

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private int summonChair(CommandContext<CommandSourceStack> ctx) {
        Location playerLocation = ((Player) ctx.getSource().getSender()).getLocation();
        BlockFace playerFacing = ((Player) ctx.getSource().getSender()).getFacing();

        // Adjust the location to be in front of the player
        Location chairLocation = playerLocation.add(playerFacing.getDirection());

        new Chair().summonChair(chairLocation, playerFacing);
        return Command.SINGLE_SUCCESS;
    }
}
