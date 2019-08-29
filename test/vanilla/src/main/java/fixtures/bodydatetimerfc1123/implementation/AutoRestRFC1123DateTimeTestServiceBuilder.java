// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.bodydatetimerfc1123.implementation;

import com.azure.core.http.HttpPipeline;
import com.azure.core.implementation.RestProxy;
import com.azure.core.implementation.annotation.ServiceClientBuilder;
import fixtures.bodydatetimerfc1123.AutoRestRFC1123DateTimeTestService;
import fixtures.bodydatetimerfc1123.Datetimerfc1123s;

/**
 * A builder for creating a new instance of the AutoRestRFC1123DateTimeTestService type.
 */
@ServiceClientBuilder(serviceClients = AutoRestRFC1123DateTimeTestService.class)
public final class AutoRestRFC1123DateTimeTestServiceBuilder {
    /*
     * The HTTP pipeline to send requests through
     */
    private HttpPipeline pipeline;

    /**
     * Sets The HTTP pipeline to send requests through.
     *
     * @param pipeline the pipeline value.
     * @return the AutoRestRFC1123DateTimeTestServiceBuilder.
     */
    public AutoRestRFC1123DateTimeTestServiceBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    /**
     * Builds an instance of AutoRestRFC1123DateTimeTestService with the provided parameters.
     *
     * @return an instance of AutoRestRFC1123DateTimeTestService.
     */
    public AutoRestRFC1123DateTimeTestService build() {
        if (pipeline == null) {
            this.pipeline = RestProxy.createDefaultPipeline();
        }
        AutoRestRFC1123DateTimeTestServiceImpl client = new AutoRestRFC1123DateTimeTestServiceImpl(pipeline);
        return client;
    }
}
