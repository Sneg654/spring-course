package com.epam.beans.aspects.mocks;

import com.epam.beans.aspects.CounterAspect;

public class CountAspectMock extends CounterAspect {

    public static void cleanup() {
        accessByNameCounter.clear();
        getPriceByNameCounter.clear();
        bookByNameCounter.clear();
    }
}
