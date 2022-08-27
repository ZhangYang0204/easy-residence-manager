package pers.zhangyang.easyresidencemanager.service.impl;

import com.google.gson.Gson;
import org.bukkit.ChatColor;
import pers.zhangyang.easyresidencemanager.dao.*;
import pers.zhangyang.easyresidencemanager.meta.*;
import pers.zhangyang.easyresidencemanager.service.BaseService;
import pers.zhangyang.easylibrary.dao.VersionDao;
import pers.zhangyang.easylibrary.meta.VersionMeta;
import pers.zhangyang.easylibrary.util.VersionUtil;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pers.zhangyang.easylibrary.base.DaoBase.getConnection;

public class BaseServiceImpl implements BaseService {





    @Override
    public void transform1_3_0() {
        try {
            DatabaseMetaData metaData = getConnection().getMetaData();
            ResultSet rs = metaData.getTables(null, null, "version", null);
            if (!rs.next()) {
                return;
            }
        VersionDao versionDao = new VersionDao();
        VersionMeta versionMeta = versionDao.get();
        assert versionMeta != null;
        if (!VersionUtil.isOlderThan(versionMeta.getBig(), versionMeta.getMiddle(), versionMeta.getSmall(), 1, 3, 0)) {
            return;
        }

        PreparedStatement ps= getConnection().prepareStatement("" +
                    "ALTER TABLE residence ADD " +
                    "residence_location TEXT ");
            ps.executeUpdate();
             ps= getConnection().prepareStatement("" +
                    "ALTER TABLE residence ADD " +
                    "mode TEXT ");
            ps.executeUpdate();

            List<ResidenceMeta> iconMetaList = new ResidenceDao().list();
            for (ResidenceMeta iconMeta : iconMetaList) {
                if (iconMeta.getMode()==null) {
                    iconMeta.setMode("私有模式");
                }
                iconMeta.setName(ChatColor.translateAlternateColorCodes('&', iconMeta.getName()));
                new ResidenceDao().deleteByUuid(iconMeta.getUuid());
                new ResidenceDao().insert(iconMeta);
            }


        versionDao.delete();
        versionDao.insert(new VersionMeta(1, 3, 0));

        } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }


}
