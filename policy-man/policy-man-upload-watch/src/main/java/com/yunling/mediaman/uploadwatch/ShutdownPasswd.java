package com.yunling.mediaman.uploadwatch;

import org.apache.commons.codec.digest.DigestUtils;

public class ShutdownPasswd {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args!=null && args.length > 0) {
			System.out.println( "Passwd is :" +DigestUtils.md5Hex(args[0]));
		}
	}

}
