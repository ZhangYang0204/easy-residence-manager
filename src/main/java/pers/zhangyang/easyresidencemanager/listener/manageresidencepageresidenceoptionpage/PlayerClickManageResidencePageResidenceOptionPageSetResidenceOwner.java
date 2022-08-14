package pers.zhangyang.easyresidencemanager.listener.manageresidencepageresidenceoptionpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.TransactionInvocationHandler;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePage;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePageResidenceOptionPage;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;

import java.util.List;

@EventListener
public class PlayerClickManageResidencePageResidenceOptionPageSetResidenceOwner implements Listener {
    @GuiDiscreteButtonHandler(guiPage = ManageResidencePageResidenceOptionPage.class,slot = {22})
    public void on(InventoryClickEvent event){


        Player player= (Player) event.getWhoClicked();

        int slot=event.getRawSlot();
        ManageResidencePageResidenceOptionPage manageTeleportAskPage= (ManageResidencePageResidenceOptionPage) event.getInventory().getHolder();

        Player onlineOwner=manageTeleportAskPage.getOwner().getPlayer();
        if (onlineOwner==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }

        new PlayerInputAfterClickManageResidencePageResidenceOptionPageSetResidenceOwner(player,onlineOwner,manageTeleportAskPage);

    }
}
