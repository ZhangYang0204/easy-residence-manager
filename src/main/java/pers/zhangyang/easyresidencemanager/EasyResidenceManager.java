package pers.zhangyang.easyresidencemanager;

import org.bstats.bukkit.Metrics;
import pers.zhangyang.easylibrary.EasyPlugin;
import pers.zhangyang.easylibrary.util.TransactionInvocationHandler;
import pers.zhangyang.easyresidencemanager.service.BaseService;
import pers.zhangyang.easyresidencemanager.service.impl.BaseServiceImpl;

public class EasyResidenceManager extends EasyPlugin {
    @Override
    public void onOpen() {

        BaseService pluginService = (BaseService) new TransactionInvocationHandler(new BaseServiceImpl()).getProxy();
        pluginService.transform1_3_0();
        // bStats统计信息
        new Metrics(EasyResidenceManager.instance,16118);
    }

    @Override
    public void onClose() {

    }
}
