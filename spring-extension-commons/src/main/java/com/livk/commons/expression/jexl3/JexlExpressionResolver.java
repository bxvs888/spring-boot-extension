/*
 * Copyright 2021 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.livk.commons.expression.jexl3;

import com.livk.commons.expression.Context;
import com.livk.commons.expression.ConverterExpressionResolver;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Engine;

/**
 * 使用<a href="https://github.com/apache/commons-jexl">Apache Commons Jexl3</a>实现的表达式解析器
 *
 * @author livk
 */
public class JexlExpressionResolver extends ConverterExpressionResolver<JexlContext, JexlExpression> {

    private final JexlEngine engine = new Engine();

    @Override
    protected JexlExpression compile(String value) {
        return engine.createExpression(value);
    }

    @Override
    protected JexlContext transform(Context context) {
        return new MapContext(context);
    }

    @Override
    protected <T> T calculate(JexlExpression expression, JexlContext context, Class<T> returnType) {
        return returnType.cast(expression.evaluate(context));
    }
}
