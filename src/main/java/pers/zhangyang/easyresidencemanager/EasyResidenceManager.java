package pers.zhangyang.easyresidencemanager;

import org.bstats.bukkit.Metrics;
import pers.zhangyang.easylibrary.EasyPlugin;

public class EasyResidenceManager extends EasyPlugin {
    @Override
    public void onOpen() {

        // bStats统计信息
        new Metrics(EasyResidenceManager.instance,16118);
    }

    @Override
    public void onClose() {

    }
}
