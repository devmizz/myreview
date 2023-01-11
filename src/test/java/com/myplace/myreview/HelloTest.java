package com.myplace.myreview;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class HelloTest {

    @Test
    public void hello() throws Exception {
        Assertions.assertThat(1).isEqualTo(1);
    }
}
