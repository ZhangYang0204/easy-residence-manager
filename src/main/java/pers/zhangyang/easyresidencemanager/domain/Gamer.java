package pers.zhangyang.easyresidencemanager.domain;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;
import pers.zhangyang.easylibrary.EasyPlugin;
import pers.zhangyang.easylibrary.util.VersionUtil;
import pers.zhangyang.easyresidencemanager.exception.NotSameWorldLocationException;

import java.util.HashMap;
import java.util.Map;

public class Gamer {
    private Player player;
    private Location firstLocation;
    private Location secondLocation;

    private Long lastBackTime;


    private Long lastSetupResidenceTime;
    private Long lastCancelSetupResidenceTime;
    private boolean showing;

    //用基岩显示选区，基岩不会被破坏，方便一些
    public  void lookSection() throws NotSameWorldLocationException {

        if (firstLocation.getWorld()==null||secondLocation.getWorld()==null){
            throw new NotSameWorldLocationException();
        }
        if (!firstLocation.getWorld().equals(secondLocation.getWorld())){
            throw new NotSameWorldLocationException();
        }

        showing=true;
        Map<Location, ItemStack[]> locationInventoryContentMap=new HashMap<>();
        Map<Location, BlockState> locationBlockStateMap=new HashMap<>();

        World world=firstLocation.getWorld();

        int xFrom=Math.min(firstLocation.getBlockX(),secondLocation.getBlockX());
        int xTo=Math.max(firstLocation.getBlockX(),secondLocation.getBlockX());
        int yFrom=Math.min(firstLocation.getBlockY(),secondLocation.getBlockY());
        int yTo=Math.max(firstLocation.getBlockY(),secondLocation.getBlockY());

        int zFrom=Math.min(firstLocation.getBlockZ(),secondLocation.getBlockZ());
        int zTo=Math.max(firstLocation.getBlockZ(),secondLocation.getBlockZ());



        for (int i = xFrom; i <= xTo; i++) {

            Location[] locations = new Location[4];
            locations[0] = new Location(world, i, yFrom, zFrom);
            locations[1] = new Location(world, i, yTo, zFrom);
            locations[2] = new Location(world, i, yFrom, zTo);
            locations[3] = new Location(world, i, yTo, zTo);

            for (int ii = 0; ii < 4; ii++) {
                if (locationBlockStateMap.containsKey(locations[ii])){
                    continue;
                }else {
                    Block block = locations[ii].getBlock();
                    BlockState blockState = block.getState();
                    locationBlockStateMap.put(locations[ii],blockState);
                }


                if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13) {

                    Block block = locations[ii].getBlock();
                    BlockState blockState = block.getState();
                    if (blockState instanceof Container){
                        Container container= (Container) blockState;
                        ItemStack[]   inventoryContent=container.getInventory().getContents();
                        locationInventoryContentMap.put(block.getLocation(),inventoryContent);
                    }

                    blockState.setType(Material.BEDROCK);
                    blockState.setRawData(new MaterialData(Material.BEDROCK).getData());
                    blockState.setData(new MaterialData(Material.BEDROCK));
                    blockState.update(true, true);



                } else {

                    Block block = locations[ii].getBlock();
                    BlockState blockState=block.getState();
                    if (blockState instanceof BlockInventoryHolder){
                        BlockInventoryHolder container= (BlockInventoryHolder) blockState;
                        ItemStack[] inventoryContent=container.getInventory().getContents();
                        locationInventoryContentMap.put(block.getLocation(),inventoryContent);
                    }
                    block.setBlockData(Bukkit.createBlockData(Material.BEDROCK),true);
                }

            }

        }


        for (int i = zFrom; i <= zTo; i++) {

            Location[] locations = new Location[4];
            locations[0] = new Location(world, xFrom, yFrom, i);
            locations[1] = new Location(world, xFrom, yTo, i);
            locations[2] = new Location(world, xTo, yFrom, i);
            locations[3] = new Location(world, xTo, yTo, i);

            for (int ii = 0; ii < 4; ii++) {
                if (locationBlockStateMap.containsKey(locations[ii])){
                    continue;
                }else {
                    Block block = locations[ii].getBlock();
                    BlockState blockState = block.getState();
                    locationBlockStateMap.put(locations[ii],blockState);
                }


                if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13) {

                    Block block = locations[ii].getBlock();
                    BlockState blockState = block.getState();
                    if (blockState instanceof Container){
                        Container container= (Container) blockState;
                        ItemStack[]   inventoryContent=container.getInventory().getContents();
                        locationInventoryContentMap.put(block.getLocation(),inventoryContent);
                    }

                    blockState.setType(Material.BEDROCK);
                    blockState.setRawData(new MaterialData(Material.BEDROCK).getData());
                    blockState.setData(new MaterialData(Material.BEDROCK));
                    blockState.update(true, false);



                } else {

                    Block block = locations[ii].getBlock();
                    BlockState blockState=block.getState();
                    if (blockState instanceof BlockInventoryHolder){
                        BlockInventoryHolder container= (BlockInventoryHolder) blockState;
                        ItemStack[] inventoryContent=container.getInventory().getContents();
                        locationInventoryContentMap.put(block.getLocation(),inventoryContent);
                    }
                    block.setBlockData(Bukkit.createBlockData(Material.BEDROCK),true);
                }

            }
        }


        for (int i = yFrom; i <= yTo; i++) {

            Location[] locations = new Location[4];
            locations[0] = new Location(world, xFrom, i, zFrom);
            locations[1] = new Location(world, xFrom, i, zTo);
            locations[2] = new Location(world, xTo, i, zFrom);
            locations[3] = new Location(world, xTo, i, zTo);

            for (int ii = 0; ii < 4; ii++) {
                if (locationBlockStateMap.containsKey(locations[ii])){
                    continue;
                }else {
                    Block block = locations[ii].getBlock();
                    BlockState blockState = block.getState();
                    locationBlockStateMap.put(locations[ii],blockState);
                }


                if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13) {

                    Block block = locations[ii].getBlock();
                    BlockState blockState = block.getState();
                    if (blockState instanceof Container){
                        Container container= (Container) blockState;
                        ItemStack[]   inventoryContent=container.getInventory().getContents();
                        locationInventoryContentMap.put(block.getLocation(),inventoryContent);
                    }

                    blockState.setType(Material.BEDROCK);
                    blockState.setRawData(new MaterialData(Material.BEDROCK).getData());
                    blockState.setData(new MaterialData(Material.BEDROCK));
                    blockState.update(true, false);



                } else {

                    Block block = locations[ii].getBlock();
                    BlockState blockState=block.getState();
                    if (blockState instanceof BlockInventoryHolder){
                        BlockInventoryHolder container= (BlockInventoryHolder) blockState;
                        ItemStack[] inventoryContent=container.getInventory().getContents();
                        locationInventoryContentMap.put(block.getLocation(),inventoryContent);
                    }
                    block.setBlockData(Bukkit.createBlockData(Material.BEDROCK),true);
                }

            }
        }


        new BukkitRunnable() {
            @Override
            public void run() {
                for (Location l:locationBlockStateMap.keySet()){
                    //需要是true，因为大箱子不及时更新问题，而如果序列化则不会出这种问题
                    locationBlockStateMap.get(l).update(true,true);
                }

                if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13) {
                    for (Location l:locationInventoryContentMap.keySet()){
                        ItemStack[] contents=locationInventoryContentMap.get(l);
                        Container container= (Container)l.getBlock().getState();
                        container.getInventory().setContents(contents);
                    }

                }else {
                    for (Location l:locationInventoryContentMap.keySet()){
                        ItemStack[] contents=locationInventoryContentMap.get(l);
                        BlockInventoryHolder container= (BlockInventoryHolder)l.getBlock().getState();
                        container.getInventory().setContents(contents);
                    }
                }

                showing=false;
            }

        }.runTaskLater(EasyPlugin.instance,100);





    }

    public Long getLastSetupResidenceTime() {
        return lastSetupResidenceTime;
    }

    public void setLastSetupResidenceTime(Long lastSetupResidenceTime) {
        this.lastSetupResidenceTime = lastSetupResidenceTime;
    }

    public Long getLastCancelSetupResidenceTime() {
        return lastCancelSetupResidenceTime;
    }

    public void setLastCancelSetupResidenceTime(Long lastCancelSetupResidenceTime) {
        this.lastCancelSetupResidenceTime = lastCancelSetupResidenceTime;
    }

    public boolean isLooking() {
        return showing;
    }


    public Long getLastBackTime() {
        return lastBackTime;
    }

    public void setLastBackTime(Long lastBackTime) {
        this.lastBackTime = lastBackTime;
    }

    public Gamer(Player player){
        this.player=player;
    }
    public Location getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(Location firstLocation) {
        this.firstLocation = firstLocation;
    }

    public Location getSecondLocation() {
        return secondLocation;
    }

    public void setSecondLocation(Location secondLocation) {
        this.secondLocation = secondLocation;
    }


    public Player getPlayer() {
        return player;
    }
}
