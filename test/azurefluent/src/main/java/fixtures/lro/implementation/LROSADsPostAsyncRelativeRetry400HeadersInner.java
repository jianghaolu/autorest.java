// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.lro.implementation;

import com.azure.core.implementation.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Defines headers for postAsyncRelativeRetry400 operation.
 */
@Fluent
public final class LROSADsPostAsyncRelativeRetry400HeadersInner {
    /*
     * Location to poll for result status: will be set to
     * /lro/nonretryerror/putasync/retry/operationResults/400
     */
    @JsonProperty(value = "Azure-AsyncOperation")
    private String azureAsyncOperation;

    /*
     * Location to poll for result status: will be set to
     * /lro/nonretryerror/putasync/retry/operationResults/400
     */
    @JsonProperty(value = "Location")
    private String location;

    /*
     * Number of milliseconds until the next poll should be sent, will be set
     * to zero
     */
    @JsonProperty(value = "Retry-After")
    private Integer retryAfter;

    /**
     * Get the azureAsyncOperation property: Location to poll for result
     * status: will be set to
     * /lro/nonretryerror/putasync/retry/operationResults/400.
     *
     * @return the azureAsyncOperation value.
     */
    public String azureAsyncOperation() {
        return this.azureAsyncOperation;
    }

    /**
     * Set the azureAsyncOperation property: Location to poll for result
     * status: will be set to
     * /lro/nonretryerror/putasync/retry/operationResults/400.
     *
     * @param azureAsyncOperation the azureAsyncOperation value to set.
     * @return the LROSADsPostAsyncRelativeRetry400HeadersInner object itself.
     */
    public LROSADsPostAsyncRelativeRetry400HeadersInner azureAsyncOperation(String azureAsyncOperation) {
        this.azureAsyncOperation = azureAsyncOperation;
        return this;
    }

    /**
     * Get the location property: Location to poll for result status: will be
     * set to /lro/nonretryerror/putasync/retry/operationResults/400.
     *
     * @return the location value.
     */
    public String location() {
        return this.location;
    }

    /**
     * Set the location property: Location to poll for result status: will be
     * set to /lro/nonretryerror/putasync/retry/operationResults/400.
     *
     * @param location the location value to set.
     * @return the LROSADsPostAsyncRelativeRetry400HeadersInner object itself.
     */
    public LROSADsPostAsyncRelativeRetry400HeadersInner location(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the retryAfter property: Number of milliseconds until the next poll
     * should be sent, will be set to zero.
     *
     * @return the retryAfter value.
     */
    public Integer retryAfter() {
        return this.retryAfter;
    }

    /**
     * Set the retryAfter property: Number of milliseconds until the next poll
     * should be sent, will be set to zero.
     *
     * @param retryAfter the retryAfter value to set.
     * @return the LROSADsPostAsyncRelativeRetry400HeadersInner object itself.
     */
    public LROSADsPostAsyncRelativeRetry400HeadersInner retryAfter(Integer retryAfter) {
        this.retryAfter = retryAfter;
        return this;
    }
}
