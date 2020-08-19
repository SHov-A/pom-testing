package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features/test.feature",
        glue = "stepDefinitions",
        plugin = {"pretty", "html:target/reportsJunit"},
        tags = {"@all"},
        strict = true,
        monochrome = true,
        dryRun = false)

public class RunnerTest {

}


