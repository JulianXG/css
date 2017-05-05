package cn.kalyter.css.model;

import java.util.List;

/**
 * Created by Kalyter on 2017-5-3 0003.
 */

public class CommunityAllHouse {
    private List<String> buildings;
    private List<List<String>> units;
    private List<List<List<String>>> rooms;

    public List<String> getBuildings() {
        return buildings;
    }

    public void setBuildings(List<String> buildings) {
        this.buildings = buildings;
    }

    public List<List<String>> getUnits() {
        return units;
    }

    public void setUnits(List<List<String>> units) {
        this.units = units;
    }

    public List<List<List<String>>> getRooms() {
        return rooms;
    }

    public void setRooms(List<List<List<String>>> rooms) {
        this.rooms = rooms;
    }
}
