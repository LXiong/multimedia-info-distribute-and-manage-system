package com.yunling.policyman.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

public class CharsetDetector implements nsICharsetDetectionObserver {
	
	private boolean found;
	private Charset foundCharset;
	
	public Charset detect(InputStream in) {
		if (in == null) {
			return null;
		}
		
		nsDetector det = new nsDetector(nsPSMDetector.CHINESE);
		found = false;
		foundCharset = null;
		det.Init(this);
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(in);
			byte[] buf = new byte[16384];
			int len = -1;
			boolean done = false;
			while( (len=bis.read(buf,0,buf.length)) != -1) {
				if (!done)
				    done = det.DoIt(buf,len, false);
			}
			det.DataEnd();
		} catch (IOException e1) {
			return null;
		} finally {
			IOUtils.closeQuietly(bis);
		}
		
		return found ? foundCharset : null;
	}
	public Charset detected(File file) {
		found = false;
		
		nsDetector det = new nsDetector(nsPSMDetector.CHINESE);
		found = false;
		foundCharset = null;
		det.Init(this);
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(file));
			byte[] buf = new byte[16384];
			int len = -1;
			boolean done = false;
			while( (len=bis.read(buf,0,buf.length)) != -1) {
				if (!done)
				    done = det.DoIt(buf,len, false);
			}
			det.DataEnd();
		} catch (FileNotFoundException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		} finally {
			IOUtils.closeQuietly(bis);
		}
		
		return found ? foundCharset : null;
	}
	
	public void Notify(String charset) {
	    found = true ;
	    foundCharset = Charset.forName(charset); 
	}

}
