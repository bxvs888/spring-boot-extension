package com.livk.commons.util;

import com.livk.commons.util.ObjectUtils;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * ObjectUtilsTest
 * </p>
 *
 * @author livk
 * @date 2022/7/6
 */
class ObjectUtilsTest {

    @Test
    void testAllChecked() {
        boolean result = ObjectUtils.allChecked(StringUtils::hasText, "1", "");
        assertFalse(result);
    }

    @Test
    void testAnyChecked() {
        boolean result = ObjectUtils.anyChecked(StringUtils::hasText, "1", "");
        assertTrue(result);
    }
}
