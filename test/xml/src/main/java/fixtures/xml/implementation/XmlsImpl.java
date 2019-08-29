// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.xml.implementation;

import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.http.rest.VoidResponse;
import com.azure.core.implementation.RestProxy;
import com.azure.core.implementation.annotation.BodyParam;
import com.azure.core.implementation.annotation.ExpectedResponses;
import com.azure.core.implementation.annotation.Get;
import com.azure.core.implementation.annotation.Host;
import com.azure.core.implementation.annotation.Put;
import com.azure.core.implementation.annotation.ReturnType;
import com.azure.core.implementation.annotation.ServiceInterface;
import com.azure.core.implementation.annotation.ServiceMethod;
import com.azure.core.implementation.annotation.UnexpectedResponseExceptionType;
import fixtures.xml.Xmls;
import fixtures.xml.models.AppleBarrel;
import fixtures.xml.models.Banana;
import fixtures.xml.models.ErrorException;
import fixtures.xml.models.Slideshow;
import fixtures.xml.models.XmlsGetHeadersResponse;
import java.util.List;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * Xmls.
 */
public final class XmlsImpl implements Xmls {
    /**
     * The proxy service used to perform REST calls.
     */
    private XmlsService service;

    /**
     * The service client containing this operation class.
     */
    private AutoRestSwaggerBATXMLServiceImpl client;

    /**
     * Initializes an instance of XmlsImpl.
     *
     * @param client the instance of the service client containing this operation class.
     */
    public XmlsImpl(AutoRestSwaggerBATXMLServiceImpl client) {
        this.service = RestProxy.create(XmlsService.class, client.getHttpPipeline());
        this.client = client;
    }

    /**
     * The interface defining all the services for
     * AutoRestSwaggerBATXMLServiceXmls to be used by the proxy service to
     * perform REST calls.
     */
    @Host("http://localhost")
    @ServiceInterface(name = "AutoRestSwaggerBATXMLServiceXmls")
    private interface XmlsService {
        @Get("xml/simple")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<SimpleResponse<Slideshow>> getSimple();

        @Put("xml/simple")
        @ExpectedResponses({201})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> putSimple(@BodyParam("application/xml; charset=utf-8") Slideshow wrappedLists);

        @Get("xml/wrapped-lists")
        @ExpectedResponses({200})
        Mono<SimpleResponse<AppleBarrel>> getWrappedLists();

        @Put("xml/wrapped-lists")
        @ExpectedResponses({201})
        @UnexpectedResponseExceptionType(ErrorException.class)
        Mono<VoidResponse> putWrappedLists(@BodyParam("application/xml; charset=utf-8") AppleBarrel wrappedLists);

        @Get("xml/headers")
        @ExpectedResponses({200})
        Mono<XmlsGetHeadersResponse> getHeaders();

        @Get("xml/empty-list")
        @ExpectedResponses({200})
        Mono<SimpleResponse<Slideshow>> getEmptyList();

        @Get("xml/empty-wrapped-lists")
        @ExpectedResponses({200})
        Mono<SimpleResponse<AppleBarrel>> getEmptyWrappedLists();

        @Get("xml/root-list")
        @ExpectedResponses({200})
        Mono<SimpleResponse<List<Banana>>> getRootList();

        @Put("xml/root-list")
        @ExpectedResponses({201})
        Mono<VoidResponse> putRootList(@BodyParam("application/xml; charset=utf-8") BananasWrapper bananas);

        @Get("xml/empty-root-list")
        @ExpectedResponses({200})
        Mono<SimpleResponse<List<Banana>>> getEmptyRootList();

        @Put("xml/empty-root-list")
        @ExpectedResponses({201})
        Mono<VoidResponse> putEmptyRootList(@BodyParam("application/xml; charset=utf-8") BananasWrapper bananas);

        @Get("xml/empty-child-element")
        @ExpectedResponses({200})
        Mono<SimpleResponse<Banana>> getEmptyChildElement();

        @Put("xml/empty-child-element")
        @ExpectedResponses({201})
        Mono<VoidResponse> putEmptyChildElement(@BodyParam("application/xml; charset=utf-8") Banana banana);
    }

    /**
     * Get a simple XML document.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Slideshow>> getSimpleWithRestResponseAsync() {
        return service.getSimple();
    }

    /**
     * Get a simple XML document.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Slideshow> getSimpleAsync() {
        return getSimpleWithRestResponseAsync()
            .flatMap((SimpleResponse<Slideshow> res) -> Mono.just(res.value()));
    }

    /**
     * Put a simple XML document.
     *
     * @param wrappedLists the Slideshow value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> putSimpleWithRestResponseAsync(Slideshow wrappedLists) {
        return service.putSimple(wrappedLists);
    }

    /**
     * Put a simple XML document.
     *
     * @param wrappedLists the Slideshow value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> putSimpleAsync(Slideshow wrappedLists) {
        return putSimpleWithRestResponseAsync(wrappedLists)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get an XML document with multiple wrapped lists.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<AppleBarrel>> getWrappedListsWithRestResponseAsync() {
        return service.getWrappedLists();
    }

    /**
     * Get an XML document with multiple wrapped lists.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<AppleBarrel> getWrappedListsAsync() {
        return getWrappedListsWithRestResponseAsync()
            .flatMap((SimpleResponse<AppleBarrel> res) -> Mono.just(res.value()));
    }

    /**
     * Put an XML document with multiple wrapped lists.
     *
     * @param wrappedLists the AppleBarrel value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> putWrappedListsWithRestResponseAsync(AppleBarrel wrappedLists) {
        return service.putWrappedLists(wrappedLists);
    }

    /**
     * Put an XML document with multiple wrapped lists.
     *
     * @param wrappedLists the AppleBarrel value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> putWrappedListsAsync(AppleBarrel wrappedLists) {
        return putWrappedListsWithRestResponseAsync(wrappedLists)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Get strongly-typed response headers.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<XmlsGetHeadersResponse> getHeadersWithRestResponseAsync() {
        return service.getHeaders();
    }

    /**
     * Get strongly-typed response headers.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> getHeadersAsync() {
        return getHeadersWithRestResponseAsync()
            .flatMap((XmlsGetHeadersResponse res) -> Mono.empty());
    }

    /**
     * Get an empty list.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Slideshow>> getEmptyListWithRestResponseAsync() {
        return service.getEmptyList();
    }

    /**
     * Get an empty list.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Slideshow> getEmptyListAsync() {
        return getEmptyListWithRestResponseAsync()
            .flatMap((SimpleResponse<Slideshow> res) -> Mono.just(res.value()));
    }

    /**
     * Gets some empty wrapped lists.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<AppleBarrel>> getEmptyWrappedListsWithRestResponseAsync() {
        return service.getEmptyWrappedLists();
    }

    /**
     * Gets some empty wrapped lists.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<AppleBarrel> getEmptyWrappedListsAsync() {
        return getEmptyWrappedListsWithRestResponseAsync()
            .flatMap((SimpleResponse<AppleBarrel> res) -> Mono.just(res.value()));
    }

    /**
     * Gets a list as the root element.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<List<Banana>>> getRootListWithRestResponseAsync() {
        return service.getRootList();
    }

    /**
     * Gets a list as the root element.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<Banana>> getRootListAsync() {
        return getRootListWithRestResponseAsync()
            .flatMap((SimpleResponse<List<Banana>> res) -> Mono.just(res.value()));
    }

    /**
     * Puts a list as the root element.
     *
     * @param bananas the List&lt;Banana&gt; value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> putRootListWithRestResponseAsync(List<Banana> bananas) {
        BananasWrapper bananasConverted = new BananasWrapper(bananas);
        return service.putRootList(bananasConverted);
    }

    /**
     * Puts a list as the root element.
     *
     * @param bananas the List&lt;Banana&gt; value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> putRootListAsync(List<Banana> bananas) {
        return putRootListWithRestResponseAsync(bananas)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Gets an empty list as the root element.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<List<Banana>>> getEmptyRootListWithRestResponseAsync() {
        return service.getEmptyRootList();
    }

    /**
     * Gets an empty list as the root element.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<List<Banana>> getEmptyRootListAsync() {
        return getEmptyRootListWithRestResponseAsync()
            .flatMap((SimpleResponse<List<Banana>> res) -> Mono.just(res.value()));
    }

    /**
     * Puts an empty list as the root element.
     *
     * @param bananas the List&lt;Banana&gt; value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> putEmptyRootListWithRestResponseAsync(List<Banana> bananas) {
        BananasWrapper bananasConverted = new BananasWrapper(bananas);
        return service.putEmptyRootList(bananasConverted);
    }

    /**
     * Puts an empty list as the root element.
     *
     * @param bananas the List&lt;Banana&gt; value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> putEmptyRootListAsync(List<Banana> bananas) {
        return putEmptyRootListWithRestResponseAsync(bananas)
            .flatMap((VoidResponse res) -> Mono.empty());
    }

    /**
     * Gets an XML document with an empty child element.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<Banana>> getEmptyChildElementWithRestResponseAsync() {
        return service.getEmptyChildElement();
    }

    /**
     * Gets an XML document with an empty child element.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Banana> getEmptyChildElementAsync() {
        return getEmptyChildElementWithRestResponseAsync()
            .flatMap((SimpleResponse<Banana> res) -> Mono.just(res.value()));
    }

    /**
     * Puts a value with an empty child element.
     *
     * @param banana the Banana value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<VoidResponse> putEmptyChildElementWithRestResponseAsync(Banana banana) {
        return service.putEmptyChildElement(banana);
    }

    /**
     * Puts a value with an empty child element.
     *
     * @param banana the Banana value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> putEmptyChildElementAsync(Banana banana) {
        return putEmptyChildElementWithRestResponseAsync(banana)
            .flatMap((VoidResponse res) -> Mono.empty());
    }
}
