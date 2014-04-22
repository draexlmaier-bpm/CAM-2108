package de.draexlmaier.bpm.sample;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.draexlmaier.bpm.DependencyResolver;
import de.draexlmaier.bpm.WebArchiveBuilder;

@RunWith(Arquillian.class)
public class IntegrationTestSample
{
    @Inject
    private SampleProcess sampleProcess;

    @Deployment
    public static WebArchive createDeployment()
    {
        // @formatter:off
        return new WebArchiveBuilder("sample.war")
            .addPackages("de.draexlmaier.bpm.sample")
            .addLibraries(DependencyResolver.resolveMavenDependencies(true, false))
            .addCommonConfigFiles()
            .getWebArchive();
        // @formatter:on
    }

    @Test
    public void test()
    {
        this.sampleProcess.startProcess();
        this.sampleProcess.assignTask(this.sampleProcess.queryTasks(null).get(0).getId(), "adam");
        this.sampleProcess.abort(this.sampleProcess.queryTasks("adam").get(0).getId());
    }
}
