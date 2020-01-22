package fixtures.paging;

import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.CookiePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;

/**
 * Initializes a new instance of the AutoRestPagingTestService type.
 */
public final class AutoRestPagingTestService {
    /**
     * server parameter.
     */
    private String _host;

    /**
     * Gets server parameter.
     * 
     * @return the _host value.
     */
    public String get_host() {
        return this._host;
    }

    /**
     * Sets server parameter.
     * 
     * @param _host the _host value.
     * @return the service client itself.
     */
    AutoRestPagingTestService set_host(String _host) {
        this._host = _host;
        return this;
    }

    /**
     * The HTTP pipeline to send requests through.
     */
    private HttpPipeline httpPipeline;

    /**
     * Gets The HTTP pipeline to send requests through.
     * 
     * @return the httpPipeline value.
     */
    public HttpPipeline getHttpPipeline() {
        return this.httpPipeline;
    }

    /**
     * The Pagings object to access its operations.
     */
    private Pagings pagings;

    /**
     * Gets the Pagings object to access its operations.
     * 
     * @return the Pagings object.
     */
    public Pagings pagings() {
        return this.pagings;
    }

    /**
     * Initializes an instance of AutoRestPagingTestService client.
     */
    public AutoRestPagingTestService() {
        new HttpPipelineBuilder().policies(new UserAgentPolicy(), new RetryPolicy(), new CookiePolicy()).build();
    }

    /**
     * Initializes an instance of AutoRestPagingTestService client.
     * 
     * @param httpPipeline The HTTP pipeline to send requests through.
     */
    public AutoRestPagingTestService(HttpPipeline httpPipeline) {
        this.httpPipeline = httpPipeline;
        this.pagings = new Pagings(this);
    }
}
