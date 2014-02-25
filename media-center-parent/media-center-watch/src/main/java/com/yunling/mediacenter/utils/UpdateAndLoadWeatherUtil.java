package com.yunling.mediacenter.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunling.mediacenter.weather.bean.WeatherWithImage;

public class UpdateAndLoadWeatherUtil {
	static final String CHARSET = "UTF-8";
	public static HttpClient client = new DefaultHttpClient();

	static Logger log = LoggerFactory.getLogger(UpdateAndLoadWeatherUtil.class);

	public static Map<String, WeatherWithImage> downloadWeathers(
			Map<String, HttpGet> gets, String dataRoot, String imageRoot) {
		Map<String, WeatherWithImage> weathers = new HashMap<String, WeatherWithImage>();
		Iterator<Entry<String, HttpGet>> it = gets.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HttpGet> en = it.next();
			String cityQueryWeatherId = en.getKey();
			HttpGet get = en.getValue();
			String saveFilePath = dataRoot + en.getKey() + ".json";
			WeatherWithImage weather = downloadWeather(get, imageRoot,
					saveFilePath);
			if (weather != null) {
				weathers.put(cityQueryWeatherId, weather);
			}
		}
		return weathers;
	}

	public static WeatherWithImage downloadWeather(HttpGet get,
			String imageRoot, String saveFilePath) {
		InputStream is = null;
		try {
			is = client.execute(get).getEntity().getContent();
			Writer writer = new StringWriter();
			IOUtils.copy(is, writer, CHARSET);
			String json = writer.toString();
			log.info("download weather string: " + json);
			saveToFile(saveFilePath, json);
			return getWeatherFromJson(imageRoot, json);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(is);
		}
		return null;
	}

	public static void saveToFile(String path, String content) {
		
		Writer writer = null;
		try {
			File file = new File(path);
			if(!file.exists()){
				file.createNewFile();
			}
			writer = new OutputStreamWriter(
					new FileOutputStream(file), CHARSET);
			// writer = new FileWriter(new File(path));
			writer.write(content);
			writer.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	public static WeatherWithImage loadWeather(String dataRoot,
			String imageRoot, String cityCode) {
		WeatherWithImage weather = null;
		Reader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(new File(
					dataRoot, cityCode + ".json")), CHARSET);
			Writer writer = new StringWriter();
			IOUtils.copy(reader, writer);
			weather = getWeatherFromJson(imageRoot, writer.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JsonParseException e) {
			e.printStackTrace();
			log.error("---------------error when parse json.-----------------");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(reader);
		}

		return weather;
	}

	public static WeatherWithImage getWeatherFromJson(String imageRoot,
			String json) {
		if (json != null && !"".equals(json)) {
			JsonFactory jsonFactory = new MappingJsonFactory();
			try {
				JsonParser jsonParser = jsonFactory.createJsonParser(json);
				jsonParser.nextToken();
				Map<String, String> pairs = new HashMap<String, String>();
				while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
					jsonParser.nextToken();
					pairs
							.put(jsonParser.getCurrentName(), jsonParser
									.getText());
				}
				WeatherWithImage weather = new WeatherWithImage();
				weather.setCity(pairs.get("city"));
				weather.setDate(pairs.get("date_y"));
				weather.setTemp(pairs.get("temp1"));
				weather.setImageDaytime(imageRoot, pairs.get("img1"));
				weather.setImageNight(imageRoot, pairs.get("img2"));
				weather.setWeather(pairs.get("weather1"));
				return weather;
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
