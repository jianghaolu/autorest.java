package fixtures.extensibleenums;

import com.azure.core.annotation.ServiceClientBuilder;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.CookiePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;
import com.azure.core.http.rest.RestProxy;

/**
 * A builder for creating a new instance of the PetStoreInc type.
 */
@ServiceClientBuilder(serviceClients = PetStoreInc.class)
public final class PetStoreIncBuilder {
    /*
     * http://localhost:3000
     */
    private String host;

    /**
     * Sets http://localhost:3000.
     * 
     * @param host the host value.
     * @return the PetStoreIncBuilder.
     */
    public PetStoreIncBuilder host(String host) {
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
     * @return the PetStoreIncBuilder.
     */
    public PetStoreIncBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    /**
     * Builds an instance of PetStoreInc with the provided parameters.
     * 
     * @return an instance of PetStoreInc.
     */
    public PetStoreInc build() {
        if (host == null) {
            this.host = "http://localhost:3000";
        }
        if (pipeline == null) {
            this.pipeline = new HttpPipelineBuilder().policies(new UserAgentPolicy(), new RetryPolicy(), new CookiePolicy()).build();
        }
        PetStoreInc client = new PetStoreInc(pipeline);
        client.setHost(this.host);
        return client;
    }
}
