package pl.kalibrov.main.server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import pl.kalibrov.main.RPGMod;
import pl.kalibrov.main.subsystems.RPGClassSystem;
/**
 * Server processing of the player's info
 * Should only work on server!
 * @author hanako-nb
 *
 */
public class RPGClassServerSystem extends RPGClassSystem{

	private HashMap<UUID, PClassInfo> all_player_info = new HashMap<>();
	public String filename = RPGMod.FOLDER + "player_info.json";
	private Gson gson = new Gson();
	static final Type type = new TypeToken<HashMap<UUID, PClassInfo>>(){}.getType();
	
	public RPGClassServerSystem() {
		loadFromFile();
	}
	public RPGClassServerSystem(String filename) {
		this.filename = filename;
		loadFromFile();
	}
	
	private void loadFromFile() {
		try {			
			all_player_info = gson.fromJson(new FileReader(filename), type);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (JsonIOException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("[RPG_MOD] First launch, file will be created on shutdown");
		}
		System.out.println("#############");
		for(PClassInfo pc : all_player_info.values()) {
			System.out.println(pc.toString());
		}
		System.out.println("#############");
	}
	public void addNewPlayer(EntityPlayer player) {
		UUID uuid = player.getUniqueID();
		if(all_player_info.containsKey(uuid)) {
			player.sendMessage(new TextComponentString("Your data have been loaded!"));
		}else {
			all_player_info.put(uuid, 
					new PClassInfo());
			player.sendMessage(new TextComponentString("Creating new data!"));
		}		
		player.sendMessage(new TextComponentString(all_player_info.get(uuid).toString()));
	}
	
	private void saveToFile() {
		// First check if directory exists
		File directory = new File(RPGMod.FOLDER);
		if(!directory.exists()) {
			directory.mkdir();
		}
		System.out.println("#############");
		for(PClassInfo pc : all_player_info.values()) {
			System.out.println(pc.toString());
		}
		System.out.println("#############");
		try {
			FileWriter fw = new FileWriter(filename);
			gson.toJson(all_player_info, fw);
			fw.flush();
			fw.close();
		} catch (JsonIOException e) {
			e.printStackTrace(); // Bad save data
		} catch (IOException e) {
			e.printStackTrace(); // No file/ file problem / directory not created
		} 
	}
	
	public void shutdown() {
		saveToFile();
	}
}
