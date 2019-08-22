/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package fixtures.lro.models;

import com.microsoft.rest.v3.RestResponse;
import com.microsoft.rest.v3.http.HttpRequest;
import java.util.Map;

/**
 * Contains all response data for the deleteAsyncRelativeRetryNoStatus operation.
 */
public final class LROSADsDeleteAsyncRelativeRetryNoStatusResponse extends RestResponse<LROSADsDeleteAsyncRelativeRetryNoStatusHeaders, Void> {
    /**
     * Creates an instance of LROSADsDeleteAsyncRelativeRetryNoStatusResponse.
     *
     * @param request the request which resulted in this LROSADsDeleteAsyncRelativeRetryNoStatusResponse.
     * @param statusCode the status code of the HTTP response.
     * @param headers the deserialized headers of the HTTP response.
     * @param rawHeaders the raw headers of the HTTP response.
     * @param body the deserialized body of the HTTP response.
     */
    public LROSADsDeleteAsyncRelativeRetryNoStatusResponse(HttpRequest request, int statusCode, LROSADsDeleteAsyncRelativeRetryNoStatusHeaders headers, Map<String, String> rawHeaders, Void body) {
        super(request, statusCode, headers, rawHeaders, body);
    }

    /**
     * @return the deserialized response headers.
     */
    @Override
    public LROSADsDeleteAsyncRelativeRetryNoStatusHeaders headers() {
        return super.headers();
    }
}