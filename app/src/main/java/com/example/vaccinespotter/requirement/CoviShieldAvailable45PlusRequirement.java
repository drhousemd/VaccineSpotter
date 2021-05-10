package com.example.vaccinespotter.requirement;

import com.example.vaccinespotter.models.NotificationModel;

public class CoviShieldAvailable45PlusRequirement extends Requirement {

    public CoviShieldAvailable45PlusRequirement(int weeks) {
        super(weeks);
    }

    @Override
    public boolean isRequirementSatisfied(NotificationModel notificationModel) {
        return notificationModel.getSession().minAgeLimitIs45()
            && notificationModel.getSession().isCovishield()
            && super.isRequirementSatisfied(notificationModel);
    }
}
