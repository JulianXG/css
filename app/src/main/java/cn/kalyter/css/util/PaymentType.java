package cn.kalyter.css.util;

import android.support.annotation.Nullable;

/**
 * Created by Kalyter on 2017-4-12 0012.
 */

public enum PaymentType {
    PROPERTY("物业费", 1),
    WATER("水费", 2),
    POWER("电费", 3),
    GAS("燃气费", 4);

    private String name;
    private Integer type;

    PaymentType(String name, Integer type) {
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

    @Nullable
    public static PaymentType getPaymentType(int type) {
        for (PaymentType paymentType : PaymentType.values()) {
            if (paymentType.getType() == type) {
                return paymentType;
            }
        }
        return null;
    }
}
