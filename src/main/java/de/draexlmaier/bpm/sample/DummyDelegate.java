package de.draexlmaier.bpm.sample;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class DummyDelegate implements JavaDelegate
{

    @Override
    public void execute(final DelegateExecution execution) throws Exception
    {

    }

}
