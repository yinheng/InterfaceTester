package com.yinheng.interfacetester.data.model;

import com.yinheng.interfacetester.runner.HttpGetRequester;
import com.yinheng.interfacetester.runner.HttpPostRequester;
import com.yinheng.interfacetester.runner.TestCaseRequester;

public enum RequestType {

    GET {
        public TestCaseRequester newCaseRequester() {
            return new HttpGetRequester();
        }
    },

    POST {
        public TestCaseRequester newCaseRequester() {
            return new HttpPostRequester();
        }
    },

    DATABASE {
        public TestCaseRequester newCaseRequester() {
            return null;
        }
    };

   public abstract TestCaseRequester newCaseRequester();
}
