// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.azurespecials;

import com.azure.core.implementation.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Error model.
 */
@Fluent
public final class Error {
    /*
     * The status property.
     */
    @JsonProperty(value = "status")
    private Integer status;

    /*
     * The constantId property.
     */
    @JsonProperty(value = "constantId", required = true)
    private int constantId;

    /*
     * The message property.
     */
    @JsonProperty(value = "message")
    private String message;

    /**
     * Creates an instance of Error class.
     */
    public Error() {
        constantId = 1;
    }

    /**
     * Get the status property: The status property.
     *
     * @return the status value.
     */
    public Integer status() {
        return this.status;
    }

    /**
     * Set the status property: The status property.
     *
     * @param status the status value to set.
     * @return the Error object itself.
     */
    public Error status(Integer status) {
        this.status = status;
        return this;
    }

    /**
     * Get the constantId property: The constantId property.
     *
     * @return the constantId value.
     */
    public int constantId() {
        return this.constantId;
    }

    /**
     * Set the constantId property: The constantId property.
     *
     * @param constantId the constantId value to set.
     * @return the Error object itself.
     */
    public Error constantId(int constantId) {
        this.constantId = constantId;
        return this;
    }

    /**
     * Get the message property: The message property.
     *
     * @return the message value.
     */
    public String message() {
        return this.message;
    }

    /**
     * Set the message property: The message property.
     *
     * @param message the message value to set.
     * @return the Error object itself.
     */
    public Error message(String message) {
        this.message = message;
        return this;
    }
}
