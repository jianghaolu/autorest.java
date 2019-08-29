// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.azurespecials.implementation;

import com.azure.core.implementation.annotation.ExpectedResponses;
import com.azure.core.implementation.annotation.Head;
import com.azure.core.implementation.annotation.HeaderParam;
import com.azure.core.implementation.annotation.Host;
import com.azure.core.implementation.annotation.Post;
import com.azure.core.implementation.annotation.ReturnType;
import com.azure.core.implementation.annotation.ServiceInterface;
import com.azure.core.implementation.annotation.ServiceMethod;
import com.azure.core.implementation.annotation.UnexpectedResponseExceptionType;
import com.microsoft.azure.v3.AzureProxy;
import fixtures.azurespecials.Headers;
import fixtures.azurespecials.models.ErrorException;
import fixtures.azurespecials.models.HeadersCustomNamedRequestIdHeadResponse;
import fixtures.azurespecials.models.HeadersCustomNamedRequestIdParamGroupingParameters;
import fixtures.azurespecials.models.HeadersCustomNamedRequestIdParamGroupingResponse;
import fixtures.azurespecials.models.HeadersCustomNamedRequestIdResponse;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * Headers.
 */
public final class HeadersImpl implements Headers {
    /**
     * The proxy service used to perform REST calls.
     */
    private HeadersService service;

    /**
     * The service client containing this operation class.
     */
    private AutoRestAzureSpecialParametersTestClientImpl client;

    /**
     * Initializes an instance of HeadersImpl.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public HeadersImpl(AutoRestAzureSpecialParametersTestClientImpl client) {
        this.service = AzureProxy.create(HeadersService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * AutoRestAzureSpecialParametersTestClientHeaders to be used by the proxy
     * service to perform REST calls.
     */
    @Host("http://localhost:3000")
    @ServiceInterface(name = "AutoRestAzureSpecialParametersTestClientHeaders")
    private interface HeadersService {
        @Post("azurespecials/customNamedRequestId")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<HeadersCustomNamedRequestIdResponse> customNamedRequestId(@HeaderParam("foo-client-request-id") String fooClientRequestId, @HeaderParam("accept-language") String acceptLanguage);

        @Post("azurespecials/customNamedRequestIdParamGrouping")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<HeadersCustomNamedRequestIdParamGroupingResponse> customNamedRequestIdParamGrouping(@HeaderParam("accept-language") String acceptLanguage, @HeaderParam("foo-client-request-id") String fooClientRequestId);

        @Head("azurespecials/customNamedRequestIdHead")
        @ExpectedResponses({200, 404})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<HeadersCustomNamedRequestIdHeadResponse> customNamedRequestIdHead(@HeaderParam("foo-client-request-id") String fooClientRequestId, @HeaderParam("accept-language") String acceptLanguage);
    }

    /**
     * Send foo-client-request-id = 9C4D50EE-2D56-4CD3-8152-34347DC9F2B0 in the header of the request.
     *
     * @param fooClientRequestId The fooRequestId.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void customNamedRequestId(String fooClientRequestId) {
        customNamedRequestIdAsync(fooClientRequestId).block();
    }

    /**
     * Send foo-client-request-id = 9C4D50EE-2D56-4CD3-8152-34347DC9F2B0 in the header of the request.
     *
     * @param fooClientRequestId The fooRequestId.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<HeadersCustomNamedRequestIdResponse> customNamedRequestIdWithRestResponseAsync(String fooClientRequestId) {
        return service.customNamedRequestId(fooClientRequestId, this.client.getAcceptLanguage());
    }

    /**
     * Send foo-client-request-id = 9C4D50EE-2D56-4CD3-8152-34347DC9F2B0 in the header of the request.
     *
     * @param fooClientRequestId The fooRequestId.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> customNamedRequestIdAsync(String fooClientRequestId) {
        return customNamedRequestIdWithRestResponseAsync(fooClientRequestId)
            .flatMap((HeadersCustomNamedRequestIdResponse res) -> Mono.empty());
    }

    /**
     * Send foo-client-request-id = 9C4D50EE-2D56-4CD3-8152-34347DC9F2B0 in the header of the request, via a parameter group.
     *
     * @param headersCustomNamedRequestIdParamGroupingParameters Additional parameters for the operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void customNamedRequestIdParamGrouping(HeadersCustomNamedRequestIdParamGroupingParameters headersCustomNamedRequestIdParamGroupingParameters) {
        customNamedRequestIdParamGroupingAsync(headersCustomNamedRequestIdParamGroupingParameters).block();
    }

    /**
     * Send foo-client-request-id = 9C4D50EE-2D56-4CD3-8152-34347DC9F2B0 in the header of the request, via a parameter group.
     *
     * @param headersCustomNamedRequestIdParamGroupingParameters Additional parameters for the operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<HeadersCustomNamedRequestIdParamGroupingResponse> customNamedRequestIdParamGroupingWithRestResponseAsync(HeadersCustomNamedRequestIdParamGroupingParameters headersCustomNamedRequestIdParamGroupingParameters) {
        String fooClientRequestId = headersCustomNamedRequestIdParamGroupingParameters.fooClientRequestId();
        return service.customNamedRequestIdParamGrouping(this.client.getAcceptLanguage(), fooClientRequestId);
    }

    /**
     * Send foo-client-request-id = 9C4D50EE-2D56-4CD3-8152-34347DC9F2B0 in the header of the request, via a parameter group.
     *
     * @param headersCustomNamedRequestIdParamGroupingParameters Additional parameters for the operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> customNamedRequestIdParamGroupingAsync(HeadersCustomNamedRequestIdParamGroupingParameters headersCustomNamedRequestIdParamGroupingParameters) {
        return customNamedRequestIdParamGroupingWithRestResponseAsync(headersCustomNamedRequestIdParamGroupingParameters)
            .flatMap((HeadersCustomNamedRequestIdParamGroupingResponse res) -> Mono.empty());
    }

    /**
     * Send foo-client-request-id = 9C4D50EE-2D56-4CD3-8152-34347DC9F2B0 in the header of the request.
     *
     * @param fooClientRequestId The fooRequestId.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the boolean object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public boolean customNamedRequestIdHead(String fooClientRequestId) {
        return customNamedRequestIdHeadAsync(fooClientRequestId).block();
    }

    /**
     * Send foo-client-request-id = 9C4D50EE-2D56-4CD3-8152-34347DC9F2B0 in the header of the request.
     *
     * @param fooClientRequestId The fooRequestId.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<HeadersCustomNamedRequestIdHeadResponse> customNamedRequestIdHeadWithRestResponseAsync(String fooClientRequestId) {
        return service.customNamedRequestIdHead(fooClientRequestId, this.client.getAcceptLanguage());
    }

    /**
     * Send foo-client-request-id = 9C4D50EE-2D56-4CD3-8152-34347DC9F2B0 in the header of the request.
     *
     * @param fooClientRequestId The fooRequestId.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Boolean> customNamedRequestIdHeadAsync(String fooClientRequestId) {
        return customNamedRequestIdHeadWithRestResponseAsync(fooClientRequestId)
            .flatMap((HeadersCustomNamedRequestIdHeadResponse res) -> Mono.just(res.value()));
    }
}
