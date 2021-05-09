package com.example.vaccinespotter.models;

public class NotificationModel {

    private CenterBase centerDetails;

    private Session session;

    public NotificationModel(CenterBase centerDetails, Session session) {
        this.centerDetails = centerDetails;
        this.session = session;
    }
}
