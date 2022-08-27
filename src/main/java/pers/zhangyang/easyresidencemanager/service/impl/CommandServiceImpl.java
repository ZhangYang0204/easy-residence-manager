package pers.zhangyang.easyresidencemanager.service.impl;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Location;
import pers.zhangyang.easylibrary.util.LocationUtil;
import pers.zhangyang.easyresidencemanager.dao.ResidenceDao;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.CommandService;
import pers.zhangyang.easyresidencemanager.yaml.SettingYaml;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommandServiceImpl implements CommandService {

    @Override
    public void correctDatabase() {
        //修理Shop表的序列化数据
        List<ResidenceMeta> shopMetaList = new ResidenceDao().list();
        for (ResidenceMeta s : shopMetaList) {

            try {
                String data = s.getFirstLocation();
                if (data != null) {
                    Location location = LocationUtil.deserializeLocation(data);
                    if (location.getWorld() == null) {
                        new ResidenceDao().deleteByUuid(s.getUuid());
                        s.setFirstLocation(null);
                        new ResidenceDao().insert(s);
                    }
                }
            } catch (Exception e) {
                new ResidenceDao().deleteByUuid(s.getUuid());
                s.setFirstLocation(null);
                new ResidenceDao().insert(s);
            }


            try {
                String data = s.getSecondLocation();
                if (data != null) {
                    Location location = LocationUtil.deserializeLocation(data);
                    if (location.getWorld() == null) {
                        new ResidenceDao().deleteByUuid(s.getUuid());
                        s.setSecondLocation(null);
                        new ResidenceDao().insert(s);
                    }
                }
            } catch (Exception e) {
                new ResidenceDao().deleteByUuid(s.getUuid());
                s.setSecondLocation(null);
                new ResidenceDao().insert(s);
            }
            try {
                String data = s.getResidenceLocation();
                if (data != null) {
                    Location location = LocationUtil.deserializeLocation(data);
                    if (location.getWorld() == null) {
                        new ResidenceDao().deleteByUuid(s.getUuid());
                        s.setResidenceLocation(null);
                        new ResidenceDao().insert(s);
                    }
                }
            } catch (Exception e) {
                new ResidenceDao().deleteByUuid(s.getUuid());
                s.setResidenceLocation(null);
                new ResidenceDao().insert(s);
            }

            List<String> wb = SettingYaml.INSTANCE.getStringList("setting.worldBlackList");
            if (wb != null && wb.contains(Objects.requireNonNull(
                    LocationUtil.deserializeLocation(s.getFirstLocation()).getWorld()).getName())) {
                new ResidenceDao().deleteByUuid(s.getUuid());
                s.setFirstLocation(null);
                new ResidenceDao().insert(s);
            }

            if (wb != null && wb.contains(Objects.requireNonNull(
                    LocationUtil.deserializeLocation(s.getSecondLocation()).getWorld()).getName())) {
                new ResidenceDao().deleteByUuid(s.getUuid());
                s.setFirstLocation(null);
                new ResidenceDao().insert(s);
            }

            if (wb != null && s.getResidenceLocation() != null && wb.contains(Objects.requireNonNull(
                    LocationUtil.deserializeLocation(s.getResidenceLocation()).getWorld()).getName())) {
                new ResidenceDao().deleteByUuid(s.getUuid());
                s.setFirstLocation(null);
                new ResidenceDao().insert(s);
            }

        }
    }
}
