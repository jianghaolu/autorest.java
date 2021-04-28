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
import com.azure.core.http.HttpHeader;
import com.azure.core.http.HttpMethod;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.util.Context;
import com.azure.core.util.FluxUtil;
import com.azure.core.util.polling.LongRunningOperationStatus;
import com.azure.core.util.polling.PollResponse;
import com.azure.core.util.polling.PollerFlux;
import com.azure.core.util.polling.PollingContext;
import fixtures.lro.models.CloudErrorException;
import fixtures.lro.models.LROsDelete202NoRetry204Response;
import fixtures.lro.models.LROsDelete202Retry200Response;
import fixtures.lro.models.LROsDeleteAsyncNoHeaderInRetryResponse;
import fixtures.lro.models.LROsDeleteAsyncNoRetrySucceededResponse;
import fixtures.lro.models.LROsDeleteAsyncRetryFailedResponse;
import fixtures.lro.models.LROsDeleteAsyncRetrySucceededResponse;
import fixtures.lro.models.LROsDeleteAsyncRetrycanceledResponse;
import fixtures.lro.models.LROsDeleteNoHeaderInRetryResponse;
import fixtures.lro.models.LROsDeleteProvisioning202Accepted200SucceededResponse;
import fixtures.lro.models.LROsDeleteProvisioning202DeletingFailed200Response;
import fixtures.lro.models.LROsDeleteProvisioning202Deletingcanceled200Response;
import fixtures.lro.models.LROsPost202ListResponse;
import fixtures.lro.models.LROsPost202NoRetry204Response;
import fixtures.lro.models.LROsPost202Retry200Response;
import fixtures.lro.models.LROsPostAsyncNoRetrySucceededResponse;
import fixtures.lro.models.LROsPostAsyncRetryFailedResponse;
import fixtures.lro.models.LROsPostAsyncRetrySucceededResponse;
import fixtures.lro.models.LROsPostAsyncRetrycanceledResponse;
import fixtures.lro.models.LROsPutAsyncNoHeaderInRetryResponse;
import fixtures.lro.models.LROsPutAsyncNoRetrySucceededResponse;
import fixtures.lro.models.LROsPutAsyncNoRetrycanceledResponse;
import fixtures.lro.models.LROsPutAsyncRetryFailedResponse;
import fixtures.lro.models.LROsPutAsyncRetrySucceededResponse;
import fixtures.lro.models.LROsPutNoHeaderInRetryResponse;
import fixtures.lro.models.Product;
import fixtures.lro.models.Sku;
import fixtures.lro.models.SubProduct;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/** An instance of this class provides access to all the operations defined in LROs. */
public final class LROs {
    /** The proxy service used to perform REST calls. */
    private final LROsService service;

    /** The service client containing this operation class. */
    private final AutoRestLongRunningOperationTestService client;

    /**
     * Initializes an instance of LROs.
     *
     * @param client the instance of the service client containing this operation class.
     */
    LROs(AutoRestLongRunningOperationTestService client) {
        this.service = RestProxy.create(LROsService.class, client.getHttpPipeline(), client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for AutoRestLongRunningOperationTestServiceLROs to be used by the proxy
     * service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "AutoRestLongRunningO")
    private interface LROsService {
        @Put("/lro/put/200/succeeded")
        @ExpectedResponses({200, 204})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> put200Succeeded(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/put/201/succeeded")
        @ExpectedResponses({201})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> put201Succeeded(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/list")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPost202ListResponse> post202List(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Put("/lro/put/200/succeeded/nostate")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> put200SucceededNoState(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/put/202/retry/200")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> put202Retry200(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/put/201/creating/succeeded/200")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> put201CreatingSucceeded200(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/put/200/updating/succeeded/200")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> put200UpdatingSucceeded204(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/put/201/created/failed/200")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> put201CreatingFailed200(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/put/200/accepted/canceled/200")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> put200Acceptedcanceled200(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/put/noheader/202/200")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPutNoHeaderInRetryResponse> putNoHeaderInRetry(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/putasync/retry/succeeded")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPutAsyncRetrySucceededResponse> putAsyncRetrySucceeded(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/putasync/noretry/succeeded")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPutAsyncNoRetrySucceededResponse> putAsyncNoRetrySucceeded(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/putasync/retry/failed")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPutAsyncRetryFailedResponse> putAsyncRetryFailed(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/putasync/noretry/canceled")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPutAsyncNoRetrycanceledResponse> putAsyncNoRetrycanceled(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/putasync/noheader/201/200")
        @ExpectedResponses({201})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPutAsyncNoHeaderInRetryResponse> putAsyncNoHeaderInRetry(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/putnonresource/202/200")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Sku>> putNonResource(
                @HostParam("$host") String host,
                @BodyParam("application/json") Sku sku,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/putnonresourceasync/202/200")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Sku>> putAsyncNonResource(
                @HostParam("$host") String host,
                @BodyParam("application/json") Sku sku,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/putsubresource/202/200")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<SubProduct>> putSubResource(
                @HostParam("$host") String host,
                @BodyParam("application/json") SubProduct product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Put("/lro/putsubresourceasync/202/200")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<SubProduct>> putAsyncSubResource(
                @HostParam("$host") String host,
                @BodyParam("application/json") SubProduct product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Delete("/lro/delete/provisioning/202/accepted/200/succeeded")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDeleteProvisioning202Accepted200SucceededResponse> deleteProvisioning202Accepted200Succeeded(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/delete/provisioning/202/deleting/200/failed")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDeleteProvisioning202DeletingFailed200Response> deleteProvisioning202DeletingFailed200(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/delete/provisioning/202/deleting/200/canceled")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDeleteProvisioning202Deletingcanceled200Response> deleteProvisioning202Deletingcanceled200(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/delete/204/succeeded")
        @ExpectedResponses({204})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Void>> delete204Succeeded(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/delete/202/retry/200")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDelete202Retry200Response> delete202Retry200(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/delete/202/noretry/204")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDelete202NoRetry204Response> delete202NoRetry204(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/delete/noheader")
        @ExpectedResponses({202, 204})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDeleteNoHeaderInRetryResponse> deleteNoHeaderInRetry(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/deleteasync/noheader/202/204")
        @ExpectedResponses({202, 204})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDeleteAsyncNoHeaderInRetryResponse> deleteAsyncNoHeaderInRetry(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/deleteasync/retry/succeeded")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDeleteAsyncRetrySucceededResponse> deleteAsyncRetrySucceeded(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/deleteasync/noretry/succeeded")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDeleteAsyncNoRetrySucceededResponse> deleteAsyncNoRetrySucceeded(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/deleteasync/retry/failed")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDeleteAsyncRetryFailedResponse> deleteAsyncRetryFailed(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Delete("/lro/deleteasync/retry/canceled")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsDeleteAsyncRetrycanceledResponse> deleteAsyncRetrycanceled(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Post("/lro/post/payload/200")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Sku>> post200WithPayload(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Post("/lro/post/202/retry/200")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPost202Retry200Response> post202Retry200(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/post/202/noretry/204")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPost202NoRetry204Response> post202NoRetry204(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/LROPostDoubleHeadersFinalLocationGet")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> postDoubleHeadersFinalLocationGet(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Post("/lro/LROPostDoubleHeadersFinalAzureHeaderGet")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> postDoubleHeadersFinalAzureHeaderGet(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Post("/lro/LROPostDoubleHeadersFinalAzureHeaderGetDefault")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<Response<Product>> postDoubleHeadersFinalAzureHeaderGetDefault(
                @HostParam("$host") String host, @HeaderParam("Accept") String accept, Context context);

        @Post("/lro/postasync/retry/succeeded")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPostAsyncRetrySucceededResponse> postAsyncRetrySucceeded(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/postasync/noretry/succeeded")
        @ExpectedResponses({200, 202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPostAsyncNoRetrySucceededResponse> postAsyncNoRetrySucceeded(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/postasync/retry/failed")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPostAsyncRetryFailedResponse> postAsyncRetryFailed(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);

        @Post("/lro/postasync/retry/canceled")
        @ExpectedResponses({202})
        @UnexpectedResponseExceptionType(CloudErrorException.class)
        Mono<LROsPostAsyncRetrycanceledResponse> postAsyncRetrycanceled(
                @HostParam("$host") String host,
                @BodyParam("application/json") Product product,
                @HeaderParam("Accept") String accept,
                Context context);
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> put200SucceededWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.put200Succeeded(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put200SucceededAsync(Product product) {
        return put200SucceededWithResponseAsync(product)
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
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put200SucceededAsync() {
        final Product product = null;
        return put200SucceededWithResponseAsync(product)
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
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put200Succeeded(Product product) {
        return put200SucceededAsync(product).block();
    }

    public PollerFlux<Response<Product>, Product> beginPut200Succeeded(Product product) {
        return PollerFlux.create(Duration.ofSeconds(30),
                ctx -> put200Acceptedcanceled200WithResponseAsync(product).map(p -> {
                    HttpHeader operationLocation = p.getHeaders().get("Operation-Location");
                    if (operationLocation != null) {
                        ctx.setData(operationLocation.getName(), operationLocation.getValue());
                        return new PollResponse<>(LongRunningOperationStatus.IN_PROGRESS, p);
                    } else if (p.getStatusCode() / 100 == 2) {
                        return new PollResponse<>(LongRunningOperationStatus.SUCCESSFULLY_COMPLETED, p);
                    } else {
                        return new PollResponse<>(LongRunningOperationStatus.FAILED, p);
                    }
                }),
                ctx -> client.getHttpPipeline().send(new HttpRequest(HttpMethod.GET, ctx.getData("Operation-Location"))).map(p -> transform.apply(ctx, p)),
                (ctx, pr) -> Mono.error(new RuntimeException("Does not support cancellation.")),
                ctx -> Mono.just(ctx.getActivationResponse().getValue().getValue()));
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that contains
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put200Succeeded() {
        final Product product = null;
        return put200SucceededAsync(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> put201SucceededWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.put201Succeeded(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put201SucceededAsync(Product product) {
        return put201SucceededWithResponseAsync(product)
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
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put201SucceededAsync() {
        final Product product = null;
        return put201SucceededWithResponseAsync(product)
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
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put201Succeeded(Product product) {
        return put201SucceededAsync(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put201Succeeded() {
        final Product product = null;
        return put201SucceededAsync(product).block();
    }

    /**
     * Long running put request, service returns a 202 with empty body to first request, returns a 200 with body [{
     * 'id': '100', 'name': 'foo' }].
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of Product.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsPost202ListResponse> post202ListWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.post202List(this.client.getHost(), accept, context));
    }

    /**
     * Long running put request, service returns a 202 with empty body to first request, returns a 200 with body [{
     * 'id': '100', 'name': 'foo' }].
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of Product.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<Product>> post202ListAsync() {
        return post202ListWithResponseAsync()
                .flatMap(
                        (LROsPost202ListResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 202 with empty body to first request, returns a 200 with body [{
     * 'id': '100', 'name': 'foo' }].
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return array of Product.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public List<Product> post202List() {
        return post202ListAsync().block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that does not contain
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> put200SucceededNoStateWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.put200SucceededNoState(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that does not contain
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put200SucceededNoStateAsync(Product product) {
        return put200SucceededNoStateWithResponseAsync(product)
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
     * Long running put request, service returns a 200 to the initial request, with an entity that does not contain
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put200SucceededNoStateAsync() {
        final Product product = null;
        return put200SucceededNoStateWithResponseAsync(product)
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
     * Long running put request, service returns a 200 to the initial request, with an entity that does not contain
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put200SucceededNoState(Product product) {
        return put200SucceededNoStateAsync(product).block();
    }

    /**
     * Long running put request, service returns a 200 to the initial request, with an entity that does not contain
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put200SucceededNoState() {
        final Product product = null;
        return put200SucceededNoStateAsync(product).block();
    }

    /**
     * Long running put request, service returns a 202 to the initial request, with a location header that points to a
     * polling URL that returns a 200 and an entity that doesn't contains ProvisioningState.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> put202Retry200WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.put202Retry200(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 202 to the initial request, with a location header that points to a
     * polling URL that returns a 200 and an entity that doesn't contains ProvisioningState.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put202Retry200Async(Product product) {
        return put202Retry200WithResponseAsync(product)
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
     * Long running put request, service returns a 202 to the initial request, with a location header that points to a
     * polling URL that returns a 200 and an entity that doesn't contains ProvisioningState.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put202Retry200Async() {
        final Product product = null;
        return put202Retry200WithResponseAsync(product)
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
     * Long running put request, service returns a 202 to the initial request, with a location header that points to a
     * polling URL that returns a 200 and an entity that doesn't contains ProvisioningState.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put202Retry200(Product product) {
        return put202Retry200Async(product).block();
    }

    /**
     * Long running put request, service returns a 202 to the initial request, with a location header that points to a
     * polling URL that returns a 200 and an entity that doesn't contains ProvisioningState.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put202Retry200() {
        final Product product = null;
        return put202Retry200Async(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> put201CreatingSucceeded200WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.put201CreatingSucceeded200(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put201CreatingSucceeded200Async(Product product) {
        return put201CreatingSucceeded200WithResponseAsync(product)
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
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put201CreatingSucceeded200Async() {
        final Product product = null;
        return put201CreatingSucceeded200WithResponseAsync(product)
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
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put201CreatingSucceeded200(Product product) {
        return put201CreatingSucceeded200Async(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put201CreatingSucceeded200() {
        final Product product = null;
        return put201CreatingSucceeded200Async(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Updating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> put200UpdatingSucceeded204WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.put200UpdatingSucceeded204(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Updating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put200UpdatingSucceeded204Async(Product product) {
        return put200UpdatingSucceeded204WithResponseAsync(product)
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
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Updating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put200UpdatingSucceeded204Async() {
        final Product product = null;
        return put200UpdatingSucceeded204WithResponseAsync(product)
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
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Updating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put200UpdatingSucceeded204(Product product) {
        return put200UpdatingSucceeded204Async(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Updating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put200UpdatingSucceeded204() {
        final Product product = null;
        return put200UpdatingSucceeded204Async(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Created’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Failed’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> put201CreatingFailed200WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.put201CreatingFailed200(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Created’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Failed’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put201CreatingFailed200Async(Product product) {
        return put201CreatingFailed200WithResponseAsync(product)
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
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Created’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Failed’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put201CreatingFailed200Async() {
        final Product product = null;
        return put201CreatingFailed200WithResponseAsync(product)
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
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Created’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Failed’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put201CreatingFailed200(Product product) {
        return put201CreatingFailed200Async(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Created’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Failed’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put201CreatingFailed200() {
        final Product product = null;
        return put201CreatingFailed200Async(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Canceled’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> put200Acceptedcanceled200WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.put200Acceptedcanceled200(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Canceled’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put200Acceptedcanceled200Async(Product product) {
        return put200Acceptedcanceled200WithResponseAsync(product)
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
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Canceled’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> put200Acceptedcanceled200Async() {
        final Product product = null;
        return put200Acceptedcanceled200WithResponseAsync(product)
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
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Canceled’.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put200Acceptedcanceled200(Product product) {
        return put200Acceptedcanceled200Async(product).block();
    }

    /**
     * Long running put request, service returns a 201 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Canceled’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product put200Acceptedcanceled200() {
        final Product product = null;
        return put200Acceptedcanceled200Async(product).block();
    }

    /**
     * Long running put request, service returns a 202 to the initial request with location header. Subsequent calls to
     * operation status do not contain location header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsPutNoHeaderInRetryResponse> putNoHeaderInRetryWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putNoHeaderInRetry(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 202 to the initial request with location header. Subsequent calls to
     * operation status do not contain location header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putNoHeaderInRetryAsync(Product product) {
        return putNoHeaderInRetryWithResponseAsync(product)
                .flatMap(
                        (LROsPutNoHeaderInRetryResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 202 to the initial request with location header. Subsequent calls to
     * operation status do not contain location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putNoHeaderInRetryAsync() {
        final Product product = null;
        return putNoHeaderInRetryWithResponseAsync(product)
                .flatMap(
                        (LROsPutNoHeaderInRetryResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 202 to the initial request with location header. Subsequent calls to
     * operation status do not contain location header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putNoHeaderInRetry(Product product) {
        return putNoHeaderInRetryAsync(product).block();
    }

    /**
     * Long running put request, service returns a 202 to the initial request with location header. Subsequent calls to
     * operation status do not contain location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putNoHeaderInRetry() {
        final Product product = null;
        return putNoHeaderInRetryAsync(product).block();
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
    public Mono<LROsPutAsyncRetrySucceededResponse> putAsyncRetrySucceededWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putAsyncRetrySucceeded(this.client.getHost(), product, accept, context));
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
    public Mono<Product> putAsyncRetrySucceededAsync(Product product) {
        return putAsyncRetrySucceededWithResponseAsync(product)
                .flatMap(
                        (LROsPutAsyncRetrySucceededResponse res) -> {
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
    public Mono<Product> putAsyncRetrySucceededAsync() {
        final Product product = null;
        return putAsyncRetrySucceededWithResponseAsync(product)
                .flatMap(
                        (LROsPutAsyncRetrySucceededResponse res) -> {
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
    public Product putAsyncRetrySucceeded(Product product) {
        return putAsyncRetrySucceededAsync(product).block();
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
    public Product putAsyncRetrySucceeded() {
        final Product product = null;
        return putAsyncRetrySucceededAsync(product).block();
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
    public Mono<LROsPutAsyncNoRetrySucceededResponse> putAsyncNoRetrySucceededWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putAsyncNoRetrySucceeded(this.client.getHost(), product, accept, context));
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
    public Mono<Product> putAsyncNoRetrySucceededAsync(Product product) {
        return putAsyncNoRetrySucceededWithResponseAsync(product)
                .flatMap(
                        (LROsPutAsyncNoRetrySucceededResponse res) -> {
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
    public Mono<Product> putAsyncNoRetrySucceededAsync() {
        final Product product = null;
        return putAsyncNoRetrySucceededWithResponseAsync(product)
                .flatMap(
                        (LROsPutAsyncNoRetrySucceededResponse res) -> {
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
    public Product putAsyncNoRetrySucceeded(Product product) {
        return putAsyncNoRetrySucceededAsync(product).block();
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
    public Product putAsyncNoRetrySucceeded() {
        final Product product = null;
        return putAsyncNoRetrySucceededAsync(product).block();
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
    public Mono<LROsPutAsyncRetryFailedResponse> putAsyncRetryFailedWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putAsyncRetryFailed(this.client.getHost(), product, accept, context));
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
    public Mono<Product> putAsyncRetryFailedAsync(Product product) {
        return putAsyncRetryFailedWithResponseAsync(product)
                .flatMap(
                        (LROsPutAsyncRetryFailedResponse res) -> {
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
    public Mono<Product> putAsyncRetryFailedAsync() {
        final Product product = null;
        return putAsyncRetryFailedWithResponseAsync(product)
                .flatMap(
                        (LROsPutAsyncRetryFailedResponse res) -> {
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
    public Product putAsyncRetryFailed(Product product) {
        return putAsyncRetryFailedAsync(product).block();
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
    public Product putAsyncRetryFailed() {
        final Product product = null;
        return putAsyncRetryFailedAsync(product).block();
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
    public Mono<LROsPutAsyncNoRetrycanceledResponse> putAsyncNoRetrycanceledWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putAsyncNoRetrycanceled(this.client.getHost(), product, accept, context));
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
    public Mono<Product> putAsyncNoRetrycanceledAsync(Product product) {
        return putAsyncNoRetrycanceledWithResponseAsync(product)
                .flatMap(
                        (LROsPutAsyncNoRetrycanceledResponse res) -> {
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
    public Mono<Product> putAsyncNoRetrycanceledAsync() {
        final Product product = null;
        return putAsyncNoRetrycanceledWithResponseAsync(product)
                .flatMap(
                        (LROsPutAsyncNoRetrycanceledResponse res) -> {
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
    public Product putAsyncNoRetrycanceled(Product product) {
        return putAsyncNoRetrycanceledAsync(product).block();
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
    public Product putAsyncNoRetrycanceled() {
        final Product product = null;
        return putAsyncNoRetrycanceledAsync(product).block();
    }

    /**
     * Long running put request, service returns a 202 to the initial request with Azure-AsyncOperation header.
     * Subsequent calls to operation status do not contain Azure-AsyncOperation header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsPutAsyncNoHeaderInRetryResponse> putAsyncNoHeaderInRetryWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putAsyncNoHeaderInRetry(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request, service returns a 202 to the initial request with Azure-AsyncOperation header.
     * Subsequent calls to operation status do not contain Azure-AsyncOperation header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncNoHeaderInRetryAsync(Product product) {
        return putAsyncNoHeaderInRetryWithResponseAsync(product)
                .flatMap(
                        (LROsPutAsyncNoHeaderInRetryResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 202 to the initial request with Azure-AsyncOperation header.
     * Subsequent calls to operation status do not contain Azure-AsyncOperation header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> putAsyncNoHeaderInRetryAsync() {
        final Product product = null;
        return putAsyncNoHeaderInRetryWithResponseAsync(product)
                .flatMap(
                        (LROsPutAsyncNoHeaderInRetryResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request, service returns a 202 to the initial request with Azure-AsyncOperation header.
     * Subsequent calls to operation status do not contain Azure-AsyncOperation header.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncNoHeaderInRetry(Product product) {
        return putAsyncNoHeaderInRetryAsync(product).block();
    }

    /**
     * Long running put request, service returns a 202 to the initial request with Azure-AsyncOperation header.
     * Subsequent calls to operation status do not contain Azure-AsyncOperation header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product putAsyncNoHeaderInRetry() {
        final Product product = null;
        return putAsyncNoHeaderInRetryAsync(product).block();
    }

    /**
     * Long running put request with non resource.
     *
     * @param sku sku to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Sku>> putNonResourceWithResponseAsync(Sku sku) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (sku != null) {
            sku.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.putNonResource(this.client.getHost(), sku, accept, context));
    }

    /**
     * Long running put request with non resource.
     *
     * @param sku sku to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Sku> putNonResourceAsync(Sku sku) {
        return putNonResourceWithResponseAsync(sku)
                .flatMap(
                        (Response<Sku> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request with non resource.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Sku> putNonResourceAsync() {
        final Sku sku = null;
        return putNonResourceWithResponseAsync(sku)
                .flatMap(
                        (Response<Sku> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request with non resource.
     *
     * @param sku sku to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Sku putNonResource(Sku sku) {
        return putNonResourceAsync(sku).block();
    }

    /**
     * Long running put request with non resource.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Sku putNonResource() {
        final Sku sku = null;
        return putNonResourceAsync(sku).block();
    }

    /**
     * Long running put request with non resource.
     *
     * @param sku Sku to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Sku>> putAsyncNonResourceWithResponseAsync(Sku sku) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (sku != null) {
            sku.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.putAsyncNonResource(this.client.getHost(), sku, accept, context));
    }

    /**
     * Long running put request with non resource.
     *
     * @param sku Sku to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Sku> putAsyncNonResourceAsync(Sku sku) {
        return putAsyncNonResourceWithResponseAsync(sku)
                .flatMap(
                        (Response<Sku> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request with non resource.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Sku> putAsyncNonResourceAsync() {
        final Sku sku = null;
        return putAsyncNonResourceWithResponseAsync(sku)
                .flatMap(
                        (Response<Sku> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request with non resource.
     *
     * @param sku Sku to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Sku putAsyncNonResource(Sku sku) {
        return putAsyncNonResourceAsync(sku).block();
    }

    /**
     * Long running put request with non resource.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Sku putAsyncNonResource() {
        final Sku sku = null;
        return putAsyncNonResourceAsync(sku).block();
    }

    /**
     * Long running put request with sub resource.
     *
     * @param provisioningState The provisioningState parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<SubProduct>> putSubResourceWithResponseAsync(String provisioningState) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        SubProduct productInternal = null;
        if (provisioningState != null) {
            productInternal = new SubProduct();
            productInternal.setProvisioningState(provisioningState);
        }
        SubProduct product = productInternal;
        return FluxUtil.withContext(context -> service.putSubResource(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request with sub resource.
     *
     * @param provisioningState The provisioningState parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SubProduct> putSubResourceAsync(String provisioningState) {
        return putSubResourceWithResponseAsync(provisioningState)
                .flatMap(
                        (Response<SubProduct> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request with sub resource.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SubProduct> putSubResourceAsync() {
        final String provisioningState = null;
        return putSubResourceWithResponseAsync(provisioningState)
                .flatMap(
                        (Response<SubProduct> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request with sub resource.
     *
     * @param provisioningState The provisioningState parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public SubProduct putSubResource(String provisioningState) {
        return putSubResourceAsync(provisioningState).block();
    }

    /**
     * Long running put request with sub resource.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public SubProduct putSubResource() {
        final String provisioningState = null;
        return putSubResourceAsync(provisioningState).block();
    }

    /**
     * Long running put request with sub resource.
     *
     * @param provisioningState The provisioningState parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<SubProduct>> putAsyncSubResourceWithResponseAsync(String provisioningState) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        SubProduct productInternal = null;
        if (provisioningState != null) {
            productInternal = new SubProduct();
            productInternal.setProvisioningState(provisioningState);
        }
        SubProduct product = productInternal;
        return FluxUtil.withContext(
                context -> service.putAsyncSubResource(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running put request with sub resource.
     *
     * @param provisioningState The provisioningState parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SubProduct> putAsyncSubResourceAsync(String provisioningState) {
        return putAsyncSubResourceWithResponseAsync(provisioningState)
                .flatMap(
                        (Response<SubProduct> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request with sub resource.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SubProduct> putAsyncSubResourceAsync() {
        final String provisioningState = null;
        return putAsyncSubResourceWithResponseAsync(provisioningState)
                .flatMap(
                        (Response<SubProduct> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running put request with sub resource.
     *
     * @param provisioningState The provisioningState parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public SubProduct putAsyncSubResource(String provisioningState) {
        return putAsyncSubResourceAsync(provisioningState).block();
    }

    /**
     * Long running put request with sub resource.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public SubProduct putAsyncSubResource() {
        final String provisioningState = null;
        return putAsyncSubResourceAsync(provisioningState).block();
    }

    /**
     * Long running delete request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Accepted’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsDeleteProvisioning202Accepted200SucceededResponse>
            deleteProvisioning202Accepted200SucceededWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteProvisioning202Accepted200Succeeded(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Accepted’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> deleteProvisioning202Accepted200SucceededAsync() {
        return deleteProvisioning202Accepted200SucceededWithResponseAsync()
                .flatMap(
                        (LROsDeleteProvisioning202Accepted200SucceededResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running delete request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Accepted’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product deleteProvisioning202Accepted200Succeeded() {
        return deleteProvisioning202Accepted200SucceededAsync().block();
    }

    /**
     * Long running delete request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Failed’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsDeleteProvisioning202DeletingFailed200Response>
            deleteProvisioning202DeletingFailed200WithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteProvisioning202DeletingFailed200(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Failed’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> deleteProvisioning202DeletingFailed200Async() {
        return deleteProvisioning202DeletingFailed200WithResponseAsync()
                .flatMap(
                        (LROsDeleteProvisioning202DeletingFailed200Response res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running delete request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Failed’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product deleteProvisioning202DeletingFailed200() {
        return deleteProvisioning202DeletingFailed200Async().block();
    }

    /**
     * Long running delete request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Canceled’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsDeleteProvisioning202Deletingcanceled200Response>
            deleteProvisioning202Deletingcanceled200WithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteProvisioning202Deletingcanceled200(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Canceled’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> deleteProvisioning202Deletingcanceled200Async() {
        return deleteProvisioning202Deletingcanceled200WithResponseAsync()
                .flatMap(
                        (LROsDeleteProvisioning202Deletingcanceled200Response res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running delete request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Polls return this value until the last poll returns a ‘200’ with
     * ProvisioningState=’Canceled’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product deleteProvisioning202Deletingcanceled200() {
        return deleteProvisioning202Deletingcanceled200Async().block();
    }

    /**
     * Long running delete succeeds and returns right away.
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
     * Long running delete succeeds and returns right away.
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
     * Long running delete succeeds and returns right away.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void delete204Succeeded() {
        delete204SucceededAsync().block();
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Polls return this value until the last
     * poll returns a ‘200’ with ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsDelete202Retry200Response> delete202Retry200WithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.delete202Retry200(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Polls return this value until the last
     * poll returns a ‘200’ with ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> delete202Retry200Async() {
        return delete202Retry200WithResponseAsync()
                .flatMap(
                        (LROsDelete202Retry200Response res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Polls return this value until the last
     * poll returns a ‘200’ with ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product delete202Retry200() {
        return delete202Retry200Async().block();
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Polls return this value until the last
     * poll returns a ‘200’ with ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsDelete202NoRetry204Response> delete202NoRetry204WithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.delete202NoRetry204(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Polls return this value until the last
     * poll returns a ‘200’ with ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> delete202NoRetry204Async() {
        return delete202NoRetry204WithResponseAsync()
                .flatMap(
                        (LROsDelete202NoRetry204Response res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Polls return this value until the last
     * poll returns a ‘200’ with ProvisioningState=’Succeeded’.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product delete202NoRetry204() {
        return delete202NoRetry204Async().block();
    }

    /**
     * Long running delete request, service returns a location header in the initial request. Subsequent calls to
     * operation status do not contain location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsDeleteNoHeaderInRetryResponse> deleteNoHeaderInRetryWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.deleteNoHeaderInRetry(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns a location header in the initial request. Subsequent calls to
     * operation status do not contain location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteNoHeaderInRetryAsync() {
        return deleteNoHeaderInRetryWithResponseAsync()
                .flatMap((LROsDeleteNoHeaderInRetryResponse res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a location header in the initial request. Subsequent calls to
     * operation status do not contain location header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteNoHeaderInRetry() {
        deleteNoHeaderInRetryAsync().block();
    }

    /**
     * Long running delete request, service returns an Azure-AsyncOperation header in the initial request. Subsequent
     * calls to operation status do not contain Azure-AsyncOperation header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsDeleteAsyncNoHeaderInRetryResponse> deleteAsyncNoHeaderInRetryWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteAsyncNoHeaderInRetry(this.client.getHost(), accept, context));
    }

    /**
     * Long running delete request, service returns an Azure-AsyncOperation header in the initial request. Subsequent
     * calls to operation status do not contain Azure-AsyncOperation header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteAsyncNoHeaderInRetryAsync() {
        return deleteAsyncNoHeaderInRetryWithResponseAsync()
                .flatMap((LROsDeleteAsyncNoHeaderInRetryResponse res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns an Azure-AsyncOperation header in the initial request. Subsequent
     * calls to operation status do not contain Azure-AsyncOperation header.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteAsyncNoHeaderInRetry() {
        deleteAsyncNoHeaderInRetryAsync().block();
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
    public Mono<LROsDeleteAsyncRetrySucceededResponse> deleteAsyncRetrySucceededWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteAsyncRetrySucceeded(this.client.getHost(), accept, context));
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
    public Mono<Void> deleteAsyncRetrySucceededAsync() {
        return deleteAsyncRetrySucceededWithResponseAsync()
                .flatMap((LROsDeleteAsyncRetrySucceededResponse res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteAsyncRetrySucceeded() {
        deleteAsyncRetrySucceededAsync().block();
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
    public Mono<LROsDeleteAsyncNoRetrySucceededResponse> deleteAsyncNoRetrySucceededWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteAsyncNoRetrySucceeded(this.client.getHost(), accept, context));
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
    public Mono<Void> deleteAsyncNoRetrySucceededAsync() {
        return deleteAsyncNoRetrySucceededWithResponseAsync()
                .flatMap((LROsDeleteAsyncNoRetrySucceededResponse res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteAsyncNoRetrySucceeded() {
        deleteAsyncNoRetrySucceededAsync().block();
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
    public Mono<LROsDeleteAsyncRetryFailedResponse> deleteAsyncRetryFailedWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.deleteAsyncRetryFailed(this.client.getHost(), accept, context));
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
    public Mono<Void> deleteAsyncRetryFailedAsync() {
        return deleteAsyncRetryFailedWithResponseAsync()
                .flatMap((LROsDeleteAsyncRetryFailedResponse res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteAsyncRetryFailed() {
        deleteAsyncRetryFailedAsync().block();
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
    public Mono<LROsDeleteAsyncRetrycanceledResponse> deleteAsyncRetrycanceledWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.deleteAsyncRetrycanceled(this.client.getHost(), accept, context));
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
    public Mono<Void> deleteAsyncRetrycanceledAsync() {
        return deleteAsyncRetrycanceledWithResponseAsync()
                .flatMap((LROsDeleteAsyncRetrycanceledResponse res) -> Mono.empty());
    }

    /**
     * Long running delete request, service returns a 202 to the initial request. Poll the endpoint indicated in the
     * Azure-AsyncOperation header for operation status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteAsyncRetrycanceled() {
        deleteAsyncRetrycanceledAsync().block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' header. Poll returns a
     * 200 with a response body after success.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Sku>> post200WithPayloadWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(context -> service.post200WithPayload(this.client.getHost(), accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' header. Poll returns a
     * 200 with a response body after success.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Sku> post200WithPayloadAsync() {
        return post200WithPayloadWithResponseAsync()
                .flatMap(
                        (Response<Sku> res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' header. Poll returns a
     * 200 with a response body after success.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Sku post200WithPayload() {
        return post200WithPayloadAsync().block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' and 'Retry-After'
     * headers, Polls return a 200 with a response body after success.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsPost202Retry200Response> post202Retry200WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.post202Retry200(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' and 'Retry-After'
     * headers, Polls return a 200 with a response body after success.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> post202Retry200Async(Product product) {
        return post202Retry200WithResponseAsync(product).flatMap((LROsPost202Retry200Response res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' and 'Retry-After'
     * headers, Polls return a 200 with a response body after success.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the completion.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> post202Retry200Async() {
        final Product product = null;
        return post202Retry200WithResponseAsync(product).flatMap((LROsPost202Retry200Response res) -> Mono.empty());
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' and 'Retry-After'
     * headers, Polls return a 200 with a response body after success.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void post202Retry200(Product product) {
        post202Retry200Async(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' and 'Retry-After'
     * headers, Polls return a 200 with a response body after success.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void post202Retry200() {
        final Product product = null;
        post202Retry200Async(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' header, 204 with
     * noresponse body after success.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsPost202NoRetry204Response> post202NoRetry204WithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.post202NoRetry204(this.client.getHost(), product, accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' header, 204 with
     * noresponse body after success.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> post202NoRetry204Async(Product product) {
        return post202NoRetry204WithResponseAsync(product)
                .flatMap(
                        (LROsPost202NoRetry204Response res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' header, 204 with
     * noresponse body after success.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> post202NoRetry204Async() {
        final Product product = null;
        return post202NoRetry204WithResponseAsync(product)
                .flatMap(
                        (LROsPost202NoRetry204Response res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' header, 204 with
     * noresponse body after success.
     *
     * @param product Product to put.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product post202NoRetry204(Product product) {
        return post202NoRetry204Async(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with 'Location' header, 204 with
     * noresponse body after success.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product post202NoRetry204() {
        final Product product = null;
        return post202NoRetry204Async(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request with both Location and Azure-Async
     * header. Poll Azure-Async and it's success. Should poll Location to get the final object.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> postDoubleHeadersFinalLocationGetWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.postDoubleHeadersFinalLocationGet(this.client.getHost(), accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request with both Location and Azure-Async
     * header. Poll Azure-Async and it's success. Should poll Location to get the final object.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> postDoubleHeadersFinalLocationGetAsync() {
        return postDoubleHeadersFinalLocationGetWithResponseAsync()
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
     * Long running post request, service returns a 202 to the initial request with both Location and Azure-Async
     * header. Poll Azure-Async and it's success. Should poll Location to get the final object.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product postDoubleHeadersFinalLocationGet() {
        return postDoubleHeadersFinalLocationGetAsync().block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request with both Location and Azure-Async
     * header. Poll Azure-Async and it's success. Should NOT poll Location to get the final object.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> postDoubleHeadersFinalAzureHeaderGetWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.postDoubleHeadersFinalAzureHeaderGet(this.client.getHost(), accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request with both Location and Azure-Async
     * header. Poll Azure-Async and it's success. Should NOT poll Location to get the final object.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> postDoubleHeadersFinalAzureHeaderGetAsync() {
        return postDoubleHeadersFinalAzureHeaderGetWithResponseAsync()
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
     * Long running post request, service returns a 202 to the initial request with both Location and Azure-Async
     * header. Poll Azure-Async and it's success. Should NOT poll Location to get the final object.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product postDoubleHeadersFinalAzureHeaderGet() {
        return postDoubleHeadersFinalAzureHeaderGetAsync().block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request with both Location and Azure-Async
     * header. Poll Azure-Async and it's success. Should NOT poll Location to get the final object if you support
     * initial Autorest behavior.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Product>> postDoubleHeadersFinalAzureHeaderGetDefaultWithResponseAsync() {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.postDoubleHeadersFinalAzureHeaderGetDefault(this.client.getHost(), accept, context));
    }

    /**
     * Long running post request, service returns a 202 to the initial request with both Location and Azure-Async
     * header. Poll Azure-Async and it's success. Should NOT poll Location to get the final object if you support
     * initial Autorest behavior.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> postDoubleHeadersFinalAzureHeaderGetDefaultAsync() {
        return postDoubleHeadersFinalAzureHeaderGetDefaultWithResponseAsync()
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
     * Long running post request, service returns a 202 to the initial request with both Location and Azure-Async
     * header. Poll Azure-Async and it's success. Should NOT poll Location to get the final object if you support
     * initial Autorest behavior.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product postDoubleHeadersFinalAzureHeaderGetDefault() {
        return postDoubleHeadersFinalAzureHeaderGetDefaultAsync().block();
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
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsPostAsyncRetrySucceededResponse> postAsyncRetrySucceededWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.postAsyncRetrySucceeded(this.client.getHost(), product, accept, context));
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
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> postAsyncRetrySucceededAsync(Product product) {
        return postAsyncRetrySucceededWithResponseAsync(product)
                .flatMap(
                        (LROsPostAsyncRetrySucceededResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> postAsyncRetrySucceededAsync() {
        final Product product = null;
        return postAsyncRetrySucceededWithResponseAsync(product)
                .flatMap(
                        (LROsPostAsyncRetrySucceededResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
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
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product postAsyncRetrySucceeded(Product product) {
        return postAsyncRetrySucceededAsync(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product postAsyncRetrySucceeded() {
        final Product product = null;
        return postAsyncRetrySucceededAsync(product).block();
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
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<LROsPostAsyncNoRetrySucceededResponse> postAsyncNoRetrySucceededWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.postAsyncNoRetrySucceeded(this.client.getHost(), product, accept, context));
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
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> postAsyncNoRetrySucceededAsync(Product product) {
        return postAsyncNoRetrySucceededWithResponseAsync(product)
                .flatMap(
                        (LROsPostAsyncNoRetrySucceededResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Product> postAsyncNoRetrySucceededAsync() {
        final Product product = null;
        return postAsyncNoRetrySucceededWithResponseAsync(product)
                .flatMap(
                        (LROsPostAsyncNoRetrySucceededResponse res) -> {
                            if (res.getValue() != null) {
                                return Mono.just(res.getValue());
                            } else {
                                return Mono.empty();
                            }
                        });
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
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product postAsyncNoRetrySucceeded(Product product) {
        return postAsyncNoRetrySucceededAsync(product).block();
    }

    /**
     * Long running post request, service returns a 202 to the initial request, with an entity that contains
     * ProvisioningState=’Creating’. Poll the endpoint indicated in the Azure-AsyncOperation header for operation
     * status.
     *
     * @throws CloudErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Product postAsyncNoRetrySucceeded() {
        final Product product = null;
        return postAsyncNoRetrySucceededAsync(product).block();
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
    public Mono<LROsPostAsyncRetryFailedResponse> postAsyncRetryFailedWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.postAsyncRetryFailed(this.client.getHost(), product, accept, context));
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
    public Mono<Void> postAsyncRetryFailedAsync(Product product) {
        return postAsyncRetryFailedWithResponseAsync(product)
                .flatMap((LROsPostAsyncRetryFailedResponse res) -> Mono.empty());
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
    public Mono<Void> postAsyncRetryFailedAsync() {
        final Product product = null;
        return postAsyncRetryFailedWithResponseAsync(product)
                .flatMap((LROsPostAsyncRetryFailedResponse res) -> Mono.empty());
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
    public void postAsyncRetryFailed(Product product) {
        postAsyncRetryFailedAsync(product).block();
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
    public void postAsyncRetryFailed() {
        final Product product = null;
        postAsyncRetryFailedAsync(product).block();
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
    public Mono<LROsPostAsyncRetrycanceledResponse> postAsyncRetrycanceledWithResponseAsync(Product product) {
        if (this.client.getHost() == null) {
            return Mono.error(
                    new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (product != null) {
            product.validate();
        }
        final String accept = "application/json";
        return FluxUtil.withContext(
                context -> service.postAsyncRetrycanceled(this.client.getHost(), product, accept, context));
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
    public Mono<Void> postAsyncRetrycanceledAsync(Product product) {
        return postAsyncRetrycanceledWithResponseAsync(product)
                .flatMap((LROsPostAsyncRetrycanceledResponse res) -> Mono.empty());
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
    public Mono<Void> postAsyncRetrycanceledAsync() {
        final Product product = null;
        return postAsyncRetrycanceledWithResponseAsync(product)
                .flatMap((LROsPostAsyncRetrycanceledResponse res) -> Mono.empty());
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
    public void postAsyncRetrycanceled(Product product) {
        postAsyncRetrycanceledAsync(product).block();
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
    public void postAsyncRetrycanceled() {
        final Product product = null;
        postAsyncRetrycanceledAsync(product).block();
    }
}
