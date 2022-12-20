package com.juan.starwarsapi.utils;

import com.juan.starwarsapi.entities.Mission;

import java.util.Comparator;

public class MissionRewardPerHourComparator implements Comparator<Mission> {

    @Override
    public int compare(Mission m1, Mission m2) {
        double rate1 = m1.getReward()/m1.getDuration();
        double rate2 = m2.getReward()/m2.getDuration();
        if(rate1 > rate2)
            return -1;
        if(rate1 < rate2)
            return 1;
        else
            return 0;
    }
}
