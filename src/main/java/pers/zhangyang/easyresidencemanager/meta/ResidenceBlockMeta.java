package pers.zhangyang.easyresidencemanager.meta;

import java.util.Objects;

public class ResidenceBlockMeta {
    private String residenceUuid;
    private String location;
    private String block;

    public ResidenceBlockMeta() {
    }

    public ResidenceBlockMeta(String residenceUuid, String location, String block) {
        this.residenceUuid = residenceUuid;
        this.location = location;
        this.block = block;
    }

    public String getResidenceUuid() {
        return residenceUuid;
    }

    public String getLocation() {
        return location;
    }

    public String getBlock() {
        return block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResidenceBlockMeta that = (ResidenceBlockMeta) o;
        return Objects.equals(residenceUuid, that.residenceUuid) && Objects.equals(location, that.location) && Objects.equals(block, that.block);
    }

    @Override
    public int hashCode() {
        return Objects.hash(residenceUuid, location, block);
    }
}
