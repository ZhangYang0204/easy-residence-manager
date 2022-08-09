package pers.zhangyang.easyresidence.meta;

public class ResidenceMeta {
    private String uuid;
    private String name;
    private String ownerUuid;
    private long createTime;
    private String firstLocation;
    private String secondLocation;
    private String teleportLocation;


    private boolean teleportFlagStats;
    private boolean moveFlagStats;
    private boolean destroyBlockFlagStats;
    private boolean placeBlockFlagStats;
    private boolean useContainerFlagStats;
    private boolean pvpFlagStats;
    private boolean killAnimalFlagStats;
    private boolean killMobFlagStats;


    private Double vaultCost;
    private Integer playerPointsCost;
    private String enterMessage;
    private String leaveMessage;
}
