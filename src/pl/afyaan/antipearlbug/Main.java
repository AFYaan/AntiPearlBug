package pl.afyaan.antipearlbug;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import pl.afyaan.antipearlbug.event.PearlTeleportEvent;

public class Main extends JavaPlugin{

	private static String aco = "cieto";
	Logger log = Bukkit.getLogger();
	
	@Override
	public void onEnable() {
		log.info("");
		log.info(">========<AntiPearlBug>========<");
		log.info(" >> Enabled");
		log.info(" >> Author: AFYaan");
		log.info(">========<AntiPearlBug>========<");
		log.info("");
		
		Bukkit.getServer().getPluginManager().registerEvents(new PearlTeleportEvent(), this);
	}
	
	@Override
	public void onDisable() {
		log.info("");
		log.info(">========<AntiPearlBug>========<");
		log.info(" >> Disabled");
		log.info(">========<AntiPearlBug>========<");
		log.info("");
	}	
}
