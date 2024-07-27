package net.yeyito.gambling.games;

import net.yeyito.gambling.Gambling;
import net.yeyito.gambling.util.Chair;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockType;

import java.util.ArrayList;
import java.util.List;

public class Blackjack {
    public List<Chair> chairs = new ArrayList<>();
    public Blackjack(Location location) {
        Chair chair1 = new Chair();
        Chair chair2 = new Chair();
        chair1.summonChair(location.clone().add(1,0,0),BlockFace.EAST);
        chair2.summonChair(location.clone().add(-1,0,0),BlockFace.WEST);
        chairs.add(chair1);
        chairs.add(chair2);

        Gambling.getOverworld().setBlockData(location, BlockType.OAK_FENCE.createBlockData());
        Gambling.getOverworld().setBlockData(location.clone().add(0,1,0), BlockType.OAK_PRESSURE_PLATE.createBlockData());
    }
}
