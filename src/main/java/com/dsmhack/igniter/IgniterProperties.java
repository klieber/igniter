package com.dsmhack.igniter;

import com.dsmhack.igniter.services.user.UserFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "igniter")
public class IgniterProperties {

  private UserFormat userFormat;

}
