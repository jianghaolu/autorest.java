// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.xml.implementation;

import com.azure.core.http.HttpPipeline;
import com.azure.core.implementation.RestProxy;
import fixtures.xml.AutoRestSwaggerBATXMLService;
import fixtures.xml.Xmls;

/**
 * Initializes a new instance of the AutoRestSwaggerBATXMLService type.
 */
public final class AutoRestSwaggerBATXMLServiceImpl implements AutoRestSwaggerBATXMLService {
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
     * The Xmls object to access its operations.
     */
    private Xmls xmls;

    /**
     * Gets the Xmls object to access its operations.
     *
     * @return the Xmls object.
     */
    public Xmls xmls() {
        return this.xmls;
    }

    /**
     * Initializes an instance of AutoRestSwaggerBATXMLService client.
     */
    public AutoRestSwaggerBATXMLServiceImpl() {
        this(RestProxy.createDefaultPipeline());
    }

    /**
     * Initializes an instance of AutoRestSwaggerBATXMLService client.
     *
     * @param httpPipeline The HTTP pipeline to send requests through.
     */
    public AutoRestSwaggerBATXMLServiceImpl(HttpPipeline httpPipeline) {
        this.httpPipeline = httpPipeline;
        this.xmls = new XmlsImpl(this);
    }
}
