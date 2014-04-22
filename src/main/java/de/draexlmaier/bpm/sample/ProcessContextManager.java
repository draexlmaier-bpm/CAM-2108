package de.draexlmaier.bpm.sample;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.runtime.ActivityInstance;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;

@ApplicationScoped
public class ProcessContextManager
{
    @Inject
    private ProcessContextContainer processContextContainer;

    @Inject
    private RuntimeService runtimeService;

    @Inject
    private BusinessProcess businessProcess;

    public void eventFired(@Observes final DrxCdiBusinessProcessEvent event)
    {
        this.processContextContainer.setIntance(new ProcessContext(event));
    }

    @Produces
    public ProcessContext getProcessContext()
    {
        return this.processContextContainer.getIntance();
    }

    @RequestScoped
    public static class ProcessContextContainer
    {
        private ProcessContext intance;

        public ProcessContext getIntance()
        {
            return this.intance;
        }

        public void setIntance(final ProcessContext intance)
        {
            this.intance = intance;
        }
    }

    public class ProcessContext
    {
        private DrxCdiBusinessProcessEvent event = null;
        private ProcessInstance processInstance = null;
        private ActivityInstance activityInstance = null;

        public ProcessContext(final DrxCdiBusinessProcessEvent event)
        {
            this.event = event;

            this.processInstance =
                    ProcessContextManager.this.runtimeService.createProcessInstanceQuery()
                            .processInstanceId(event.getProcessInstanceId()).singleResult();

            // remove this line to make the Unit test work
            this.activityInstance =
                    ProcessContextManager.this.runtimeService.getActivityInstance(event.getProcessInstanceId());
        }

        public ProcessInstance getProcessInstance()
        {
            return this.processInstance;
        }

        public ActivityInstance getActivityInstance()
        {
            return this.activityInstance;
        }

        public ProcessDefinition getProcessDefinition()
        {
            return this.event.getProcessDefinition();
        }

        public String getActivityId()
        {
            return this.event.getActivityId();
        }

        public String getProcessInstanceId()
        {
            return this.event.getProcessInstanceId();
        }

        public String getProcessDefinitionId()
        {
            return this.processInstance.getProcessDefinitionId();
        }

        public String getActivityName()
        {
            return this.event.getDelegateExcecution().getCurrentActivityName();
        }

        public String getExecutionId()
        {
            return this.event.getExecutionId();
        }

        public Task getTask()
        {
            if(!ProcessContextManager.this.businessProcess.isTaskAssociated())
            {
                throw new IllegalStateException("There is currently no task associated!");
            }

            return ProcessContextManager.this.businessProcess.getTask();
        }
    }
}
