package manolovisoromero.person_processor.service;


import java.util.Collection;

public interface Processor <Entity> {

    CheckResult executeCriteriaCheck(Collection<Entity> entities);

    Collection<Entity> getListToEnforceIntegrity(Entity current, Collection<Entity> all);
}
