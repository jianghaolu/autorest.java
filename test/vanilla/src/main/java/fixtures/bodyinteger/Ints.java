// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.bodyinteger;

import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.http.rest.VoidResponse;
import com.azure.core.implementation.annotation.ReturnType;
import com.azure.core.implementation.annotation.ServiceMethod;
import java.time.OffsetDateTime;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * Ints.
 */
public interface Ints {
    /**
     * Get null Int value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the int object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    int getNull();

    /**
     * Get null Int value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<Integer>> getNullWithRestResponseAsync();

    /**
     * Get null Int value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Integer> getNullAsync();

    /**
     * Get invalid Int value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the int object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    int getInvalid();

    /**
     * Get invalid Int value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<Integer>> getInvalidWithRestResponseAsync();

    /**
     * Get invalid Int value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Integer> getInvalidAsync();

    /**
     * Get overflow Int32 value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the int object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    int getOverflowInt32();

    /**
     * Get overflow Int32 value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<Integer>> getOverflowInt32WithRestResponseAsync();

    /**
     * Get overflow Int32 value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Integer> getOverflowInt32Async();

    /**
     * Get underflow Int32 value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the int object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    int getUnderflowInt32();

    /**
     * Get underflow Int32 value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<Integer>> getUnderflowInt32WithRestResponseAsync();

    /**
     * Get underflow Int32 value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Integer> getUnderflowInt32Async();

    /**
     * Get overflow Int64 value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the long object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    long getOverflowInt64();

    /**
     * Get overflow Int64 value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<Long>> getOverflowInt64WithRestResponseAsync();

    /**
     * Get overflow Int64 value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Long> getOverflowInt64Async();

    /**
     * Get underflow Int64 value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the long object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    long getUnderflowInt64();

    /**
     * Get underflow Int64 value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<Long>> getUnderflowInt64WithRestResponseAsync();

    /**
     * Get underflow Int64 value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Long> getUnderflowInt64Async();

    /**
     * Put max int32 value.
     *
     * @param intBody the int value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void putMax32(int intBody);

    /**
     * Put max int32 value.
     *
     * @param intBody the int value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<VoidResponse> putMax32WithRestResponseAsync(int intBody);

    /**
     * Put max int32 value.
     *
     * @param intBody the int value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Void> putMax32Async(int intBody);

    /**
     * Put max int64 value.
     *
     * @param intBody the long value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void putMax64(long intBody);

    /**
     * Put max int64 value.
     *
     * @param intBody the long value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<VoidResponse> putMax64WithRestResponseAsync(long intBody);

    /**
     * Put max int64 value.
     *
     * @param intBody the long value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Void> putMax64Async(long intBody);

    /**
     * Put min int32 value.
     *
     * @param intBody the int value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void putMin32(int intBody);

    /**
     * Put min int32 value.
     *
     * @param intBody the int value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<VoidResponse> putMin32WithRestResponseAsync(int intBody);

    /**
     * Put min int32 value.
     *
     * @param intBody the int value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Void> putMin32Async(int intBody);

    /**
     * Put min int64 value.
     *
     * @param intBody the long value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void putMin64(long intBody);

    /**
     * Put min int64 value.
     *
     * @param intBody the long value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<VoidResponse> putMin64WithRestResponseAsync(long intBody);

    /**
     * Put min int64 value.
     *
     * @param intBody the long value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Void> putMin64Async(long intBody);

    /**
     * Get datetime encoded as Unix time value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the OffsetDateTime object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    OffsetDateTime getUnixTime();

    /**
     * Get datetime encoded as Unix time value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<OffsetDateTime>> getUnixTimeWithRestResponseAsync();

    /**
     * Get datetime encoded as Unix time value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<OffsetDateTime> getUnixTimeAsync();

    /**
     * Put datetime encoded as Unix time.
     *
     * @param intBody the OffsetDateTime value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void putUnixTimeDate(OffsetDateTime intBody);

    /**
     * Put datetime encoded as Unix time.
     *
     * @param intBody the OffsetDateTime value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<VoidResponse> putUnixTimeDateWithRestResponseAsync(OffsetDateTime intBody);

    /**
     * Put datetime encoded as Unix time.
     *
     * @param intBody the OffsetDateTime value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Void> putUnixTimeDateAsync(OffsetDateTime intBody);

    /**
     * Get invalid Unix time value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the OffsetDateTime object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    OffsetDateTime getInvalidUnixTime();

    /**
     * Get invalid Unix time value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<OffsetDateTime>> getInvalidUnixTimeWithRestResponseAsync();

    /**
     * Get invalid Unix time value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<OffsetDateTime> getInvalidUnixTimeAsync();

    /**
     * Get null Unix time value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the OffsetDateTime object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    OffsetDateTime getNullUnixTime();

    /**
     * Get null Unix time value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<OffsetDateTime>> getNullUnixTimeWithRestResponseAsync();

    /**
     * Get null Unix time value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<OffsetDateTime> getNullUnixTimeAsync();
}
