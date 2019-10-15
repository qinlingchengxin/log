package net.ys.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * User: NMY
 * Date: 2019-10-15
 * Time: 14:45
 */
@Data
@Component
@ConfigurationProperties(prefix = "sys-log")
public class SysConfig {

    private List<String> path;

}
