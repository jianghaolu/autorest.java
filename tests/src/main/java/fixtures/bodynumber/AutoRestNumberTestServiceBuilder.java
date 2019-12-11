package fixtures.bodynumber;

import com.azure.core.annotation.ServiceClientBuilder;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.CookiePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;

/**
 * A builder for creating a new instance of the AutoRestNumberTestService type.
 */
@ServiceClientBuilder(serviceClients = AutoRestNumberTestService.class)
public final class AutoRestNumberTestServiceBuilder {
    /*
     * http://localhost:3000
     */
    private String host;

    /**
     * Sets http://localhost:3000.
     * 
     * @param host the host value.
     * @return the AutoRestNumberTestServiceBuilder.
     */
    public AutoRestNumberTestServiceBuilder host(String host) {
        this.host = host;
        return this;
    }

    /*
     * The HTTP pipeline to send requests through
     */
    private HttpPipeline pipeline;

    /**
     * Sets The HTTP pipeline to send requests through.
     * 
     * @param pipeline the pipeline value.
     * @return the AutoRestNumberTestServiceBuilder.
     */
    public AutoRestNumberTestServiceBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    /**
     * Builds an instance of AutoRestNumberTestService with the provided parameters.
     * 
     * @return an instance of AutoRestNumberTestService.
     */
    public AutoRestNumberTestService build() {
        if (host == null) {
            this.host = "http://localhost:3000";
        }
        if (pipeline == null) {
            this.pipeline = new HttpPipelineBuilder().policies(new UserAgentPolicy(), new RetryPolicy(), new CookiePolicy()).build();
        }
        AutoRestNumberTestService client = new AutoRestNumberTestService(pipeline);
        if (this.host != null) {
            client.setHost(this.host);
        }
        return client;
    }
}
