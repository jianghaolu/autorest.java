#### Javagen

``` yaml
use:
  - $(this-folder)/../preprocessor
  - $(this-folder)/../postprocessor

pipeline:

# --- extension remodeler ---
  javagen:
    scope: java
    input: preprocessor
    output-artifact: java-files
  
  java/emitter:
    input: javagen
    scope: scope-javagen/emitter
    
  postprocessor:
    input: javagen
    output-artifact: java-files
  

scope-javagen/emitter:
    input-artifact: java-files
    output-uri-expr: $key

output-artifact: java-files
```
