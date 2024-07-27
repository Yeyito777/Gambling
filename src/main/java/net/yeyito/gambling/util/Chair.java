package net.yeyito.gambling.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Chair implements Listener {
    public Interaction interaction;
    public Block chairBlock;
    public Chair() {}

    public void summonChair(Location location, BlockFace facing) {
        // Set the chair block
        Block chairBlock = location.getBlock();
        chairBlock.setType(Material.OAK_STAIRS);

        // Set the facing direction of the stairs
        Directional blockData = (Directional) chairBlock.getBlockData();
        blockData.setFacing(facing);
        chairBlock.setBlockData(blockData);

        // Spawn the interaction entity
        Location interactionLoc = chairBlock.getLocation().add(0.5, 0.5, 0.5);
        Interaction interaction = (Interaction) interactionLoc.getWorld().spawnEntity(interactionLoc, EntityType.INTERACTION);
        interaction.setInteractionWidth(1f);
        interaction.setInteractionHeight(0.6f);

        this.interaction = interaction;
        this.chairBlock = chairBlock;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Interaction) {
            Interaction interaction = (Interaction) event.getRightClicked();
            Player player = event.getPlayer();

            // Check if the interaction entity is above a stairs block
            Block block = interaction.getLocation().subtract(0, 0, 0).getBlock();
            if (block.getType() == Material.OAK_STAIRS && interaction.getPassengers().size() < 1) {
                // Make the player sit (ride) the interaction entity
                interaction.addPassenger(player);
            }
        }
    }
}