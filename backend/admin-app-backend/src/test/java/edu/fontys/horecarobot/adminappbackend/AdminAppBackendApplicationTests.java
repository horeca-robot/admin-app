package edu.fontys.horecarobot.adminappbackend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AdminAppBackendApplicationTests {
    @Test
    @DisplayName("Test which succeed with simple assertion")
    void shouldShowSimpleAssertion() {
        Assertions.assertEquals(1, 1);
    }
}
