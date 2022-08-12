package pers.zhangyang.easyresidencemanage.dao;

import pers.zhangyang.easylibrary.base.DaoBase;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResidenceDao extends DaoBase {
    @Override
    public int init() {
        try {
            PreparedStatement ps= getConnection().prepareStatement("" +
                    "CREATE TABLE IF NOT EXIST residence(" +
                    "uuid TEXT ," +
                    "" +
                    "" +
                    "" +
                    "" +
                    ")");

        return ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
