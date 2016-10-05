package ch.uzh.ifi.seal.monolith2microservices.main.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

	public static String hash(String key){
		try{
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(key.getBytes());
			return new String(digest.digest());
			
		}catch (NoSuchAlgorithmException e){
			return Integer.toString(key.hashCode());
		}
	}
	
}
