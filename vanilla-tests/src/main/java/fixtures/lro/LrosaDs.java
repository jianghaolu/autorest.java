package fixtures.lro;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.Delete;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.HeaderParam;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.Post;
import com.azure.core.annotation.Put;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceInterface;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.annotation.UnexpectedResponseExceptionType;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.util.Context;
import com.azure.core.util.FluxUtil;
import fixtures.lro.models.CloudErrorException;
import fixtures.lro.models.LrosaDsDelete202NonRetry400Response;
import fixtures.lro.models.LrosaDsDelete202RetryInvalidHeaderResponse;
import fixtures.lro.models.LrosaDsDeleteAsyncRelativeRetry400Response;
import fixtures.lro.models.LrosaDsDeleteAsyncRelativeRetryInvalidHeaderResponse;
import fixtures.lro.models.LrosaDsDeleteAsyncRelativeRetryInvalidJsonPollingResponse;
import fixtures.lro.models.LrosaDsDeleteAsyncRelativeRetryNoStatusResponse;
import fixtures.lro.models.LrosaDsDeleteNonRetry400Response;
import fixtures.lro.models.LrosaDsPost202NoLocationResponse;
import fixtures.lro.models.LrosaDsPost202NonRetry400Response;
import fixtures.lro.models.LrosaDsPost202RetryInvalidHeaderResponse;
import fixtures.lro.models.LrosaDsPostAsyncRelativeRetry400Response;
import fixtures.lro.models.LrosaDsPostAsyncRelativeRetryInvalidHeaderResponse;
import fixtures.lro.models.LrosaDsPostAsyncRelativeRetryInvalidJsonPollingResponse;
import fixtures.lro.models.LrosaDsPostAsyncRelativeRetryNoPayloadResponse;
import fixtures.lro.models.LrosaDsPostNonRetry400Response;
import fixtures.lro.models.LrosaDsPutAsyncRelativeRetry400Response;
import fixtures.lro.models.LrosaDsPutAsyncRelativeRetryInvalidHeaderResponse;
import fixtures.lro.models.LrosaDsPutAsyncRelativeRetryInvalidJsonPollingResponse;
import fixtures.lro.models.LrosaDsPutAsyncRelativeRetryNoStatusPayloadResponse;
import fixtures.lro.models.LrosaDsPutAsyncRelativeRetryNoStatusResponse;
import fixtures.lro.models.Product;
import reactor.core.publisher.Mono;

/** An instance of this class provides access to all the operations defined in LrosaDs. */
public final class LrosaDs {
    /** The proxy service used to perform REST calls. */
    private final LrosaDsService service;

    /** The service client containing this operation class. */
    private final AutoRestLongRunningOperationTestService client;

    /**
     * Initializes an instance of LrosaDs.
     *
     * @param client the instance of the service client containing this operation class.
     */
    LrosaDs(AutoRestLongRunningOperationTestService client) {
        this.service = RestProxy.create(LrosaDsService.class, client.getHttpPipeline(), client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for AutoRestLongRunningOperationTestServiceLrosaDs to be used by the
     * proxy service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "AutoRestLongRunningO")
    private interface LrosaDsService {
        @Put("/lro/nonretryerror/put/400")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> putNonRetry400(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/nonretryerror/put/201/creating/400")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> putNonRetry201Creating400(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/nonretryerror/put/201/creating/400/invalidjson")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> putNonRetry201Creating400InvalidJson(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/nonretryerror/putasync/retry/400")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPutAsyncRelativeRetry400Response> putAsyncRelativeRetry400(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Delete("/lro/nonretryerror/delete/400")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsDeleteNonRetry400Response> deleteNonRetry400(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/nonretryerror/delete/202/retry/400")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsDelete202NonRetry400Response> delete202NonRetry400(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/nonretryerror/deleteasync/retry/400")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsDeleteAsyncRelativeRetry400Response> deleteAsyncRelativeRetry400(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Post("/lro/nonretryerror/post/400")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPostNonRetry400Response> postNonRetry400(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/nonretryerror/post/202/retry/400")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPost202NonRetry400Response> post202NonRetry400(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/nonretryerror/postasync/retry/400")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPostAsyncRelativeRetry400Response> postAsyncRelativeRetry400(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/error/put/201/noprovisioningstatepayload")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> putError201NoProvisioningStatePayload(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/error/putasync/retry/nostatus")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPutAsyncRelativeRetryNoStatusResponse> putAsyncRelativeRetryNoStatus(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/error/putasync/retry/nostatuspayload")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPutAsyncRelativeRetryNoStatusPayloadResponse> putAsyncRelativeRetryNoStatusPayload(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Delete("/lro/error/delete/204/nolocation")
        @ExpectedResponses({204})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Void>> delete204Succeeded(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/error/deleteasync/retry/nostatus")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsDeleteAsyncRelativeRetryNoStatusResponse> deleteAsyncRelativeRetryNoStatus(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Post("/lro/error/post/202/nolocation")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPost202NoLocationResponse> post202NoLocation(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/error/postasync/retry/nopayload")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPostAsyncRelativeRetryNoPayloadResponse> postAsyncRelativeRetryNoPayload(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/error/put/200/invalidjson")
        @ExpectedResponses({200, 204})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> put200InvalidJson(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/error/putasync/retry/invalidheader")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPutAsyncRelativeRetryInvalidHeaderResponse> putAsyncRelativeRetryInvalidHeader(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/error/putasync/retry/invalidjsonpolling")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPutAsyncRelativeRetryInvalidJsonPollingResponse> putAsyncRelativeRetryInvalidJsonPolling(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Delete("/lro/error/delete/202/retry/invalidheader")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsDelete202RetryInvalidHeaderResponse> delete202RetryInvalidHeader(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/error/deleteasync/retry/invalidheader")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsDeleteAsyncRelativeRetryInvalidHeaderResponse> deleteAsyncRelativeRetryInvalidHeader(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/error/deleteasync/retry/invalidjsonpolling")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsDeleteAsyncRelativeRetryInvalidJsonPollingResponse> deleteAsyncRelativeRetryInvalidJsonPolling(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Post("/lro/error/post/202/retry/invalidheader")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPost202RetryInvalidHeaderResponse> post202RetryInvalidHeader(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/error/postasync/retry/invalidheader")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPostAsyncRelativeRetryInvalidHeaderResponse> postAsyncRelativeRetryInvalidHeader(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/error/postasync/retry/invalidjsonpolling")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LrosaDsPostAsyncRelativeRetryInvalidJsonPollingResponse> postAsyncRelativeRetryInvalidJsonPolling(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);
    }

    /**
     * Long running put request, service returns a 400 to the initial request.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> putNonRetry400WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.putNonRetry400(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 400 to the initial request.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putNonRetry400Async(Product product) {
        return putNonRetry400WithResponseAsync(product)
                .flatMap(
                        (Response<Product> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 400 to the initial request.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putNonRetry400Async() {
        final Product product = null;
        return putNonRetry400WithResponseAsync(product)
                .flatMap(
                        (Response<Product> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 400 to the initial request.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putNonRetry400(Product product) {
        return putNonRetry400Async(product).block();
    }

    /**
     * Long running put request, service returns a 400 to the initial request.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putNonRetry400() {
        final Product product = null;
        return putNonRetry400Async(product).block();
    }

    /**
     * Long running put request, service returns a Product with 'ProvisioningState' = 'Creating' and 201 response code.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> putNonRetry201Creating400WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putNonRetry201Creating400(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a Product with 'ProvisioningState' = 'Creating' and 201 response code.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putNonRetry201Creating400Async(Product product) {
        return putNonRetry201Creating400WithResponseAsync(product)
                .flatMap(
                        (Response<Product> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a Product with 'ProvisioningState' = 'Creating' and 201 response code.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putNonRetry201Creating400Async() {
        final Product product = null;
        return putNonRetry201Creating400WithResponseAsync(product)
                .flatMap(
                        (Response<Product> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a Product with 'ProvisioningState' = 'Creating' and 201 response code.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putNonRetry201Creating400(Product product) {
        return putNonRetry201Creating400Async(product).block();
    }

    /**
     * Long running put request, service returns a Product with 'ProvisioningState' = 'Creating' and 201 response code.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putNonRetry201Creating400() {
        final Product product = null;
        return putNonRetry201Creating400Async(product).block();
    }

    /**
     * Long running put request, service returns a Product with 'ProvisioningState' = 'Creating' and 201 response code.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> putNonRetry201Creating400InvalidJsonWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context ->
                        service.putNonRetry201Creating400InvalidJson(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a Product with 'ProvisioningState' = 'Creating' and 201 response code.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putNonRetry201Creating400InvalidJsonAsync(Product product) {
        return putNonRetry201Creating400InvalidJsonWithResponseAsync(product)
                .flatMap(
                        (Response<Product> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a Product with 'ProvisioningState' = 'Creating' and 201 response code.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putNonRetry201Creating400InvalidJsonAsync() {
        final Product product = null;
        return putNonRetry201Creating400InvalidJsonWithResponseAsync(product)
                .flatMap(
                        (Response<Product> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a Product with 'ProvisioningState' = 'Creating' and 201 response code.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putNonRetry201Creating400InvalidJson(Product product) {
        return putNonRetry201Creating400InvalidJsonAsync(product).block();
    }

    /**
     * Long running put request, service returns a Product with 'ProvisioningState' = 'Creating' and 201 response code.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putNonRetry201Creating400InvalidJson() {
        final Product product = null;
        return putNonRetry201Creating400InvalidJsonAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 with ProvisioningState=’Creating’. Poll the endpoint indicated in
     * the Azure-AsyncOperation header for operation status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPutAsyncRelativeRetry400Response> putAsyncRelativeRetry400WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putAsyncRelativeRetry400(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 200 with ProvisioningState=’Creating’. Poll the endpoint indicated in
     * the Azure-AsyncOperation header for operation status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncRelativeRetry400Async(Product product) {
        return putAsyncRelativeRetry400WithResponseAsync(product)
                .flatMap(
                        (LrosaDsPutAsyncRelativeRetry400Response res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 with ProvisioningState=’Creating’. Poll the endpoint indicated in
     * the Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncRelativeRetry400Async() {
        final Product product = null;
        return putAsyncRelativeRetry400WithResponseAsync(product)
                .flatMap(
                        (LrosaDsPutAsyncRelativeRetry400Response res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 with ProvisioningState=’Creating’. Poll the endpoint indicated in
     * the Azure-AsyncOperation header for operation status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncRelativeRetry400(Product product) {
        return putAsyncRelativeRetry400Async(product).block();
    }

    /**
     * Long running put request, service returns a 200 with ProvisioningState=’Creating’. Poll the endpoint indicated in
     * the Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncRelativeRetry400() {
        final Product product = null;
        return putAsyncRelativeRetry400Async(product).block();
    }

    /**
     * Long running delete request, service returns a 400 with an error body.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsDeleteNonRetry400Response> deleteNonRetry400WithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.deleteNonRetry400(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 400 with an error body.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteNonRetry400Async() {
        return deleteNonRetry400WithResponseAsync().flatMap((LrosaDsDeleteNonRetry400Response res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 400 with an error body.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteNonRetry400() {
        deleteNonRetry400Async().block();
    }

    /**
     * Long running delete request, service returns a 202 with a location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsDelete202NonRetry400Response> delete202NonRetry400WithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.delete202NonRetry400(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 with a location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> delete202NonRetry400Async() {
        return delete202NonRetry400WithResponseAsync()
                .flatMap((LrosaDsDelete202NonRetry400Response res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 202 with a location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete202NonRetry400() {
        delete202NonRetry400Async().block();
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsDeleteAsyncRelativeRetry400Response> deleteAsyncRelativeRetry400WithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteAsyncRelativeRetry400(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsyncRelativeRetry400Async() {
        return deleteAsyncRelativeRetry400WithResponseAsync()
                .flatMap((LrosaDsDeleteAsyncRelativeRetry400Response res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteAsyncRelativeRetry400() {
        deleteAsyncRelativeRetry400Async().block();
    }

    /**
     * Long running post request, service returns a 400 with no error body.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPostNonRetry400Response> postNonRetry400WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.postNonRetry400(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running post request, service returns a 400 with no error body.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> postNonRetry400Async(Product product) {
        return postNonRetry400WithResponseAsync(product).flatMap((LrosaDsPostNonRetry400Response res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 400 with no error body.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> postNonRetry400Async() {
        final Product product = null;
        return postNonRetry400WithResponseAsync(product).flatMap((LrosaDsPostNonRetry400Response res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 400 with no error body.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void postNonRetry400(Product product) {
        postNonRetry400Async(product).block();
    }

    /**
     * Long running post request, service returns a 400 with no error body.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void postNonRetry400() {
        final Product product = null;
        postNonRetry400Async(product).block();
    }

    /**
     * Long running post request, service returns a 202 with a location header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPost202NonRetry400Response> post202NonRetry400WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.post202NonRetry400(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running post request, service returns a 202 with a location header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> post202NonRetry400Async(Product product) {
        return post202NonRetry400WithResponseAsync(product)
                .flatMap((LrosaDsPost202NonRetry400Response res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 with a location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> post202NonRetry400Async() {
        final Product product = null;
        return post202NonRetry400WithResponseAsync(product)
                .flatMap((LrosaDsPost202NonRetry400Response res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 with a location header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void post202NonRetry400(Product product) {
        post202NonRetry400Async(product).block();
    }

    /**
     * Long running post request, service returns a 202 with a location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void post202NonRetry400() {
        final Product product = null;
        post202NonRetry400Async(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPostAsyncRelativeRetry400Response> postAsyncRelativeRetry400WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.postAsyncRelativeRetry400(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> postAsyncRelativeRetry400Async(Product product) {
        return postAsyncRelativeRetry400WithResponseAsync(product)
                .flatMap((LrosaDsPostAsyncRelativeRetry400Response res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> postAsyncRelativeRetry400Async() {
        final Product product = null;
        return postAsyncRelativeRetry400WithResponseAsync(product)
                .flatMap((LrosaDsPostAsyncRelativeRetry400Response res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void postAsyncRelativeRetry400(Product product) {
        postAsyncRelativeRetry400Async(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void postAsyncRelativeRetry400() {
        final Product product = null;
        postAsyncRelativeRetry400Async(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request with no payload.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> putError201NoProvisioningStatePayloadWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context ->
                        service.putError201NoProvisioningStatePayload(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 201 to the initial request with no payload.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putError201NoProvisioningStatePayloadAsync(Product product) {
        return putError201NoProvisioningStatePayloadWithResponseAsync(product)
                .flatMap(
                        (Response<Product> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 201 to the initial request with no payload.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putError201NoProvisioningStatePayloadAsync() {
        final Product product = null;
        return putError201NoProvisioningStatePayloadWithResponseAsync(product)
                .flatMap(
                        (Response<Product> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 201 to the initial request with no payload.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putError201NoProvisioningStatePayload(Product product) {
        return putError201NoProvisioningStatePayloadAsync(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request with no payload.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putError201NoProvisioningStatePayload() {
        final Product product = null;
        return putError201NoProvisioningStatePayloadAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPutAsyncRelativeRetryNoStatusResponse> putAsyncRelativeRetryNoStatusWithResponseAsync(
            Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putAsyncRelativeRetryNoStatus(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncRelativeRetryNoStatusAsync(Product product) {
        return putAsyncRelativeRetryNoStatusWithResponseAsync(product)
                .flatMap(
                        (LrosaDsPutAsyncRelativeRetryNoStatusResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncRelativeRetryNoStatusAsync() {
        final Product product = null;
        return putAsyncRelativeRetryNoStatusWithResponseAsync(product)
                .flatMap(
                        (LrosaDsPutAsyncRelativeRetryNoStatusResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncRelativeRetryNoStatus(Product product) {
        return putAsyncRelativeRetryNoStatusAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncRelativeRetryNoStatus() {
        final Product product = null;
        return putAsyncRelativeRetryNoStatusAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPutAsyncRelativeRetryNoStatusPayloadResponse>
            putAsyncRelativeRetryNoStatusPayloadWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context ->
                        service.putAsyncRelativeRetryNoStatusPayload(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncRelativeRetryNoStatusPayloadAsync(Product product) {
        return putAsyncRelativeRetryNoStatusPayloadWithResponseAsync(product)
                .flatMap(
                        (LrosaDsPutAsyncRelativeRetryNoStatusPayloadResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncRelativeRetryNoStatusPayloadAsync() {
        final Product product = null;
        return putAsyncRelativeRetryNoStatusPayloadWithResponseAsync(product)
                .flatMap(
                        (LrosaDsPutAsyncRelativeRetryNoStatusPayloadResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncRelativeRetryNoStatusPayload(Product product) {
        return putAsyncRelativeRetryNoStatusPayloadAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncRelativeRetryNoStatusPayload() {
        final Product product = null;
        return putAsyncRelativeRetryNoStatusPayloadAsync(product).block();
    }

    /**
     * Long running delete request, service returns a 204 to the initial request, indicating success.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> delete204SucceededWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.delete204Succeeded(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 204 to the initial request, indicating success.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> delete204SucceededAsync() {
        return delete204SucceededWithResponseAsync().flatMap((Response<Void> res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 204 to the initial request, indicating success.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete204Succeeded() {
        delete204SucceededAsync().block();
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsDeleteAsyncRelativeRetryNoStatusResponse> deleteAsyncRelativeRetryNoStatusWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteAsyncRelativeRetryNoStatus(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsyncRelativeRetryNoStatusAsync() {
        return deleteAsyncRelativeRetryNoStatusWithResponseAsync()
                .flatMap((LrosaDsDeleteAsyncRelativeRetryNoStatusResponse res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteAsyncRelativeRetryNoStatus() {
        deleteAsyncRelativeRetryNoStatusAsync().block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, without a location header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPost202NoLocationResponse> post202NoLocationWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.post202NoLocation(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request, without a location header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> post202NoLocationAsync(Product product) {
        return post202NoLocationWithResponseAsync(product)
                .flatMap((LrosaDsPost202NoLocationResponse res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, without a location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> post202NoLocationAsync() {
        final Product product = null;
        return post202NoLocationWithResponseAsync(product)
                .flatMap((LrosaDsPost202NoLocationResponse res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, without a location header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void post202NoLocation(Product product) {
        post202NoLocationAsync(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, without a location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void post202NoLocation() {
        final Product product = null;
        post202NoLocationAsync(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPostAsyncRelativeRetryNoPayloadResponse> postAsyncRelativeRetryNoPayloadWithResponseAsync(
            Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.postAsyncRelativeRetryNoPayload(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> postAsyncRelativeRetryNoPayloadAsync(Product product) {
        return postAsyncRelativeRetryNoPayloadWithResponseAsync(product)
                .flatMap((LrosaDsPostAsyncRelativeRetryNoPayloadResponse res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> postAsyncRelativeRetryNoPayloadAsync() {
        final Product product = null;
        return postAsyncRelativeRetryNoPayloadWithResponseAsync(product)
                .flatMap((LrosaDsPostAsyncRelativeRetryNoPayloadResponse res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void postAsyncRelativeRetryNoPayload(Product product) {
        postAsyncRelativeRetryNoPayloadAsync(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void postAsyncRelativeRetryNoPayload() {
        final Product product = null;
        postAsyncRelativeRetryNoPayloadAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that is not a valid json.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> put200InvalidJsonWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.put200InvalidJson(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that is not a valid json.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put200InvalidJsonAsync(Product product) {
        return put200InvalidJsonWithResponseAsync(product)
                .flatMap(
                        (Response<Product> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that is not a valid json.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put200InvalidJsonAsync() {
        final Product product = null;
        return put200InvalidJsonWithResponseAsync(product)
                .flatMap(
                        (Response<Product> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that is not a valid json.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put200InvalidJson(Product product) {
        return put200InvalidJsonAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that is not a valid json.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put200InvalidJson() {
        final Product product = null;
        return put200InvalidJsonAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. The endpoint indicated in the Azure-AsyncOperation header is invalid.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPutAsyncRelativeRetryInvalidHeaderResponse> putAsyncRelativeRetryInvalidHeaderWithResponseAsync(
            Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putAsyncRelativeRetryInvalidHeader(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. The endpoint indicated in the Azure-AsyncOperation header is invalid.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncRelativeRetryInvalidHeaderAsync(Product product) {
        return putAsyncRelativeRetryInvalidHeaderWithResponseAsync(product)
                .flatMap(
                        (LrosaDsPutAsyncRelativeRetryInvalidHeaderResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. The endpoint indicated in the Azure-AsyncOperation header is invalid.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncRelativeRetryInvalidHeaderAsync() {
        final Product product = null;
        return putAsyncRelativeRetryInvalidHeaderWithResponseAsync(product)
                .flatMap(
                        (LrosaDsPutAsyncRelativeRetryInvalidHeaderResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. The endpoint indicated in the Azure-AsyncOperation header is invalid.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncRelativeRetryInvalidHeader(Product product) {
        return putAsyncRelativeRetryInvalidHeaderAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. The endpoint indicated in the Azure-AsyncOperation header is invalid.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncRelativeRetryInvalidHeader() {
        final Product product = null;
        return putAsyncRelativeRetryInvalidHeaderAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPutAsyncRelativeRetryInvalidJsonPollingResponse>
            putAsyncRelativeRetryInvalidJsonPollingWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context ->
                        service.putAsyncRelativeRetryInvalidJsonPolling(
                                this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncRelativeRetryInvalidJsonPollingAsync(Product product) {
        return putAsyncRelativeRetryInvalidJsonPollingWithResponseAsync(product)
                .flatMap(
                        (LrosaDsPutAsyncRelativeRetryInvalidJsonPollingResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncRelativeRetryInvalidJsonPollingAsync() {
        final Product product = null;
        return putAsyncRelativeRetryInvalidJsonPollingWithResponseAsync(product)
                .flatMap(
                        (LrosaDsPutAsyncRelativeRetryInvalidJsonPollingResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncRelativeRetryInvalidJsonPolling(Product product) {
        return putAsyncRelativeRetryInvalidJsonPollingAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncRelativeRetryInvalidJsonPolling() {
        final Product product = null;
        return putAsyncRelativeRetryInvalidJsonPollingAsync(product).block();
    }

    /**
     * Long running delete request, service returns a 202 to the initial request receing a reponse with an invalid
     * 'Location' and 'Retry-After' headers.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsDelete202RetryInvalidHeaderResponse> delete202RetryInvalidHeaderWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.delete202RetryInvalidHeader(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 to the initial request receing a reponse with an invalid
     * 'Location' and 'Retry-After' headers.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> delete202RetryInvalidHeaderAsync() {
        return delete202RetryInvalidHeaderWithResponseAsync()
                .flatMap((LrosaDsDelete202RetryInvalidHeaderResponse res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 202 to the initial request receing a reponse with an invalid
     * 'Location' and 'Retry-After' headers.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete202RetryInvalidHeader() {
        delete202RetryInvalidHeaderAsync().block();
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. The endpoint indicated in the
     * Azure-AsyncOperation header is invalid.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsDeleteAsyncRelativeRetryInvalidHeaderResponse>
            deleteAsyncRelativeRetryInvalidHeaderWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteAsyncRelativeRetryInvalidHeader(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. The endpoint indicated in the
     * Azure-AsyncOperation header is invalid.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsyncRelativeRetryInvalidHeaderAsync() {
        return deleteAsyncRelativeRetryInvalidHeaderWithResponseAsync()
                .flatMap((LrosaDsDeleteAsyncRelativeRetryInvalidHeaderResponse res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. The endpoint indicated in the
     * Azure-AsyncOperation header is invalid.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteAsyncRelativeRetryInvalidHeader() {
        deleteAsyncRelativeRetryInvalidHeaderAsync().block();
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsDeleteAsyncRelativeRetryInvalidJsonPollingResponse>
            deleteAsyncRelativeRetryInvalidJsonPollingWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteAsyncRelativeRetryInvalidJsonPolling(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsyncRelativeRetryInvalidJsonPollingAsync() {
        return deleteAsyncRelativeRetryInvalidJsonPollingWithResponseAsync()
                .flatMap((LrosaDsDeleteAsyncRelativeRetryInvalidJsonPollingResponse res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteAsyncRelativeRetryInvalidJsonPolling() {
        deleteAsyncRelativeRetryInvalidJsonPollingAsync().block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with invalid 'Location' and
     * 'Retry-After' headers.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPost202RetryInvalidHeaderResponse> post202RetryInvalidHeaderWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.post202RetryInvalidHeader(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with invalid 'Location' and
     * 'Retry-After' headers.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> post202RetryInvalidHeaderAsync(Product product) {
        return post202RetryInvalidHeaderWithResponseAsync(product)
                .flatMap((LrosaDsPost202RetryInvalidHeaderResponse res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with invalid 'Location' and
     * 'Retry-After' headers.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> post202RetryInvalidHeaderAsync() {
        final Product product = null;
        return post202RetryInvalidHeaderWithResponseAsync(product)
                .flatMap((LrosaDsPost202RetryInvalidHeaderResponse res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with invalid 'Location' and
     * 'Retry-After' headers.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void post202RetryInvalidHeader(Product product) {
        post202RetryInvalidHeaderAsync(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with invalid 'Location' and
     * 'Retry-After' headers.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void post202RetryInvalidHeader() {
        final Product product = null;
        post202RetryInvalidHeaderAsync(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. The endpoint indicated in the Azure-AsyncOperation header is invalid.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPostAsyncRelativeRetryInvalidHeaderResponse>
            postAsyncRelativeRetryInvalidHeaderWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context ->
                        service.postAsyncRelativeRetryInvalidHeader(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. The endpoint indicated in the Azure-AsyncOperation header is invalid.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> postAsyncRelativeRetryInvalidHeaderAsync(Product product) {
        return postAsyncRelativeRetryInvalidHeaderWithResponseAsync(product)
                .flatMap((LrosaDsPostAsyncRelativeRetryInvalidHeaderResponse res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. The endpoint indicated in the Azure-AsyncOperation header is invalid.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> postAsyncRelativeRetryInvalidHeaderAsync() {
        final Product product = null;
        return postAsyncRelativeRetryInvalidHeaderWithResponseAsync(product)
                .flatMap((LrosaDsPostAsyncRelativeRetryInvalidHeaderResponse res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. The endpoint indicated in the Azure-AsyncOperation header is invalid.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void postAsyncRelativeRetryInvalidHeader(Product product) {
        postAsyncRelativeRetryInvalidHeaderAsync(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. The endpoint indicated in the Azure-AsyncOperation header is invalid.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void postAsyncRelativeRetryInvalidHeader() {
        final Product product = null;
        postAsyncRelativeRetryInvalidHeaderAsync(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LrosaDsPostAsyncRelativeRetryInvalidJsonPollingResponse>
            postAsyncRelativeRetryInvalidJsonPollingWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context ->
                        service.postAsyncRelativeRetryInvalidJsonPolling(
                                this.client.getHost(), product, accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> postAsyncRelativeRetryInvalidJsonPollingAsync(Product product) {
        return postAsyncRelativeRetryInvalidJsonPollingWithResponseAsync(product)
                .flatMap((LrosaDsPostAsyncRelativeRetryInvalidJsonPollingResponse res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> postAsyncRelativeRetryInvalidJsonPollingAsync() {
        final Product product = null;
        return postAsyncRelativeRetryInvalidJsonPollingWithResponseAsync(product)
                .flatMap((LrosaDsPostAsyncRelativeRetryInvalidJsonPollingResponse res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void postAsyncRelativeRetryInvalidJsonPolling(Product product) {
        postAsyncRelativeRetryInvalidJsonPollingAsync(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void postAsyncRelativeRetryInvalidJsonPolling() {
        final Product product = null;
        postAsyncRelativeRetryInvalidJsonPollingAsync(product).block();
    }
}
