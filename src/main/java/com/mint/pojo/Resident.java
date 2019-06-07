package com.mint.pojo;

public class Resident {
    private String uid;

    private String name;

    private String building;

    private String unit;

    private String floor;

    private String room;

    private String phone;

    private String idcnum;

    @Override
    public String toString() {
        return "【" + "Resident{" +
                "uid（住户唯一标识）='" + uid + '\'' +
                ", name（姓名）='" + name + '\'' +
                ", building（楼号）='" + building + '\'' +
                ", unit（单元号）='" + unit + '\'' +
                ", floor（楼层号）='" + floor + '\'' +
                ", room（室号）='" + room + '\'' +
                ", phone（手机号）='" + phone + '\'' +
                ", idcnum（身份证号）='" + idcnum + '\'' +
                '}' + "】";
    }

    public Resident(String uid, String name, String building, String unit, String floor, String room, String phone, String idcnum) {
        this.uid = uid;
        this.name = name;
        this.building = building;
        this.unit = unit;
        this.floor = floor;
        this.room = room;
        this.phone = phone;
        this.idcnum = idcnum;
    }

    public Resident() {
        super();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building == null ? null : building.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor == null ? null : floor.trim();
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room == null ? null : room.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getIdcnum() {
        return idcnum;
    }

    public void setIdcnum(String idcnum) {
        this.idcnum = idcnum == null ? null : idcnum.trim();
    }
}