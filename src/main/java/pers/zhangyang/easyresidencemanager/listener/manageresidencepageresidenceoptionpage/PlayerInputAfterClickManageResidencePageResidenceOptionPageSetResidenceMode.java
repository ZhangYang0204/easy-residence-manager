package pers.zhangyang.easyresidencemanager.listener.manageresidencepageresidenceoptionpage;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pers.zhangyang.easylibrary.base.FiniteInputListenerBase;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.TransactionInvocationHandler;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePage;
import pers.zhangyang.easyresidencemanager.domain.ManageResidencePageResidenceOptionPage;
import pers.zhangyang.easyresidencemanager.exception.DuplicateResidenceException;
import pers.zhangyang.easyresidencemanager.exception.NotExistResidenceException;
import pers.zhangyang.easyresidencemanager.exception.NotSetUpResidenceException;
import pers.zhangyang.easyresidencemanager.exception.StateChangeException;
import pers.zhangyang.easyresidencemanager.meta.ResidenceMeta;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;

import java.util.List;

public class PlayerInputAfterClickManageResidencePageResidenceOptionPageSetResidenceMode extends FiniteInputListenerBase {

    public PlayerInputAfterClickManageResidencePageResidenceOptionPageSetResidenceMode(Player player, OfflinePlayer owner, GuiPage previousPage) {
        super(player, owner, previousPage, 1);
        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.howToSetResidenceMode");
        MessageUtil.sendMessageTo(player, list);
    }


    @Override
    public void run() {


        if (!messages[0].equalsIgnoreCase(MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.privateMode"))
        &&!messages[0].equalsIgnoreCase(MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.protectedMode"))
        &&!messages[0].equalsIgnoreCase(MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.publicMode"))){
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.unknownResidenceMode");
            MessageUtil.sendMessageTo(player, list);
            return;
        }


        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        ManageResidencePageResidenceOptionPage manageResidencePageResidenceOptionPage= (ManageResidencePageResidenceOptionPage) previousPage;
        try {
            if (messages[0].equalsIgnoreCase(MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.privateMode"))) {
                guiService.setResidenceMode(manageResidencePageResidenceOptionPage.getResidenceMeta().getUuid(), "私有模式");
            }
            if (messages[0].equalsIgnoreCase(MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.protectedMode"))) {
                guiService.setResidenceMode(manageResidencePageResidenceOptionPage.getResidenceMeta().getUuid(), "保护模式");
            }
            if (messages[0].equalsIgnoreCase(MessageYaml.INSTANCE.getNonemptyStringDefault("message.input.publicMode"))) {
                guiService.setResidenceMode(manageResidencePageResidenceOptionPage.getResidenceMeta().getUuid(), "公共模式");
            }
        } catch (NotExistResidenceException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }


        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.setResidenceMode");
        MessageUtil.sendMessageTo(player, list);

    }
}
