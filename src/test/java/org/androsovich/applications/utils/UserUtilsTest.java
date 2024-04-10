package org.androsovich.applications.utils;

import org.androsovich.applications.entities.Privilege;
import org.androsovich.applications.watchers.LoggingTestWatcher;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.androsovich.applications.entities.enums.OperationType.UPDATE_STATUS_TO;
import static org.androsovich.applications.entities.enums.StatusType.ACCEPTED;
import static org.androsovich.applications.entities.enums.StatusType.SENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(value = LoggingTestWatcher.class)
class UserUtilsTest {
    private List<Privilege> privileges;

    @BeforeEach
    void initPrivileges() {
        privileges = new ArrayList<>(){{
            add(new Privilege("UPDATE_STATUS_TO:SENT", Collections.emptyList()));
            add(new Privilege("UPDATE_STATUS_TO:ACCEPTED", Collections.emptyList()));
        }};
    }

    @AfterEach
    void clearData() {
        privileges.clear();
    }

    @Test
    void parsePrivileges() {
        Map<String, List<String>> mapPrivileges = UserUtils.parsePrivileges(privileges);
        List<String> actionsPrivileges = mapPrivileges.get(UPDATE_STATUS_TO.name());
        assertEquals(2, actionsPrivileges.size());
    }

    @Test
    void getAvailableStatusesForUser() {
        List<String> statuses = UserUtils.getAvailableStatusesForUser(privileges, UPDATE_STATUS_TO);

        assertThat(statuses)
                .hasSize(2)
                .containsExactlyInAnyOrder(SENT.name(), ACCEPTED.name());
    }
}