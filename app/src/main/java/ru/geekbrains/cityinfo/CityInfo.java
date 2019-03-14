package ru.geekbrains.cityinfo;

public class CityInfo {


    private int imageIndex;
    private String cityName;
    private String temperature;
    private String pressure;
    private String wind;

    public int getImageIndex() {
        return imageIndex;
    }

    public String getCityName() {
        return cityName;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWind() {
        return wind;
    }

    public CityInfo(int imageIndex, String cityName, String temperature, String pressure, String wind) {
        this.imageIndex = imageIndex;
        this.cityName = cityName;
        this.temperature = temperature;
        this.pressure = pressure;
        this.wind = wind;
    }
}
