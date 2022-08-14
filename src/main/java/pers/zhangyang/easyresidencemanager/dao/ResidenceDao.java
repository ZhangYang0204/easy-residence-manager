package pers.zhangyang.easyresidencemanager.dao;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.DaoBase;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResidenceDao extends DaoBase {


    @Override
    public int init() {
        try {
            PreparedStatement ps= getConnection().prepareStatement("" +
                    "CREATE TABLE IF NOT EXISTS residence(" +
                    "uuid TEXT ," +
                    "`name` TEXT," +
                    "owner_uuid TEXT," +
                    "create_time BIGINT," +
                    "first_location TEXT," +
                    "second_location TEXT," +
                    "cost DOUBLE," +
                    "setup BIT " +
                    ")");

        return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int insert(ResidenceMeta residenceMeta){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("" +
                    "INSERT INTO residence(uuid,`name`,owner_uuid,create_time,first_location,second_location,cost,setup)" +
                    "values (?,?,?,?,?,?,?,?)");
            ps.setString(1,residenceMeta.getUuid());
            ps.setString(2,residenceMeta.getName());
            ps.setString(3,residenceMeta.getOwnerUuid());
            ps.setLong(4,residenceMeta.getCreateTime());
            ps.setString(5,residenceMeta.getFirstLocation());
            ps.setString(6,residenceMeta.getSecondLocation());
            ps.setObject(7,residenceMeta.getCost());
            ps.setBoolean(8,residenceMeta.isSetup());
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    public List<ResidenceMeta> list(){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("SELECT * FROM residence");
            ResultSet rs=ps.executeQuery();
            return multipleTransform(rs,ResidenceMeta.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @NotNull
    public List<ResidenceMeta> listByOwnerUuid(String ownerUuid){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("SELECT * FROM residence where owner_uuid=?");
            ps.setString(1,ownerUuid);
            ResultSet rs=ps.executeQuery();
            return multipleTransform(rs,ResidenceMeta.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Nullable
    public ResidenceMeta getByName(String name){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("SELECT * FROM residence where name=?");
            ps.setString(1,name);
            ResultSet rs=ps.executeQuery();
            return singleTransform(rs,ResidenceMeta.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }    @Nullable
    public ResidenceMeta getByUuid(String uuid){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("SELECT * FROM residence where uuid=?");
            ps.setString(1,uuid);
            ResultSet rs=ps.executeQuery();
            return singleTransform(rs,ResidenceMeta.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public int deleteByUuid(String uuid){
        PreparedStatement ps= null;
        try {
            ps = getConnection().prepareStatement("DELETE FROM residence where uuid=?");
            ps.setString(1,uuid);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
