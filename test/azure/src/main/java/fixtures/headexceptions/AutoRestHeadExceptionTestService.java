// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.headexceptions;

import com.azure.core.http.HttpPipeline;

/**
 * The interface for AutoRestHeadExceptionTestService class.
 */
public interface AutoRestHeadExceptionTestService {
    /**
     * Gets The preferred language for the response.
     *
     * @return the acceptLanguage value.
     */
    String getAcceptLanguage();

    /**
     * Gets The retry timeout in seconds for Long Running Operations. Default value is 30.
     *
     * @return the longRunningOperationRetryTimeout value.
     */
    Integer getLongRunningOperationRetryTimeout();

    /**
     * Gets Whether a unique x-ms-client-request-id should be generated. When set to true a unique x-ms-client-request-id value is generated and included in each request. Default is true.
     *
     * @return the generateClientRequestId value.
     */
    Boolean getGenerateClientRequestId();

    /**
     * Gets The HTTP pipeline to send requests through.
     *
     * @return the httpPipeline value.
     */
    HttpPipeline getHttpPipeline();

    /**
     * Gets the HeadExceptions object to access its operations.
     *
     * @return the HeadExceptions object.
     */
    HeadExceptions headExceptions();
}
