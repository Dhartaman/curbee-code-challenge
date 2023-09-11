package com.carsilva.audit;

import com.carsilva.audit.annotation.AuditKey;
import com.carsilva.audit.exception.AuditKeyNotFoundException;
import com.carsilva.audit.model.ChangeType;
import com.carsilva.audit.model.ListUpdate;
import com.carsilva.audit.model.PropertyUpdate;

import java.lang.reflect.Field;
import java.util.*;

public class DiffTool {

    public List<ChangeType> diff(Object previous, Object current) throws Exception {
        List<ChangeType> changes = new ArrayList<>();
        diffRecursive(previous, current, "", changes);
        return changes;
    }

    private static void diffRecursive(Object previous, Object current, String path, List<ChangeType> changes) throws Exception {
        // If both objects are null, no changes
        if (previous == null && current == null) {
            return;
        }

        // If one of the objects is null, it's a change
        if (previous == null || current == null) {
            changes.add(new PropertyUpdate(path, previous, current));
            return;
        }

        // Ensure both objects are of the same type
        if (!previous.getClass().equals(current.getClass())) {
            throw new IllegalArgumentException("Both objects should be of the same type");
        }

        // Iterate over fields of the object
        for (Field field : previous.getClass().getDeclaredFields()) {
            field.setAccessible(true);

            Object prevValue = field.get(previous);
            Object currValue = field.get(current);

            // Build the property path
            String newPath = path.isEmpty() ? field.getName() : path + "." + field.getName();

            // Handle list fields
            if (prevValue instanceof List && currValue instanceof List) {

                List<?> prevList = (List<?>) prevValue;
                List<?> currList = (List<?>) currValue;
                handleListChanges(prevList, currList, newPath, changes);

                // Handle nested object fields
            } else if (prevValue != null && currValue != null
                    && prevValue.getClass().equals(currValue.getClass())
                    && !prevValue.getClass().isPrimitive()
                    && !(prevValue instanceof String)) {

                diffRecursive(prevValue, currValue, newPath, changes);

            } else { // Handle primitive and string fields
                if ((prevValue == null && currValue != null)
                        || (prevValue != null && currValue == null)
                        || (prevValue != null && !prevValue.equals(currValue))) {
                    changes.add(new PropertyUpdate(newPath, prevValue, currValue));
                }
            }
        }
    }

    private static void handleListChanges(List<?> prevList, List<?> currList, String path, List<ChangeType> changes)
            throws Exception {
        Map<Object, Object> prevMap = listToMap(prevList);
        Map<Object, Object> currMap = listToMap(currList);

        List<Object> added = new ArrayList<>();
        List<Object> removed = new ArrayList<>();

        // Detect removed items
        for (Object key : prevMap.keySet()) {
            if (!currMap.containsKey(key)) {
                removed.add(prevMap.get(key));
            }
        }

        // Detect added items and recursively check for changes in existing items
        for (Object key : currMap.keySet()) {
            if (!prevMap.containsKey(key)) {
                added.add(currMap.get(key));
            } else {
                diffRecursive(prevMap.get(key), currMap.get(key), path + "[" + key + "]", changes);
            }
        }

        // Add list changes to the result
        if (!added.isEmpty() || !removed.isEmpty()) {
            changes.add(new ListUpdate(path, added, removed));
        }
    }

    // Convert a list to a map using the audit key or 'id' field as the key
    private static Map<Object, Object> listToMap(List<?> list) throws Exception {
        Map<Object, Object> map = new HashMap<>();
        for (Object obj : list) {
            Object key = null;
            for (Field f : obj.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                if (f.getName().equals("id") || f.isAnnotationPresent(AuditKey.class)) {
                    key = f.get(obj);
                    break;
                }
            }
            if (key == null) {
                throw new AuditKeyNotFoundException("No audit key found for object " + obj);
            }
            map.put(key, obj);
        }
        return map;
    }
}
