package com.sagishchori.weatherapplication.models.forecast;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListItem{
	private int dt;

	@SerializedName("dt_txt")
	private String dtTxt;
	private Snow snow;
	private List<WeatherItem> weather;
	private Main main;
	private Clouds clouds;
	private Sys sys;
	private Wind wind;

	public void setDt(int dt){
		this.dt = dt;
	}

	public int getDt(){
		return dt;
	}

	public void setDtTxt(String dtTxt){
		this.dtTxt = dtTxt;
	}

	public String getDtTxt(){
		return dtTxt;
	}

	public void setSnow(Snow snow){
		this.snow = snow;
	}

	public Snow getSnow(){
		return snow;
	}

	public void setWeather(List<WeatherItem> weather){
		this.weather = weather;
	}

	public List<WeatherItem> getWeather(){
		return weather;
	}

	public void setMain(Main main){
		this.main = main;
	}

	public Main getMain(){
		return main;
	}

	public void setClouds(Clouds clouds){
		this.clouds = clouds;
	}

	public Clouds getClouds(){
		return clouds;
	}

	public void setSys(Sys sys){
		this.sys = sys;
	}

	public Sys getSys(){
		return sys;
	}

	public void setWind(Wind wind){
		this.wind = wind;
	}

	public Wind getWind(){
		return wind;
	}

	@Override
 	public String toString(){
		return 
			"ListItem{" + 
			"dt = '" + dt + '\'' + 
			",dt_txt = '" + dtTxt + '\'' + 
			",snow = '" + snow + '\'' + 
			",weather = '" + weather + '\'' + 
			",main = '" + main + '\'' + 
			",clouds = '" + clouds + '\'' + 
			",sys = '" + sys + '\'' + 
			",wind = '" + wind + '\'' + 
			"}";
		}
}