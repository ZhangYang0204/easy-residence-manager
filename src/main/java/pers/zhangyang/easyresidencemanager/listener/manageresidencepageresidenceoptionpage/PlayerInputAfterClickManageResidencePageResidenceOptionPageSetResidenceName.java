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

public class PlayerInputAfterClickManageResidencePageResidenceOptionPageSetResidenceName extends FiniteInputListenerBase {

    public PlayerInputAfterClickManageResidencePageResidenceOptionPageSetResidenceName(Player player, OfflinePlayer owner, GuiPage previousPage) {
        super(player, owner, previousPage, 1);
        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.howToSetResidenceName");
        MessageUtil.sendMessageTo(player, list);
    }


    @Override
    public void run() {



        GuiService guiService= (GuiService) new TransactionInvocationHandler(new GuiServiceImpl()).getProxy();
        ManageResidencePageResidenceOptionPage manageResidencePageResidenceOptionPage= (ManageResidencePageResidenceOptionPage) previousPage;
        try {
            guiService.setResidenceName(manageResidencePageResidenceOptionPage.getResidenceMeta().getUuid(),messages[0]);
        } catch (NotExistResidenceException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.notExistResidence");
            MessageUtil.sendMessageTo(player, list);
            return;
        } catch (DuplicateResidenceException e) {
            List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.duplicateResidenceWhenSetResidenceName");
            MessageUtil.sendMessageTo(player, list);
            return;
        }


        List<String> list = MessageYaml.INSTANCE.getStringList("message.chat.setResidenceName");
        MessageUtil.sendMessageTo(player, list);

    }
}
