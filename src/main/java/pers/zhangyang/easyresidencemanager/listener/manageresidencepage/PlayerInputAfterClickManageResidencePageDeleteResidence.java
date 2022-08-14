package pers.zhangyang.easyresidencemanager.listener.manageresidencepage;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pers.zhangyang.easylibrary.base.FiniteInputListenerBase;
import pers.zhangyang.easylibrary.base.GuiPage;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.TransactionInvocationHandler;
import pers.zhangyang.easylibrary.yaml.MessageYaml;
import pers.zhangyang.easyresidencemanager.exception.NotExistResidenceException;
import pers.zhangyang.easyresidencemanager.exception.NotSetUpResidenceException;
import pers.zhangyang.easyresidencemanager.service.GuiService;
import pers.zhangyang.easyresidencemanager.service.impl.GuiServiceImpl;

import java.util.List;

public class PlayerInputAfterClickManageResidencePageDeleteResidence extends FiniteInputListenerBase {
    public PlayerInputAfterClickManageResidencePageDeleteResidence(Player player, OfflinePlayer owner, GuiPage previousPage) {
        super(player, owner, previousPage, 1);
        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.howToDeleteResidence");
        MessageUtil.sendMessageTo(player, list);
    }

    @Override
    public void run() {
        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();

        try {
            guiService.deleteResidence(messages[0]);
        } catch (NotExistResidenceException e) {

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        } catch (NotSetUpResidenceException e) {

            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notSetupResidenceWhenDeleteResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        }

        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.deleteResidence");
        MessageUtil.sendMessageTo(player, list);

    }
}
