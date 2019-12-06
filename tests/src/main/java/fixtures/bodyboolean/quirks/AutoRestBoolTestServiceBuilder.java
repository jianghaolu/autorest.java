package fixtures.bodyboolean.quirks;

import com.azure.core.annotation.ServiceClientBuilder;
import com.azure.core.http.HttpPipeline;
import com.azure.core.implementation.RestProxy;

/**
 * A builder for creating a new instance of the AutoRestBoolTestService type.
 */
@ServiceClientBuilder(serviceClients = AutoRestBoolTestService.class)
public final class AutoRestBoolTestServiceBuilder {
    /*
     * http://localhost:3000
     */
    private String host;

    /**
     * Sets http://localhost:3000.
     * 
     * @param host the host value.
     * @return the AutoRestBoolTestServiceBuilder.
     */
    public AutoRestBoolTestServiceBuilder host(String host) {
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
     * @return the AutoRestBoolTestServiceBuilder.
     */
    public AutoRestBoolTestServiceBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    /**
     * Builds an instance of AutoRestBoolTestService with the provided parameters.
     * 
     * @return an instance of AutoRestBoolTestService.
     */
    public AutoRestBoolTestService build() {
        if (host == null) {
            this.host = "http://localhost:3000";
        }
        if (pipeline == null) {
            this.pipeline = RestProxy.createDefaultPipeline();
        }
        AutoRestBoolTestService client = new AutoRestBoolTestService(pipeline);
        if (this.host != null) {
            client.setHost(this.host);
        }
        return client;
    }
}