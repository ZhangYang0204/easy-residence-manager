package pers.zhangyang.easyresidencemanager.dao;

import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.DaoBase;
import pers.zhangyang.easyresidencemanager.meta.ResidenceBlockMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceInventoryContentMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResidenceInventoryContentDao extends DaoBase {


    @Override
    public int init() {
        try {
            PreparedStatement ps= getConnection().prepareStatement("" +
                    "CREATE TABLE IF NOT EXISTS residence_inventory(" +
                    "residence_uuid TEXT ," +
                    "location TEXT," +
                    "inventory_content TEXT" +
                    ")");

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(ResidenceInventoryContentMeta residenceInventoryContentMeta){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("" +
                    "INSERT INTO residence_inventory (residence_uuid,location,inventory_content)" +
                    "values (?,?,?)");
            ps.setString(1, residenceInventoryContentMeta.getResidenceUuid());
            ps.setString(2, residenceInventoryContentMeta.getLocation());
            ps.setString(3, residenceInventoryContentMeta.getInventoryContent());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @NotNull
    public List<ResidenceInventoryContentMeta> listByResidenceUuid(String residenceUuid){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("SELECT * FROM residence_inventory where residence_uuid=?");
            ps.setString(1,residenceUuid);
            ResultSet rs=ps.executeQuery();
            return multipleTransform(rs,ResidenceInventoryContentMeta.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int deleteByResidenceUuid(String residenceUuid){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("DELETE FROM residence_inventory where residence_uuid=?");
            ps.setString(1,residenceUuid);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
