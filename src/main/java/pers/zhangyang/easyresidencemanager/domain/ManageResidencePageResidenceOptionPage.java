package pers.zhangyang.easyresidencemanager.domain;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pers.zhangyang.easylibrary.base.BackAble;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.base.SingleGuiPageBase;
import pers.zhangyang.easylibrary.util.ReplaceUtil;
import pers.zhangyang.easylibrary.util.TimeUtil;
import pers.zhangyang.easylibrary.util.TransactionInvocationHandler;
import pers.zhangyang.easyresidencemanager.exception.NotExistResidenceException;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;
import pers.zhangyang.easyresidencemanager.yaml.GuiYaml;

import java.util.HashMap;

public class ManageResidencePageResidenceOptionPage extends SingleGuiPageBase implements BackAble {
    private  ResidenceMeta residenceMeta;
    public ManageResidencePageResidenceOptionPage(ResidenceMeta residenceMeta, Player viewer, GuiPage backPage, OfflinePlayer owner) {

        super(GuiYaml.INSTANCE.getString("gui.title.manageResidencePageResidenceOptionPage"), viewer, backPage, owner);
        this.residenceMeta=residenceMeta;
    }


    @Override
    public void refresh() {

        this.inventory.clear();

        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();

        this.residenceMeta=guiService.getResidenceByUuid(residenceMeta.getUuid());
        if (this.residenceMeta==null){

            backPage.refresh();
            return;
        }

        ItemStack backPage= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePageResidenceOptionPage.back");
        this.inventory.setItem(49,backPage);



        ItemStack cancelTeleportAsk = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePageResidenceOptionPage.setResidenceOwner");
        this.inventory.setItem(22, cancelTeleportAsk);



        if (!residenceMeta.isSetup()) {
            ItemStack setupResidence = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePageResidenceOptionPage.setupResidence");
            this.inventory.setItem(21, setupResidence);
        }

        if (residenceMeta.isSetup()) {
            ItemStack cancelSetupResidence = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePageResidenceOptionPage.cancelSetupResidence");
            this.inventory.setItem(23, cancelSetupResidence);
        }

        ItemStack cancelSetupResidence = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePageResidenceOptionPage.teleportResidence");
        this.inventory.setItem(13, cancelSetupResidence);


        viewer.openInventory(this.inventory);
    }

    public ResidenceMeta getResidenceMeta() {
        return residenceMeta;
    }

    @Override
    public void back() {
        backPage.refresh();
    }
}
