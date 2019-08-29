// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.validation.models;

import com.azure.core.implementation.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * The product documentation.
 */
@Fluent
public final class Product {
    /*
     * Non required array of unique items from 0 to 6 elements.
     */
    @JsonProperty(value = "display_names")
    private List<String> displayNames;

    /*
     * Non required int betwen 0 and 100 exclusive.
     */
    @JsonProperty(value = "capacity")
    private Integer capacity;

    /*
     * Image URL representing the product.
     */
    @JsonProperty(value = "image")
    private String image;

    /*
     * The child property.
     */
    @JsonProperty(value = "child", required = true)
    private ChildProduct child;

    /*
     * The constChild property.
     */
    @JsonProperty(value = "constChild", required = true)
    private ConstantProduct constChild;

    /*
     * Constant int
     */
    @JsonProperty(value = "constInt", required = true)
    private int constInt;

    /*
     * Constant string
     */
    @JsonProperty(value = "constString", required = true)
    private String constString;

    /*
     * Constant string as Enum. Possible values include:
     * 'constant_string_as_enum'
     */
    @JsonProperty(value = "constStringAsEnum")
    private EnumConst constStringAsEnum;

    /**
     * Creates an instance of Product class.
     */
    public Product() {
        constChild = new ConstantProduct();
        constInt = 0;
        constString = "constant";
    }

    /**
     * Get the displayNames property: Non required array of unique items from 0
     * to 6 elements.
     *
     * @return the displayNames value.
     */
    public List<String> displayNames() {
        return this.displayNames;
    }

    /**
     * Set the displayNames property: Non required array of unique items from 0
     * to 6 elements.
     *
     * @param displayNames the displayNames value to set.
     * @return the Product object itself.
     */
    public Product displayNames(List<String> displayNames) {
        this.displayNames = displayNames;
        return this;
    }

    /**
     * Get the capacity property: Non required int betwen 0 and 100 exclusive.
     *
     * @return the capacity value.
     */
    public Integer capacity() {
        return this.capacity;
    }

    /**
     * Set the capacity property: Non required int betwen 0 and 100 exclusive.
     *
     * @param capacity the capacity value to set.
     * @return the Product object itself.
     */
    public Product capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    /**
     * Get the image property: Image URL representing the product.
     *
     * @return the image value.
     */
    public String image() {
        return this.image;
    }

    /**
     * Set the image property: Image URL representing the product.
     *
     * @param image the image value to set.
     * @return the Product object itself.
     */
    public Product image(String image) {
        this.image = image;
        return this;
    }

    /**
     * Get the child property: The child property.
     *
     * @return the child value.
     */
    public ChildProduct child() {
        return this.child;
    }

    /**
     * Set the child property: The child property.
     *
     * @param child the child value to set.
     * @return the Product object itself.
     */
    public Product child(ChildProduct child) {
        this.child = child;
        return this;
    }

    /**
     * Get the constChild property: The constChild property.
     *
     * @return the constChild value.
     */
    public ConstantProduct constChild() {
        return this.constChild;
    }

    /**
     * Set the constChild property: The constChild property.
     *
     * @param constChild the constChild value to set.
     * @return the Product object itself.
     */
    public Product constChild(ConstantProduct constChild) {
        this.constChild = constChild;
        return this;
    }

    /**
     * Get the constInt property: Constant int.
     *
     * @return the constInt value.
     */
    public int constInt() {
        return this.constInt;
    }

    /**
     * Set the constInt property: Constant int.
     *
     * @param constInt the constInt value to set.
     * @return the Product object itself.
     */
    public Product constInt(int constInt) {
        this.constInt = constInt;
        return this;
    }

    /**
     * Get the constString property: Constant string.
     *
     * @return the constString value.
     */
    public String constString() {
        return this.constString;
    }

    /**
     * Set the constString property: Constant string.
     *
     * @param constString the constString value to set.
     * @return the Product object itself.
     */
    public Product constString(String constString) {
        this.constString = constString;
        return this;
    }

    /**
     * Get the constStringAsEnum property: Constant string as Enum. Possible
     * values include: 'constant_string_as_enum'.
     *
     * @return the constStringAsEnum value.
     */
    public EnumConst constStringAsEnum() {
        return this.constStringAsEnum;
    }

    /**
     * Set the constStringAsEnum property: Constant string as Enum. Possible
     * values include: 'constant_string_as_enum'.
     *
     * @param constStringAsEnum the constStringAsEnum value to set.
     * @return the Product object itself.
     */
    public Product constStringAsEnum(EnumConst constStringAsEnum) {
        this.constStringAsEnum = constStringAsEnum;
        return this;
    }
}
