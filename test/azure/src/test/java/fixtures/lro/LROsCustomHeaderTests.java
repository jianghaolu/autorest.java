package fixtures.lro;

import com.microsoft.azure.v2.AzureProxy;
import com.microsoft.azure.v2.serializer.AzureJacksonAdapter;
import com.microsoft.rest.v2.RestClient;
import com.microsoft.rest.v2.credentials.BasicAuthenticationCredentials;
import com.microsoft.rest.v2.http.HttpHeaders;
import com.microsoft.rest.v2.policy.AddHeadersPolicy;
import com.microsoft.rest.v2.policy.CredentialsPolicy;
import fixtures.lro.implementation.AutoRestLongRunningOperationTestServiceImpl;
import fixtures.lro.models.Product;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class LROsCustomHeaderTests {
    private static AutoRestLongRunningOperationTestServiceImpl client;

    @BeforeClass
    public static void setup() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-ms-client-request-id", "9C4D50EE-2D56-4CD3-8152-34347DC9F2B0");

        RestClient restClient = new RestClient.Builder()
                .withBaseUrl("http://localhost:3000")
                .withCredentialsPolicy(new CredentialsPolicy.Factory(new BasicAuthenticationCredentials(null, null)))
                .withSerializerAdapter(new AzureJacksonAdapter())
                .addRequestPolicy(new AddHeadersPolicy.Factory(headers))
                .build();
        AzureProxy.setDefaultDelayInMilliseconds(0);
        client = new AutoRestLongRunningOperationTestServiceImpl(restClient);
    }

    @Test
    public void putAsyncRetrySucceeded() throws Exception {
        Product product = new Product();
        product.withLocation("West US");
        Product response = client.lROsCustomHeaders().putAsyncRetrySucceeded(product);
        Assert.assertEquals("Succeeded", response.provisioningState());
    }

    @Test
    public void put201CreatingSucceeded200() throws Exception {
        Product product = new Product();
        product.withLocation("West US");
        Product response = client.lROsCustomHeaders().put201CreatingSucceeded200(product);
        Assert.assertEquals("Succeeded", response.provisioningState());
    }

    @Test
    public void post202Retry200() throws Exception {
        Product product = new Product();
        product.withLocation("West US");
        client.lROsCustomHeaders().post202Retry200(product);
    }

    @Test
    public void postAsyncRetrySucceeded() throws Exception {
        Product product = new Product();
        product.withLocation("West US");
        client.lROsCustomHeaders().postAsyncRetrySucceeded(product);
    }
}
