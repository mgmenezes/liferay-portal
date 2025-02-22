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

package com.liferay.dynamic.data.mapping.service.http;

import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.rmi.RemoteException;

import java.util.Locale;
import java.util.Map;

/**
 * Provides the SOAP utility for the
 * <code>DDMDataProviderInstanceServiceUtil</code> service
 * utility. The static methods of this class call the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap</code>. If the method in the
 * service utility returns a
 * <code>com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance</code>, that is translated to a
 * <code>com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap</code>. Methods that SOAP
 * cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at http://localhost:8080/api/axis. Set the
 * property <b>axis.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceServiceHttp
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class DDMDataProviderInstanceServiceSoap {

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap
				addDataProviderInstance(
					long groupId, String[] nameMapLanguageIds,
					String[] nameMapValues, String[] descriptionMapLanguageIds,
					String[] descriptionMapValues,
					com.liferay.dynamic.data.mapping.storage.DDMFormValues
						ddmFormValues,
					String type,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
				returnValue =
					DDMDataProviderInstanceServiceUtil.addDataProviderInstance(
						groupId, nameMap, descriptionMap, ddmFormValues, type,
						serviceContext);

			return com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static void deleteDataProviderInstance(long dataProviderInstanceId)
		throws RemoteException {

		try {
			DDMDataProviderInstanceServiceUtil.deleteDataProviderInstance(
				dataProviderInstanceId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap
				fetchDataProviderInstance(long dataProviderInstanceId)
			throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
				returnValue =
					DDMDataProviderInstanceServiceUtil.
						fetchDataProviderInstance(dataProviderInstanceId);

			return com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap
				fetchDataProviderInstanceByUuid(String uuid)
			throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
				returnValue =
					DDMDataProviderInstanceServiceUtil.
						fetchDataProviderInstanceByUuid(uuid);

			return com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap
				getDataProviderInstance(long dataProviderInstanceId)
			throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
				returnValue =
					DDMDataProviderInstanceServiceUtil.getDataProviderInstance(
						dataProviderInstanceId);

			return com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap
				getDataProviderInstanceByUuid(String uuid)
			throws RemoteException {

		try {
			com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
				returnValue =
					DDMDataProviderInstanceServiceUtil.
						getDataProviderInstanceByUuid(uuid);

			return com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap[]
				getDataProviderInstances(
					long companyId, long[] groupIds, int start, int end)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
					returnValue =
						DDMDataProviderInstanceServiceUtil.
							getDataProviderInstances(
								companyId, groupIds, start, end);

			return com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstanceSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int getDataProviderInstancesCount(
			long companyId, long[] groupIds)
		throws RemoteException {

		try {
			int returnValue =
				DDMDataProviderInstanceServiceUtil.
					getDataProviderInstancesCount(companyId, groupIds);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap[]
				search(
					long companyId, long[] groupIds, String keywords, int start,
					int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.dynamic.data.mapping.model.
							DDMDataProviderInstance> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
					returnValue = DDMDataProviderInstanceServiceUtil.search(
						companyId, groupIds, keywords, start, end,
						orderByComparator);

			return com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstanceSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap[]
				search(
					long companyId, long[] groupIds, String name,
					String description, boolean andOperator, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.dynamic.data.mapping.model.
							DDMDataProviderInstance> orderByComparator)
			throws RemoteException {

		try {
			java.util.List
				<com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance>
					returnValue = DDMDataProviderInstanceServiceUtil.search(
						companyId, groupIds, name, description, andOperator,
						start, end, orderByComparator);

			return com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstanceSoap.toSoapModels(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int searchCount(
			long companyId, long[] groupIds, String keywords)
		throws RemoteException {

		try {
			int returnValue = DDMDataProviderInstanceServiceUtil.searchCount(
				companyId, groupIds, keywords);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static int searchCount(
			long companyId, long[] groupIds, String name, String description,
			boolean andOperator)
		throws RemoteException {

		try {
			int returnValue = DDMDataProviderInstanceServiceUtil.searchCount(
				companyId, groupIds, name, description, andOperator);

			return returnValue;
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	public static
		com.liferay.dynamic.data.mapping.model.DDMDataProviderInstanceSoap
				updateDataProviderInstance(
					long dataProviderInstanceId, String[] nameMapLanguageIds,
					String[] nameMapValues, String[] descriptionMapLanguageIds,
					String[] descriptionMapValues,
					com.liferay.dynamic.data.mapping.storage.DDMFormValues
						ddmFormValues,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws RemoteException {

		try {
			Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
				nameMapLanguageIds, nameMapValues);
			Map<Locale, String> descriptionMap =
				LocalizationUtil.getLocalizationMap(
					descriptionMapLanguageIds, descriptionMapValues);

			com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance
				returnValue =
					DDMDataProviderInstanceServiceUtil.
						updateDataProviderInstance(
							dataProviderInstanceId, nameMap, descriptionMap,
							ddmFormValues, serviceContext);

			return com.liferay.dynamic.data.mapping.model.
				DDMDataProviderInstanceSoap.toSoapModel(returnValue);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw new RemoteException(exception.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		DDMDataProviderInstanceServiceSoap.class);

}