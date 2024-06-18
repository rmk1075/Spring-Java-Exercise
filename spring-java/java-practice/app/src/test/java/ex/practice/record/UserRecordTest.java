package ex.practice.record;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UserRecordTest {

    @Test
    void testUserRecord() {
        long id = 1L;
        String name = "test";
        int age = 10;

        UserRecord user = new UserRecord(id, name, age);
        System.out.println("toString: " + user.toString());

        System.out.println("id: " + user.id());
        System.out.println("name: " + user.name());
        System.out.println(user.age());

        // age must larget than 0.
        assertThrows(IllegalArgumentException.class, () -> {new UserRecord(id, name, 0);});
    }
}
