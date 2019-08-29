// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.lro.models;

import com.azure.core.implementation.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines headers for postAsyncRelativeRetryInvalidHeader operation.
 */
@Fluent
public final class LROSADsPostAsyncRelativeRetryInvalidHeaderHeaders {
    /*
     * Location to poll for result status: will be set to foo
     */
    @JsonProperty(value = "Azure-AsyncOperation")
    private String azureAsyncOperation;

    /*
     * Location to poll for result status: will be set to foo
     */
    @JsonProperty(value = "Location")
    private String location;

    /*
     * Number of milliseconds until the next poll should be sent, will be set
     * to /bar
     */
    @JsonProperty(value = "Retry-After")
    private Integer retryAfter;

    /**
     * Get the azureAsyncOperation property: Location to poll for result
     * status: will be set to foo.
     *
     * @return the azureAsyncOperation value.
     */
    public String azureAsyncOperation() {
        return this.azureAsyncOperation;
    }

    /**
     * Set the azureAsyncOperation property: Location to poll for result
     * status: will be set to foo.
     *
     * @param azureAsyncOperation the azureAsyncOperation value to set.
     * @return the LROSADsPostAsyncRelativeRetryInvalidHeaderHeaders object
     * itself.
     */
    public LROSADsPostAsyncRelativeRetryInvalidHeaderHeaders azureAsyncOperation(String azureAsyncOperation) {
        this.azureAsyncOperation = azureAsyncOperation;
        return this;
    }

    /**
     * Get the location property: Location to poll for result status: will be
     * set to foo.
     *
     * @return the location value.
     */
    public String location() {
        return this.location;
    }

    /**
     * Set the location property: Location to poll for result status: will be
     * set to foo.
     *
     * @param location the location value to set.
     * @return the LROSADsPostAsyncRelativeRetryInvalidHeaderHeaders object
     * itself.
     */
    public LROSADsPostAsyncRelativeRetryInvalidHeaderHeaders location(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the retryAfter property: Number of milliseconds until the next poll
     * should be sent, will be set to /bar.
     *
     * @return the retryAfter value.
     */
    public Integer retryAfter() {
        return this.retryAfter;
    }

    /**
     * Set the retryAfter property: Number of milliseconds until the next poll
     * should be sent, will be set to /bar.
     *
     * @param retryAfter the retryAfter value to set.
     * @return the LROSADsPostAsyncRelativeRetryInvalidHeaderHeaders object
     * itself.
     */
    public LROSADsPostAsyncRelativeRetryInvalidHeaderHeaders retryAfter(Integer retryAfter) {
        this.retryAfter = retryAfter;
        return this;
    }
}
