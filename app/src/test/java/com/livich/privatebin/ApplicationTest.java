package com.livich.privatebin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.assertj.core.api.Java6Assertions.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ApplicationTest {

    @Test
    public void testStub() {
        assertThat(true);
    }


}