package pers.zhangyang.easyresidencemanager.listener.manageresidencepageresidenceoptionpage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.util.*;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePageResidenceOptionPage;
import pers.zhangyang.easyresidencemanager.exception.DuplicateSetupResidenceException;
import pers.zhangyang.easyresidencemanager.exception.NotExistResidenceException;
import pers.zhangyang.easyresidencemanager.meta.ResidenceBlockMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceInventoryContentMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;

import java.util.ArrayList;
import java.util.List;

@EventListener
public class PlayerClickManageResidencePageResidenceOptionPageSetupResidence implements Listener {
    @GuiDiscreteButtonHandler(guiPage = ManageResidencePageResidenceOptionPage.class,slot = {13},refreshGui = true,closeGui = false)
    public void on(InventoryClickEvent event){

        Player player= (Player) event.getWhoClicked();

        int slot=event.getRawSlot();
        ManageResidencePageResidenceOptionPage manageTeleportAskPage= (ManageResidencePageResidenceOptionPage) event.getInventory().getHolder();


        assert manageTeleportAskPage != null;
        ResidenceMeta residenceMeta=manageTeleportAskPage.getResidenceMeta();


        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        List[] lists;
        try {
          lists=guiService.setupResidenceAndGetTotal(residenceMeta.getUuid());
        } catch (NotExistResidenceException e) {

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        } catch (DuplicateSetupResidenceException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.duplicateSetupResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }finally {
            manageTeleportAskPage.refresh();
        }

        List<ResidenceBlockMeta> residenceBlockMetaList=lists[0];
        List<ResidenceInventoryContentMeta> residenceInventoryContentMetaList=lists[1];
        List<ResidenceBlockMeta> torL=new ArrayList<>();
        for (ResidenceBlockMeta b:residenceBlockMetaList){
            Location location= LocationUtil.deserializeLocation(b.getLocation());

            Block block = location.getBlock();

            if (VersionUtil.getMinecraftBigVersion()==1&&VersionUtil.getMinecraftMiddleVersion()<13){
                if (b.getBlock().contains("TORCH")){
                    torL.add(b);
                    continue;
                }
                MaterialData materialData= MaterialDataUtil.deserializeMaterialData(b.getBlock());
                BlockState blockState=block.getState();
                blockState.setType(materialData.getItemType());
                blockState.setRawData(materialData.getData());
                blockState.setData(materialData);
                blockState.update(true,false);
            }else {
                block.setBlockData(Bukkit.createBlockData(b.getBlock()));
            }

        }
        if (VersionUtil.getMinecraftBigVersion()==1&&VersionUtil.getMinecraftMiddleVersion()<13) {
            for (ResidenceBlockMeta b : torL) {
                Location location= LocationUtil.deserializeLocation(b.getLocation());
                Block block = location.getBlock();
                MaterialData materialData=MaterialDataUtil.deserializeMaterialData(b.getBlock());
                BlockState blockState=block.getState();
                blockState.setType(materialData.getItemType());
                blockState.setRawData(materialData.getData());
                blockState.update(true,false);
            }
        }
        for (ResidenceInventoryContentMeta m: residenceInventoryContentMetaList){
            Location location= LocationUtil.deserializeLocation(m.getLocation());
            Block block = location.getBlock();
            Inventory inv;
            if (VersionUtil.getMinecraftBigVersion()==1&&VersionUtil.getMinecraftMiddleVersion()<13) {
                inv = ((Container) block.getState()).getInventory();
            }else {
                inv = ((BlockInventoryHolder) block.getState()).getInventory();
            }
            Gson gson=new GsonBuilder().serializeNulls().create();
            String[] invContent0=gson.fromJson(m.getInventoryContent(),String[].class);
            ItemStack[] itemStacks=new ItemStack[invContent0.length];
            for (int i=0;i<invContent0.length;i++){
                if (invContent0[i]==null){
                    itemStacks[i]=null;
                    continue;
                }
                itemStacks[i]=ItemStackUtil.itemStackDeserialize(invContent0[i]);
            }
            inv.setContents(itemStacks);
        }


        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.setupResidence");
        MessageUtil.sendMessageTo(player, list);

    }
}
