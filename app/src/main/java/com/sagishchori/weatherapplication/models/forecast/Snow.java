package com.sagishchori.weatherapplication.models.forecast;

public class Snow{
	private double jsonMember3h;

	public void setJsonMember3h(double jsonMember3h){
		this.jsonMember3h = jsonMember3h;
	}

	public double getJsonMember3h(){
		return jsonMember3h;
	}

	@Override
 	public String toString(){
		return 
			"Snow{" + 
			"3h = '" + jsonMember3h + '\'' + 
			"}";
		}
}
