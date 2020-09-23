package com.azure.autorest.postprocessor.ls.models;

public class WillSaveTextDocumentParams {
    private TextDocumentIdentifier textDocument;

    public TextDocumentIdentifier getTextDocument() {
        return textDocument;
    }

    public void setTextDocument(TextDocumentIdentifier textDocument) {
        this.textDocument = textDocument;
    }

    public int getReason() {
        return 1; // Manual
    }
}
