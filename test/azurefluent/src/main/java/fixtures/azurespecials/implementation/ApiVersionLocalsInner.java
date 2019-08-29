// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.azurespecials.implementation;

import com.azure.core.http.rest.VoidResponse;
import com.azure.core.implementation.annotation.ExpectedResponses;
import com.azure.core.implementation.annotation.Get;
import com.azure.core.implementation.annotation.HeaderParam;
import com.azure.core.implementation.annotation.Host;
import com.azure.core.implementation.annotation.QueryParam;
import com.azure.core.implementation.annotation.ReturnType;
import com.azure.core.implementation.annotation.ServiceInterface;
import com.azure.core.implementation.annotation.ServiceMethod;
import com.azure.core.implementation.annotation.UnexpectedResponseExceptionType;
import com.microsoft.azure.v3.AzureProxy;
import fixtures.azurespecials.ErrorException;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * ApiVersionLocals.
 */
public final class ApiVersionLocalsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private ApiVersionLocalsService service;

    /**
     * The service client containing this operation class.
     */
    private AutoRestAzureSpecialParametersTestClientImpl client;

    /**
     * Initializes an instance of ApiVersionLocalsInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public ApiVersionLocalsInner(AutoRestAzureSpecialParametersTestClientImpl client) {
        this.service = AzureProxy.create(ApiVersionLocalsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * AutoRestAzureSpecialParametersTestClientApiVersionLocals to be used by
     * the proxy service to perform REST calls.
     */
    @Host("http://localhost:3000")
    @ServiceInterface(name = "AutoRestAzureSpecialParametersTestClientApiVersionLocals")
    private interface ApiVersionLocalsService {
        @Get("azurespecials/apiVersion/method/string/none/query/local/2.0")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getMethodLocalValid(@QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @Get("azurespecials/apiVersion/method/string/none/query/local/null")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getMethodLocalNull(@QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @Get("azurespecials/apiVersion/path/string/none/query/local/2.0")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getPathLocalValid(@QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);

        @Get("azurespecials/apiVersion/swagger/string/none/query/local/2.0")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getSwaggerLocalValid(@QueryParam("api-version") String apiVersion, @HeaderParam("accept-language") String acceptLanguage);
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = '2.0' to succeed.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getMethodLocalValid() {
        getMethodLocalValidAsync().block();
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = '2.0' to succeed.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getMethodLocalValidWithRestResponseAsync() {
        final String apiVersion = "2.0";
        return service.getMethodLocalValid(apiVersion, this.client.getAcceptLanguage());
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = '2.0' to succeed.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getMethodLocalValidAsync() {
        return getMethodLocalValidWithRestResponseAsync()
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = null to succeed.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getMethodLocalNull() {
        getMethodLocalNullAsync().block();
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = null to succeed.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getMethodLocalNullWithRestResponseAsync() {
        final String apiVersion = null;
        return service.getMethodLocalNull(apiVersion, this.client.getAcceptLanguage());
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = null to succeed.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getMethodLocalNullAsync() {
        return getMethodLocalNullWithRestResponseAsync()
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = null to succeed.
     *
     * @param apiVersion This should appear as a method parameter, use value null, this should result in no serialized parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getMethodLocalNull(String apiVersion) {
        getMethodLocalNullAsync(apiVersion).block();
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = null to succeed.
     *
     * @param apiVersion This should appear as a method parameter, use value null, this should result in no serialized parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getMethodLocalNullWithRestResponseAsync(String apiVersion) {
        return service.getMethodLocalNull(apiVersion, this.client.getAcceptLanguage());
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = null to succeed.
     *
     * @param apiVersion This should appear as a method parameter, use value null, this should result in no serialized parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getMethodLocalNullAsync(String apiVersion) {
        return getMethodLocalNullWithRestResponseAsync(apiVersion)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = '2.0' to succeed.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getPathLocalValid() {
        getPathLocalValidAsync().block();
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = '2.0' to succeed.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getPathLocalValidWithRestResponseAsync() {
        final String apiVersion = "2.0";
        return service.getPathLocalValid(apiVersion, this.client.getAcceptLanguage());
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = '2.0' to succeed.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getPathLocalValidAsync() {
        return getPathLocalValidWithRestResponseAsync()
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = '2.0' to succeed.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getSwaggerLocalValid() {
        getSwaggerLocalValidAsync().block();
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = '2.0' to succeed.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getSwaggerLocalValidWithRestResponseAsync() {
        final String apiVersion = "2.0";
        return service.getSwaggerLocalValid(apiVersion, this.client.getAcceptLanguage());
    }

    /**
     * Get method with api-version modeled in the method.  pass in api-version = '2.0' to succeed.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getSwaggerLocalValidAsync() {
        return getSwaggerLocalValidWithRestResponseAsync()
            .flatMap((VoidResponse res) -> Mono.empty());
    }
}
