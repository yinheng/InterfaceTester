package com.yinheng.interfacetester.data.model;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import com.yinheng.interfacetester.BuildConfig;
import com.yinheng.interfacetester.util.TextUtils;
import org.apache.logging.log4j.LogManager;
import org.testng.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TestCaseConfigs {
    private final static String EXPECTED_TOKEN = "=";

    private String rawConfigString;

    private Map<String, String> configMap;

    public TestCaseConfigs(String rawConfigString) {
        this.rawConfigString = rawConfigString;
    }

    public TestCaseConfigs(File configFile) throws IOException {
        Assert.assertNotNull(configFile);
        Assert.assertTrue(configFile.exists());
        StringBuilder content = new StringBuilder();
        Files.asCharSource(configFile, Charset.defaultCharset())
                .copyTo(content);
        this.rawConfigString = content.toString();

    }

    private boolean hasAnyConfigInput() {
        return !TextUtils.isEmpty(rawConfigString);
    }

    private synchronized void initLines() throws IOException {
        if (!hasAnyConfigInput() || configMap != null) {
            return;
        }

        configMap = new HashMap<String, String>();

        // Read lines from input.
        BufferedReader br = new BufferedReader(new StringReader(rawConfigString));
        String line;
        while ((line = br.readLine()) != null) {
            if (BuildConfig.DEBUG) {
                LogManager.getLogger().debug("TestCaseConfigs read line: " + line);
            }

            // Split.
            if (!TextUtils.isEmpty(line)) {

                String[] pair = line.split(EXPECTED_TOKEN, 2); // Limit to 2, key and value.
                if (BuildConfig.DEBUG) {
                    LogManager.getLogger().debug("TestCaseConfigs read pair: " + Arrays.toString(pair));
                }

                int count = pair.length;
                if (count == 2) { // Key and value.
                    // Found valid token.
                    String key = pair[0];
                    String value = pair[1];
                    boolean isAValidConfig =
                            key != null && value != null
                                    && !TextUtils.isEmpty(key.trim())
                                    && !TextUtils.isEmpty(value.trim());
                    if (!isAValidConfig) {
                        LogManager.getLogger().warn("Found invalid config line: " + line);
                        continue;
                    }
                    // Save.
                    configMap.put(key.trim(), value.trim());

                    // No need to check another token.

                    if (BuildConfig.DEBUG) {
                        LogManager.getLogger().debug(String.format("TestCaseConfigs put: %s %s", key, value));
                    }
                }
            }
        }
    }

    public boolean has(String key) throws IOException {
        Assert.assertNotNull(key, "Key is null");
        initLines();
        return configMap.containsKey(key);
    }

    public String getStringOrThrow(String key) throws IOException {
        return Preconditions.checkNotNull(getString(key), "Missing config for key: " + key);
    }

    public String getString(String key) throws IOException {
        return getString(key, null);
    }

    public String getString(String key, String defValue) throws IOException {
        Assert.assertNotNull(key, "Key is null");
        initLines();
        if (!has(key)) {
            return defValue;
        }
        return configMap.get(key);
    }

    public int getInt(String key, int defValue) throws IOException, BadConfigsException {
        Assert.assertNotNull(key, "Key is null");
        initLines();
        if (!has(key)) {
            return defValue;
        }
        try {
            return Integer.parseInt(configMap.get(key));
        } catch (NumberFormatException e) {
            throw new BadConfigsException("Bad config, check your config for this test", e);
        }
    }

    public long getLong(String key, long defValue) throws IOException, BadConfigsException {
        Assert.assertNotNull(key, "Key is null");
        initLines();
        if (!has(key)) {
            return defValue;
        }
        try {
            return Long.parseLong(configMap.get(key));
        } catch (NumberFormatException e) {
            throw new BadConfigsException("Bad config, check your config for this test", e);
        }
    }

    public static class BadConfigsException extends Exception {
        public BadConfigsException(String message) {
            super(message);
        }

        public BadConfigsException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
