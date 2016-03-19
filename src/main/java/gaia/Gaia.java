package gaia;

import gaia.init.GaiaBlock;
import gaia.init.GaiaConfigGeneration;
import gaia.init.GaiaEntity;
import gaia.init.GaiaItem;
import gaia.init.GaiaSpawning;
import gaia.items.GaiaItemHandlerFuel;
import gaia.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(
		modid = GaiaReference.MOD_ID, 
		name = GaiaReference.MOD_NAME, 
		version = GaiaReference.VERSION,
		guiFactory = GaiaReference.guiFactory)


public class Gaia 
{
	@Instance(GaiaReference.MOD_ID)
	public static Gaia instance = new Gaia();
	@SidedProxy
	(clientSide = GaiaReference.CLIENT_PROXY_CLASS, 
	serverSide = GaiaReference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	public static CreativeTabs tabGaia = new CreativeTabs("tabGaia") 
	{
		@Override
		public Item getTabIconItem() {
			return GaiaItem.FoodBerryHealth;
		}

	};
	
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		//Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
    	//GaiaConfigGeneration.configOptions(cfg);
		GaiaConfigGeneration.configOptions(event);
		
		GaiaBlock.init();
		GaiaBlock.register();
    	GaiaItem.init();
    	GaiaItem.register();
    	GaiaItem.oreRegistration();
		//proxy.registerRenderingFactories();
    	/** Separated and moved item render from entity portion **/
    	proxy.registerItemsRender();
    	proxy.registerBlocksRender();
    	
	}

	@EventHandler
	public void load(FMLInitializationEvent event) 
	{

		GameRegistry.registerFuelHandler(new GaiaItemHandlerFuel());
		GaiaItem.addRecipes();
		
		//TEMP_Entity.register();
		GaiaEntity.register();
		GaiaSpawning.register();
		//TEMP_Spawning.register_spawn();
		/** Moved register renders to load init; required for entity renders **/
		proxy.registerRenders();    	
    	
		
		MinecraftForge.EVENT_BUS.register(this);		
	}
	
	@SubscribeEvent
	  public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs) {
	    if(eventArgs.modID.equals(GaiaReference.MOD_ID))
	      GaiaConfigGeneration.syncConfig();
	  }
	
	/*
	//debug, remove before compile/release
	MinecraftForge.EVENT_BUS.register(this);
	@SubscribeEvent
	public void postInit(CommandReceivedEvent event) {
		if ("out".equals(event.command)) {
			event.handled = true;
			System.out.println("test");
		}
		if ("butcher".equals(event.command)) {
			event.handled = true;
			EntityPlayer p = (EntityPlayer)event.sender;
			World w = p.worldObj;
			List<Entity> list = w.loadedEntityList;
			for (Entity e : list) {
				if (e instanceof EntityPlayer) continue;
				e.setDead();
			}
		}
		if ("listitems".equals(event.command)) {
			event.handled = true;
			Iterator<String> it = Item.itemRegistry.getKeys().iterator();
			while (it.hasNext()) {
				String s = it.next();
				if (!s.startsWith("minecraft:")) {
					System.out.println(s);	
				}

			}
		}

		if ("spawnall".equals(event.command)) {
			event.handled = true;
			spawnCreatures((EntityPlayer)event.sender);
		}

	}

	public static void spawnCreatures(EntityPlayer player) {
		World world = player.worldObj;
		double posX = player.posX;
		double posY = player.posY;
		double posZ = player.posZ;
		posY+=1.38D;
		for (ModContainer c : EntityRegistry.instance().entityRegistrations.keySet()) {
			System.out.println("modcontainer "+c);
			List<EntityRegistration> l = EntityRegistry.instance().entityRegistrations.get(c);
			for (EntityRegistration r : l) {
				posX+=1.08D;
				try {
					EntityLiving entity = (EntityLiving)r.getEntityClass().getConstructor(new Class[]{World.class}).newInstance(new Object[]{world});
					entity.setLocationAndAngles(posX, posY, posZ, MathHelper.wrapAngleTo180_float(world.rand.nextFloat() * 360.0F), 0.0F);
					entity.rotationYawHead = entity.rotationYaw;
					entity.renderYawOffset = entity.rotationYaw;
					entity.onSpawnWithEgg((IEntityLivingData)null);
					if (!world.spawnEntityInWorld(entity)) {
						System.err.println("Failed to spawn "+r.getEntityName()+" - "+r.getEntityClass());
					}
				} catch (Exception var11) {
					System.err.println("Failed to spawn "+r.getEntityName()+" - "+r.getEntityClass());
					var11.printStackTrace();
				}

			}
		}
	}
	*/
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}
}
