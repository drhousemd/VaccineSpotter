package com.example.vaccinespotter.requirement;

import com.example.vaccinespotter.models.NotificationModel;

public class AnyVaxAvailable18PlusRequirement extends Requirement {

    public AnyVaxAvailable18PlusRequirement(int weeks) {
        super(weeks);
    }

    @Override
    public boolean isRequirementSatisfied(NotificationModel notificationModel) {
        return notificationModel.getSession().isFor18YearsPlus()
            && super.isRequirementSatisfied(notificationModel);
    }
}
