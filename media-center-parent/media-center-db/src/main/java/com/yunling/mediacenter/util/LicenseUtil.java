package com.yunling.mediacenter.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class LicenseUtil {

	private static String encKey = "ShanghaiYunling2011";

	public static boolean verfiy(String targetLicense, String customerId,
			String productName, int client_number) {
		String m = customerId + ":" + getMacString() + ":" + encKey + ":"
				+ client_number;
		String calculated = DigestUtils.md5Hex(m).toUpperCase();
		return calculated.equals(targetLicense);
	}

	public static String getMacString() {
		byte[] bytes;
		//boolean flag = true;
		try {
			/*InetAddress ia = null;
			NetworkInterface iface = null;
			for (Enumeration<NetworkInterface> ifaces = NetworkInterface
					.getNetworkInterfaces(); ifaces.hasMoreElements() && flag;) {
				iface = (NetworkInterface) ifaces.nextElement();
				System.out.println("Interface: " + iface.getDisplayName());
				for (Enumeration<InetAddress> ips = iface.getInetAddresses(); ips
						.hasMoreElements();) {
					ia = (InetAddress) ips.nextElement();
					System.out.println(ia.getCanonicalHostName() + "------"
							+ ia.getHostAddress());
					//!ia.isSiteLocalAddress() && 
					if (!ia.isLoopbackAddress()
							&& ia.getHostAddress().indexOf(":") == -1) {
						flag = false;
						break;
					} else {
						ia = null;
					}
				}
			}*/

			InetAddress ia = InetAddress.getLocalHost();
			System.out.println("InetAddress: " + ia);
			bytes = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			return Hex.encodeHexString(bytes).toUpperCase();
		} catch (UnknownHostException e) {
			
		} catch (SocketException e) {
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Failed to get mac address");
	}

	public static void main(String[] arg) {
		System.out.println(getMacString());
		String m = "kfc:" + getMacString() + ":ShanghaiYunling2011:" + 1000;
		System.out.println(DigestUtils.md5Hex(m).toUpperCase());
	}

}
