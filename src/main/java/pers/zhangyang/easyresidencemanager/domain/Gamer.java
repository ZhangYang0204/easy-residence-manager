package pers.zhangyang.easyresidencemanager.domain;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class Gamer {
    private Player player;
    private Location firstLocation;
    private Location secondLocation;

    private Long lastBackTime;

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
