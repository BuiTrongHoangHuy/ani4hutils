package site.ani4h.api.user;

public enum Role {
    ADMIN(){
        @Override
        public String toString() {
            return "admin";
        }
    },
    USER(){
        @Override
        public String toString() {
            return "user";
        }
    };
    public static Role fromString(String value) {
        return value == null ? null : Role.valueOf(value.toUpperCase());
    }
}
