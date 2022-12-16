package model;

public enum DATA_ELEMENT {
    DONATIONS{
        @Override
        public String toString() {return "../resources/fxml/donations.fxml";}
    },
    TRANSFUSIONS{
        @Override
        public String toString() {return "../resources/fxml/transfusions.fxml";}
    },
    HOSPITAL_STOCKS{
        @Override
        public String toString() {return "../resources/fxml/hospital_stocks.fxml";}
    },
    ORDER{
        @Override
        public String toString() {return "../resources/fxml/order.fxml";}
    },
    BLOOD_IN_STOCK{
        @Override
        public String toString() {return "../resources/fxml/blood_in_stock.fxml";}
    };




    @Override
    public abstract String toString();
}
