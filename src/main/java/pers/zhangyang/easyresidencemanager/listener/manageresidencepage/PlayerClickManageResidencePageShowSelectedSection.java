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
import pers.zhangyang.easyresidencemanager.exception.NotSameWorldLocationException;
import pers.zhangyang.easyresidencemanager.manager.GamerManager;

import java.util.List;

@EventListener
public class PlayerClickManageResidencePageShowSelectedSection implements Listener {
        @GuiDiscreteButtonHandler(guiPage = ManageResidencePage.class,slot = {52},refreshGui = true,closeGui = true)
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
            if (gamer.getFirstLocation()==null||gamer.getSecondLocation()==null){
                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notSelectedTwoLocationWhenShowSelectedSection");
                MessageUtil.sendMessageTo(player, list);
                return;
            }

            try {
                gamer.lookSection();
            } catch (NotSameWorldLocationException e) {
                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notSameWorldLocationWhenShowSelectedSection");
                MessageUtil.sendMessageTo(player, list);
                return;
            }

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.showSelectedSection");
            MessageUtil.sendMessageTo(player, list);
        }


}
