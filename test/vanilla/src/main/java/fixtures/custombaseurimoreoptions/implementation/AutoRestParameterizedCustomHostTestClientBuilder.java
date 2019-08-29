// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.custombaseurimoreoptions.implementation;

import com.azure.core.http.HttpPipeline;
import com.azure.core.implementation.RestProxy;
import com.azure.core.implementation.annotation.ServiceClientBuilder;
import fixtures.custombaseurimoreoptions.AutoRestParameterizedCustomHostTestClient;
import fixtures.custombaseurimoreoptions.Paths;

/**
 * A builder for creating a new instance of the AutoRestParameterizedCustomHostTestClient type.
 */
@ServiceClientBuilder(serviceClients = AutoRestParameterizedCustomHostTestClient.class)
public final class AutoRestParameterizedCustomHostTestClientBuilder {
    /*
     * The subscription id with value 'test12'.
     */
    private String subscriptionId;

    /**
     * Sets The subscription id with value 'test12'.
     *
     * @param subscriptionId the subscriptionId value.
     * @return the AutoRestParameterizedCustomHostTestClientBuilder.
     */
    public AutoRestParameterizedCustomHostTestClientBuilder subscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
        return this;
    }

    /*
     * A string value that is used as a global part of the parameterized host. Default value 'host'.
     */
    private String dnsSuffix;

    /**
     * Sets A string value that is used as a global part of the parameterized host. Default value 'host'.
     *
     * @param dnsSuffix the dnsSuffix value.
     * @return the AutoRestParameterizedCustomHostTestClientBuilder.
     */
    public AutoRestParameterizedCustomHostTestClientBuilder dnsSuffix(String dnsSuffix) {
        this.dnsSuffix = dnsSuffix;
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
     * @return the AutoRestParameterizedCustomHostTestClientBuilder.
     */
    public AutoRestParameterizedCustomHostTestClientBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    /**
     * Builds an instance of AutoRestParameterizedCustomHostTestClient with the provided parameters.
     *
     * @return an instance of AutoRestParameterizedCustomHostTestClient.
     */
    public AutoRestParameterizedCustomHostTestClient build() {
        if (pipeline == null) {
            this.pipeline = RestProxy.createDefaultPipeline();
        }
        AutoRestParameterizedCustomHostTestClientImpl client = new AutoRestParameterizedCustomHostTestClientImpl(pipeline);
        if (this.subscriptionId != null) {
            client.setSubscriptionId(this.subscriptionId);
        }
        if (this.dnsSuffix != null) {
            client.setDnsSuffix(this.dnsSuffix);
        } else {
            client.setDnsSuffix("host");
        }
        return client;
    }
}
