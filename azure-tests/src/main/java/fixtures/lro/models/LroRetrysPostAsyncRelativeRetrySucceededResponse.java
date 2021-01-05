package fixtures.lro.models;

import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.rest.ResponseBase;

/**
 * Contains all response data for the postAsyncRelativeRetrySucceeded operation.
 */
public final class LroRetrysPostAsyncRelativeRetrySucceededResponse extends ResponseBase<LroRetrysPostAsyncRelativeRetrySucceededHeaders, Void> {
    /**
     * Creates an instance of LroRetrysPostAsyncRelativeRetrySucceededResponse.
     * 
     * @param request the request which resulted in this LroRetrysPostAsyncRelativeRetrySucceededResponse.
     * @param statusCode the status code of the HTTP response.
     * @param rawHeaders the raw headers of the HTTP response.
     * @param value the deserialized value of the HTTP response.
     * @param headers the deserialized headers of the HTTP response.
     */
    public LroRetrysPostAsyncRelativeRetrySucceededResponse(HttpRequest request, int statusCode, HttpHeaders rawHeaders, Void value, LroRetrysPostAsyncRelativeRetrySucceededHeaders headers) {
        super(request, statusCode, rawHeaders, value, headers);
    }
}
