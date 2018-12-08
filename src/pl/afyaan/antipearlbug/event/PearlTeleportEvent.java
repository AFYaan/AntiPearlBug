package pl.afyaan.antipearlbug.event;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

public class PearlTeleportEvent implements Listener{
	
	private final Set<Material> xList;
	private final Set<Material> gateList;
	private final Set<Material> slabList;
	private final Set<Material> stairList;
	private final Set<Material> fenceList;
	private final Set<Material> doorList;
	private final Set<Material> otherIgnoreBlockList;
	
	public PearlTeleportEvent(){
		this.xList = new HashSet<Material>(Arrays.asList(new Material[] { Material.COBBLE_WALL, Material.IRON_FENCE, Material.THIN_GLASS, Material.STAINED_GLASS_PANE}));
		this.gateList = new HashSet<Material>(Arrays.asList(new Material[] { Material.FENCE_GATE, Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.ACACIA_FENCE_GATE}));
		this.slabList = new HashSet<Material>(Arrays.asList(new Material[] { Material.STEP, Material.WOOD_STEP, Material.STONE_SLAB2}));
		this.stairList = new HashSet<Material>(Arrays.asList(new Material[] { Material.WOOD_STAIRS, Material.COBBLESTONE_STAIRS, Material.BRICK_STAIRS, Material.SMOOTH_STAIRS, Material.NETHER_BRICK_STAIRS, Material.SANDSTONE_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.JUNGLE_WOOD_STAIRS, Material.QUARTZ_STAIRS, Material.ACACIA_STAIRS, Material.DARK_OAK_STAIRS, Material.RED_SANDSTONE_STAIRS}));
		this.fenceList = new HashSet<Material>(Arrays.asList(new Material[] { Material.FENCE, Material.NETHER_FENCE, Material.DARK_OAK_FENCE, Material.SPRUCE_FENCE, Material.BIRCH_FENCE, Material.JUNGLE_FENCE, Material.DARK_OAK_FENCE, Material.ACACIA_FENCE}));
		this.doorList = new HashSet<Material>(Arrays.asList(new Material[] { Material.WOODEN_DOOR, Material.IRON_DOOR_BLOCK, Material.SPRUCE_DOOR, Material.BIRCH_DOOR, Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR}));
		this.otherIgnoreBlockList = new HashSet<Material>(Arrays.asList(
				new Material[] { Material.LADDER, Material.SAPLING, Material.POWERED_RAIL, Material.DETECTOR_RAIL, Material.RAILS,
						Material.ACTIVATOR_RAIL, Material.THIN_GLASS, Material.STAINED_GLASS_PANE, Material.PISTON_EXTENSION,
						Material.PISTON_BASE, Material.PISTON_STICKY_BASE, Material.LONG_GRASS, Material.DEAD_BUSH, Material.RED_ROSE, 
						Material.YELLOW_FLOWER, Material.RED_MUSHROOM, Material.BROWN_MUSHROOM, Material.CHEST, Material.TRAPPED_CHEST, 
						Material.TORCH, Material.REDSTONE_TORCH_ON, Material.REDSTONE_TORCH_OFF, Material.TRIPWIRE_HOOK, //Material.TRIPWIRE, 
						Material.STONE_PLATE, Material.WOOD_PLATE, Material.GOLD_PLATE, Material.IRON_PLATE, Material.SNOW, Material.STONE_BUTTON, 
						Material.WOOD_BUTTON, Material.STONE_BUTTON, Material.ANVIL, Material.CACTUS, Material.IRON_FENCE, Material.TRAP_DOOR, 
						Material.IRON_TRAPDOOR, Material.ENDER_PORTAL, Material.VINE, Material.ENDER_CHEST, Material.COBBLE_WALL, Material.HOPPER,
						Material.DAYLIGHT_DETECTOR, Material.DAYLIGHT_DETECTOR_INVERTED, Material.REDSTONE_WIRE, Material.ENCHANTMENT_TABLE,
						Material.CARPET, Material.DOUBLE_PLANT, Material.STATIONARY_LAVA, Material.STATIONARY_WATER, Material.DIODE_BLOCK_OFF,
						Material.DIODE_BLOCK_ON, Material.REDSTONE_COMPARATOR, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON,
						Material.CAKE_BLOCK, Material.STANDING_BANNER, Material.SIGN, Material.WALL_SIGN, Material.ARMOR_STAND}));
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.hasItem() && event.getItem().getType() == Material.ENDER_PEARL) {
			Location playerLoc = event.getPlayer().getLocation();
			Block block = playerLoc.getWorld().getBlockAt(new Location(playerLoc.getWorld(), playerLoc.getX(), playerLoc.getY() + 1, playerLoc.getZ()));
		
			//Gracz nie moze uzyc perly jesli jest w bloku
			if(block.getType() != Material.AIR && !this.fenceList.contains(block.getType()) && !this.doorList.contains(block.getType()) && !this.otherIgnoreBlockList.contains(block.getType()) &&
					!this.gateList.contains(block.getType())) {
				event.setCancelled(true);
			}
		}
	}
	
    public static Vector getUnitVectorFacing(final Player ply) {
        double x = -Math.sin(Math.toRadians(ply.getLocation().getYaw())) *
                Math.cos(Math.toRadians(ply.getLocation().getPitch()));
        double z = Math.cos(Math.toRadians(ply.getLocation().getYaw())) *
                Math.cos(Math.toRadians(ply.getLocation().getPitch()));
        double y = 0;
        return new Vector(x, y, z);
    }
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
	public void onPearlTeleport(PlayerTeleportEvent event) {
		if(event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {	
			Player pl = event.getPlayer();
			
			Location pearlLoc = event.getTo();		
			
			Block bottomBlock = event.getTo().getWorld().getBlockAt(
					new Location(event.getTo().getWorld(), pearlLoc.getBlockX(), pearlLoc.getBlockY() - 1, pearlLoc.getZ()));
			
			Block centerBlock = event.getTo().getWorld().getBlockAt(
					new Location(event.getTo().getWorld(), pearlLoc.getBlockX(), pearlLoc.getBlockY(), pearlLoc.getZ()));
			
			Block top2Block = event.getTo().getWorld().getBlockAt(
					new Location(event.getTo().getWorld(), pearlLoc.getBlockX(), pearlLoc.getBlockY() + 2, pearlLoc.getZ()));
			
			//Zmienia pozycje perly na srodek bloku i blok nizej jesli jeden blok pod lokalizacja perly jest powietrze
			if(bottomBlock.getType() == Material.AIR) {
				pearlLoc.setX(pearlLoc.getBlockX() + 0.5);
				pearlLoc.setY(pearlLoc.getBlockY() - 1);
				pearlLoc.setZ(pearlLoc.getBlockZ() + 0.5);
			}
			//Zmienia pozycje perly na srodek bloku jesli w lokalizacji perly jest powietrze lub drzwi
			if(centerBlock.getType() == Material.AIR || this.doorList.contains(centerBlock.getType())) {
				pearlLoc.setX(pearlLoc.getBlockX() + 0.5);
				pearlLoc.setZ(pearlLoc.getBlockZ() + 0.5);
			}
			//Zmienia pozycje perly 1.5 bloku nizej jesli w lokalizacji perly jest nitka
			if(top2Block.getType() == Material.TRIPWIRE) {
				pearlLoc.setY(pearlLoc.getBlockY() - 1.5);				
			}
			//Zmienia pozycje perly kilka kratek do tylu jesli w lokalizacji perly jest blok typu plot, szyba, lub furtka
			if(this.xList.contains(pearlLoc.getBlock().getType()) || this.xList.contains(bottomBlock.getType())
					|| this.gateList.contains(pearlLoc.getBlock().getType()) || this.gateList.contains(bottomBlock.getType())
					|| this.fenceList.contains(pearlLoc.getBlock().getType()) || this.fenceList.contains(bottomBlock.getType()))
			{
				Vector vec = getUnitVectorFacing(pl);
				if(event.getFrom().distance(pearlLoc) < 3)
					vec.multiply(-2);
				else {
					vec.multiply(-1);
				}
				pl.setVelocity(vec);
				if(this.gateList.contains(pearlLoc.getBlock().getType()) || this.gateList.contains(bottomBlock.getType())) {
					pearlLoc.setX((vec.getX() * 2) + pearlLoc.getX());
					pearlLoc.setZ((vec.getZ() * 2) + pearlLoc.getZ());
				}	
			}
			//Zmienia pozycje perly na srodek bloku i 0.5 bloku wyzej jesli w lokalizacji perly jest polblok
			if(this.slabList.contains(pearlLoc.getBlock().getType())) {
				pearlLoc.setX(pearlLoc.getBlockX() + 0.5);
				pearlLoc.setY(pearlLoc.getBlockY() + 0.5);
				pearlLoc.setZ(pearlLoc.getBlockZ() + 0.5);
			}
			//Zmienia pozycje perly 0.5 bloku wyzej jesli w lokalizacji perly sa schody
			if(this.stairList.contains(pearlLoc.getBlock().getType())) {
				pearlLoc.setY(pearlLoc.getBlockY() + 1.0);
			}
			if(!this.otherIgnoreBlockList.contains(pearlLoc.getBlock().getType()) || !this.otherIgnoreBlockList.contains(bottomBlock.getType())) {					
				pearlLoc.setX(pearlLoc.getX());
				pearlLoc.setY(pearlLoc.getY());
				pearlLoc.setZ(pearlLoc.getZ());
			}
			
			event.setTo(pearlLoc);				
		}	
	}	
}