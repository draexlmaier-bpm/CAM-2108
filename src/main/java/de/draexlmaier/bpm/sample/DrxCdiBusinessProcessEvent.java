package de.draexlmaier.bpm.sample;

import java.util.Date;

import org.camunda.bpm.engine.cdi.BusinessProcessEventType;
import org.camunda.bpm.engine.cdi.impl.event.CdiBusinessProcessEvent;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.repository.ProcessDefinition;

public class DrxCdiBusinessProcessEvent extends CdiBusinessProcessEvent
{
    private final DelegateExecution delegateExcution;

    public DrxCdiBusinessProcessEvent(final String activityId, final String transitionName,
            final ProcessDefinition processDefinition, final DelegateExecution delegateExecution,
            final BusinessProcessEventType type, final Date timeStamp)
    {
        super(activityId, transitionName, processDefinition, delegateExecution, type, timeStamp);

        this.delegateExcution = delegateExecution;
    }

    public DelegateExecution getDelegateExcecution()
    {
        return this.delegateExcution;
    }
}
