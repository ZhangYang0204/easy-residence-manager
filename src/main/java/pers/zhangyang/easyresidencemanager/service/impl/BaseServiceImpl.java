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
    public void transform1_2_1() {
        //从2.0.0到2.2.4的 如果存在version表并且版本小于2.2.4，需要更新

        VersionDao versionDao = new VersionDao();
        VersionMeta versionMeta = versionDao.get();
        assert versionMeta != null;
        if (!VersionUtil.isOlderThan(versionMeta.getBig(), versionMeta.getMiddle(), versionMeta.getSmall(), 1, 2, 1)) {
            return;
        }


        List<ResidenceMeta> iconMetaList = new ResidenceDao().list();
        for (ResidenceMeta iconMeta : iconMetaList) {
            iconMeta.setName(ChatColor.translateAlternateColorCodes('&', iconMeta.getName()));
            new ResidenceDao().deleteByUuid(iconMeta.getUuid());
            new ResidenceDao().insert(iconMeta);
        }


        versionDao.delete();
        versionDao.insert(new VersionMeta(1, 2, 1));
    }



}
