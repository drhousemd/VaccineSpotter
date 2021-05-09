package com.example.vaccinespotter.models;

import java.text.SimpleDateFormat;

public class NotificationModel {

    private CenterBase centerDetails;

    private Session session;

    public CenterBase getCenterDetails() {
        return centerDetails;
    }

    public Session getSession() {
        return session;
    }

    public NotificationModel(CenterBase centerDetails, Session session) {
        this.centerDetails = centerDetails;
        this.session = session;
    }

    public String getNotificationText() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String strDate = dateFormat.format(session.getDate());
        return  String.format("Center name: %s %s Vaccine: %s for Age %s, available slots: %s on %s", centerDetails.name, centerDetails.pincode, session.getVaccine(), session.getMin_age_limit(), session.getAvailable_capacity(), strDate);
    }

    // Overriding equals() to compare two Complex objects
    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of NotificationModel or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof NotificationModel)) {
            return false;
        }

        // typecast o to NotificationModel so that we can compare data members
        NotificationModel model = (NotificationModel) o;

        // Compare the data members and return accordingly
        return compareCentre(model) && compareSession(model);
    }

    private boolean compareCentre(NotificationModel model) {
        return model.centerDetails.pincode == centerDetails.pincode
            && model.centerDetails.name == centerDetails.name;
    }

    private boolean compareSession(NotificationModel model) {
        return model.session.getVaccine() == session.getVaccine()
            && model.session.getDate().compareTo(session.getDate()) == 0;
    }
}
