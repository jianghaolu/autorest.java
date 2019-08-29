// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.report.implementation;

import com.azure.core.http.HttpPipeline;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.implementation.RestProxy;
import com.azure.core.implementation.annotation.ExpectedResponses;
import com.azure.core.implementation.annotation.Get;
import com.azure.core.implementation.annotation.Host;
import com.azure.core.implementation.annotation.QueryParam;
import com.azure.core.implementation.annotation.ReturnType;
import com.azure.core.implementation.annotation.ServiceClientBuilder;
import com.azure.core.implementation.annotation.ServiceInterface;
import com.azure.core.implementation.annotation.ServiceMethod;
import com.azure.core.implementation.annotation.UnexpectedResponseExceptionType;
import fixtures.report.AutoRestReportService;
import fixtures.report.models.ErrorException;
import java.util.Map;
import reactor.core.publisher.Mono;

/**
 * A builder for creating a new instance of the AutoRestReportService type.
 */
@ServiceClientBuilder(serviceClients = AutoRestReportService.class)
public final class AutoRestReportServiceBuilder {
    /*
     * The HTTP pipeline to send requests through
     */
    private HttpPipeline pipeline;

    /**
     * Sets The HTTP pipeline to send requests through.
     *
     * @param pipeline the pipeline value.
     * @return the AutoRestReportServiceBuilder.
     */
    public AutoRestReportServiceBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    /**
     * Builds an instance of AutoRestReportService with the provided parameters.
     *
     * @return an instance of AutoRestReportService.
     */
    public AutoRestReportService build() {
        if (pipeline == null) {
            this.pipeline = RestProxy.createDefaultPipeline();
        }
        AutoRestReportServiceImpl client = new AutoRestReportServiceImpl(pipeline);
        return client;
    }
}
