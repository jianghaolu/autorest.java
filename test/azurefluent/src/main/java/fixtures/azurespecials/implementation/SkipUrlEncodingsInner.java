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
import com.azure.core.implementation.annotation.PathParam;
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
 * SkipUrlEncodings.
 */
public final class SkipUrlEncodingsInner {
    /**
     * The proxy service used to perform REST calls.
     */
    private SkipUrlEncodingsService service;

    /**
     * The service client containing this operation class.
     */
    private AutoRestAzureSpecialParametersTestClientImpl client;

    /**
     * Initializes an instance of SkipUrlEncodingsInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public SkipUrlEncodingsInner(AutoRestAzureSpecialParametersTestClientImpl client) {
        this.service = AzureProxy.create(SkipUrlEncodingsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * AutoRestAzureSpecialParametersTestClientSkipUrlEncodings to be used by
     * the proxy service to perform REST calls.
     */
    @Host("http://localhost:3000")
    @ServiceInterface(name = "AutoRestAzureSpecialParametersTestClientSkipUrlEncodings")
    private interface SkipUrlEncodingsService {
        @Get("azurespecials/skipUrlEncoding/method/path/valid/{unencodedPathParam}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getMethodPathValid(@PathParam(value = "unencodedPathParam", encoded = true) String unencodedPathParam, @HeaderParam("accept-language") String acceptLanguage);

        @Get("azurespecials/skipUrlEncoding/path/path/valid/{unencodedPathParam}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getPathPathValid(@PathParam(value = "unencodedPathParam", encoded = true) String unencodedPathParam, @HeaderParam("accept-language") String acceptLanguage);

        @Get("azurespecials/skipUrlEncoding/swagger/path/valid/{unencodedPathParam}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getSwaggerPathValid(@PathParam(value = "unencodedPathParam", encoded = true) String unencodedPathParam, @HeaderParam("accept-language") String acceptLanguage);

        @Get("azurespecials/skipUrlEncoding/method/query/valid")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getMethodQueryValid(@QueryParam(value = "q1", encoded = true) String q1, @HeaderParam("accept-language") String acceptLanguage);

        @Get("azurespecials/skipUrlEncoding/method/query/null")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getMethodQueryNull(@QueryParam(value = "q1", encoded = true) String q1, @HeaderParam("accept-language") String acceptLanguage);

        @Get("azurespecials/skipUrlEncoding/path/query/valid")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getPathQueryValid(@QueryParam(value = "q1", encoded = true) String q1, @HeaderParam("accept-language") String acceptLanguage);

        @Get("azurespecials/skipUrlEncoding/swagger/query/valid")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> getSwaggerQueryValid(@QueryParam(value = "q1", encoded = true) String q1, @HeaderParam("accept-language") String acceptLanguage);
    }

    /**
     * Get method with unencoded path parameter with value 'path1/path2/path3'.
     *
     * @param unencodedPathParam Unencoded path parameter with value 'path1/path2/path3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getMethodPathValid(String unencodedPathParam) {
        getMethodPathValidAsync(unencodedPathParam).block();
    }

    /**
     * Get method with unencoded path parameter with value 'path1/path2/path3'.
     *
     * @param unencodedPathParam Unencoded path parameter with value 'path1/path2/path3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getMethodPathValidWithRestResponseAsync(String unencodedPathParam) {
        return service.getMethodPathValid(unencodedPathParam, this.client.getAcceptLanguage());
    }

    /**
     * Get method with unencoded path parameter with value 'path1/path2/path3'.
     *
     * @param unencodedPathParam Unencoded path parameter with value 'path1/path2/path3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getMethodPathValidAsync(String unencodedPathParam) {
        return getMethodPathValidWithRestResponseAsync(unencodedPathParam)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with unencoded path parameter with value 'path1/path2/path3'.
     *
     * @param unencodedPathParam Unencoded path parameter with value 'path1/path2/path3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getPathPathValid(String unencodedPathParam) {
        getPathPathValidAsync(unencodedPathParam).block();
    }

    /**
     * Get method with unencoded path parameter with value 'path1/path2/path3'.
     *
     * @param unencodedPathParam Unencoded path parameter with value 'path1/path2/path3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getPathPathValidWithRestResponseAsync(String unencodedPathParam) {
        return service.getPathPathValid(unencodedPathParam, this.client.getAcceptLanguage());
    }

    /**
     * Get method with unencoded path parameter with value 'path1/path2/path3'.
     *
     * @param unencodedPathParam Unencoded path parameter with value 'path1/path2/path3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getPathPathValidAsync(String unencodedPathParam) {
        return getPathPathValidWithRestResponseAsync(unencodedPathParam)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with unencoded path parameter with value 'path1/path2/path3'.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getSwaggerPathValid() {
        getSwaggerPathValidAsync().block();
    }

    /**
     * Get method with unencoded path parameter with value 'path1/path2/path3'.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getSwaggerPathValidWithRestResponseAsync() {
        final String unencodedPathParam = "path1/path2/path3";
        return service.getSwaggerPathValid(unencodedPathParam, this.client.getAcceptLanguage());
    }

    /**
     * Get method with unencoded path parameter with value 'path1/path2/path3'.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getSwaggerPathValidAsync() {
        return getSwaggerPathValidWithRestResponseAsync()
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     *
     * @param q1 Unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getMethodQueryValid(String q1) {
        getMethodQueryValidAsync(q1).block();
    }

    /**
     * Get method with unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     *
     * @param q1 Unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getMethodQueryValidWithRestResponseAsync(String q1) {
        return service.getMethodQueryValid(q1, this.client.getAcceptLanguage());
    }

    /**
     * Get method with unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     *
     * @param q1 Unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getMethodQueryValidAsync(String q1) {
        return getMethodQueryValidWithRestResponseAsync(q1)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with unencoded query parameter with value null.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getMethodQueryNull() {
        getMethodQueryNullAsync().block();
    }

    /**
     * Get method with unencoded query parameter with value null.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getMethodQueryNullWithRestResponseAsync() {
        final String q1 = null;
        return service.getMethodQueryNull(q1, this.client.getAcceptLanguage());
    }

    /**
     * Get method with unencoded query parameter with value null.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getMethodQueryNullAsync() {
        return getMethodQueryNullWithRestResponseAsync()
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with unencoded query parameter with value null.
     *
     * @param q1 Unencoded query parameter with value null.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getMethodQueryNull(String q1) {
        getMethodQueryNullAsync(q1).block();
    }

    /**
     * Get method with unencoded query parameter with value null.
     *
     * @param q1 Unencoded query parameter with value null.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getMethodQueryNullWithRestResponseAsync(String q1) {
        return service.getMethodQueryNull(q1, this.client.getAcceptLanguage());
    }

    /**
     * Get method with unencoded query parameter with value null.
     *
     * @param q1 Unencoded query parameter with value null.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getMethodQueryNullAsync(String q1) {
        return getMethodQueryNullWithRestResponseAsync(q1)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     *
     * @param q1 Unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getPathQueryValid(String q1) {
        getPathQueryValidAsync(q1).block();
    }

    /**
     * Get method with unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     *
     * @param q1 Unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getPathQueryValidWithRestResponseAsync(String q1) {
        return service.getPathQueryValid(q1, this.client.getAcceptLanguage());
    }

    /**
     * Get method with unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     *
     * @param q1 Unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getPathQueryValidAsync(String q1) {
        return getPathQueryValidWithRestResponseAsync(q1)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get method with unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void getSwaggerQueryValid() {
        getSwaggerQueryValidAsync().block();
    }

    /**
     * Get method with unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> getSwaggerQueryValidWithRestResponseAsync() {
        final String q1 = "value1&q2=value2&q3=value3";
        return service.getSwaggerQueryValid(q1, this.client.getAcceptLanguage());
    }

    /**
     * Get method with unencoded query parameter with value 'value1&amp;q2=value2&amp;q3=value3'.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getSwaggerQueryValidAsync() {
        return getSwaggerQueryValidWithRestResponseAsync()
            .flatMap((VoidResponse res) -> Mono.empty());
    }
}
