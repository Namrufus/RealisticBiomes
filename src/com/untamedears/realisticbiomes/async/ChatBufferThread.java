package com.untamedears.realisticbiomes.async;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.untamedears.realisticbiomes.RealisticBiomes;

public class ChatBufferThread implements Runnable{
	
	public static HashMap<Player, HashMap<String, Integer>> buffer = new HashMap<Player, HashMap<String, Integer>>();
	public static final int delay = 20; // Delay before chat refresh.
	
	@Override
	public void run() {
		while(RealisticBiomes.isEnabled){
			for (Player p : buffer.keySet()){
				HashMap<String, Integer> msgBuffer = buffer.get(p);
				for (String msg : msgBuffer.keySet()){
					if (msgBuffer.get(msg)<=1){
						msgBuffer.remove(msg);
					} else {
						msgBuffer.put(msg, buffer.get(p).get(msg)-1);
					}
				}
				buffer.put(p, msgBuffer);
			}
		}
	}
	
	/**
	 * Send a message with a check to prevent message multiplying.
	 * @param p The player to send the message to.
	 * @param msg The message.
	 */
	public static void sendMsg(Player p, String msg){
		if (!buffer.containsKey(p)){
			HashMap<String, Integer> msgBuffer = new HashMap<String, Integer>();
			msgBuffer.put(msg, delay);
			buffer.put(p, msgBuffer);
			p.sendMessage(msg);
		} else {
			HashMap<String, Integer> msgBuffer = buffer.get(p);
			if (msgBuffer.containsKey(msg)){
				msgBuffer.put(msg, delay);
			} else {
				msgBuffer.put(msg, delay);
				p.sendMessage(msg);
			}
		}
	}

}
