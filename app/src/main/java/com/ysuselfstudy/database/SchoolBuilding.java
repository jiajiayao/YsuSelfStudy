package com.ysuselfstudy.database;

public class SchoolBuilding {
    String BuildingName;

    public SchoolBuilding(String name) {
        this.BuildingName=name;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }


}
