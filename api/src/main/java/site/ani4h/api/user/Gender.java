package site.ani4h.api.user;

public enum Gender {
    MALE() {
        @Override
        public String toString() {
            return "male";
        }
    } ,
    FEMALE() {
        @Override
        public String toString() {
            return "female";
        }
    },
    OTHER() {
        @Override
        public String toString() {
            return "other";
        }
    };
    public static Gender fromString(String value) {
        return value == null ? null : Gender.valueOf(value.toUpperCase());
    }
}
