// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.http.implementation;

import com.azure.core.http.HttpPipeline;
import com.azure.core.implementation.RestProxy;
import fixtures.http.AutoRestHttpInfrastructureTestService;
import fixtures.http.HttpClientFailures;
import fixtures.http.HttpFailures;
import fixtures.http.HttpRedirects;
import fixtures.http.HttpRetrys;
import fixtures.http.HttpServerFailures;
import fixtures.http.HttpSuccess;
import fixtures.http.MultipleResponses;

/**
 * Initializes a new instance of the AutoRestHttpInfrastructureTestService type.
 */
public final class AutoRestHttpInfrastructureTestServiceImpl implements AutoRestHttpInfrastructureTestService {
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
     * The HttpFailures object to access its operations.
     */
    private HttpFailures httpFailures;

    /**
     * Gets the HttpFailures object to access its operations.
     *
     * @return the HttpFailures object.
     */
    public HttpFailures httpFailures() {
        return this.httpFailures;
    }

    /**
     * The HttpSuccess object to access its operations.
     */
    private HttpSuccess httpSuccess;

    /**
     * Gets the HttpSuccess object to access its operations.
     *
     * @return the HttpSuccess object.
     */
    public HttpSuccess httpSuccess() {
        return this.httpSuccess;
    }

    /**
     * The HttpRedirects object to access its operations.
     */
    private HttpRedirects httpRedirects;

    /**
     * Gets the HttpRedirects object to access its operations.
     *
     * @return the HttpRedirects object.
     */
    public HttpRedirects httpRedirects() {
        return this.httpRedirects;
    }

    /**
     * The HttpClientFailures object to access its operations.
     */
    private HttpClientFailures httpClientFailures;

    /**
     * Gets the HttpClientFailures object to access its operations.
     *
     * @return the HttpClientFailures object.
     */
    public HttpClientFailures httpClientFailures() {
        return this.httpClientFailures;
    }

    /**
     * The HttpServerFailures object to access its operations.
     */
    private HttpServerFailures httpServerFailures;

    /**
     * Gets the HttpServerFailures object to access its operations.
     *
     * @return the HttpServerFailures object.
     */
    public HttpServerFailures httpServerFailures() {
        return this.httpServerFailures;
    }

    /**
     * The HttpRetrys object to access its operations.
     */
    private HttpRetrys httpRetrys;

    /**
     * Gets the HttpRetrys object to access its operations.
     *
     * @return the HttpRetrys object.
     */
    public HttpRetrys httpRetrys() {
        return this.httpRetrys;
    }

    /**
     * The MultipleResponses object to access its operations.
     */
    private MultipleResponses multipleResponses;

    /**
     * Gets the MultipleResponses object to access its operations.
     *
     * @return the MultipleResponses object.
     */
    public MultipleResponses multipleResponses() {
        return this.multipleResponses;
    }

    /**
     * Initializes an instance of AutoRestHttpInfrastructureTestService client.
     */
    public AutoRestHttpInfrastructureTestServiceImpl() {
        this(RestProxy.createDefaultPipeline());
    }

    /**
     * Initializes an instance of AutoRestHttpInfrastructureTestService client.
     *
     * @param httpPipeline The HTTP pipeline to send requests through.
     */
    public AutoRestHttpInfrastructureTestServiceImpl(HttpPipeline httpPipeline) {
        this.httpPipeline = httpPipeline;
        this.httpFailures = new HttpFailuresImpl(this);
        this.httpSuccess = new HttpSuccessImpl(this);
        this.httpRedirects = new HttpRedirectsImpl(this);
        this.httpClientFailures = new HttpClientFailuresImpl(this);
        this.httpServerFailures = new HttpServerFailuresImpl(this);
        this.httpRetrys = new HttpRetrysImpl(this);
        this.multipleResponses = new MultipleResponsesImpl(this);
    }
}
