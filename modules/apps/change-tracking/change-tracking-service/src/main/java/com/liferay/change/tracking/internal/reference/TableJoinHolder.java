/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.internal.reference;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.petra.sql.dsl.query.WhereStep;
import com.liferay.petra.string.StringBundler;

import java.util.function.Function;

/**
 * @author Preston Crary
 */
public class TableJoinHolder {

	public static TableJoinHolder reverse(TableJoinHolder tableJoinHolder) {
		return new TableJoinHolder(
			tableJoinHolder.getChildPKColumn(),
			tableJoinHolder.getParentPKColumn(),
			tableJoinHolder.getMissingRequirementWhereStep(),
			tableJoinHolder.getMissingRequirementWherePredicate(),
			tableJoinHolder.getJoinFunction(), !tableJoinHolder.isReversed());
	}

	public TableJoinHolder(
		Column<?, Long> parentPKColumn, Column<?, Long> childPKColumn,
		WhereStep missingRequirementWhereStep,
		Predicate missingRequirementWherePredicate,
		Function<FromStep, JoinStep> joinFunction) {

		this(
			parentPKColumn, childPKColumn, missingRequirementWhereStep,
			missingRequirementWherePredicate, joinFunction, false);
	}

	public Column<?, Long> getChildPKColumn() {
		return _childPKColumn;
	}

	public Function<FromStep, JoinStep> getJoinFunction() {
		return _joinFunction;
	}

	public Predicate getMissingRequirementWherePredicate() {
		return _missingRequirementWherePredicate;
	}

	public WhereStep getMissingRequirementWhereStep() {
		return _missingRequirementWhereStep;
	}

	public Column<?, Long> getParentPKColumn() {
		return _parentPKColumn;
	}

	public boolean isReversed() {
		return _reversed;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{childPKColumn=", _childPKColumn, ", joinFunction=", _joinFunction,
			", missingParentWhereStep", _missingRequirementWhereStep,
			", _missingRequirementWherePredicate",
			_missingRequirementWherePredicate, ", parentPKColumn=",
			_parentPKColumn, ", reversed=", _reversed, "}");
	}

	private TableJoinHolder(
		Column<?, Long> parentPKColumn, Column<?, Long> childPKColumn,
		WhereStep missingRequirementWhereStep,
		Predicate missingRequirementWherePredicate,
		Function<FromStep, JoinStep> joinFunction, boolean reversed) {

		_parentPKColumn = parentPKColumn;
		_childPKColumn = childPKColumn;
		_missingRequirementWhereStep = missingRequirementWhereStep;
		_missingRequirementWherePredicate = missingRequirementWherePredicate;
		_joinFunction = joinFunction;
		_reversed = reversed;
	}

	private final Column<?, Long> _childPKColumn;
	private final Function<FromStep, JoinStep> _joinFunction;
	private final Predicate _missingRequirementWherePredicate;
	private final WhereStep _missingRequirementWhereStep;
	private final Column<?, Long> _parentPKColumn;
	private final boolean _reversed;

}