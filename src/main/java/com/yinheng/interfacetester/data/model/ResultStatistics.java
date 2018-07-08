package com.yinheng.interfacetester.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ResultStatistics {
    private int success, fail;
}
