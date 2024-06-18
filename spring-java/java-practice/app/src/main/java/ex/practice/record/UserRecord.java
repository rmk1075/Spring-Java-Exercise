package ex.practice.record;

public record UserRecord(long id, String name, int age) {
    // Constructor
    // public UserRecord(long id, String name, int age) {
    //     if (!validateAge(age)) {
    //         throw new IllegalArgumentException("age must be larger than 0.");
    //     }

    //     this.id = id;
    //     this.name = name;
    //     this.age = age;
    // }

    public UserRecord {
        if (!validateAge(age)) {
            throw new IllegalArgumentException("age must be larger than 0.");
        }
    }

    private boolean validateAge(int age) {
        return 0 < age;
    }
}
