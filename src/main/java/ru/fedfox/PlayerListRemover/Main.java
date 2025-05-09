package ru.fedfox.PlayerListRemover;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.PlayerListPacket;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.PluginTask;

import java.util.Collection;

public class Main extends PluginBase {

    @Override
    public void onEnable() {
        this.getServer().getLogger().info("Player List Remover Activated! \n§4YouTube: https://youtube.com/@fed_fox?si=EefQDO4u96PcUrmw");
        this.getServer().getScheduler().scheduleRepeatingTask(new Shedule(this), 20);
    }

    public class Shedule extends PluginTask{

        public Main plugin;

        public Shedule(Main plug) {
            super(plug);
            plugin = plug;
        }

        @Override
        public void onRun(int i) {
            for (Player pl:plugin.getServer().getOnlinePlayers().values()){
                if(plugin.getServer().getOnlinePlayers().values().size() > 1){
                    PlayerListPacket remPlayersPacket = new PlayerListPacket();
                    remPlayersPacket.type = PlayerListPacket.TYPE_REMOVE;

                    for (Player dpl:plugin.getServer().getOnlinePlayers().values()){
                        if (!plugin.getServer().getLevelByName(pl.getLevel().getName()).getPlayers().values().contains(dpl)){
                            remPlayersPacket.entries = new PlayerListPacket.Entry[]{
                                    new PlayerListPacket.Entry(dpl.getUniqueId())
                            };
                        }
                    }

                    pl.dataPacket(remPlayersPacket);
                }
            }
        }
    }

}
