package com.example.vaccinespotter.requirement;

import com.example.vaccinespotter.models.NotificationModel;

public interface IRequirement {
    boolean isRequirementSatisfied(NotificationModel notificationModel);

    int getRangeForSearchLimit();
}
