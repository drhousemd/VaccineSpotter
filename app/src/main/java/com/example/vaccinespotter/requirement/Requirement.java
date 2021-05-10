package com.example.vaccinespotter.requirement;

import com.example.vaccinespotter.models.NotificationModel;

public abstract class Requirement implements IRequirement {

    protected final int mWeeks;

    public Requirement(int weeks) {
        mWeeks = weeks;
    }

    @Override
    public boolean isRequirementSatisfied(NotificationModel notificationModel) {
        return notificationModel.getSession().getAvailable_capacity() > 0;
    }

    @Override
    public int getRangeForSearchLimit() {
        return mWeeks;
    }
}
