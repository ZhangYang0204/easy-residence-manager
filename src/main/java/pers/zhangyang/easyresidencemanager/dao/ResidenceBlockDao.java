package pers.zhangyang.easyresidencemanager.dao;

import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easylibrary.base.DaoBase;
import pers.zhangyang.easyresidencemanager.meta.ResidenceBlockMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResidenceBlockDao extends DaoBase {
    @Override
    public int init() {
        try {
            PreparedStatement ps= getConnection().prepareStatement("" +
                    "CREATE TABLE IF NOT EXISTS residence_block(" +
                    "residence_uuid TEXT ," +
                    "location TEXT," +
                    "block TEXT" +
                    ")");

            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(ResidenceBlockMeta residenceBlockMeta){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("" +
                    "INSERT INTO residence_block (residence_uuid,location,block)" +
                    "values (?,?,?)");
            ps.setString(1,residenceBlockMeta.getResidenceUuid());
            ps.setString(2,residenceBlockMeta.getLocation());
            ps.setString(3,residenceBlockMeta.getBlock());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public List<ResidenceBlockMeta> listByResidenceUuid(String residenceUuid){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("SELECT * FROM residence_block where residence_uuid=?");
            ps.setString(1,residenceUuid);
            ResultSet rs=ps.executeQuery();
            return multipleTransform(rs,ResidenceBlockMeta.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int deleteByResidenceUuid(String residenceUuid){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("DELETE FROM residence_block where residence_uuid=? ");
            ps.setString(1,residenceUuid);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
