package com.carsilva.audit;

import com.carsilva.audit.exception.AuditKeyNotFoundException;
import com.carsilva.audit.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestDiffTool {

    private DiffTool diffTool;

    @BeforeEach
    void setUp() {
        diffTool = new DiffTool();
    }

    @Test
    void testFirstNameUpdate() throws Exception {
        User prev = new User();
        prev.setFirstName("James");

        User curr = new User();
        curr.setFirstName("Jim");

        List<ChangeType> changes = diffTool.diff(prev, curr);
        assertEquals(1, changes.size());
        assertTrue(changes.get(0) instanceof PropertyUpdate);

        PropertyUpdate update = (PropertyUpdate) changes.get(0);
        assertEquals("firstName", update.getProperty());
        assertEquals("James", update.getPrevious());
        assertEquals("Jim", update.getCurrent());
    }

    @Test
    void testNestedSubscriptionStatusUpdate() throws Exception {
        Subscription prevSubs = new Subscription("ACTIVE");
        Subscription currSubs = new Subscription("EXPIRED");

        User prev = new User();
        prev.setFirstName("James");
        prev.setSubscription(prevSubs);

        User curr = new User();
        curr.setFirstName("James");
        curr.setSubscription(currSubs);

        List<ChangeType> changes = diffTool.diff(prev, curr);
        assertEquals(1, changes.size());
        assertTrue(changes.get(0) instanceof PropertyUpdate);

        PropertyUpdate update = (PropertyUpdate) changes.get(0);
        assertEquals("subscription.status", update.getProperty());
        assertEquals("ACTIVE", update.getPrevious());
        assertEquals("EXPIRED", update.getCurrent());
    }

    @Test
    void testServiceListUpdate() throws Exception {
        User prev = new User();
        prev.setServices(Arrays.asList(new Service("s_1", "Interior/Exterior Wash")));

        User curr = new User();
        curr.setServices(Arrays.asList(new Service("s_2", "Oil Change")));

        List<ChangeType> changes = diffTool.diff(prev, curr);
        assertTrue(changes.get(0) instanceof ListUpdate);

        ListUpdate update = (ListUpdate) changes.get(0);
        assertEquals(1, update.getRemoved().size());
        assertEquals("Interior/Exterior Wash", ((Service) update.getRemoved().get(0)).getServiceName());
        assertEquals(1, update.getAdded().size());
        assertEquals("Oil Change", ((Service) update.getAdded().get(0)).getServiceName());
    }

    @Test
    void testVehicleDisplayNameUpdate() throws Exception {
        User prev = new User();
        prev.setVehicles(Arrays.asList(new Vehicle("v_1", "My Car")));

        User curr = new User();
        curr.setVehicles(Arrays.asList(new Vehicle("v_1", "23 Ferrari 296 GTS")));

        List<ChangeType> changes = diffTool.diff(prev, curr);

        assertEquals(1, changes.size());
        assertTrue(changes.get(0) instanceof PropertyUpdate);

        PropertyUpdate update = (PropertyUpdate) changes.get(0);
        assertEquals("vehicles[v_1].displayName", update.getProperty());
        assertEquals("My Car", update.getPrevious());
        assertEquals("23 Ferrari 296 GTS", update.getCurrent());
    }

    @Test
    void testMissingAuditKey() {

        User prev = new User();
        prev.setServices(Arrays.asList(new MissingAuditKey("Oil Change"), new MissingAuditKey("Interior/Exterior Wash")));

        User curr = new User();
        curr.setServices(List.of(new MissingAuditKey("Oil Change")));

        Exception exception = assertThrows(AuditKeyNotFoundException.class, () -> {
            diffTool.diff(prev, curr);
        });
        assertEquals("Audit system lacks the necessary information to determine what has changed.", exception.getMessage());
    }


}