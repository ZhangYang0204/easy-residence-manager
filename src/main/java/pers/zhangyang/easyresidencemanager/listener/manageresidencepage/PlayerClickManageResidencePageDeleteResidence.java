package pers.zhangyang.easyresidencemanager.listener.manageresidencepage;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePage;

import java.util.List;

@EventListener
public class PlayerClickManageResidencePageDeleteResidence {
    @GuiDiscreteButtonHandler(guiPage = ManageResidencePage.class,slot = {50},refreshGui = false,closeGui = true)
    public void on(InventoryClickEvent event){
        Player player= (Player) event.getWhoClicked();

        ManageResidencePage manageTeleportAskPage= (ManageResidencePage) event.getInventory().getHolder();

        Player onlineOwner=manageTeleportAskPage.getOwner().getPlayer();
        if (onlineOwner==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }

        new PlayerInputAfterClickManageResidencePageDeleteResidence(player,onlineOwner,manageTeleportAskPage);

    }
}
