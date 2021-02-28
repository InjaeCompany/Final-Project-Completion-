package com.injaecompany.pandemichotel.views;

import java.util.ResourceBundle;


public enum FxmlView {

    RESERVATION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("reservation.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Reservation.fxml";
        }
    },
    CUSTOMER {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("customer.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Customer.fxml";
        }
    } , 
    PAYMENT {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("payment.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/Payment.fxml";
        }
    };

    public abstract String getTitle();

    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key) {
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
