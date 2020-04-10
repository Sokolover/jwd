package by.training.sokolov.oop.agregation_and_composition.task3.model;

import java.util.List;

public class Region {

    private City regionCenter;
    private String name;
    private List<District> districtList;

//    public Region(String name, List<District> districtList) {
//        this.name = name;
//        this.districtList = districtList;
//    }

    public Region(City regionCenter, String name, List<District> districtList) {
        this.regionCenter = regionCenter;
        this.name = name;
        this.districtList = districtList;
    }

    public City getRegionCenter() {
        return regionCenter;
    }

    public void setRegionCenter(City regionCenter) {
        this.regionCenter = regionCenter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<District> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<District> districtList) {
        this.districtList = districtList;
    }
}
