package pers.zhangyang.easyresidencemanager.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pers.zhangyang.easylibrary.annotation.EventListener;
import pers.zhangyang.easylibrary.util.LocationUtil;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.TransactionInvocationHandler;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;
import pers.zhangyang.easyresidencemanager.yaml.MessageYaml;

import java.util.List;

@EventListener
public class EntityDamageByPlayerInPrivateResidence implements Listener {
    @EventHandler
    public void on(EntityDamageByEntityEvent event){

        if (!(event.getDamager() instanceof Player)){
            return;
        }

        //攻击者
        Player player= (Player) event.getDamager();

        //被攻击的
        Entity entity=event.getEntity();


        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();

        ResidenceMeta residenceMeta=null;

        for (ResidenceMeta rm:guiService.listResidence()){
            if (!LocationUtil.blockIsIn(LocationUtil.deserializeLocation(rm.getFirstLocation()),
                    LocationUtil.deserializeLocation(rm.getSecondLocation()),entity.getLocation())){
                continue;
            }
            if (rm.getOwnerUuid().equalsIgnoreCase(player.getUniqueId().toString())){
                continue;
            }
            residenceMeta=rm;
        }
        if (residenceMeta==null){
            return;
        }
        if (!residenceMeta.getMode().equalsIgnoreCase("私有模式")){
            return;
        }

        event.setCancelled(true);

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notPrivateModeResidenceOwner");
        MessageUtil.sendMessageTo(player, list);
    }

}
