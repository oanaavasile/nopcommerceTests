package tests.dataProviders;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

    @DataProvider(name="shoesAttributes")
    public static Object[][] getShoesAttributes(){
        return new Object[][]{
                {"9","Blue"},
                {"10","Red"}
        };
    }
}
