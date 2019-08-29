// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.lro.models;

import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.rest.ResponseBase;
import fixtures.lro.implementation.LROsDeleteProvisioning202Deletingcanceled200HeadersInner;
import fixtures.lro.implementation.ProductInner;

/**
 * Contains all response data for the beginDeleteProvisioning202Deletingcanceled200 operation.
 */
public final class LROsBeginDeleteProvisioning202Deletingcanceled200Response extends ResponseBase<LROsDeleteProvisioning202Deletingcanceled200HeadersInner, ProductInner> {
    /**
     * Creates an instance of LROsBeginDeleteProvisioning202Deletingcanceled200Response.
     *
     * @param request the request which resulted in this LROsBeginDeleteProvisioning202Deletingcanceled200Response.
     * @param statusCode the status code of the HTTP response.
     * @param rawHeaders the raw headers of the HTTP response.
     * @param value the deserialized value of the HTTP response.
     * @param headers the deserialized headers of the HTTP response.
     */
    public LROsBeginDeleteProvisioning202Deletingcanceled200Response(HttpRequest request, int statusCode, HttpHeaders rawHeaders, ProductInner value, LROsDeleteProvisioning202Deletingcanceled200HeadersInner headers) {
        super(request, statusCode, rawHeaders, value, headers);
    }

    /**
     * @return the deserialized response body.
     */
    @Override
    public ProductInner value() {
        return super.value();
    }
}
