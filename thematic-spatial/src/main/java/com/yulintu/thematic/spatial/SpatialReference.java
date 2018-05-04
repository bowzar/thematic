package com.yulintu.thematic.spatial;

public class SpatialReference {

    //region wkid
    private int wkid;

    public int getWkid() {
        return wkid;
    }

    public void setWkid(int wkid) {
        this.wkid = wkid;
    }
    //endregion

    //region wkt
    private String wkt;

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }
    //endregion

    //region ctor
    public SpatialReference() {

    }

    public SpatialReference(int wkid) {
        this(wkid, null);
    }

    public SpatialReference(String wkt) {
        this(0, wkt);
    }

    public SpatialReference(int wkid, String wkt) {
        this.wkid = wkid;
        this.wkt = wkt;
    }
    //endregion
}
