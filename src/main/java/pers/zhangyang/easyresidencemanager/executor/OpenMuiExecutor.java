package pers.zhangyang.easyresidencemanager.executor;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePage;
import pers.zhangyang.easyresidencemanager.yaml.MessageYaml;
import pers.zhangyang.easylibrary.base.ExecutorBase;
import pers.zhangyang.easylibrary.util.MessageUtil;

import java.util.List;

public class OpenMuiExecutor extends ExecutorBase {
    public OpenMuiExecutor(@NotNull CommandSender sender, String commandName, @NotNull String[] args) {
        super(sender, commandName, args);
    }

    @Override
    protected void run() {
        if (args.length!=1){
            return;
        }
        if (!(sender instanceof Player)){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notPlayer");
            MessageUtil.sendMessageTo(this.sender, list);
            return;
        }
        Player player = (Player) sender;
        Player target = Bukkit.getPlayer(args[0]);
        if (target==null){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notOnline");
            MessageUtil.sendMessageTo(this.sender, list);
            return;
        }
        new ManageResidencePage(player,null,target).send();
    }
}
