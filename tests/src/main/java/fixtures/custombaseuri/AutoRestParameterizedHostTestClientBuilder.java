package fixtures.custombaseuri;

import com.azure.core.annotation.ServiceClientBuilder;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.CookiePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;

/**
 * A builder for creating a new instance of the AutoRestParameterizedHostTestClient type.
 */
@ServiceClientBuilder(serviceClients = AutoRestParameterizedHostTestClient.class)
public final class AutoRestParameterizedHostTestClientBuilder {
    /*
     * The HTTP pipeline to send requests through
     */
    private HttpPipeline pipeline;

    /**
     * Sets The HTTP pipeline to send requests through.
     * 
     * @param pipeline the pipeline value.
     * @return the AutoRestParameterizedHostTestClientBuilder.
     */
    public AutoRestParameterizedHostTestClientBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    /**
     * Builds an instance of AutoRestParameterizedHostTestClient with the provided parameters.
     * 
     * @return an instance of AutoRestParameterizedHostTestClient.
     */
    public AutoRestParameterizedHostTestClient build() {
        if (pipeline == null) {
            this.pipeline = new HttpPipelineBuilder().policies(new UserAgentPolicy(), new RetryPolicy(), new CookiePolicy()).build();
        }
        AutoRestParameterizedHostTestClient client = new AutoRestParameterizedHostTestClient(pipeline);
        return client;
    }
}
