package pers.zhangyang.easyresidencemanager.domain;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pers.zhangyang.easylibrary.base.BackAble;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.base.MultipleGuiPageBase;
import pers.zhangyang.easylibrary.util.*;
import pers.zhangyang.easylibrary.yaml.SettingYaml;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;
import pers.zhangyang.easyresidencemanager.yaml.GuiYaml;
import pers.zhangyang.easyresidencemanager.yaml.MessageYaml;

import java.util.*;

public class ManageResidencePage extends MultipleGuiPageBase implements BackAble {
    public ManageResidencePage( @NotNull Player viewer, @Nullable GuiPage backPage, OfflinePlayer owner) {
        super(GuiYaml.INSTANCE.getString("gui.title.manageResidencePage"), viewer, backPage, owner,54);
    }

    private List<ResidenceMeta> residenceMetaList;
    @Override
    public void back() {
        List<String> cmdList= GuiYaml.INSTANCE.getStringList("gui.firstPageBackCommand");
        if (cmdList==null){
            return;
        }
        CommandUtil.dispatchCommandList(viewer,cmdList);
    }

    @Override
    public int getBackSlot() {
        return 49;
    }

    @Override
    public void send() {
        this.pageIndex=0;
        refresh();
    }

    @Override
    public void refresh() {
        Player onlineOwner=owner.getPlayer();
        if (onlineOwner==null){
            back();
            return;
        }

        this.inventory.clear();
        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();

        List<ResidenceMeta> residenceMetaList=guiService.listResidence(onlineOwner.getUniqueId().toString());

        this.residenceMetaList = PageUtil.page(this.pageIndex,45,residenceMetaList );

        for (int i=0;i<45;i++){
            if (i >= this.residenceMetaList.size()) {
                break;
            }

            ItemStack itemStack=GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePage.manageResidencePageResidenceOptionPage");
            ResidenceMeta ask=residenceMetaList.get(i);
            HashMap<String,String> rep=new HashMap<>();
           OfflinePlayer offlinePlayer= Bukkit.getOfflinePlayer(UUID.fromString(ask.getOwnerUuid()));
            rep.put("{owner_name}", offlinePlayer.getName()==null?"/":offlinePlayer.getName());
            rep.put("{name}",ask.getName());
            rep.put("{cost}", ask.getCost()==null?"/": String.valueOf(ask.getCost()));

            if (ask.getMode().equalsIgnoreCase("私有模式")){
                rep.put("{mode}", MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.privateMode"));
            }
            if (ask.getMode().equalsIgnoreCase("保护模式")){
                rep.put("{mode}", MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.protectedMode"));
            }
            if (ask.getMode().equalsIgnoreCase("公共模式")){
                rep.put("{mode}", MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.publicMode"));
            }


            rep.put("{create_time}", TimeUtil.getTimeFromTimeMill(ask.getCreateTime()));

            ReplaceUtil.replaceDisplayName(itemStack,rep);
            ReplaceUtil.replaceLore(itemStack,rep);

            this.inventory.setItem(i,itemStack);
        }




        ItemStack returnPage= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePage.back");
        this.inventory.setItem(49,returnPage);

        ItemStack createResidence= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePage.createResidence");
        this.inventory.setItem(48,createResidence);

        ItemStack deleteResidence= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePage.deleteResidence");
        this.inventory.setItem(50,deleteResidence);

        ItemStack selectFirstLocation= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePage.selectFirstLocation");
        this.inventory.setItem(47,selectFirstLocation);

        ItemStack selectSecondLocation= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePage.selectSecondLocation");
        this.inventory.setItem(51,selectSecondLocation);
        ItemStack showSelectedSection= GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePage.showSelectedSection");
        this.inventory.setItem(52,showSelectedSection);


        if (pageIndex > 0) {
            ItemStack previous = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePage.previousPage");
            inventory.setItem(45, previous);
        }
        int maxIndex = PageUtil.computeMaxPageIndex(residenceMetaList.size(), 45);
        if (pageIndex > maxIndex) {
            this.pageIndex = maxIndex;
        }
        if (pageIndex < maxIndex) {
            ItemStack next = GuiYaml.INSTANCE.getButtonDefault("gui.button.manageResidencePage.nextPage");
            inventory.setItem(53, next);
        }

        viewer.openInventory(this.inventory);
    }

    @Override
    public int getPreviousPageSlot() {
        return 45;
    }

    @Override
    public int getNextPageSlot() {
        return 53;
    }

    public List<ResidenceMeta> getResidenceMetaList() {
        return residenceMetaList;
    }
}
