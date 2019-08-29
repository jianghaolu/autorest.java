package fixtures.modelflattening;

import fixtures.modelflattening.implementation.AutoRestResourceFlatteningTestServiceImpl;
import fixtures.modelflattening.models.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelFlatteningTests {
    private static AutoRestResourceFlatteningTestService client;

    @BeforeClass
    public static void setup() {
        client = new AutoRestResourceFlatteningTestServiceImpl();
    }

    @Test
    public void getArray() throws Exception {
        List<FlattenedProduct> result = client.getArray();
        Assert.assertEquals(3, result.size());
        // Resource 1
        Assert.assertEquals("1", result.get(0).id());
        Assert.assertEquals("OK", result.get(0).provisioningStateValues());
        Assert.assertEquals("Product1", result.get(0).pname());
        Assert.assertEquals("Flat", result.get(0).flattenedProductType());
        Assert.assertEquals("Building 44", result.get(0).location());
        Assert.assertEquals("Resource1", result.get(0).name());
        Assert.assertEquals("Succeeded", result.get(0).provisioningState());
        Assert.assertEquals("Microsoft.Web/sites", result.get(0).type());
        Assert.assertEquals("value1", result.get(0).tags().get("tag1"));
        Assert.assertEquals("value3", result.get(0).tags().get("tag2"));
        // Resource 2
        Assert.assertEquals("2", result.get(1).id());
        Assert.assertEquals("Resource2", result.get(1).name());
        Assert.assertEquals("Building 44", result.get(1).location());
        // Resource 3
        Assert.assertEquals("3", result.get(2).id());
        Assert.assertEquals("Resource3", result.get(2).name());
    }

    @Test
    public void putArray() throws Exception {
        List<Resource> body = new ArrayList<>();
        FlattenedProduct product = new FlattenedProduct();
        product.location("West US");
        product.tags(new HashMap<String, String>());
        product.tags().put("tag1", "value1");
        product.tags().put("tag2", "value3");
        body.add(product);
        FlattenedProduct product1 = new FlattenedProduct();
        product1.location("Building 44");
        body.add(product1);
        client.putArray(body);
    }

    @Test
    public void getDictionary() throws Exception {
        Map<String, FlattenedProduct> result = client.getDictionary();
        Assert.assertEquals(3, result.size());
        // Resource 1
        Assert.assertEquals("1", result.get("Product1").id());
        Assert.assertEquals("OK", result.get("Product1").provisioningStateValues());
        Assert.assertEquals("Product1", result.get("Product1").pname());
        Assert.assertEquals("Flat", result.get("Product1").flattenedProductType());
        Assert.assertEquals("Building 44", result.get("Product1").location());
        Assert.assertEquals("Resource1", result.get("Product1").name());
        Assert.assertEquals("Succeeded", result.get("Product1").provisioningState());
        Assert.assertEquals("Microsoft.Web/sites", result.get("Product1").type());
        Assert.assertEquals("value1", result.get("Product1").tags().get("tag1"));
        Assert.assertEquals("value3", result.get("Product1").tags().get("tag2"));
        // Resource 2
        Assert.assertEquals("2", result.get("Product2").id());
        Assert.assertEquals("Resource2", result.get("Product2").name());
        Assert.assertEquals("Building 44", result.get("Product2").location());
        // Resource 3
        Assert.assertEquals("3", result.get("Product3").id());
        Assert.assertEquals("Resource3", result.get("Product3").name());
    }

    @Test
    public void putDictionary() throws Exception {
        Map<String, FlattenedProduct> body = new HashMap<>();
        FlattenedProduct product = new FlattenedProduct();
        product.location("West US");
        product.tags(new HashMap<String, String>());
        product.tags().put("tag1", "value1");
        product.tags().put("tag2", "value3");
        product.pname("Product1");
        product.flattenedProductType("Flat");
        body.put("Resource1", product);
        FlattenedProduct product1 = new FlattenedProduct();
        product1.location("Building 44");
        product1.pname("Product2");
        product1.flattenedProductType("Flat");
        body.put("Resource2", product1);
        client.putDictionary(body);
    }

    @Test
    public void getResourceCollection() throws Exception {
        ResourceCollection resultResource = client.getResourceCollection();
        //Dictionaryofresources
        Assert.assertEquals(3, resultResource.dictionaryofresources().size());
        // Resource 1
        Assert.assertEquals("1", resultResource.dictionaryofresources().get("Product1").id());
        Assert.assertEquals("OK", resultResource.dictionaryofresources().get("Product1").provisioningStateValues());
        Assert.assertEquals("Product1", resultResource.dictionaryofresources().get("Product1").pname());
        Assert.assertEquals("Flat", resultResource.dictionaryofresources().get("Product1").flattenedProductType());
        Assert.assertEquals("Building 44", resultResource.dictionaryofresources().get("Product1").location());
        Assert.assertEquals("Resource1", resultResource.dictionaryofresources().get("Product1").name());
        Assert.assertEquals("Succeeded", resultResource.dictionaryofresources().get("Product1").provisioningState());
        Assert.assertEquals("Microsoft.Web/sites", resultResource.dictionaryofresources().get("Product1").type());
        Assert.assertEquals("value1", resultResource.dictionaryofresources().get("Product1").tags().get("tag1"));
        Assert.assertEquals("value3", resultResource.dictionaryofresources().get("Product1").tags().get("tag2"));
        // Resource 2
        Assert.assertEquals("2", resultResource.dictionaryofresources().get("Product2").id());
        Assert.assertEquals("Resource2", resultResource.dictionaryofresources().get("Product2").name());
        Assert.assertEquals("Building 44", resultResource.dictionaryofresources().get("Product2").location());
        // Resource 3
        Assert.assertEquals("3", resultResource.dictionaryofresources().get("Product3").id());
        Assert.assertEquals("Resource3", resultResource.dictionaryofresources().get("Product3").name());

        //Arrayofresources
        Assert.assertEquals(3, resultResource.arrayofresources().size());
        // Resource 1
        Assert.assertEquals("4", resultResource.arrayofresources().get(0).id());
        Assert.assertEquals("OK", resultResource.arrayofresources().get(0).provisioningStateValues());
        Assert.assertEquals("Product4", resultResource.arrayofresources().get(0).pname());
        Assert.assertEquals("Flat", resultResource.arrayofresources().get(0).flattenedProductType());
        Assert.assertEquals("Building 44", resultResource.arrayofresources().get(0).location());
        Assert.assertEquals("Resource4", resultResource.arrayofresources().get(0).name());
        Assert.assertEquals("Succeeded", resultResource.arrayofresources().get(0).provisioningState());
        Assert.assertEquals("Microsoft.Web/sites", resultResource.arrayofresources().get(0).type());
        Assert.assertEquals("value1", resultResource.arrayofresources().get(0).tags().get("tag1"));
        Assert.assertEquals("value3", resultResource.arrayofresources().get(0).tags().get("tag2"));
        // Resource 2
        Assert.assertEquals("5", resultResource.arrayofresources().get(1).id());
        Assert.assertEquals("Resource5", resultResource.arrayofresources().get(1).name());
        Assert.assertEquals("Building 44", resultResource.arrayofresources().get(1).location());
        // Resource 3
        Assert.assertEquals("6", resultResource.arrayofresources().get(2).id());
        Assert.assertEquals("Resource6", resultResource.arrayofresources().get(2).name());

        //productresource
        Assert.assertEquals("7", resultResource.productresource().id());
        Assert.assertEquals("Resource7", resultResource.productresource().name());
    }

    @Test
    public void putResourceCollection() throws Exception {
        Map<String, FlattenedProduct> resources = new HashMap<>();
        resources.put("Resource1", new FlattenedProduct());
        resources.get("Resource1").location("West US");
        resources.get("Resource1").pname("Product1");
        resources.get("Resource1").flattenedProductType("Flat");
        resources.get("Resource1").tags(new HashMap<String, String>());
        resources.get("Resource1").tags().put("tag1", "value1");
        resources.get("Resource1").tags().put("tag2", "value3");

        resources.put("Resource2", new FlattenedProduct());
        resources.get("Resource2").location("Building 44");
        resources.get("Resource2").pname("Product2");
        resources.get("Resource2").flattenedProductType("Flat");

        ResourceCollection complexObj = new ResourceCollection();
        complexObj.dictionaryofresources(resources);
        complexObj.arrayofresources(new ArrayList<FlattenedProduct>());
        complexObj.arrayofresources().add(resources.get("Resource1"));
        FlattenedProduct p1 = new FlattenedProduct();
        p1.location("East US");
        p1.pname("Product2");
        p1.flattenedProductType("Flat");
        complexObj.arrayofresources().add(p1);
        FlattenedProduct pr = new FlattenedProduct();
        pr.location("India");
        pr.pname("Azure");
        pr.flattenedProductType("Flat");
        complexObj.productresource(pr);

        client.putResourceCollection(complexObj);
    }

    @Test
    public void putSimpleProduct() throws Exception {
        SimpleProduct simpleProduct = new SimpleProduct();
        simpleProduct.description("product description");
        simpleProduct.productId("123");
        simpleProduct.maxProductDisplayName("max name");
        simpleProduct.capacity("Large");
        simpleProduct.odatavalue("http://foo");
        simpleProduct.genericValue("https://generic");

        SimpleProduct product = client.putSimpleProduct(simpleProduct);
        assertSimpleProductEquals(simpleProduct, product);
    }

    @Test
    public void postFlattenedSimpleProduct() throws Exception {
        SimpleProduct simpleProduct = new SimpleProduct();
        simpleProduct.description("product description");
        simpleProduct.productId("123");
        simpleProduct.maxProductDisplayName("max name");
        simpleProduct.capacity("Large");
        simpleProduct.odatavalue("http://foo");
        client.postFlattenedSimpleProduct("123", "max name", "product description", null, "http://foo");
    }

    @Test
    public void putSimpleProductWithGrouping() throws Exception {
        SimpleProduct simpleProduct = new SimpleProduct();
        simpleProduct.description("product description");
        simpleProduct.productId("123");
        simpleProduct.maxProductDisplayName("max name");
        simpleProduct.capacity("Large");
        simpleProduct.odatavalue("http://foo");

        FlattenParameterGroup flattenParameterGroup = new FlattenParameterGroup();
        flattenParameterGroup.description("product description");
        flattenParameterGroup.productId("123");
        flattenParameterGroup.maxProductDisplayName("max name");
        flattenParameterGroup.odatavalue("http://foo");
        flattenParameterGroup.name("groupproduct");

        SimpleProduct product = client.putSimpleProductWithGrouping(flattenParameterGroup);
        assertSimpleProductEquals(simpleProduct, product);
    }

    private void assertSimpleProductEquals(SimpleProduct expected, SimpleProduct actual) throws Exception {
        Assert.assertEquals(expected.productId(), actual.productId());
        Assert.assertEquals(expected.description(), actual.description());
        Assert.assertEquals(expected.capacity(), actual.capacity());
        Assert.assertEquals(expected.maxProductDisplayName(), actual.maxProductDisplayName());
        Assert.assertEquals(expected.odatavalue(), actual.odatavalue());
    }
}