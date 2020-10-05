package com.example.user.shake;

public class BikeInfo {

    private String bikeOwner, bikeCode, bikeImgUrl, bikeLockId, bikeModelName, bikeType, bikeAddInfo;
    private double bikeLatitude, bikeLongitude;
    private int bikeCost;

    public BikeInfo(String bikeOwner, String bikeCode, double bikeLatitude
    , double bikeLongitude, int bikeCost, String bikeImgUrl, String bikeLockId
    , String bikeModelName, String bikeType, String bikeAddInfo){
        this.bikeOwner = bikeOwner;
        this.bikeCode = bikeCode;
        this.bikeLatitude = bikeLatitude;
        this.bikeLongitude = bikeLongitude;
        this.bikeCost = bikeCost;
        this.bikeImgUrl = bikeImgUrl;
        this.bikeLockId = bikeLockId;
        this.bikeModelName = bikeModelName;
        this.bikeType = bikeType;
        this.bikeAddInfo = bikeAddInfo;
    }

    public String getBikeOwner() {
        return bikeOwner;
    }
    public double getBikeLatitude(){
        return bikeLatitude;
    }
    public String getBikeCode(){
        return bikeCode;
    }
}
