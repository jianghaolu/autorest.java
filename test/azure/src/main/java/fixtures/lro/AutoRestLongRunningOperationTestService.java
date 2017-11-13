/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package fixtures.lro;

import com.microsoft.rest.v2.RestClient;

/**
 * The interface for AutoRestLongRunningOperationTestService class.
 */
public interface AutoRestLongRunningOperationTestService {
    /**
     * Gets the REST client.
     *
     * @return the {@link RestClient} object.
     */
    RestClient restClient();

    /**
     * Gets the User-Agent header for the client.
     *
     * @return the user agent string.
     */
    String userAgent();

    /**
     * Gets Gets or sets the preferred language for the response..
     *
     * @return the acceptLanguage value.
     */
    String acceptLanguage();

    /**
     * Sets Gets or sets the preferred language for the response..
     *
     * @param acceptLanguage the acceptLanguage value.
     * @return the service client itself
     */
    AutoRestLongRunningOperationTestService withAcceptLanguage(String acceptLanguage);

    /**
     * Gets Gets or sets the retry timeout in seconds for Long Running Operations. Default value is 30..
     *
     * @return the longRunningOperationRetryTimeout value.
     */
    int longRunningOperationRetryTimeout();

    /**
     * Sets Gets or sets the retry timeout in seconds for Long Running Operations. Default value is 30..
     *
     * @param longRunningOperationRetryTimeout the longRunningOperationRetryTimeout value.
     * @return the service client itself
     */
    AutoRestLongRunningOperationTestService withLongRunningOperationRetryTimeout(int longRunningOperationRetryTimeout);

    /**
     * Gets When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true..
     *
     * @return the generateClientRequestId value.
     */
    boolean generateClientRequestId();

    /**
     * Sets When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true..
     *
     * @param generateClientRequestId the generateClientRequestId value.
     * @return the service client itself
     */
    AutoRestLongRunningOperationTestService withGenerateClientRequestId(boolean generateClientRequestId);

    /**
     * Gets the LROs object to access its operations.
     * @return the LROs object.
     */
    LROs lROs();

    /**
     * Gets the LRORetrys object to access its operations.
     * @return the LRORetrys object.
     */
    LRORetrys lRORetrys();

    /**
     * Gets the LROSADs object to access its operations.
     * @return the LROSADs object.
     */
    LROSADs lROSADs();

    /**
     * Gets the LROsCustomHeaders object to access its operations.
     * @return the LROsCustomHeaders object.
     */
    LROsCustomHeaders lROsCustomHeaders();

}
