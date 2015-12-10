package com.pojo;

import java.io.Serializable;

/**
 * Created by 95 on 2015/12/6.
 */
public class LagouCity implements Serializable{
    private int id;
    private String city;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LagouCity lagouCity = (LagouCity) o;

        if (id != lagouCity.id) return false;
        if (city != null ? !city.equals(lagouCity.city) : lagouCity.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LagouCity{" +
                "id=" + id +
                ", city='" + city + '\'' +
                '}';
    }
}
