package com.dsmhack.igniter.steps;

import com.github.seratch.jslack.Slack;
import cucumber.api.java.After;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.junit.runner.RunWith;
import org.kohsuke.github.GitHub;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class SpringIntegrationStepDefinitions {

  @Autowired
  @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
  private Slack slack;

  @Autowired
  @MockBean
  private GitHub gitHubService;

  @Autowired
  @MockBean
  private GitHubClient gitHubClient;

  @After
  public void afterScenario() {
    if (slack != null) {
      Mockito.reset(slack, gitHubService, gitHubClient);
    }
  }
}
