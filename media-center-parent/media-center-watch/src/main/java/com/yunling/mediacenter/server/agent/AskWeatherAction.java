package com.yunling.mediacenter.server.agent;

import java.util.HashMap;
import java.util.Map;

import org.jboss.netty.channel.Channel;

import com.yunling.mediacenter.db.model.Stb;
import com.yunling.mediacenter.utils.UpdateAndLoadWeatherUtil;
import com.yunling.mediacenter.weather.bean.WeatherWithImage;

public class AskWeatherAction extends RequestBaseAction {

	@Override
	public String execute(Channel ch, Map<String, String> params) {
		String mac = getKeyByValue(getMacChannels(), ch);
		if (mac != null) {
			Stb stb = getStbService().findByMac(mac);
			String cityId = stb.getCityId();
			if(cityId == null || "".equals(cityId)){
				//default shang hai.
				cityId = "73";
			}
			String queryId = getCityService().findById(Long.parseLong(cityId))
					.getQueryWeatherId();
			getLog().info("------------queryId:"+queryId);
			Map<String, String> result = new HashMap<String, String>();
			result.put("response", "ask_weather");
			WeatherWithImage weather = null;
			if (queryId != null && !"".equals(queryId)) {
				getLog().info("get weather from cache");
				weather = getAllCitiesWeather().get(queryId);
				if (weather == null) {
					getLog().info("get null from cache");
					weather = UpdateAndLoadWeatherUtil.loadWeather(
							getWeatherConfig().getDataRoot(),
							getWeatherConfig().getFtpImageRoot(), queryId);
					getLog().info("load weather");
					if (weather != null) {
						getAllCitiesWeather().put(queryId, weather);
					}else{
						getLog().info("no result loaded");
					}
				}

			}
			if (weather == null) {
				getLog().info("load default weather : shang-hai");
				weather = UpdateAndLoadWeatherUtil.loadWeather(
						getWeatherConfig().getDataRoot(), getWeatherConfig()
								.getFtpImageRoot(), "101020100");
				getAllCitiesWeather().put("101020100", weather);
			}
			result.put("status_code", "0000");
			result.put("message", "success");
			result.putAll(weather.asMap());
			String msg = getMessageFromMap(result);
			getLog().info("send--weather--begin-------------------------");
			getLog().info(msg);
			ch.write(getMessageFromMap(result));
			getLog().info("send--weather--end---------------------------");
		} else {
			this.refuseConnect(ch, "can_not_find_stb");
		}

		return "success";
	}

}
