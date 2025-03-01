package site.ani4h.api.user;

public enum Gender {
    GENDER_MALE() {
        @Override
        public String toString() {
            return "male";
        }
    } ,
    GENDER_FEMALE() {
        @Override
        public String toString() {
            return "female";
        }
    },
    GENDER_OTHER() {
        @Override
        public String toString() {
            return "other";
        }
    }
}
