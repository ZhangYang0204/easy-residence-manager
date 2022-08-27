package pers.zhangyang.easyresidencemanager.meta;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ResidenceMeta {
    private String uuid;
    private String name;
    private String ownerUuid;
    private long createTime;
    private String firstLocation;
    private String secondLocation;
    private Double cost;
    private boolean setup;

    private String mode;
    private String residenceLocation;
    public ResidenceMeta() {
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public ResidenceMeta(String uuid, String name, String ownerUuid, long createTime, String firstLocation,
                         String secondLocation, boolean setup,String mode) {
        this.uuid = uuid;
        this.mode=mode;
        this.name = name;
        this.ownerUuid = ownerUuid;
        this.createTime = createTime;
        this.firstLocation = firstLocation;
        this.secondLocation = secondLocation;
        this.setup = setup;
    }

    @NotNull
    public String getMode() {
        return mode;
    }

    public void setMode(@NotNull String mode) {
        this.mode = mode;
    }

    public void setFirstLocation(String firstLocation) {
        this.firstLocation = firstLocation;
    }

    public void setSecondLocation(String secondLocation) {
        this.secondLocation = secondLocation;
    }

    public String getResidenceLocation() {
        return residenceLocation;
    }

    public void setResidenceLocation(String residenceLocation) {
        this.residenceLocation = residenceLocation;
    }

    public boolean isSetup() {
        return setup;
    }

    public void setSetup(boolean setup) {
        this.setup = setup;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getFirstLocation() {
        return firstLocation;
    }

    public String getSecondLocation() {
        return secondLocation;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResidenceMeta that = (ResidenceMeta) o;
        return createTime == that.createTime && setup == that.setup && Objects.equals(uuid, that.uuid) && Objects.equals(name, that.name) && Objects.equals(ownerUuid, that.ownerUuid) && Objects.equals(firstLocation, that.firstLocation) && Objects.equals(secondLocation, that.secondLocation) && Objects.equals(cost, that.cost) && Objects.equals(mode, that.mode) && Objects.equals(residenceLocation, that.residenceLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, ownerUuid, createTime, firstLocation, secondLocation, cost, setup, mode, residenceLocation);
    }
}
