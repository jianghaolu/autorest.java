// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.azurespecials.models;

import com.azure.core.implementation.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Additional parameters for customNamedRequestIdParamGrouping operation.
 */
@Fluent
public final class HeadersCustomNamedRequestIdParamGroupingParameters {
    /*
     * The fooRequestId
     */
    @JsonProperty(value = "", required = true)
    private String fooClientRequestId;

    /**
     * Get the fooClientRequestId property: The fooRequestId.
     *
     * @return the fooClientRequestId value.
     */
    public String fooClientRequestId() {
        return this.fooClientRequestId;
    }

    /**
     * Set the fooClientRequestId property: The fooRequestId.
     *
     * @param fooClientRequestId the fooClientRequestId value to set.
     * @return the HeadersCustomNamedRequestIdParamGroupingParameters object
     * itself.
     */
    public HeadersCustomNamedRequestIdParamGroupingParameters fooClientRequestId(String fooClientRequestId) {
        this.fooClientRequestId = fooClientRequestId;
        return this;
    }
}
