package com.msrazavi.test.pooyabyte.common.schema.mapper;

import org.hibernate.Hibernate;
import org.hibernate.collection.internal.PersistentBag;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import java.util.*;

/**
 * @author Mehdi Sadat Razavi
 */
@SuppressWarnings("unchecked")
public class CycleAvoidingMappingContext {
    private Map<Object, Object> knownInstances = new IdentityHashMap<>();

    @BeforeMapping
    public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {

        if (!Hibernate.isInitialized(source)) {
            return returnEmpty(targetType);
        }

        if (source instanceof PersistentBag) {
            PersistentBag persistent = (PersistentBag) source;
            if (!persistent.wasInitialized()) {
                return returnEmpty(targetType);
            }
        }
        return (T) knownInstances.get(source);
    }

    @BeforeMapping
    public void storeMappedInstance(Object source, @MappingTarget Object target) {
        knownInstances.put(source, target);
    }

    private <T> T returnEmpty(Class<T> clazz) {
        if (List.class.isAssignableFrom(clazz)) {
            return (T) new ArrayList<>();
        } else if (Set.class.isAssignableFrom(clazz)) {
            return (T) new HashSet<>();
        } else {
            try {
                return clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
