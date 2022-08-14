package pers.zhangyang.easyresidencemanager.listener.manageresidencepageresidenceoptionpage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.annotation.GuiDiscreteButtonHandler;
import pers.zhangyang.easylibrary.util.*;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyresidencemanager.domain.Gamer;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePageResidenceOptionPage;
import pers.zhangyang.easyresidencemanager.exception.DuplicateSetupResidenceException;
import pers.zhangyang.easyresidencemanager.exception.NotExistResidenceException;
import pers.zhangyang.easyresidencemanager.exception.NotSetUpResidenceException;
import pers.zhangyang.easyresidencemanager.manager.GamerManager;
import pers.zhangyang.easyresidencemanager.meta.ResidenceBlockMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceInventoryContentMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;

import java.util.ArrayList;
import java.util.List;

@EventListener
public class PlayerClickManageResidencePageResidenceOptionPageCancelSetupResidence implements Listener {
    @GuiDiscreteButtonHandler(guiPage = ManageResidencePageResidenceOptionPage.class,slot = {31})
    public void on(InventoryClickEvent event){
        Player player= (Player) event.getWhoClicked();

        int slot=event.getRawSlot();
        ManageResidencePageResidenceOptionPage manageTeleportAskPage= (ManageResidencePageResidenceOptionPage) event.getInventory().getHolder();


        assert manageTeleportAskPage != null;
        ResidenceMeta residenceMeta=manageTeleportAskPage.getResidenceMeta();


        List<ResidenceBlockMeta> residenceBlockMetaList=new ArrayList<>();

        List<ResidenceInventoryContentMeta> residenceInventoryContentMetaList =new ArrayList<>();


        //保存方块数据
        int x1 = LocationUtil.deserializeLocation(residenceMeta.getFirstLocation()).getBlockX();
        int x2 = LocationUtil.deserializeLocation(residenceMeta.getSecondLocation()).getBlockX();
        int z1 = LocationUtil.deserializeLocation(residenceMeta.getFirstLocation()).getBlockZ();
        int z2 = LocationUtil.deserializeLocation(residenceMeta.getSecondLocation()).getBlockZ();
        int y1 = LocationUtil.deserializeLocation(residenceMeta.getFirstLocation()).getBlockY();
        int y2 = LocationUtil.deserializeLocation(residenceMeta.getSecondLocation()).getBlockY();
        int xFrom = Math.min(x1, x2);
        int xTo = Math.max(x1, x2);
        int yFrom = Math.min(y1, y2);
        int yTo = Math.max(y1, y2);
        int zFrom = Math.min(z1, z2);
        int zTo = Math.max(z1, z2);
        for (int x = xFrom; x <= xTo; x++) {
            for (int y = yFrom; y <= yTo; y++) {
                for (int z = zFrom; z <= zTo; z++) {
                    Location location=new Location(LocationUtil.deserializeLocation(residenceMeta.getFirstLocation()).getWorld(), x, y, z);
                    Block block = location.getBlock();
                    if (block.getType().equals(Material.AIR)){
                        continue;
                    }
                    BlockState blockState = block.getState();
                    if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13) {
                        if (blockState instanceof Container) {
                            ItemStack[] invContentsO = ((Container) blockState).getInventory().getContents();
                            String[] invContents=new String[invContentsO.length];
                            for (int i=0;i<invContentsO.length;i++){
                                if (invContentsO[i]==null){
                                    invContents[i]=null;
                                    continue;
                                }
                                invContents[i]= ItemStackUtil.itemStackSerialize(invContentsO[i]);
                            }
                            Gson gson=new GsonBuilder().serializeNulls().create();
                            String inventoryContent=gson.toJson(invContents);
                            ResidenceInventoryContentMeta residenceInventoryContentMeta=new ResidenceInventoryContentMeta(
                                    residenceMeta.getUuid(), LocationUtil.serializeLocation(location),inventoryContent
                            );
                            residenceInventoryContentMetaList.add(residenceInventoryContentMeta);
                        }
                    } else {
                        if (blockState instanceof BlockInventoryHolder) {
                            ItemStack[] invContentsO = ((BlockInventoryHolder) blockState).getInventory().getContents();
                            String[] invContents=new String[invContentsO.length];
                            for (int i=0;i<invContentsO.length;i++){
                                if (invContentsO[i]==null){
                                    invContents[i]=null;
                                    continue;
                                }
                                invContents[i]=ItemStackUtil.itemStackSerialize(invContentsO[i]);
                            }

                            Gson gson=new GsonBuilder().serializeNulls().create();
                            String inventoryContent=gson.toJson(invContents);
                            ResidenceInventoryContentMeta residenceInventoryContentMeta=new ResidenceInventoryContentMeta(
                                    residenceMeta.getUuid(),LocationUtil.serializeLocation(location),inventoryContent
                            );

                            residenceInventoryContentMetaList.add(residenceInventoryContentMeta);

                        }
                    }

                    ResidenceBlockMeta blockInfo;

                    if (VersionUtil.getMinecraftBigVersion() == 1 && VersionUtil.getMinecraftMiddleVersion() < 13) {
                        blockInfo = new ResidenceBlockMeta(residenceMeta.getUuid(),LocationUtil.serializeLocation(location),
                                MaterialDataUtil.serializeMaterialData(blockState.getData()));
                    } else {
                        blockInfo = new ResidenceBlockMeta(residenceMeta.getUuid(),LocationUtil.serializeLocation(location),
                                blockState.getBlockData().getAsString());
                    }
                    residenceBlockMetaList.add(blockInfo);


                }
            }
        }







        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        try {
            guiService.cancelSetupResidence(residenceMeta.getUuid(),residenceBlockMetaList,residenceInventoryContentMetaList);
        } catch (NotExistResidenceException e) {

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        } catch (NotSetUpResidenceException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notSetupResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }finally {
            manageTeleportAskPage.refresh();
        }

        for (int x = xFrom; x <= xTo; x++) {
            for (int y = yFrom; y <= yTo; y++) {
                for (int z = zFrom; z <= zTo; z++) {
                    Location location=new Location(LocationUtil.deserializeLocation(residenceMeta.getFirstLocation()).getWorld(), x, y, z);
                    Block block=location.getBlock();
                    if (block.getType().equals(Material.AIR)){
                        continue;
                    }
                    block.setBlockData(Bukkit.createBlockData(Material.BARRIER));
                }
            }
        }


        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.cancelSetupResidence");
        MessageUtil.sendMessageTo(player, list);
    }
}
