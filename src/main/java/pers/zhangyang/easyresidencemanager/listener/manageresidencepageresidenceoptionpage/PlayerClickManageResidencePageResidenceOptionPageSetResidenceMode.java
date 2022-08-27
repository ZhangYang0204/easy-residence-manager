package pers.zhangyang.easyresidencemanager.listener.manageresidencepageresidenceoptionpage;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePageResidenceOptionPage;

@EventListener
public class PlayerClickManageResidencePageResidenceOptionPageSetResidenceMode implements Listener {

    @GuiDiscreteButtonHandler(guiPage = ManageResidencePageResidenceOptionPage.class,
    slot = {40},refreshGui = false,closeGui = true)
    public void on(InventoryClickEvent event){

        ManageResidencePageResidenceOptionPage manageBackPointPage= (ManageResidencePageResidenceOptionPage) event.getInventory().getHolder();

        int slot=event.getRawSlot();

        Player viewer= (Player) event.getWhoClicked();

        assert manageBackPointPage != null;

        new PlayerInputAfterClickManageResidencePageResidenceOptionPageSetResidenceMode(viewer, manageBackPointPage.getOwner(),
                manageBackPointPage);



    }

}
