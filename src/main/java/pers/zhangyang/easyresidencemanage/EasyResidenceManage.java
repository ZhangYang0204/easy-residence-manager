package pers.zhangyang.easyresidencemanage;

import org.bstats.bukkit.Metrics;
import pers.zhangyang.easylibrary.EasyPlugin;

public class EasyResidenceManage extends EasyPlugin {
    @Override
    public void onOpen() {

        // bStats统计信息
        new Metrics(EasyResidenceManage.instance,15906);
    }

    @Override
    public void onClose() {

    }
}
