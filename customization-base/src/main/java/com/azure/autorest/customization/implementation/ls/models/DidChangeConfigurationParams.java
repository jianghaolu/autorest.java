package com.azure.autorest.customization.implementation.ls.models;

import java.util.Map;

public class DidChangeConfigurationParams {
    private Map<String, String> settings;

    public Map<String, String> getSettings() {
        return settings;
    }

    public void setSettings(Map<String, String> settings) {
        this.settings = settings;
    }
}
