package pers.zhangyang.easyresidence.meta;

public class ResidenceMeta {
    private String uuid;
    private String name;
    private String ownerUuid;
    private long createTime;
    private String firstLocation;
    private String secondLocation;
    private String teleportLocation;


    private boolean teleportFlag;
    private boolean moveFlag;
    private boolean destroyBlockFlag;
    private boolean placeBlockFlag;
    private boolean useContainerFlag;
    private boolean pvpFlag;
    private boolean killAnimalFlag;
    private boolean killMobFlag;


    private Double vaultCost;
    private Integer playerPointsCost;
    private String enterMessage;
    private String leaveMessage;
}
