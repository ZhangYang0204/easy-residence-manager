package pers.zhangyang.easyresidencemanager.executor;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easyresidencemanager.service.CommandService;
import pers.zhangyang.easyresidencemanager.service.impl.CommandServiceImpl;
import pers.zhangyang.easyresidencemanager.yaml.MessageYaml;
import pers.zhangyang.easylibrary.base.ExecutorBase;
import pers.zhangyang.easylibrary.util.MessageUtil;
import pers.zhangyang.easylibrary.util.TransactionInvocationHandler;

public class CorrectDatabaseExecutor extends ExecutorBase {
    public CorrectDatabaseExecutor(@NotNull CommandSender sender, String cmdName, @NotNull String[] args) {
        super(sender, cmdName, args);
    }

    @Override
    protected void run() {
        if (args.length != 0) {
            return;
        }

        CommandService guiService = (CommandService) new TransactionInvocationHandler(new CommandServiceImpl()).getProxy();

        guiService.correctDatabase();

        MessageUtil.sendMessageTo(sender, MessageYaml.INSTANCE.getStringList("message.chat.correctDatabase"));
    }
}