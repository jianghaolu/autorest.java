// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
//
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package fixtures.xml.models;

import com.azure.core.implementation.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data about a slideshow.
 */
@JacksonXmlRootElement(localName = "slideshow")
@Fluent
public final class Slideshow {
    /*
     * The title property.
     */
    @JacksonXmlProperty(localName = "title", isAttribute = true)
    private String title;

    /*
     * The date property.
     */
    @JacksonXmlProperty(localName = "date", isAttribute = true)
    private String date;

    /*
     * The author property.
     */
    @JacksonXmlProperty(localName = "author", isAttribute = true)
    private String author;

    /*
     * The slides property.
     */
    @JsonProperty("slide")
    private List<Slide> slides = new ArrayList<>();

    /**
     * Get the title property: The title property.
     *
     * @return the title value.
     */
    public String title() {
        return this.title;
    }

    /**
     * Set the title property: The title property.
     *
     * @param title the title value to set.
     * @return the Slideshow object itself.
     */
    public Slideshow title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Get the date property: The date property.
     *
     * @return the date value.
     */
    public String date() {
        return this.date;
    }

    /**
     * Set the date property: The date property.
     *
     * @param date the date value to set.
     * @return the Slideshow object itself.
     */
    public Slideshow date(String date) {
        this.date = date;
        return this;
    }

    /**
     * Get the author property: The author property.
     *
     * @return the author value.
     */
    public String author() {
        return this.author;
    }

    /**
     * Set the author property: The author property.
     *
     * @param author the author value to set.
     * @return the Slideshow object itself.
     */
    public Slideshow author(String author) {
        this.author = author;
        return this;
    }

    /**
     * Get the slides property: The slides property.
     *
     * @return the slides value.
     */
    public List<Slide> slides() {
        return this.slides;
    }

    /**
     * Set the slides property: The slides property.
     *
     * @param slides the slides value to set.
     * @return the Slideshow object itself.
     */
    public Slideshow slides(List<Slide> slides) {
        this.slides = slides;
        return this;
    }
}
