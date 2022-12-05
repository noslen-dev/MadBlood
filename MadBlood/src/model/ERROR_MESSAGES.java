package model;

/**
 * Each constant in this enum represents an error message that may be returned by a method.
 */
public enum ERROR_MESSAGES {
    /**
     * Returned when a username is invalid.
     */
    INVALID_USERNAME{
        @Override
        public String toString() {return "The username is not valid";}
    },
    /**
     * Returned when a username is invalid.
     */
    INVALID_PASSWORD{
        @Override
        public String toString() {return "The password is not valid";}
    },
    /**
     * The user is an administrative.
     */
    USER_IS_ADMINISTRATIVE {
        @Override
        public String toString() {return "The user is an administrative";}
    },
    /**
     * The user is a Data Analyst.
     */
    USER_IS_DATA_ANALYST {
        @Override
        public String toString() {return "The user is a Data Analyst";}
    },
    /**
     * the connection to the database failed
     */
    SQL_ERROR{
        @Override
        public String toString(){return "An error ocurred when contacting the Database";}
    }
    ;

    /**
     * Returns a description of this ERROR_MESSAGE constant.
     * @return a description of this ERROR_MESSAGE constant.
     */
    @Override
    public abstract String toString();
}
