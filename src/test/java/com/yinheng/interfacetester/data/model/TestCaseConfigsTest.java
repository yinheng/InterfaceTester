package com.yinheng.interfacetester.data.model;

import org.testng.Assert;
import org.testng.annotations.Test;

public class TestCaseConfigsTest {

    @Test
    public void testHas() throws Exception {
        String prop = "foo=bar\n\n\nfoo2=23";
        TestCaseConfigs configs = new TestCaseConfigs(prop);
        Assert.assertTrue(configs.has("foo"));
        Assert.assertFalse(configs.has("bar"));
        Assert.assertTrue(configs.has("foo2"));
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testGetStringOrThrow() throws Exception {
        String prop = "foo=bar\n\n\nfoo2=23";
        TestCaseConfigs configs = new TestCaseConfigs(prop);
        configs.getStringOrThrow("foo3");
    }

    @Test
    public void testGetString1() throws Exception {
        String defValue = "Hello! world@#.=?";
        String prop = "foo:bar\n\n\nfoo2=23";
        TestCaseConfigs configs = new TestCaseConfigs(prop);
        Assert.assertEquals(configs.getString("foo", defValue), defValue);
        Assert.assertNull(configs.getString("foo"), defValue);
        Assert.assertNull(configs.getString("foo", null), defValue);
    }

    @Test
    public void testGetString() throws Exception {
        String propString = "name=tornaco\n" +
                "age=28\n" +
                "url=www.google.com";

        TestCaseConfigs testCaseConfigs = new TestCaseConfigs(propString);
        String name = testCaseConfigs.getString("name");
        String age = testCaseConfigs.getString("age");
        String url = testCaseConfigs.getString("url");
        Assert.assertEquals(name, "tornaco");
        Assert.assertEquals(age, "28");
        Assert.assertEquals(url, "www.google.com");

    }

    @Test
    public void testGetInt() throws Exception {
        String prop = "foo:bar\n\n\nfoo2=23";
        TestCaseConfigs configs = new TestCaseConfigs(prop);
        Assert.assertEquals(configs.getInt("foo2", 0), 23);
        Assert.assertEquals(configs.getInt("foo3", 10), 10);
    }

    @Test
    public void testGetLong() throws Exception {
        String prop = "foo:bar\n\n\nfoo2=23";
        TestCaseConfigs configs = new TestCaseConfigs(prop);
        Assert.assertEquals(configs.getLong("foo2", 0), 23);
        Assert.assertEquals(configs.getLong("foo3", 10), 10);
    }


}