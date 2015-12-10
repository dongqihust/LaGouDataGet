package com.pojo;

import java.io.Serializable;

/**
 * Created by 95 on 2015/12/6.
 */
public class LagouJobStyle implements Serializable{
    private int id;
    private String substyle1;
    private String substyle2;
    private String substyle3;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubstyle1() {
        return substyle1;
    }

    public void setSubstyle1(String substyle1) {
        this.substyle1 = substyle1;
    }

    public String getSubstyle2() {
        return substyle2;
    }

    public void setSubstyle2(String substyle2) {
        this.substyle2 = substyle2;
    }

    public String getSubstyle3() {
        return substyle3;
    }

    public void setSubstyle3(String substyle3) {
        this.substyle3 = substyle3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LagouJobStyle that = (LagouJobStyle) o;

        if (id != that.id) return false;
        if (substyle1 != null ? !substyle1.equals(that.substyle1) : that.substyle1 != null) return false;
        if (substyle2 != null ? !substyle2.equals(that.substyle2) : that.substyle2 != null) return false;
        if (substyle3 != null ? !substyle3.equals(that.substyle3) : that.substyle3 != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (substyle1 != null ? substyle1.hashCode() : 0);
        result = 31 * result + (substyle2 != null ? substyle2.hashCode() : 0);
        result = 31 * result + (substyle3 != null ? substyle3.hashCode() : 0);
        return result;
    }
}
