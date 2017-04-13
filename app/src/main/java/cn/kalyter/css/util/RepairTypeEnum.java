package cn.kalyter.css.util;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public enum RepairTypeEnum {
    HOUSE("房屋维修", 1),
    WATER_OR_GAS("水电燃气", 2),
    PUBLIC_FACILITIES("公共设施", 3);

    private String name;
    private Integer type;

    RepairTypeEnum(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public static RepairTypeEnum parse(int code) {
        for (RepairTypeEnum repairTypeEnum : RepairTypeEnum.values()) {
            if (repairTypeEnum.getType() == code) {
                return repairTypeEnum;
            }
        }
        return null;
    }
}
