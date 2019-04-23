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

package com.liferay.talend.service;

import com.liferay.talend.data.store.GenericDataStore;
import com.liferay.talend.dataset.InputDataSet;
import com.liferay.talend.http.client.exception.MalformedURLException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.JsonObject;

import org.talend.sdk.component.api.record.Schema;
import org.talend.sdk.component.api.service.Service;
import org.talend.sdk.component.api.service.record.RecordBuilderFactory;

/**
 * @author Igor Beslic
 * @author Zoltán Takács
 * @author Matija Petanjek
 */
@Service
public class LiferayService {

	public Schema getEndpointTalendSchema(
		InputDataSet inputDataSet, RecordBuilderFactory recordBuilderFactory) {

		String endpoint = inputDataSet.getEndpoint();

		JsonObject openAPISpecification = _getOpenAPISpecification(
			inputDataSet);

		Map<String, String> pathResponseEntities = _mapKeysToPatternEvaluations(
			_GET_METHOD_RESPONSE_PATTERN,
			openAPISpecification.getJsonObject("paths"));

		String entityName = pathResponseEntities.get(endpoint);

		JsonObject components = openAPISpecification.getJsonObject(
			"components");

		JsonObject schemas = components.getJsonObject("schemas");

		JsonObject schema = schemas.getJsonObject(entityName);

		JsonObject properties = schema.getJsonObject("properties");

		JsonObject items = properties.getJsonObject("items");

		items = items.getJsonObject("items");

		String ref = items.getString("$ref");

		ref = _stripPath(ref);

		schema = schemas.getJsonObject(ref);

		properties = schema.getJsonObject("properties");

		return _talendService.getTalendSchema(
			properties, schema.getJsonArray("required"), recordBuilderFactory);
	}

	public List<String> getPageableEndpoints(InputDataSet inputDataSet) {
		JsonObject openAPISpecification = _getOpenAPISpecification(
			inputDataSet);

		if (openAPISpecification == null) {
			return Collections.emptyList();
		}

		return getPageableEndpoints(openAPISpecification);
	}

	public boolean isValidEndpointURL(String endpointURL) {
		Matcher serverURLMatcher = _serverURLPattern.matcher(endpointURL);

		if (serverURLMatcher.matches()) {
			return true;
		}

		return false;
	}

	protected List<String> getPageableEndpoints(JsonObject responseJsonObject) {
		Map<String, String> pathResponseEntities = _mapKeysToPatternEvaluations(
			_GET_METHOD_RESPONSE_PATTERN,
			responseJsonObject.getJsonObject("paths"));

		return _filterPageableEndpoints(
			pathResponseEntities, responseJsonObject);
	}

	/**
	 * Gets string value of json node pointed by given <code>pattern</code>.
	 *
	 * Method recursively resolves value searched by given pattern.
	 *
	 * <code>pattern</code> must match key1>key2>...>keyN syntax where key is
	 * expected key value in given json structure.
	 *
	 * @param  pattern
	 * @param  jsonObject
	 * @return keyN string value of (N-1)th <code>jsonObject</code> if keyN is
	 *         reachable through given <code>pattern</code>, <code>null</code>
	 *         otherwise
	 */
	private String _evaluatePattern(String pattern, JsonObject jsonObject) {
		int delimiterIdx = pattern.indexOf(">");

		if (delimiterIdx == -1) {
			if (jsonObject.containsKey(pattern)) {
				return jsonObject.getString(pattern);
			}

			return null;
		}

		String substring = pattern.substring(0, delimiterIdx);

		if (!jsonObject.containsKey(substring)) {
			return null;
		}

		return _evaluatePattern(
			pattern.substring(delimiterIdx + 1),
			jsonObject.getJsonObject(substring));
	}

	private String _extractEndpoint(String endpointURL) {
		Matcher serverURLMatcher = _serverURLPattern.matcher(endpointURL);

		if (!serverURLMatcher.matches()) {
			throw new MalformedURLException(
				"Unable to extract Open API endpoint from URL " + endpointURL);
		}

		String serverInstanceURL = serverURLMatcher.group(1);

		String endpoint = endpointURL.substring(serverInstanceURL.length());

		String endpointExtension = serverURLMatcher.group(6);

		if (endpointExtension.equals("yaml")) {
			endpoint = endpoint.replace(".yaml", ".json");
		}

		return endpoint;
	}

	private List<String> _filterPageableEndpoints(
		Map<String, String> patternEvaluations, JsonObject openApiJsonObject) {

		List<String> pageableEndpoints = new ArrayList<>();

		patternEvaluations.forEach(
			(enpoint, entity) -> {
				String typeValue = _evaluatePattern(
					String.format(_SCHEMA_PROPERTY_ITEMS_TYPE_PATTERN, entity),
					openApiJsonObject);

				if (!"array".equals(typeValue)) {
					return;
				}

				typeValue = _evaluatePattern(
					String.format(_SCHEMA_PROPERTY_PAGE_TYPE_PATTERN, entity),
					openApiJsonObject);

				if (!"integer".equals(typeValue)) {
					return;
				}

				pageableEndpoints.add(enpoint);
			});

		Collections.sort(pageableEndpoints);

		return pageableEndpoints;
	}

	private JsonObject _getOpenAPISpecification(InputDataSet inputDataSet) {
		GenericDataStore genericDataStore = inputDataSet.getGenericDataStore();

		URL openAPISpecURL = genericDataStore.getOpenAPISpecURL();

		if (!isValidEndpointURL(openAPISpecURL.toString())) {
			throw new MalformedURLException(
				"Provided URL does not match pattern " +
					_serverURLPattern.pattern());
		}

		inputDataSet.setEndpoint(_extractEndpoint(openAPISpecURL.toString()));

		return _connectionService.getResponseJsonObject(inputDataSet);
	}

	/**
	 * Gets key, value map where each key is contained in given
	 * <code>jsonObject</code> and value is String value pointed by given
	 * <code>pattern</code>
	 *
	 * <code>pattern</code> must match key1>key2>...>keyN syntax
	 *
	 * @param  pattern expression used to traverse underlying
	 *         <code>jsonObject</code> hierarchy
	 * @param  jsonObject source for key values
	 * @return Map of key, values where keys point to resolvable values
	 *         according given pattern
	 */
	private Map<String, String> _mapKeysToPatternEvaluations(
		final String pattern, JsonObject jsonObject) {

		final Map<String, String> evaluatedPatterns = new HashMap<>();

		jsonObject.forEach(
			(key, jsonValue) -> {
				String evaluation = _evaluatePattern(
					pattern, jsonValue.asJsonObject());

				if (evaluation != null) {
					evaluatedPatterns.put(key, _stripPath(evaluation));
				}
			});

		return evaluatedPatterns;
	}

	private String _stripPath(String reference) {
		if (reference.indexOf("/") == -1) {
			return reference;
		}

		return reference.substring(reference.lastIndexOf("/") + 1);
	}

	private static final String _GET_METHOD_RESPONSE_PATTERN =
		"get>responses>default>content>application/json>schema>$ref";

	private static final String _SCHEMA_PROPERTY_ITEMS_TYPE_PATTERN =
		"components>schemas>%s>properties>items>type";

	private static final String _SCHEMA_PROPERTY_PAGE_TYPE_PATTERN =
		"components>schemas>%s>properties>page>type";

	private static final Pattern _serverURLPattern = Pattern.compile(
		"(https?://.+(:\\d+)?)/o/(.+)/(v\\d+(.\\d+)*)/openapi\\.(yaml|json)");

	@Service
	private ConnectionService _connectionService;

	@Service
	private TalendService _talendService;

}