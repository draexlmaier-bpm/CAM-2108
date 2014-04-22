package de.draexlmaier.bpm.sample;

import org.camunda.bpm.engine.cdi.BusinessProcessEvent;
import org.camunda.bpm.engine.cdi.BusinessProcessEventType;
import org.camunda.bpm.engine.cdi.impl.event.CdiExecutionListener;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.util.ClockUtil;
import org.camunda.bpm.engine.repository.ProcessDefinition;

public class DrxCdiExecutionListener extends CdiExecutionListener
{
    private static final long serialVersionUID = 1L;

    @Override
    protected BusinessProcessEvent createEvent(final DelegateExecution oDelegateExecution)
    {
        final ProcessDefinition processDefinition = Context.getExecutionContext().getProcessDefinition();

        // map type
        final String eventName = oDelegateExecution.getEventName();
        BusinessProcessEventType type = null;
        if(ExecutionListener.EVENTNAME_START.equals(eventName))
        {
            type = BusinessProcessEventType.START_ACTIVITY;
        }
        else if(ExecutionListener.EVENTNAME_END.equals(eventName))
        {
            type = BusinessProcessEventType.END_ACTIVITY;
        }
        else if(ExecutionListener.EVENTNAME_TAKE.equals(eventName))
        {
            type = BusinessProcessEventType.TAKE;
        }

        return new DrxCdiBusinessProcessEvent(oDelegateExecution.getCurrentActivityId(),
                oDelegateExecution.getCurrentTransitionId(), processDefinition, oDelegateExecution, type,
                ClockUtil.getCurrentTime());
    }
}
