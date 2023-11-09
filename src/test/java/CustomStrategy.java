import org.junit.platform.engine.ConfigurationParameters;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfiguration;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfigurationStrategy;

import com.codio.helpers.AppConfig;

public class CustomStrategy implements ParallelExecutionConfiguration, ParallelExecutionConfigurationStrategy {

    int n = AppConfig.parallel;

    @Override
    public int getParallelism() {
        return n;
    }

    @Override
    public int getMinimumRunnable() {
        return 1;
    }

    @Override
    public int getMaxPoolSize() {
        return n;
    }

    @Override
    public int getCorePoolSize() {
        return 1;
    }

    @Override
    public int getKeepAliveSeconds() {
        return 30;
    }

    @Override
    public ParallelExecutionConfiguration createConfiguration(final ConfigurationParameters configurationParameters) {
        return this;
    }
}
