package pers.zhangyang.easyresidencemanager.listener.manageresidencepage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;
import pers.zhangyang.easylibrary.base.FiniteInputListenerBase;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.other.vault.Vault;
import pers.zhangyang.easylibrary.util.*;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easylibrary.yaml.SettingYaml;
import pers.zhangyang.easyresidencemanager.domain.Gamer;
import pers.zhangyang.easyresidencemanager.exception.ConflictResidenceException;
import pers.zhangyang.easyresidencemanager.exception.DuplicateResidenceException;
import pers.zhangyang.easyresidencemanager.manager.GamerManager;
import pers.zhangyang.easyresidencemanager.meta.ResidenceBlockMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceInventoryContentMeta;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlayerInputAfterClickManageResidencePageCreateResidence extends FiniteInputListenerBase {
    public PlayerInputAfterClickManageResidencePageCreateResidence(Player player, OfflinePlayer owner, GuiPage previousPage) {
        super(player, owner, previousPage, 1);

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.howToCreateResidence");
        MessageUtil.sendMessageTo(player, list);
    }

    @Override
    public void run() {
        Player onlineOwner=owner.getPlayer();
        if (onlineOwner==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(player, list);
            return;
        }
        Gamer gamer= GamerManager.INSTANCE.getGamer(onlineOwner);


        List<String> worldNameBlackList=SettingYaml.INSTANCE.getStringList("setting.worldBlackList");
        World world=gamer.getFirstLocation().getWorld();
        if (world!=null&&worldNameBlackList!=null &&worldNameBlackList.contains(world.getName())){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.worldBlackListWhenCreateResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }


        Integer amountPerm=PermUtil.getMaxNumberPerm("EasyResidenceManager.residenceAmount.",onlineOwner);
        if (amountPerm==null){
            amountPerm=0;
        }
        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        if (!onlineOwner.isOp()&&amountPerm<=guiService.listResidence(onlineOwner.getUniqueId().toString()).size()){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.beyondResidenceAmountWhenCreateResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }
        if (gamer.getFirstLocation()==null||gamer.getSecondLocation()==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notSelectedTwoLocationWhenCreateResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }
        if (LocationUtil.isDifferentWorld(gamer.getFirstLocation(),gamer.getSecondLocation())){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notSameWorldLocationWhenCreateResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }



        int zLength=Math.abs(gamer.getFirstLocation().getBlockZ()-gamer.getSecondLocation().getBlockZ());
        int xLength=Math.abs(gamer.getFirstLocation().getBlockX()-gamer.getSecondLocation().getBlockX());
        int yLength=Math.abs(gamer.getFirstLocation().getBlockY()-gamer.getSecondLocation().getBlockY());

        Integer zPerm=PermUtil.getMaxNumberPerm("EasyResidenceManager.zLength.",onlineOwner);
        Integer xPerm=PermUtil.getMaxNumberPerm("EasyResidenceManager.xLength.",onlineOwner);
        Integer yPerm=PermUtil.getMaxNumberPerm("EasyResidenceManager.yLength.",onlineOwner);

        if (zPerm==null){
            zPerm=0;
        }
        if (xPerm==null){
            xPerm=0;
        }
        if (yPerm==null){
            yPerm=0;
        }
        if (!onlineOwner.isOp()&&xPerm<xLength){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.beyondXLengthWhenCreateResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }
        if (!onlineOwner.isOp()&&zPerm<zLength){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.beyondZLengthWhenCreateResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }
        if (!onlineOwner.isOp()&&yPerm<yLength){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.beyondYLengthWhenCreateResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }


        Double perBlockCost= SettingYaml.INSTANCE.getNonnegativeDouble("setting.perBlockCost");

        Double cost;

        if (perBlockCost==null){
            cost=null;
        }else {
            cost=Math.abs(gamer.getFirstLocation().getBlockX()-gamer.getSecondLocation().getBlockX())
                    *Math.abs(gamer.getFirstLocation().getBlockZ()-gamer.getSecondLocation().getBlockZ())
            *Math.abs(gamer.getFirstLocation().getBlockY()-gamer.getSecondLocation().getBlockY())*perBlockCost;

            if (Vault.hook()==null){
                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notHookVault");
                MessageUtil.sendMessageTo(player, list);
                return;
            }
            if (!Vault.hook().has(onlineOwner,cost)){
                List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notEnoughVaultWhenCreateResidence");
                MessageUtil.sendMessageTo(player, list);
                return;
            }
        }

        ResidenceMeta residenceMeta=new ResidenceMeta(UuidUtil.getUUID(),
                ChatColor.translateAlternateColorCodes('&',messages[0]),owner.getUniqueId().toString(),
                System.currentTimeMillis(), LocationUtil.serializeLocation(gamer.getFirstLocation()),
                LocationUtil.serializeLocation(gamer.getSecondLocation()),true);
        residenceMeta.setCost(cost);







        try {
            guiService.createResidence(residenceMeta);
        } catch (DuplicateResidenceException e) {

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.duplicateResidenceWhenCreateResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        } catch (ConflictResidenceException e) {

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.conflictResidenceWhenCreateResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }
        if (cost!=null){
            Vault.hook().withdrawPlayer(onlineOwner,cost);
        }

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.createResidence");
        MessageUtil.sendMessageTo(player, list);

    }
}
