// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.azurespecials.implementation;

import com.azure.core.credentials.ServiceClientCredentials;
import com.azure.core.http.HttpPipeline;
import com.microsoft.azure.v3.AzureEnvironment;
import com.microsoft.azure.v3.AzureProxy;
import com.microsoft.azure.v3.AzureServiceClient;
import fixtures.azurespecials.ApiVersionDefaults;
import fixtures.azurespecials.ApiVersionLocals;
import fixtures.azurespecials.AutoRestAzureSpecialParametersTestClient;
import fixtures.azurespecials.Headers;
import fixtures.azurespecials.Odatas;
import fixtures.azurespecials.SkipUrlEncodings;
import fixtures.azurespecials.SubscriptionInCredentials;
import fixtures.azurespecials.SubscriptionInMethods;
import fixtures.azurespecials.XMsClientRequestIds;

/**
 * Initializes a new instance of the AutoRestAzureSpecialParametersTestClient type.
 */
public final class AutoRestAzureSpecialParametersTestClientImpl implements AutoRestAzureSpecialParametersTestClient {
    /**
     * The subscription id, which appears in the path, always modeled in credentials. The value is always '1234-5678-9012-3456'.
     */
    private String subscriptionId;

    /**
     * Gets The subscription id, which appears in the path, always modeled in credentials. The value is always '1234-5678-9012-3456'.
     *
     * @return the subscriptionId value.
     */
    public String getSubscriptionId() {
        return this.subscriptionId;
    }

    /**
     * Sets The subscription id, which appears in the path, always modeled in credentials. The value is always '1234-5678-9012-3456'.
     *
     * @param subscriptionId the subscriptionId value.
     */
    void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    /**
     * The api version, which appears in the query, the value is always '2015-07-01-preview'.
     */
    private String apiVersion;

    /**
     * Gets The api version, which appears in the query, the value is always '2015-07-01-preview'.
     *
     * @return the apiVersion value.
     */
    public String getApiVersion() {
        return this.apiVersion;
    }

    /**
     * The preferred language for the response.
     */
    private String acceptLanguage;

    /**
     * Gets The preferred language for the response.
     *
     * @return the acceptLanguage value.
     */
    public String getAcceptLanguage() {
        return this.acceptLanguage;
    }

    /**
     * Sets The preferred language for the response.
     *
     * @param acceptLanguage the acceptLanguage value.
     */
    void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    /**
     * The retry timeout in seconds for Long Running Operations. Default value is 30.
     */
    private Integer longRunningOperationRetryTimeout;

    /**
     * Gets The retry timeout in seconds for Long Running Operations. Default value is 30.
     *
     * @return the longRunningOperationRetryTimeout value.
     */
    public Integer getLongRunningOperationRetryTimeout() {
        return this.longRunningOperationRetryTimeout;
    }

    /**
     * Sets The retry timeout in seconds for Long Running Operations. Default value is 30.
     *
     * @param longRunningOperationRetryTimeout the longRunningOperationRetryTimeout value.
     */
    void setLongRunningOperationRetryTimeout(Integer longRunningOperationRetryTimeout) {
        this.longRunningOperationRetryTimeout = longRunningOperationRetryTimeout;
    }

    /**
     * Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     */
    private Boolean generateClientRequestId;

    /**
     * Gets Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     *
     * @return the generateClientRequestId value.
     */
    public Boolean getGenerateClientRequestId() {
        return this.generateClientRequestId;
    }

    /**
     * Sets Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     *
     * @param generateClientRequestId the generateClientRequestId value.
     */
    void setGenerateClientRequestId(Boolean generateClientRequestId) {
        this.generateClientRequestId = generateClientRequestId;
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
     * The XMsClientRequestIds object to access its operations.
     */
    private XMsClientRequestIds xMsClientRequestIds;

    /**
     * Gets the XMsClientRequestIds object to access its operations.
     *
     * @return the XMsClientRequestIds object.
     */
    public XMsClientRequestIds xMsClientRequestIds() {
        return this.xMsClientRequestIds;
    }

    /**
     * The SubscriptionInCredentials object to access its operations.
     */
    private SubscriptionInCredentials subscriptionInCredentials;

    /**
     * Gets the SubscriptionInCredentials object to access its operations.
     *
     * @return the SubscriptionInCredentials object.
     */
    public SubscriptionInCredentials subscriptionInCredentials() {
        return this.subscriptionInCredentials;
    }

    /**
     * The SubscriptionInMethods object to access its operations.
     */
    private SubscriptionInMethods subscriptionInMethods;

    /**
     * Gets the SubscriptionInMethods object to access its operations.
     *
     * @return the SubscriptionInMethods object.
     */
    public SubscriptionInMethods subscriptionInMethods() {
        return this.subscriptionInMethods;
    }

    /**
     * The ApiVersionDefaults object to access its operations.
     */
    private ApiVersionDefaults apiVersionDefaults;

    /**
     * Gets the ApiVersionDefaults object to access its operations.
     *
     * @return the ApiVersionDefaults object.
     */
    public ApiVersionDefaults apiVersionDefaults() {
        return this.apiVersionDefaults;
    }

    /**
     * The ApiVersionLocals object to access its operations.
     */
    private ApiVersionLocals apiVersionLocals;

    /**
     * Gets the ApiVersionLocals object to access its operations.
     *
     * @return the ApiVersionLocals object.
     */
    public ApiVersionLocals apiVersionLocals() {
        return this.apiVersionLocals;
    }

    /**
     * The SkipUrlEncodings object to access its operations.
     */
    private SkipUrlEncodings skipUrlEncodings;

    /**
     * Gets the SkipUrlEncodings object to access its operations.
     *
     * @return the SkipUrlEncodings object.
     */
    public SkipUrlEncodings skipUrlEncodings() {
        return this.skipUrlEncodings;
    }

    /**
     * The Odatas object to access its operations.
     */
    private Odatas odatas;

    /**
     * Gets the Odatas object to access its operations.
     *
     * @return the Odatas object.
     */
    public Odatas odatas() {
        return this.odatas;
    }

    /**
     * The Headers object to access its operations.
     */
    private Headers headers;

    /**
     * Gets the Headers object to access its operations.
     *
     * @return the Headers object.
     */
    public Headers headers() {
        return this.headers;
    }

    /**
     * Initializes an instance of AutoRestAzureSpecialParametersTestClient client.
     *
     * @param credentials the management credentials for Azure.
     */
    public AutoRestAzureSpecialParametersTestClientImpl(ServiceClientCredentials credentials) {
        this(AzureProxy.createDefaultPipeline(AutoRestAzureSpecialParametersTestClientImpl.class, credentials));
    }

    /**
     * Initializes an instance of AutoRestAzureSpecialParametersTestClient client.
     *
     * @param credentials the management credentials for Azure.
     * @param azureEnvironment The environment that requests will target.
     */
    public AutoRestAzureSpecialParametersTestClientImpl(ServiceClientCredentials credentials, AzureEnvironment azureEnvironment) {
        this(AzureProxy.createDefaultPipeline(AutoRestAzureSpecialParametersTestClientImpl.class, credentials), azureEnvironment);
    }

    /**
     * Initializes an instance of AutoRestAzureSpecialParametersTestClient client.
     *
     * @param httpPipeline The HTTP pipeline to send requests through.
     */
    public AutoRestAzureSpecialParametersTestClientImpl(HttpPipeline httpPipeline) {
        this(httpPipeline, null);
    }

    /**
     * Initializes an instance of AutoRestAzureSpecialParametersTestClient client.
     *
     * @param httpPipeline The HTTP pipeline to send requests through.
     * @param azureEnvironment The environment that requests will target.
     */
    public AutoRestAzureSpecialParametersTestClientImpl(HttpPipeline httpPipeline, AzureEnvironment azureEnvironment) {
        super(httpPipeline, azureEnvironment);
        this.apiVersion = "2015-07-01-preview";
        this.xMsClientRequestIds = new XMsClientRequestIdsImpl(this);
        this.subscriptionInCredentials = new SubscriptionInCredentialsImpl(this);
        this.subscriptionInMethods = new SubscriptionInMethodsImpl(this);
        this.apiVersionDefaults = new ApiVersionDefaultsImpl(this);
        this.apiVersionLocals = new ApiVersionLocalsImpl(this);
        this.skipUrlEncodings = new SkipUrlEncodingsImpl(this);
        this.odatas = new OdatasImpl(this);
        this.headers = new HeadersImpl(this);
    }
}
