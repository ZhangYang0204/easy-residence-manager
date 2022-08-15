package pers.zhangyang.easyresidencemanager.listener.manageresidencepage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyresidencemanager.domain.Gamer;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePage;
import pers.zhangyang.easyresidencemanager.manager.GamerManager;

import java.util.List;

@EventListener
public class PlayerClickManageResidencePageSelectSecondLocation implements Listener {
    @GuiDiscreteButtonHandler(guiPage = ManageResidencePage.class,slot = {51})
    public void on(InventoryClickEvent event){

        Player player= (Player) event.getWhoClicked();

        ManageResidencePage manageTeleportAskPage= (ManageResidencePage) event.getInventory().getHolder();

        Player onlineOwner=manageTeleportAskPage.getOwner().getPlayer();
        if (onlineOwner==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }
        Gamer gamer= GamerManager.INSTANCE.getGamer(onlineOwner);
        gamer.setSecondLocation(player.getLocation());



        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.setSecondLocation");
        MessageUtil.sendMessageTo(player, list);
    }
}
