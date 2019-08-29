// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.bodydate;

import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.http.rest.VoidResponse;
import com.azure.core.implementation.annotation.ReturnType;
import com.azure.core.implementation.annotation.ServiceMethod;
import java.time.LocalDate;
import reactor.core.publisher.Mono;

/**
 * An instance of this class provides access to all the operations defined in
 * Dates.
 */
public interface Dates {
    /**
     * Get null date value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the LocalDate object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    LocalDate getNull();

    /**
     * Get null date value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<LocalDate>> getNullWithRestResponseAsync();

    /**
     * Get null date value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<LocalDate> getNullAsync();

    /**
     * Get invalid date value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the LocalDate object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    LocalDate getInvalidDate();

    /**
     * Get invalid date value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<LocalDate>> getInvalidDateWithRestResponseAsync();

    /**
     * Get invalid date value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<LocalDate> getInvalidDateAsync();

    /**
     * Get overflow date value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the LocalDate object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    LocalDate getOverflowDate();

    /**
     * Get overflow date value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<LocalDate>> getOverflowDateWithRestResponseAsync();

    /**
     * Get overflow date value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<LocalDate> getOverflowDateAsync();

    /**
     * Get underflow date value.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the LocalDate object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    LocalDate getUnderflowDate();

    /**
     * Get underflow date value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<LocalDate>> getUnderflowDateWithRestResponseAsync();

    /**
     * Get underflow date value.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<LocalDate> getUnderflowDateAsync();

    /**
     * Put max date value 9999-12-31.
     *
     * @param dateBody the LocalDate value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void putMaxDate(LocalDate dateBody);

    /**
     * Put max date value 9999-12-31.
     *
     * @param dateBody the LocalDate value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<VoidResponse> putMaxDateWithRestResponseAsync(LocalDate dateBody);

    /**
     * Put max date value 9999-12-31.
     *
     * @param dateBody the LocalDate value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Void> putMaxDateAsync(LocalDate dateBody);

    /**
     * Get max date value 9999-12-31.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the LocalDate object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    LocalDate getMaxDate();

    /**
     * Get max date value 9999-12-31.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<LocalDate>> getMaxDateWithRestResponseAsync();

    /**
     * Get max date value 9999-12-31.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<LocalDate> getMaxDateAsync();

    /**
     * Put min date value 0000-01-01.
     *
     * @param dateBody the LocalDate value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void putMinDate(LocalDate dateBody);

    /**
     * Put min date value 0000-01-01.
     *
     * @param dateBody the LocalDate value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<VoidResponse> putMinDateWithRestResponseAsync(LocalDate dateBody);

    /**
     * Put min date value 0000-01-01.
     *
     * @param dateBody the LocalDate value.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Void> putMinDateAsync(LocalDate dateBody);

    /**
     * Get min date value 0000-01-01.
     *
     * @throws ErrorException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the LocalDate object if successful.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    LocalDate getMinDate();

    /**
     * Get min date value 0000-01-01.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<SimpleResponse<LocalDate>> getMinDateWithRestResponseAsync();

    /**
     * Get min date value 0000-01-01.
     *
     * @return a Mono which performs the network request upon subscription.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<LocalDate> getMinDateAsync();
}
