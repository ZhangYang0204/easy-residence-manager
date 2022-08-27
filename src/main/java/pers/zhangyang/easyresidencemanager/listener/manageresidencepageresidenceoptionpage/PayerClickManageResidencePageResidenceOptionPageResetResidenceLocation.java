package pers.zhangyang.easyresidencemanager.listener.manageresidencepageresidenceoptionpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.util.LocationUtil;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.TransactionInvocationHandler;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePageResidenceOptionPage;
import pers.zhangyang.easyresidencemanager.exception.NotExistResidenceException;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;

import java.util.List;

@EventListener
public class PayerClickManageResidencePageResidenceOptionPageResetResidenceLocation implements Listener {


    @GuiDiscreteButtonHandler(guiPage = ManageResidencePageResidenceOptionPage.class,slot = {32},
            refreshGui = true,closeGui = false)
    public void on(InventoryClickEvent event){

        ManageResidencePageResidenceOptionPage manageBackPointPage= (ManageResidencePageResidenceOptionPage) event.getInventory().getHolder();

        int slot=event.getRawSlot();

        Player viewer= (Player) event.getWhoClicked();
        assert manageBackPointPage != null;

        ResidenceMeta backPoint=manageBackPointPage.getResidenceMeta();
        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();

        try {
            guiService.setResidenceLocation(backPoint.getUuid(), null);
        } catch (NotExistResidenceException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistResidence");
            MessageUtil.sendMessageTo(viewer, list);
            return;
        }
        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.resetResidenceLocation");
        MessageUtil.sendMessageTo(viewer, list);

    }


}
