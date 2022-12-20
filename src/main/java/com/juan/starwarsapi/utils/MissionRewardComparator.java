package com.juan.starwarsapi.utils;

import com.juan.starwarsapi.entities.Mission;

import java.util.Comparator;

public class MissionRewardComparator implements Comparator<Mission> {

    @Override
    public int compare(Mission m1, Mission m2) {
        return Double.compare(m1.getReward(), m2.getReward());
    }
}
