/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
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
 */

package com.livk.core.mybatisplugins.inject;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.livk.commons.util.DateUtils;
import com.livk.core.mybatisplugins.inject.annotation.SqlFunction;
import com.livk.core.mybatisplugins.inject.enums.FunctionType;
import com.livk.core.mybatisplugins.inject.enums.SqlFill;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * User
 * </p>
 *
 * @author livk
 */
@Data
class User implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String username;

	@JsonFormat(pattern = DateUtils.YMD_HMS, timezone = "GMT+8")
	@SqlFunction(fill = SqlFill.INSERT, time = FunctionType.DATE)
	private Date insertTime;

	@JsonFormat(pattern = DateUtils.YMD_HMS, timezone = "GMT+8")
	@SqlFunction(fill = SqlFill.INSERT_UPDATE, time = FunctionType.DATE)
	private Date updateTime;

}