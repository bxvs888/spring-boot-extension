package com.livk.autoconfigure.curator;

import com.livk.commons.function.Customizer;
import org.apache.curator.framework.CuratorFrameworkFactory;

/**
 * <p>
 * CuratorFrameworkBuilder
 * </p>
 *
 * @author livk
 */
@FunctionalInterface
public interface CuratorFrameworkBuilderCustomizer extends Customizer<CuratorFrameworkFactory.Builder> {
}
