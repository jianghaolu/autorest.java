/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package fixtures.header.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;

/**
 * Defines headers for responseDuration operation.
 */
public final class HeaderResponseDurationHeaders {
    /**
     * response with header values "P123DT22H14M12.011S".
     */
    @JsonProperty(value = "value")
    private Duration value;

    /**
     * Get the value value.
     *
     * @return the value value.
     */
    public Duration value() {
        return this.value;
    }

    /**
     * Set the value value.
     *
     * @param value the value value to set.
     * @return the HeaderResponseDurationHeaders object itself.
     */
    public HeaderResponseDurationHeaders withValue(Duration value) {
        this.value = value;
        return this;
    }
}