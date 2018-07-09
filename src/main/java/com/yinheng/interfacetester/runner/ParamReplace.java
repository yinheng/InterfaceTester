package com.yinheng.interfacetester.runner;

import com.yinheng.interfacetester.data.model.TestCase;
import org.apache.logging.log4j.LogManager;

import java.util.List;
import java.util.Map;

/**
 * Created by 尹恒 on 2018/7/9.
 */
public class ParamReplace {

    public void replaceParam(List<TestCase> testCaseList, TestCase testCase) {
        if(testCase.getIndex() !=0) {
            String output = testCaseList.get(testCase.getIndex()-1).getOutput();
            if(output != null) {
                Map<String, String> outputMap = testCaseList.get(testCase.getIndex()-1).getOutputMap();
                for(String key: outputMap.keySet()) {
                    String replaceStr = "\"$<" + key + ">\"";
                    String param = testCase.getParam();
                    if(param.contains(replaceStr)) {
                        String newParam = param.replace(replaceStr, outputMap.get(key));
                        testCase.setParam(newParam);
                        LogManager.getLogger().debug("new param: " + newParam);
                    }
                }

            }
        }

    }
}
