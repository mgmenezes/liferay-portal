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

package com.liferay.commerce.product.service.persistence.impl;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionTable;
import com.liferay.commerce.product.model.impl.CPDefinitionImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionModelImpl;
import com.liferay.commerce.product.service.persistence.CPDefinitionPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the cp definition service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CPDefinitionPersistenceImpl
	extends BasePersistenceImpl<CPDefinition>
	implements CPDefinitionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CPDefinitionUtil</code> to access the cp definition persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CPDefinitionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the cp definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CPDefinition> list = null;

		if (useFinderCache) {
			list = (List<CPDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinition cpDefinition : list) {
					if (!uuid.equals(cpDefinition.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<CPDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByUuid_First(
			String uuid, OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByUuid_First(uuid, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByUuid_First(
		String uuid, OrderByComparator<CPDefinition> orderByComparator) {

		List<CPDefinition> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByUuid_Last(
			String uuid, OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByUuid_Last(uuid, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the last cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByUuid_Last(
		String uuid, OrderByComparator<CPDefinition> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CPDefinition> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where uuid = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition[] findByUuid_PrevAndNext(
			long CPDefinitionId, String uuid,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		uuid = Objects.toString(uuid, "");

		CPDefinition cpDefinition = findByPrimaryKey(CPDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CPDefinition[] array = new CPDefinitionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, cpDefinition, uuid, orderByComparator, true);

			array[1] = cpDefinition;

			array[2] = getByUuid_PrevAndNext(
				session, cpDefinition, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinition getByUuid_PrevAndNext(
		Session session, CPDefinition cpDefinition, String uuid,
		OrderByComparator<CPDefinition> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(cpDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definitions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CPDefinition cpDefinition :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"cpDefinition.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(cpDefinition.uuid IS NULL OR cpDefinition.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the cp definition where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchCPDefinitionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByUUID_G(uuid, groupId);

		if (cpDefinition == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPDefinitionException(sb.toString());
		}

		return cpDefinition;
	}

	/**
	 * Returns the cp definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the cp definition where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof CPDefinition) {
			CPDefinition cpDefinition = (CPDefinition)result;

			if (!Objects.equals(uuid, cpDefinition.getUuid()) ||
				(groupId != cpDefinition.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<CPDefinition> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					CPDefinition cpDefinition = list.get(0);

					result = cpDefinition;

					cacheResult(cpDefinition);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CPDefinition)result;
		}
	}

	/**
	 * Removes the cp definition where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the cp definition that was removed
	 */
	@Override
	public CPDefinition removeByUUID_G(String uuid, long groupId)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByUUID_G(uuid, groupId);

		return remove(cpDefinition);
	}

	/**
	 * Returns the number of cp definitions where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"cpDefinition.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(cpDefinition.uuid IS NULL OR cpDefinition.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"cpDefinition.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CPDefinition> list = null;

		if (useFinderCache) {
			list = (List<CPDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinition cpDefinition : list) {
					if (!uuid.equals(cpDefinition.getUuid()) ||
						(companyId != cpDefinition.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<CPDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the first cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CPDefinition> orderByComparator) {

		List<CPDefinition> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the last cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CPDefinition> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinition> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition[] findByUuid_C_PrevAndNext(
			long CPDefinitionId, String uuid, long companyId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		uuid = Objects.toString(uuid, "");

		CPDefinition cpDefinition = findByPrimaryKey(CPDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CPDefinition[] array = new CPDefinitionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, cpDefinition, uuid, companyId, orderByComparator,
				true);

			array[1] = cpDefinition;

			array[2] = getByUuid_C_PrevAndNext(
				session, cpDefinition, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinition getByUuid_C_PrevAndNext(
		Session session, CPDefinition cpDefinition, String uuid, long companyId,
		OrderByComparator<CPDefinition> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(cpDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definitions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CPDefinition cpDefinition :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"cpDefinition.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(cpDefinition.uuid IS NULL OR cpDefinition.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"cpDefinition.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the cp definitions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<CPDefinition> list = null;

		if (useFinderCache) {
			list = (List<CPDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinition cpDefinition : list) {
					if (groupId != cpDefinition.getGroupId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<CPDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByGroupId_First(
			long groupId, OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByGroupId_First(
			groupId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByGroupId_First(
		long groupId, OrderByComparator<CPDefinition> orderByComparator) {

		List<CPDefinition> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByGroupId_Last(
			long groupId, OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByGroupId_Last(
		long groupId, OrderByComparator<CPDefinition> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CPDefinition> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where groupId = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition[] findByGroupId_PrevAndNext(
			long CPDefinitionId, long groupId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByPrimaryKey(CPDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CPDefinition[] array = new CPDefinitionImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, cpDefinition, groupId, orderByComparator, true);

			array[1] = cpDefinition;

			array[2] = getByGroupId_PrevAndNext(
				session, cpDefinition, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinition getByGroupId_PrevAndNext(
		Session session, CPDefinition cpDefinition, long groupId,
		OrderByComparator<CPDefinition> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(cpDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definitions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CPDefinition cpDefinition :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"cpDefinition.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the cp definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<CPDefinition> list = null;

		if (useFinderCache) {
			list = (List<CPDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinition cpDefinition : list) {
					if (companyId != cpDefinition.getCompanyId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<CPDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByCompanyId_First(
			long companyId, OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the first cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByCompanyId_First(
		long companyId, OrderByComparator<CPDefinition> orderByComparator) {

		List<CPDefinition> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByCompanyId_Last(
			long companyId, OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the last cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByCompanyId_Last(
		long companyId, OrderByComparator<CPDefinition> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<CPDefinition> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where companyId = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition[] findByCompanyId_PrevAndNext(
			long CPDefinitionId, long companyId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByPrimaryKey(CPDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CPDefinition[] array = new CPDefinitionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, cpDefinition, companyId, orderByComparator, true);

			array[1] = cpDefinition;

			array[2] = getByCompanyId_PrevAndNext(
				session, cpDefinition, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinition getByCompanyId_PrevAndNext(
		Session session, CPDefinition cpDefinition, long companyId,
		OrderByComparator<CPDefinition> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(cpDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definitions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (CPDefinition cpDefinition :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"cpDefinition.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCPTaxCategoryId;
	private FinderPath _finderPathWithoutPaginationFindByCPTaxCategoryId;
	private FinderPath _finderPathCountByCPTaxCategoryId;

	/**
	 * Returns all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCPTaxCategoryId(long CPTaxCategoryId) {
		return findByCPTaxCategoryId(
			CPTaxCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end) {

		return findByCPTaxCategoryId(CPTaxCategoryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByCPTaxCategoryId(
			CPTaxCategoryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByCPTaxCategoryId(
		long CPTaxCategoryId, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCPTaxCategoryId;
				finderArgs = new Object[] {CPTaxCategoryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCPTaxCategoryId;
			finderArgs = new Object[] {
				CPTaxCategoryId, start, end, orderByComparator
			};
		}

		List<CPDefinition> list = null;

		if (useFinderCache) {
			list = (List<CPDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinition cpDefinition : list) {
					if (CPTaxCategoryId != cpDefinition.getCPTaxCategoryId()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPTaxCategoryId);

				list = (List<CPDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByCPTaxCategoryId_First(
			long CPTaxCategoryId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByCPTaxCategoryId_First(
			CPTaxCategoryId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the first cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByCPTaxCategoryId_First(
		long CPTaxCategoryId,
		OrderByComparator<CPDefinition> orderByComparator) {

		List<CPDefinition> list = findByCPTaxCategoryId(
			CPTaxCategoryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByCPTaxCategoryId_Last(
			long CPTaxCategoryId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByCPTaxCategoryId_Last(
			CPTaxCategoryId, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CPTaxCategoryId=");
		sb.append(CPTaxCategoryId);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the last cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByCPTaxCategoryId_Last(
		long CPTaxCategoryId,
		OrderByComparator<CPDefinition> orderByComparator) {

		int count = countByCPTaxCategoryId(CPTaxCategoryId);

		if (count == 0) {
			return null;
		}

		List<CPDefinition> list = findByCPTaxCategoryId(
			CPTaxCategoryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where CPTaxCategoryId = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param CPTaxCategoryId the cp tax category ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition[] findByCPTaxCategoryId_PrevAndNext(
			long CPDefinitionId, long CPTaxCategoryId,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByPrimaryKey(CPDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CPDefinition[] array = new CPDefinitionImpl[3];

			array[0] = getByCPTaxCategoryId_PrevAndNext(
				session, cpDefinition, CPTaxCategoryId, orderByComparator,
				true);

			array[1] = cpDefinition;

			array[2] = getByCPTaxCategoryId_PrevAndNext(
				session, cpDefinition, CPTaxCategoryId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinition getByCPTaxCategoryId_PrevAndNext(
		Session session, CPDefinition cpDefinition, long CPTaxCategoryId,
		OrderByComparator<CPDefinition> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CPTaxCategoryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(cpDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definitions where CPTaxCategoryId = &#63; from the database.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 */
	@Override
	public void removeByCPTaxCategoryId(long CPTaxCategoryId) {
		for (CPDefinition cpDefinition :
				findByCPTaxCategoryId(
					CPTaxCategoryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions where CPTaxCategoryId = &#63;.
	 *
	 * @param CPTaxCategoryId the cp tax category ID
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByCPTaxCategoryId(long CPTaxCategoryId) {
		FinderPath finderPath = _finderPathCountByCPTaxCategoryId;

		Object[] finderArgs = new Object[] {CPTaxCategoryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CPTaxCategoryId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_CPTAXCATEGORYID_CPTAXCATEGORYID_2 =
			"cpDefinition.CPTaxCategoryId = ?";

	private FinderPath _finderPathWithPaginationFindByG_SE;
	private FinderPath _finderPathWithoutPaginationFindByG_SE;
	private FinderPath _finderPathCountByG_SE;

	/**
	 * Returns all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled) {

		return findByG_SE(
			groupId, subscriptionEnabled, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end) {

		return findByG_SE(groupId, subscriptionEnabled, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByG_SE(
			groupId, subscriptionEnabled, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_SE(
		long groupId, boolean subscriptionEnabled, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_SE;
				finderArgs = new Object[] {groupId, subscriptionEnabled};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_SE;
			finderArgs = new Object[] {
				groupId, subscriptionEnabled, start, end, orderByComparator
			};
		}

		List<CPDefinition> list = null;

		if (useFinderCache) {
			list = (List<CPDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinition cpDefinition : list) {
					if ((groupId != cpDefinition.getGroupId()) ||
						(subscriptionEnabled !=
							cpDefinition.isSubscriptionEnabled())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_G_SE_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_SE_SUBSCRIPTIONENABLED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(subscriptionEnabled);

				list = (List<CPDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByG_SE_First(
			long groupId, boolean subscriptionEnabled,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByG_SE_First(
			groupId, subscriptionEnabled, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", subscriptionEnabled=");
		sb.append(subscriptionEnabled);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByG_SE_First(
		long groupId, boolean subscriptionEnabled,
		OrderByComparator<CPDefinition> orderByComparator) {

		List<CPDefinition> list = findByG_SE(
			groupId, subscriptionEnabled, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByG_SE_Last(
			long groupId, boolean subscriptionEnabled,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByG_SE_Last(
			groupId, subscriptionEnabled, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", subscriptionEnabled=");
		sb.append(subscriptionEnabled);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByG_SE_Last(
		long groupId, boolean subscriptionEnabled,
		OrderByComparator<CPDefinition> orderByComparator) {

		int count = countByG_SE(groupId, subscriptionEnabled);

		if (count == 0) {
			return null;
		}

		List<CPDefinition> list = findByG_SE(
			groupId, subscriptionEnabled, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition[] findByG_SE_PrevAndNext(
			long CPDefinitionId, long groupId, boolean subscriptionEnabled,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByPrimaryKey(CPDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CPDefinition[] array = new CPDefinitionImpl[3];

			array[0] = getByG_SE_PrevAndNext(
				session, cpDefinition, groupId, subscriptionEnabled,
				orderByComparator, true);

			array[1] = cpDefinition;

			array[2] = getByG_SE_PrevAndNext(
				session, cpDefinition, groupId, subscriptionEnabled,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinition getByG_SE_PrevAndNext(
		Session session, CPDefinition cpDefinition, long groupId,
		boolean subscriptionEnabled,
		OrderByComparator<CPDefinition> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_G_SE_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_SE_SUBSCRIPTIONENABLED_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(subscriptionEnabled);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(cpDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definitions where groupId = &#63; and subscriptionEnabled = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 */
	@Override
	public void removeByG_SE(long groupId, boolean subscriptionEnabled) {
		for (CPDefinition cpDefinition :
				findByG_SE(
					groupId, subscriptionEnabled, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions where groupId = &#63; and subscriptionEnabled = &#63;.
	 *
	 * @param groupId the group ID
	 * @param subscriptionEnabled the subscription enabled
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByG_SE(long groupId, boolean subscriptionEnabled) {
		FinderPath finderPath = _finderPathCountByG_SE;

		Object[] finderArgs = new Object[] {groupId, subscriptionEnabled};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_G_SE_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_SE_SUBSCRIPTIONENABLED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(subscriptionEnabled);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_SE_GROUPID_2 =
		"cpDefinition.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_SE_SUBSCRIPTIONENABLED_2 =
		"cpDefinition.subscriptionEnabled = ?";

	private FinderPath _finderPathWithPaginationFindByG_S;
	private FinderPath _finderPathWithoutPaginationFindByG_S;
	private FinderPath _finderPathCountByG_S;

	/**
	 * Returns all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_S(long groupId, int status) {
		return findByG_S(
			groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_S(
		long groupId, int status, int start, int end) {

		return findByG_S(groupId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByG_S(groupId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByG_S(
		long groupId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByG_S;
				finderArgs = new Object[] {groupId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByG_S;
			finderArgs = new Object[] {
				groupId, status, start, end, orderByComparator
			};
		}

		List<CPDefinition> list = null;

		if (useFinderCache) {
			list = (List<CPDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinition cpDefinition : list) {
					if ((groupId != cpDefinition.getGroupId()) ||
						(status != cpDefinition.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

				list = (List<CPDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByG_S_First(
			long groupId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByG_S_First(
			groupId, status, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the first cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByG_S_First(
		long groupId, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		List<CPDefinition> list = findByG_S(
			groupId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByG_S_Last(
			long groupId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByG_S_Last(
			groupId, status, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the last cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByG_S_Last(
		long groupId, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		int count = countByG_S(groupId, status);

		if (count == 0) {
			return null;
		}

		List<CPDefinition> list = findByG_S(
			groupId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where groupId = &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param groupId the group ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition[] findByG_S_PrevAndNext(
			long CPDefinitionId, long groupId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByPrimaryKey(CPDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CPDefinition[] array = new CPDefinitionImpl[3];

			array[0] = getByG_S_PrevAndNext(
				session, cpDefinition, groupId, status, orderByComparator,
				true);

			array[1] = cpDefinition;

			array[2] = getByG_S_PrevAndNext(
				session, cpDefinition, groupId, status, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinition getByG_S_PrevAndNext(
		Session session, CPDefinition cpDefinition, long groupId, int status,
		OrderByComparator<CPDefinition> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(cpDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definitions where groupId = &#63; and status = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 */
	@Override
	public void removeByG_S(long groupId, int status) {
		for (CPDefinition cpDefinition :
				findByG_S(
					groupId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions where groupId = &#63; and status = &#63;.
	 *
	 * @param groupId the group ID
	 * @param status the status
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByG_S(long groupId, int status) {
		FinderPath finderPath = _finderPathCountByG_S;

		Object[] finderArgs = new Object[] {groupId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_G_S_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_S_GROUPID_2 =
		"cpDefinition.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_STATUS_2 =
		"cpDefinition.status = ?";

	private FinderPath _finderPathFetchByC_V;
	private FinderPath _finderPathCountByC_V;

	/**
	 * Returns the cp definition where CProductId = &#63; and version = &#63; or throws a <code>NoSuchCPDefinitionException</code> if it could not be found.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByC_V(long CProductId, int version)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByC_V(CProductId, version);

		if (cpDefinition == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("CProductId=");
			sb.append(CProductId);

			sb.append(", version=");
			sb.append(version);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCPDefinitionException(sb.toString());
		}

		return cpDefinition;
	}

	/**
	 * Returns the cp definition where CProductId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByC_V(long CProductId, int version) {
		return fetchByC_V(CProductId, version, true);
	}

	/**
	 * Returns the cp definition where CProductId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByC_V(
		long CProductId, int version, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {CProductId, version};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_V, finderArgs, this);
		}

		if (result instanceof CPDefinition) {
			CPDefinition cpDefinition = (CPDefinition)result;

			if ((CProductId != cpDefinition.getCProductId()) ||
				(version != cpDefinition.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_V_CPRODUCTID_2);

			sb.append(_FINDER_COLUMN_C_V_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

				queryPos.add(version);

				List<CPDefinition> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_V, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {CProductId, version};
							}

							_log.warn(
								"CPDefinitionPersistenceImpl.fetchByC_V(long, int, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CPDefinition cpDefinition = list.get(0);

					result = cpDefinition;

					cacheResult(cpDefinition);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CPDefinition)result;
		}
	}

	/**
	 * Removes the cp definition where CProductId = &#63; and version = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the cp definition that was removed
	 */
	@Override
	public CPDefinition removeByC_V(long CProductId, int version)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByC_V(CProductId, version);

		return remove(cpDefinition);
	}

	/**
	 * Returns the number of cp definitions where CProductId = &#63; and version = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param version the version
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByC_V(long CProductId, int version) {
		FinderPath finderPath = _finderPathCountByC_V;

		Object[] finderArgs = new Object[] {CProductId, version};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_V_CPRODUCTID_2);

			sb.append(_FINDER_COLUMN_C_V_VERSION_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

				queryPos.add(version);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_V_CPRODUCTID_2 =
		"cpDefinition.CProductId = ? AND ";

	private static final String _FINDER_COLUMN_C_V_VERSION_2 =
		"cpDefinition.version = ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByC_S(long CProductId, int status) {
		return findByC_S(
			CProductId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByC_S(
		long CProductId, int status, int start, int end) {

		return findByC_S(CProductId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByC_S(
		long CProductId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByC_S(
			CProductId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByC_S(
		long CProductId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_S;
				finderArgs = new Object[] {CProductId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				CProductId, status, start, end, orderByComparator
			};
		}

		List<CPDefinition> list = null;

		if (useFinderCache) {
			list = (List<CPDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinition cpDefinition : list) {
					if ((CProductId != cpDefinition.getCProductId()) ||
						(status != cpDefinition.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_S_CPRODUCTID_2);

			sb.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

				queryPos.add(status);

				list = (List<CPDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByC_S_First(
			long CProductId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByC_S_First(
			CProductId, status, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CProductId=");
		sb.append(CProductId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the first cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByC_S_First(
		long CProductId, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		List<CPDefinition> list = findByC_S(
			CProductId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByC_S_Last(
			long CProductId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByC_S_Last(
			CProductId, status, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CProductId=");
		sb.append(CProductId);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the last cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByC_S_Last(
		long CProductId, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		int count = countByC_S(CProductId, status);

		if (count == 0) {
			return null;
		}

		List<CPDefinition> list = findByC_S(
			CProductId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where CProductId = &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param CProductId the c product ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition[] findByC_S_PrevAndNext(
			long CPDefinitionId, long CProductId, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByPrimaryKey(CPDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CPDefinition[] array = new CPDefinitionImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, cpDefinition, CProductId, status, orderByComparator,
				true);

			array[1] = cpDefinition;

			array[2] = getByC_S_PrevAndNext(
				session, cpDefinition, CProductId, status, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinition getByC_S_PrevAndNext(
		Session session, CPDefinition cpDefinition, long CProductId, int status,
		OrderByComparator<CPDefinition> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

		sb.append(_FINDER_COLUMN_C_S_CPRODUCTID_2);

		sb.append(_FINDER_COLUMN_C_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CProductId);

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(cpDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definitions where CProductId = &#63; and status = &#63; from the database.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long CProductId, int status) {
		for (CPDefinition cpDefinition :
				findByC_S(
					CProductId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions where CProductId = &#63; and status = &#63;.
	 *
	 * @param CProductId the c product ID
	 * @param status the status
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByC_S(long CProductId, int status) {
		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {CProductId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			sb.append(_FINDER_COLUMN_C_S_CPRODUCTID_2);

			sb.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CProductId);

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_S_CPRODUCTID_2 =
		"cpDefinition.CProductId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_STATUS_2 =
		"cpDefinition.status = ?";

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;

	/**
	 * Returns all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cp definitions
	 */
	@Override
	public List<CPDefinition> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtD_S;
		finderArgs = new Object[] {
			_getTime(displayDate), status, start, end, orderByComparator
		};

		List<CPDefinition> list = null;

		if (useFinderCache) {
			list = (List<CPDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CPDefinition cpDefinition : list) {
					if ((displayDate.getTime() <= cpDefinition.getDisplayDate(
						).getTime()) || (status != cpDefinition.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDisplayDate) {
					queryPos.add(new Timestamp(displayDate.getTime()));
				}

				queryPos.add(status);

				list = (List<CPDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the first cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		List<CPDefinition> list = findByLtD_S(
			displayDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition
	 * @throws NoSuchCPDefinitionException if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByLtD_S_Last(
			displayDate, status, orderByComparator);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchCPDefinitionException(sb.toString());
	}

	/**
	 * Returns the last cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cp definition, or <code>null</code> if a matching cp definition could not be found
	 */
	@Override
	public CPDefinition fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CPDefinition> orderByComparator) {

		int count = countByLtD_S(displayDate, status);

		if (count == 0) {
			return null;
		}

		List<CPDefinition> list = findByLtD_S(
			displayDate, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cp definitions before and after the current cp definition in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param CPDefinitionId the primary key of the current cp definition
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition[] findByLtD_S_PrevAndNext(
			long CPDefinitionId, Date displayDate, int status,
			OrderByComparator<CPDefinition> orderByComparator)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = findByPrimaryKey(CPDefinitionId);

		Session session = null;

		try {
			session = openSession();

			CPDefinition[] array = new CPDefinitionImpl[3];

			array[0] = getByLtD_S_PrevAndNext(
				session, cpDefinition, displayDate, status, orderByComparator,
				true);

			array[1] = cpDefinition;

			array[2] = getByLtD_S_PrevAndNext(
				session, cpDefinition, displayDate, status, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CPDefinition getByLtD_S_PrevAndNext(
		Session session, CPDefinition cpDefinition, Date displayDate,
		int status, OrderByComparator<CPDefinition> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_CPDEFINITION_WHERE);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CPDefinitionModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindDisplayDate) {
			queryPos.add(new Timestamp(displayDate.getTime()));
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(cpDefinition)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CPDefinition> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cp definitions where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		for (CPDefinition cpDefinition :
				findByLtD_S(
					displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching cp definitions
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtD_S;

		Object[] finderArgs = new Object[] {_getTime(displayDate), status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CPDEFINITION_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDisplayDate) {
					queryPos.add(new Timestamp(displayDate.getTime()));
				}

				queryPos.add(status);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_1 =
		"cpDefinition.displayDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 =
		"cpDefinition.displayDate < ? AND ";

	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 =
		"cpDefinition.status = ?";

	public CPDefinitionPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"deliverySubscriptionTypeSettings", "deliverySubTypeSettings");

		setDBColumnNames(dbColumnNames);

		setModelClass(CPDefinition.class);

		setModelImplClass(CPDefinitionImpl.class);
		setModelPKClass(long.class);

		setTable(CPDefinitionTable.INSTANCE);
	}

	/**
	 * Caches the cp definition in the entity cache if it is enabled.
	 *
	 * @param cpDefinition the cp definition
	 */
	@Override
	public void cacheResult(CPDefinition cpDefinition) {
		entityCache.putResult(
			CPDefinitionImpl.class, cpDefinition.getPrimaryKey(), cpDefinition);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {cpDefinition.getUuid(), cpDefinition.getGroupId()},
			cpDefinition);

		finderCache.putResult(
			_finderPathFetchByC_V,
			new Object[] {
				cpDefinition.getCProductId(), cpDefinition.getVersion()
			},
			cpDefinition);
	}

	/**
	 * Caches the cp definitions in the entity cache if it is enabled.
	 *
	 * @param cpDefinitions the cp definitions
	 */
	@Override
	public void cacheResult(List<CPDefinition> cpDefinitions) {
		for (CPDefinition cpDefinition : cpDefinitions) {
			if (entityCache.getResult(
					CPDefinitionImpl.class, cpDefinition.getPrimaryKey()) ==
						null) {

				cacheResult(cpDefinition);
			}
		}
	}

	/**
	 * Clears the cache for all cp definitions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CPDefinitionImpl.class);

		finderCache.clearCache(CPDefinitionImpl.class);
	}

	/**
	 * Clears the cache for the cp definition.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CPDefinition cpDefinition) {
		entityCache.removeResult(CPDefinitionImpl.class, cpDefinition);
	}

	@Override
	public void clearCache(List<CPDefinition> cpDefinitions) {
		for (CPDefinition cpDefinition : cpDefinitions) {
			entityCache.removeResult(CPDefinitionImpl.class, cpDefinition);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CPDefinitionImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CPDefinitionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CPDefinitionModelImpl cpDefinitionModelImpl) {

		Object[] args = new Object[] {
			cpDefinitionModelImpl.getUuid(), cpDefinitionModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, cpDefinitionModelImpl);

		args = new Object[] {
			cpDefinitionModelImpl.getCProductId(),
			cpDefinitionModelImpl.getVersion()
		};

		finderCache.putResult(_finderPathCountByC_V, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_V, args, cpDefinitionModelImpl);
	}

	/**
	 * Creates a new cp definition with the primary key. Does not add the cp definition to the database.
	 *
	 * @param CPDefinitionId the primary key for the new cp definition
	 * @return the new cp definition
	 */
	@Override
	public CPDefinition create(long CPDefinitionId) {
		CPDefinition cpDefinition = new CPDefinitionImpl();

		cpDefinition.setNew(true);
		cpDefinition.setPrimaryKey(CPDefinitionId);

		String uuid = PortalUUIDUtil.generate();

		cpDefinition.setUuid(uuid);

		cpDefinition.setCompanyId(CompanyThreadLocal.getCompanyId());

		return cpDefinition;
	}

	/**
	 * Removes the cp definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CPDefinitionId the primary key of the cp definition
	 * @return the cp definition that was removed
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition remove(long CPDefinitionId)
		throws NoSuchCPDefinitionException {

		return remove((Serializable)CPDefinitionId);
	}

	/**
	 * Removes the cp definition with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cp definition
	 * @return the cp definition that was removed
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition remove(Serializable primaryKey)
		throws NoSuchCPDefinitionException {

		Session session = null;

		try {
			session = openSession();

			CPDefinition cpDefinition = (CPDefinition)session.get(
				CPDefinitionImpl.class, primaryKey);

			if (cpDefinition == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCPDefinitionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cpDefinition);
		}
		catch (NoSuchCPDefinitionException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CPDefinition removeImpl(CPDefinition cpDefinition) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cpDefinition)) {
				cpDefinition = (CPDefinition)session.get(
					CPDefinitionImpl.class, cpDefinition.getPrimaryKeyObj());
			}

			if (cpDefinition != null) {
				session.delete(cpDefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (cpDefinition != null) {
			clearCache(cpDefinition);
		}

		return cpDefinition;
	}

	@Override
	public CPDefinition updateImpl(CPDefinition cpDefinition) {
		boolean isNew = cpDefinition.isNew();

		if (!(cpDefinition instanceof CPDefinitionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cpDefinition.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cpDefinition);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cpDefinition proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CPDefinition implementation " +
					cpDefinition.getClass());
		}

		CPDefinitionModelImpl cpDefinitionModelImpl =
			(CPDefinitionModelImpl)cpDefinition;

		if (Validator.isNull(cpDefinition.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			cpDefinition.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cpDefinition.getCreateDate() == null)) {
			if (serviceContext == null) {
				cpDefinition.setCreateDate(now);
			}
			else {
				cpDefinition.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!cpDefinitionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cpDefinition.setModifiedDate(now);
			}
			else {
				cpDefinition.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(cpDefinition);
			}
			else {
				cpDefinition = (CPDefinition)session.merge(cpDefinition);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CPDefinitionImpl.class, cpDefinitionModelImpl, false, true);

		cacheUniqueFindersCache(cpDefinitionModelImpl);

		if (isNew) {
			cpDefinition.setNew(false);
		}

		cpDefinition.resetOriginalValues();

		return cpDefinition;
	}

	/**
	 * Returns the cp definition with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cp definition
	 * @return the cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCPDefinitionException {

		CPDefinition cpDefinition = fetchByPrimaryKey(primaryKey);

		if (cpDefinition == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCPDefinitionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cpDefinition;
	}

	/**
	 * Returns the cp definition with the primary key or throws a <code>NoSuchCPDefinitionException</code> if it could not be found.
	 *
	 * @param CPDefinitionId the primary key of the cp definition
	 * @return the cp definition
	 * @throws NoSuchCPDefinitionException if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition findByPrimaryKey(long CPDefinitionId)
		throws NoSuchCPDefinitionException {

		return findByPrimaryKey((Serializable)CPDefinitionId);
	}

	/**
	 * Returns the cp definition with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CPDefinitionId the primary key of the cp definition
	 * @return the cp definition, or <code>null</code> if a cp definition with the primary key could not be found
	 */
	@Override
	public CPDefinition fetchByPrimaryKey(long CPDefinitionId) {
		return fetchByPrimaryKey((Serializable)CPDefinitionId);
	}

	/**
	 * Returns all the cp definitions.
	 *
	 * @return the cp definitions
	 */
	@Override
	public List<CPDefinition> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cp definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @return the range of cp definitions
	 */
	@Override
	public List<CPDefinition> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cp definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cp definitions
	 */
	@Override
	public List<CPDefinition> findAll(
		int start, int end, OrderByComparator<CPDefinition> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cp definitions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CPDefinitionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cp definitions
	 * @param end the upper bound of the range of cp definitions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cp definitions
	 */
	@Override
	public List<CPDefinition> findAll(
		int start, int end, OrderByComparator<CPDefinition> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CPDefinition> list = null;

		if (useFinderCache) {
			list = (List<CPDefinition>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CPDEFINITION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CPDEFINITION;

				sql = sql.concat(CPDefinitionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CPDefinition>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the cp definitions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CPDefinition cpDefinition : findAll()) {
			remove(cpDefinition);
		}
	}

	/**
	 * Returns the number of cp definitions.
	 *
	 * @return the number of cp definitions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CPDEFINITION);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CPDefinitionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CPDEFINITION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CPDefinitionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cp definition persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CPDefinitionPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class, new CPDefinitionModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCPTaxCategoryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"CPTaxCategoryId"}, true);

		_finderPathWithoutPaginationFindByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCPTaxCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"CPTaxCategoryId"}, true);

		_finderPathCountByCPTaxCategoryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPTaxCategoryId",
			new String[] {Long.class.getName()},
			new String[] {"CPTaxCategoryId"}, false);

		_finderPathWithPaginationFindByG_SE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_SE",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "subscriptionEnabled"}, true);

		_finderPathWithoutPaginationFindByG_SE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_SE",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "subscriptionEnabled"}, true);

		_finderPathCountByG_SE = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_SE",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "subscriptionEnabled"}, false);

		_finderPathWithPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "status"}, true);

		_finderPathWithoutPaginationFindByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, true);

		_finderPathCountByG_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"groupId", "status"}, false);

		_finderPathFetchByC_V = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_V",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"CProductId", "version"}, true);

		_finderPathCountByC_V = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_V",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"CProductId", "version"}, false);

		_finderPathWithPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"CProductId", "status"}, true);

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"CProductId", "status"}, true);

		_finderPathCountByC_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			new String[] {"CProductId", "status"}, false);

		_finderPathWithPaginationFindByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"displayDate", "status"}, true);

		_finderPathWithPaginationCountByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"displayDate", "status"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CPDefinitionImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_CPDEFINITION =
		"SELECT cpDefinition FROM CPDefinition cpDefinition";

	private static final String _SQL_SELECT_CPDEFINITION_WHERE =
		"SELECT cpDefinition FROM CPDefinition cpDefinition WHERE ";

	private static final String _SQL_COUNT_CPDEFINITION =
		"SELECT COUNT(cpDefinition) FROM CPDefinition cpDefinition";

	private static final String _SQL_COUNT_CPDEFINITION_WHERE =
		"SELECT COUNT(cpDefinition) FROM CPDefinition cpDefinition WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cpDefinition.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CPDefinition exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CPDefinition exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "deliverySubscriptionTypeSettings"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CPDefinitionModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CPDefinitionModelImpl cpDefinitionModelImpl =
				(CPDefinitionModelImpl)baseModel;

			long columnBitmask = cpDefinitionModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(cpDefinitionModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						cpDefinitionModelImpl.getColumnBitmask(columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(cpDefinitionModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CPDefinitionImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CPDefinitionTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CPDefinitionModelImpl cpDefinitionModelImpl, String[] columnNames,
			boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] = cpDefinitionModelImpl.getColumnOriginalValue(
						columnName);
				}
				else {
					arguments[i] = cpDefinitionModelImpl.getColumnValue(
						columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}