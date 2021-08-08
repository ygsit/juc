package com.yu.concurrent;

import lombok.Getter;

public enum CountryEnum {
    ONE(1, "齐"),
    TWO(2, "楚"),
    THREE(3, "燕"),
    FOUR(4, "赵"),
    FIVE(5, "魏"),
    SIX(6, "韩");

    @Getter
    private Integer countryId;
    @Getter
    private String countryName;

    CountryEnum(Integer countryId, String countryName) {
        this.countryId = countryId;
        this.countryName = countryName;
    }

    public static CountryEnum countryEnumForeach(int value){
        CountryEnum[] countryEnums = CountryEnum.values();
        for (CountryEnum countryEnum : countryEnums) {
            if(countryEnum.getCountryId() == value) {
                return countryEnum;
            }
        }
        return null;
    }
}
