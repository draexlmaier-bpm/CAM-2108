package de.draexlmaier.bpm.sample;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.cdi.BusinessProcess;
import org.camunda.bpm.engine.cdi.impl.context.ContextAssociationManager;
import org.camunda.bpm.engine.task.Task;

@Stateless
public class SampleProcess
{
    @Inject
    private BusinessProcess businessProcess;

    @Inject
    private TaskService taskService;

    @Inject
    private ContextAssociationManager contextAssociationManager;

    @Inject
    private RuntimeService runtimeService;

    public void startProcess()
    {
        this.businessProcess.startProcessByKey("sample", "sample");
        this.contextAssociationManager.disAssociate();
    }

    public List<Task> queryTasks(final String assignee)
    {
        if(assignee != null)
        {
            return this.taskService.createTaskQuery().orderByTaskCreateTime().asc().taskAssignee(assignee)
                    .taskDefinitionKeyLike("%").list();
        }

        return this.taskService.createTaskQuery().orderByTaskCreateTime().asc().list();
    }

    public Task assignTask(final String taskId, final String assignee)
    {

        this.taskService.setAssignee(taskId, assignee);
        return this.taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    public void abort(final String taskId)
    {
        final Task task = this.taskService.createTaskQuery().taskId(taskId).singleResult();
        final String executionId = task.getExecutionId();
        this.runtimeService.messageEventReceived("abort", executionId);
    }
}
