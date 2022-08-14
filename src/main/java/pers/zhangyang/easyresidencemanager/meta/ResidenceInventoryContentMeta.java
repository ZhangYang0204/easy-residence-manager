package pers.zhangyang.easyresidencemanager.meta;

import java.util.Objects;

public class ResidenceInventoryContentMeta {
    private String residenceUuid;
    private String location;
    private String inventoryContent;
    public ResidenceInventoryContentMeta() {
    }
    public ResidenceInventoryContentMeta(String residenceUuid, String location, String inventoryContent) {
        this.residenceUuid = residenceUuid;
        this.location = location;
        this.inventoryContent = inventoryContent;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResidenceInventoryContentMeta that = (ResidenceInventoryContentMeta) o;
        return Objects.equals(residenceUuid, that.residenceUuid) && Objects.equals(location, that.location) && Objects.equals(inventoryContent, that.inventoryContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(residenceUuid, location, inventoryContent);
    }

    public String getInventoryContent() {
        return inventoryContent;
    }

    public String getResidenceUuid() {
        return residenceUuid;
    }

    public String getLocation() {
        return location;
    }

}
