package pers.zhangyang.easyresidencemanager.listener.manageresidencepage;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.annotation.GuiSerialButtonHandler;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePage;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePageResidenceOptionPage;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;

@EventListener
public class PlayerClickManageResidencePageManageResidencePageResidenceOptionPage implements Listener {
    @GuiSerialButtonHandler(guiPage = ManageResidencePage.class,from = 0,to = 44,refreshGui = false,closeGui = false)
    public void on(InventoryClickEvent event){
        int slot=event.getRawSlot();
        Player player= (Player) event.getWhoClicked();

        ManageResidencePage manageTeleportAskPage= (ManageResidencePage) event.getInventory().getHolder();

        assert manageTeleportAskPage != null;
        ResidenceMeta teleportAsk=manageTeleportAskPage.getResidenceMetaList().get(slot);
        new ManageResidencePageResidenceOptionPage(teleportAsk,player,manageTeleportAskPage,manageTeleportAskPage.getOwner()).send();

    }
}
