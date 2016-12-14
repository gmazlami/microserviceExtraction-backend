package ch.uzh.ifi.seal.monolith2microservices.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	
	private String hash;
	
	public Hash(String key){
		this.hash = generateHash(key);
	}
	
	public String get(){
		return this.hash;
	}
	
	private String generateHash(String key){
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(key.getBytes());
			return new String(digest.digest());
			
		}catch (NoSuchAlgorithmException e){
			return Integer.toString(key.hashCode());
		}
	}

}
