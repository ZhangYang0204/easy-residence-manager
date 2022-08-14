package pers.zhangyang.easyresidencemanager.listener.manageresidencepageresidenceoptionpage;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.util.LocationUtil;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.PermUtil;
import pers.zhangyang.easylibrary.util.TransactionInvocationHandler;
import pers.zhangyang.easyresidencemanager.domain.Gamer;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePageResidenceOptionPage;
import pers.zhangyang.easyresidencemanager.exception.NotExistResidenceException;
import pers.zhangyang.easyresidencemanager.manager.GamerManager;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;
import pers.zhangyang.easyresidencemanager.yaml.MessageYaml;

import java.util.List;

@EventListener
public class PlayerClickManageResidencePageResidenceOptionPageTeleportResidence implements Listener {
    @GuiDiscreteButtonHandler(guiPage = ManageResidencePageResidenceOptionPage.class,slot = {13})
    public void on(InventoryClickEvent event){


        ManageResidencePageResidenceOptionPage manageBackPointPage= (ManageResidencePageResidenceOptionPage) event.getInventory().getHolder();

        int slot=event.getRawSlot();

        Player viewer= (Player) event.getWhoClicked();

        assert manageBackPointPage != null;

        ResidenceMeta backPoint=manageBackPointPage.getResidenceMeta();
        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        backPoint=guiService.getResidenceByUuid(backPoint.getUuid());
        if (backPoint==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistResidence");
            MessageUtil.sendMessageTo(viewer, list);
            manageBackPointPage.refresh();
            return;
        }
        Player onlineOwner=manageBackPointPage.getOwner().getPlayer();
        if (onlineOwner==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(viewer, list);
            return;
        }

        if (!onlineOwner.isOp()) {
            Integer perm = PermUtil.getMinNumberPerm("EasyBack.teleportResidenceInterval.", onlineOwner);
            if (perm == null) {
                perm = 0;
            }
            Gamer gamer= GamerManager.INSTANCE.getGamer(onlineOwner);
            if (gamer.getLastBackTime() != null && System.currentTimeMillis() - gamer.getLastBackTime()
                    < perm * 1000L) {

                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.tooFastWhenTeleportResidence");
                MessageUtil.sendMessageTo(viewer, list);
                return;
            }
        }

        viewer.teleport(LocationUtil.deserializeLocation(backPoint.getFirstLocation()));


        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.teleportResidence");
        MessageUtil.sendMessageTo(viewer, list);

    }
}
