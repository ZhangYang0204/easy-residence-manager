package pers.zhangyang.easyresidencemanager.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
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
public class PlayerInteractInProtectedModeResidence implements Listener {
    @EventHandler
    public void on(PlayerInteractEvent event){

        Player player=event.getPlayer();

        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();

        ResidenceMeta residenceMeta=null;



        Location location=null;
        if (event.getClickedBlock()!=null){
            location=event.getClickedBlock().getLocation();
        }else {
            location=player.getLocation();
        }

        for (ResidenceMeta rm:guiService.listResidence()){
            if (!LocationUtil.blockIsIn(LocationUtil.deserializeLocation(rm.getFirstLocation()),
                    LocationUtil.deserializeLocation(rm.getSecondLocation()),location)){
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
        if (!residenceMeta.getMode().equalsIgnoreCase("保护模式")){
            return;
        }

        event.setCancelled(true);

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notProtectedModeResidenceOwner");
        MessageUtil.sendMessageTo(player, list);
    }

}
