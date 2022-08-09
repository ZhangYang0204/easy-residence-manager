package pers.zhangyang.easyresidence;

import org.bstats.bukkit.Metrics;
import pers.zhangyang.easylibrary.EasyPlugin;

public class EasyResidence extends EasyPlugin {
    @Override
    public void onOpen() {

        // bStats统计信息
        new Metrics(EasyResidence.instance,15906);
    }

    @Override
    public void onClose() {

    }
}
