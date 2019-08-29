// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.requiredoptional.models;

import com.azure.core.implementation.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The ArrayOptionalWrapper model.
 */
@Fluent
public final class ArrayOptionalWrapper {
    /*
     * The value property.
     */
    @JsonProperty(value = "value")
    private List<String> value;

    /**
     * Get the value property: The value property.
     *
     * @return the value value.
     */
    public List<String> value() {
        return this.value;
    }

    /**
     * Set the value property: The value property.
     *
     * @param value the value value to set.
     * @return the ArrayOptionalWrapper object itself.
     */
    public ArrayOptionalWrapper value(List<String> value) {
        this.value = value;
        return this;
    }
}
