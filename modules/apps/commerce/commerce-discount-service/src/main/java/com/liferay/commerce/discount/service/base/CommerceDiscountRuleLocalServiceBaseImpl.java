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

package com.liferay.commerce.discount.service.base;

import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.CommerceDiscountRuleLocalService;
import com.liferay.commerce.discount.service.CommerceDiscountRuleLocalServiceUtil;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountAccountRelFinder;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountAccountRelPersistence;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountCommerceAccountGroupRelFinder;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountCommerceAccountGroupRelPersistence;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountFinder;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountPersistence;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountRelFinder;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountRelPersistence;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountRuleFinder;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountRulePersistence;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountUsageEntryPersistence;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the commerce discount rule local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.commerce.discount.service.impl.CommerceDiscountRuleLocalServiceImpl}.
 * </p>
 *
 * @author Marco Leo
 * @see com.liferay.commerce.discount.service.impl.CommerceDiscountRuleLocalServiceImpl
 * @generated
 */
public abstract class CommerceDiscountRuleLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements CommerceDiscountRuleLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>CommerceDiscountRuleLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>CommerceDiscountRuleLocalServiceUtil</code>.
	 */

	/**
	 * Adds the commerce discount rule to the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountRule the commerce discount rule
	 * @return the commerce discount rule that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscountRule addCommerceDiscountRule(
		CommerceDiscountRule commerceDiscountRule) {

		commerceDiscountRule.setNew(true);

		return commerceDiscountRulePersistence.update(commerceDiscountRule);
	}

	/**
	 * Creates a new commerce discount rule with the primary key. Does not add the commerce discount rule to the database.
	 *
	 * @param commerceDiscountRuleId the primary key for the new commerce discount rule
	 * @return the new commerce discount rule
	 */
	@Override
	@Transactional(enabled = false)
	public CommerceDiscountRule createCommerceDiscountRule(
		long commerceDiscountRuleId) {

		return commerceDiscountRulePersistence.create(commerceDiscountRuleId);
	}

	/**
	 * Deletes the commerce discount rule with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountRuleId the primary key of the commerce discount rule
	 * @return the commerce discount rule that was removed
	 * @throws PortalException if a commerce discount rule with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceDiscountRule deleteCommerceDiscountRule(
			long commerceDiscountRuleId)
		throws PortalException {

		return commerceDiscountRulePersistence.remove(commerceDiscountRuleId);
	}

	/**
	 * Deletes the commerce discount rule from the database. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountRule the commerce discount rule
	 * @return the commerce discount rule that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public CommerceDiscountRule deleteCommerceDiscountRule(
			CommerceDiscountRule commerceDiscountRule)
		throws PortalException {

		return commerceDiscountRulePersistence.remove(commerceDiscountRule);
	}

	@Override
	public <T> T dslQuery(DSLQuery dslQuery) {
		return commerceDiscountRulePersistence.dslQuery(dslQuery);
	}

	@Override
	public int dslQueryCount(DSLQuery dslQuery) {
		Long count = dslQuery(dslQuery);

		return count.intValue();
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			CommerceDiscountRule.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return commerceDiscountRulePersistence.findWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountRuleModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return commerceDiscountRulePersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountRuleModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return commerceDiscountRulePersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return commerceDiscountRulePersistence.countWithDynamicQuery(
			dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return commerceDiscountRulePersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public CommerceDiscountRule fetchCommerceDiscountRule(
		long commerceDiscountRuleId) {

		return commerceDiscountRulePersistence.fetchByPrimaryKey(
			commerceDiscountRuleId);
	}

	/**
	 * Returns the commerce discount rule with the primary key.
	 *
	 * @param commerceDiscountRuleId the primary key of the commerce discount rule
	 * @return the commerce discount rule
	 * @throws PortalException if a commerce discount rule with the primary key could not be found
	 */
	@Override
	public CommerceDiscountRule getCommerceDiscountRule(
			long commerceDiscountRuleId)
		throws PortalException {

		return commerceDiscountRulePersistence.findByPrimaryKey(
			commerceDiscountRuleId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			commerceDiscountRuleLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceDiscountRule.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceDiscountRuleId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			commerceDiscountRuleLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(
			CommerceDiscountRule.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceDiscountRuleId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			commerceDiscountRuleLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(CommerceDiscountRule.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"commerceDiscountRuleId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceDiscountRulePersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return commerceDiscountRuleLocalService.deleteCommerceDiscountRule(
			(CommerceDiscountRule)persistedModel);
	}

	@Override
	public BasePersistence<CommerceDiscountRule> getBasePersistence() {
		return commerceDiscountRulePersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return commerceDiscountRulePersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the commerce discount rules.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.commerce.discount.model.impl.CommerceDiscountRuleModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount rules
	 * @param end the upper bound of the range of commerce discount rules (not inclusive)
	 * @return the range of commerce discount rules
	 */
	@Override
	public List<CommerceDiscountRule> getCommerceDiscountRules(
		int start, int end) {

		return commerceDiscountRulePersistence.findAll(start, end);
	}

	/**
	 * Returns the number of commerce discount rules.
	 *
	 * @return the number of commerce discount rules
	 */
	@Override
	public int getCommerceDiscountRulesCount() {
		return commerceDiscountRulePersistence.countAll();
	}

	/**
	 * Updates the commerce discount rule in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * <p>
	 * <strong>Important:</strong> Inspect CommerceDiscountRuleLocalServiceImpl for overloaded versions of the method. If provided, use these entry points to the API, as the implementation logic may require the additional parameters defined there.
	 * </p>
	 *
	 * @param commerceDiscountRule the commerce discount rule
	 * @return the commerce discount rule that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceDiscountRule updateCommerceDiscountRule(
		CommerceDiscountRule commerceDiscountRule) {

		return commerceDiscountRulePersistence.update(commerceDiscountRule);
	}

	/**
	 * Returns the commerce discount local service.
	 *
	 * @return the commerce discount local service
	 */
	public com.liferay.commerce.discount.service.CommerceDiscountLocalService
		getCommerceDiscountLocalService() {

		return commerceDiscountLocalService;
	}

	/**
	 * Sets the commerce discount local service.
	 *
	 * @param commerceDiscountLocalService the commerce discount local service
	 */
	public void setCommerceDiscountLocalService(
		com.liferay.commerce.discount.service.CommerceDiscountLocalService
			commerceDiscountLocalService) {

		this.commerceDiscountLocalService = commerceDiscountLocalService;
	}

	/**
	 * Returns the commerce discount persistence.
	 *
	 * @return the commerce discount persistence
	 */
	public CommerceDiscountPersistence getCommerceDiscountPersistence() {
		return commerceDiscountPersistence;
	}

	/**
	 * Sets the commerce discount persistence.
	 *
	 * @param commerceDiscountPersistence the commerce discount persistence
	 */
	public void setCommerceDiscountPersistence(
		CommerceDiscountPersistence commerceDiscountPersistence) {

		this.commerceDiscountPersistence = commerceDiscountPersistence;
	}

	/**
	 * Returns the commerce discount finder.
	 *
	 * @return the commerce discount finder
	 */
	public CommerceDiscountFinder getCommerceDiscountFinder() {
		return commerceDiscountFinder;
	}

	/**
	 * Sets the commerce discount finder.
	 *
	 * @param commerceDiscountFinder the commerce discount finder
	 */
	public void setCommerceDiscountFinder(
		CommerceDiscountFinder commerceDiscountFinder) {

		this.commerceDiscountFinder = commerceDiscountFinder;
	}

	/**
	 * Returns the commerce discount account rel local service.
	 *
	 * @return the commerce discount account rel local service
	 */
	public
		com.liferay.commerce.discount.service.
			CommerceDiscountAccountRelLocalService
				getCommerceDiscountAccountRelLocalService() {

		return commerceDiscountAccountRelLocalService;
	}

	/**
	 * Sets the commerce discount account rel local service.
	 *
	 * @param commerceDiscountAccountRelLocalService the commerce discount account rel local service
	 */
	public void setCommerceDiscountAccountRelLocalService(
		com.liferay.commerce.discount.service.
			CommerceDiscountAccountRelLocalService
				commerceDiscountAccountRelLocalService) {

		this.commerceDiscountAccountRelLocalService =
			commerceDiscountAccountRelLocalService;
	}

	/**
	 * Returns the commerce discount account rel persistence.
	 *
	 * @return the commerce discount account rel persistence
	 */
	public CommerceDiscountAccountRelPersistence
		getCommerceDiscountAccountRelPersistence() {

		return commerceDiscountAccountRelPersistence;
	}

	/**
	 * Sets the commerce discount account rel persistence.
	 *
	 * @param commerceDiscountAccountRelPersistence the commerce discount account rel persistence
	 */
	public void setCommerceDiscountAccountRelPersistence(
		CommerceDiscountAccountRelPersistence
			commerceDiscountAccountRelPersistence) {

		this.commerceDiscountAccountRelPersistence =
			commerceDiscountAccountRelPersistence;
	}

	/**
	 * Returns the commerce discount account rel finder.
	 *
	 * @return the commerce discount account rel finder
	 */
	public CommerceDiscountAccountRelFinder
		getCommerceDiscountAccountRelFinder() {

		return commerceDiscountAccountRelFinder;
	}

	/**
	 * Sets the commerce discount account rel finder.
	 *
	 * @param commerceDiscountAccountRelFinder the commerce discount account rel finder
	 */
	public void setCommerceDiscountAccountRelFinder(
		CommerceDiscountAccountRelFinder commerceDiscountAccountRelFinder) {

		this.commerceDiscountAccountRelFinder =
			commerceDiscountAccountRelFinder;
	}

	/**
	 * Returns the commerce discount commerce account group rel local service.
	 *
	 * @return the commerce discount commerce account group rel local service
	 */
	public com.liferay.commerce.discount.service.
		CommerceDiscountCommerceAccountGroupRelLocalService
			getCommerceDiscountCommerceAccountGroupRelLocalService() {

		return commerceDiscountCommerceAccountGroupRelLocalService;
	}

	/**
	 * Sets the commerce discount commerce account group rel local service.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelLocalService the commerce discount commerce account group rel local service
	 */
	public void setCommerceDiscountCommerceAccountGroupRelLocalService(
		com.liferay.commerce.discount.service.
			CommerceDiscountCommerceAccountGroupRelLocalService
				commerceDiscountCommerceAccountGroupRelLocalService) {

		this.commerceDiscountCommerceAccountGroupRelLocalService =
			commerceDiscountCommerceAccountGroupRelLocalService;
	}

	/**
	 * Returns the commerce discount commerce account group rel persistence.
	 *
	 * @return the commerce discount commerce account group rel persistence
	 */
	public CommerceDiscountCommerceAccountGroupRelPersistence
		getCommerceDiscountCommerceAccountGroupRelPersistence() {

		return commerceDiscountCommerceAccountGroupRelPersistence;
	}

	/**
	 * Sets the commerce discount commerce account group rel persistence.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelPersistence the commerce discount commerce account group rel persistence
	 */
	public void setCommerceDiscountCommerceAccountGroupRelPersistence(
		CommerceDiscountCommerceAccountGroupRelPersistence
			commerceDiscountCommerceAccountGroupRelPersistence) {

		this.commerceDiscountCommerceAccountGroupRelPersistence =
			commerceDiscountCommerceAccountGroupRelPersistence;
	}

	/**
	 * Returns the commerce discount commerce account group rel finder.
	 *
	 * @return the commerce discount commerce account group rel finder
	 */
	public CommerceDiscountCommerceAccountGroupRelFinder
		getCommerceDiscountCommerceAccountGroupRelFinder() {

		return commerceDiscountCommerceAccountGroupRelFinder;
	}

	/**
	 * Sets the commerce discount commerce account group rel finder.
	 *
	 * @param commerceDiscountCommerceAccountGroupRelFinder the commerce discount commerce account group rel finder
	 */
	public void setCommerceDiscountCommerceAccountGroupRelFinder(
		CommerceDiscountCommerceAccountGroupRelFinder
			commerceDiscountCommerceAccountGroupRelFinder) {

		this.commerceDiscountCommerceAccountGroupRelFinder =
			commerceDiscountCommerceAccountGroupRelFinder;
	}

	/**
	 * Returns the commerce discount rel local service.
	 *
	 * @return the commerce discount rel local service
	 */
	public com.liferay.commerce.discount.service.CommerceDiscountRelLocalService
		getCommerceDiscountRelLocalService() {

		return commerceDiscountRelLocalService;
	}

	/**
	 * Sets the commerce discount rel local service.
	 *
	 * @param commerceDiscountRelLocalService the commerce discount rel local service
	 */
	public void setCommerceDiscountRelLocalService(
		com.liferay.commerce.discount.service.CommerceDiscountRelLocalService
			commerceDiscountRelLocalService) {

		this.commerceDiscountRelLocalService = commerceDiscountRelLocalService;
	}

	/**
	 * Returns the commerce discount rel persistence.
	 *
	 * @return the commerce discount rel persistence
	 */
	public CommerceDiscountRelPersistence getCommerceDiscountRelPersistence() {
		return commerceDiscountRelPersistence;
	}

	/**
	 * Sets the commerce discount rel persistence.
	 *
	 * @param commerceDiscountRelPersistence the commerce discount rel persistence
	 */
	public void setCommerceDiscountRelPersistence(
		CommerceDiscountRelPersistence commerceDiscountRelPersistence) {

		this.commerceDiscountRelPersistence = commerceDiscountRelPersistence;
	}

	/**
	 * Returns the commerce discount rel finder.
	 *
	 * @return the commerce discount rel finder
	 */
	public CommerceDiscountRelFinder getCommerceDiscountRelFinder() {
		return commerceDiscountRelFinder;
	}

	/**
	 * Sets the commerce discount rel finder.
	 *
	 * @param commerceDiscountRelFinder the commerce discount rel finder
	 */
	public void setCommerceDiscountRelFinder(
		CommerceDiscountRelFinder commerceDiscountRelFinder) {

		this.commerceDiscountRelFinder = commerceDiscountRelFinder;
	}

	/**
	 * Returns the commerce discount rule local service.
	 *
	 * @return the commerce discount rule local service
	 */
	public CommerceDiscountRuleLocalService
		getCommerceDiscountRuleLocalService() {

		return commerceDiscountRuleLocalService;
	}

	/**
	 * Sets the commerce discount rule local service.
	 *
	 * @param commerceDiscountRuleLocalService the commerce discount rule local service
	 */
	public void setCommerceDiscountRuleLocalService(
		CommerceDiscountRuleLocalService commerceDiscountRuleLocalService) {

		this.commerceDiscountRuleLocalService =
			commerceDiscountRuleLocalService;
	}

	/**
	 * Returns the commerce discount rule persistence.
	 *
	 * @return the commerce discount rule persistence
	 */
	public CommerceDiscountRulePersistence
		getCommerceDiscountRulePersistence() {

		return commerceDiscountRulePersistence;
	}

	/**
	 * Sets the commerce discount rule persistence.
	 *
	 * @param commerceDiscountRulePersistence the commerce discount rule persistence
	 */
	public void setCommerceDiscountRulePersistence(
		CommerceDiscountRulePersistence commerceDiscountRulePersistence) {

		this.commerceDiscountRulePersistence = commerceDiscountRulePersistence;
	}

	/**
	 * Returns the commerce discount rule finder.
	 *
	 * @return the commerce discount rule finder
	 */
	public CommerceDiscountRuleFinder getCommerceDiscountRuleFinder() {
		return commerceDiscountRuleFinder;
	}

	/**
	 * Sets the commerce discount rule finder.
	 *
	 * @param commerceDiscountRuleFinder the commerce discount rule finder
	 */
	public void setCommerceDiscountRuleFinder(
		CommerceDiscountRuleFinder commerceDiscountRuleFinder) {

		this.commerceDiscountRuleFinder = commerceDiscountRuleFinder;
	}

	/**
	 * Returns the commerce discount usage entry local service.
	 *
	 * @return the commerce discount usage entry local service
	 */
	public
		com.liferay.commerce.discount.service.
			CommerceDiscountUsageEntryLocalService
				getCommerceDiscountUsageEntryLocalService() {

		return commerceDiscountUsageEntryLocalService;
	}

	/**
	 * Sets the commerce discount usage entry local service.
	 *
	 * @param commerceDiscountUsageEntryLocalService the commerce discount usage entry local service
	 */
	public void setCommerceDiscountUsageEntryLocalService(
		com.liferay.commerce.discount.service.
			CommerceDiscountUsageEntryLocalService
				commerceDiscountUsageEntryLocalService) {

		this.commerceDiscountUsageEntryLocalService =
			commerceDiscountUsageEntryLocalService;
	}

	/**
	 * Returns the commerce discount usage entry persistence.
	 *
	 * @return the commerce discount usage entry persistence
	 */
	public CommerceDiscountUsageEntryPersistence
		getCommerceDiscountUsageEntryPersistence() {

		return commerceDiscountUsageEntryPersistence;
	}

	/**
	 * Sets the commerce discount usage entry persistence.
	 *
	 * @param commerceDiscountUsageEntryPersistence the commerce discount usage entry persistence
	 */
	public void setCommerceDiscountUsageEntryPersistence(
		CommerceDiscountUsageEntryPersistence
			commerceDiscountUsageEntryPersistence) {

		this.commerceDiscountUsageEntryPersistence =
			commerceDiscountUsageEntryPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService
		getClassNameLocalService() {

		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService
			classNameLocalService) {

		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {

		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService
		getResourceLocalService() {

		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService
			resourceLocalService) {

		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService
		getUserLocalService() {

		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {

		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.commerce.discount.model.CommerceDiscountRule",
			commerceDiscountRuleLocalService);

		_setLocalServiceUtilService(commerceDiscountRuleLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.commerce.discount.model.CommerceDiscountRule");

		_setLocalServiceUtilService(null);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return CommerceDiscountRuleLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return CommerceDiscountRule.class;
	}

	protected String getModelClassName() {
		return CommerceDiscountRule.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				commerceDiscountRulePersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	private void _setLocalServiceUtilService(
		CommerceDiscountRuleLocalService commerceDiscountRuleLocalService) {

		try {
			Field field =
				CommerceDiscountRuleLocalServiceUtil.class.getDeclaredField(
					"_service");

			field.setAccessible(true);

			field.set(null, commerceDiscountRuleLocalService);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@BeanReference(
		type = com.liferay.commerce.discount.service.CommerceDiscountLocalService.class
	)
	protected com.liferay.commerce.discount.service.CommerceDiscountLocalService
		commerceDiscountLocalService;

	@BeanReference(type = CommerceDiscountPersistence.class)
	protected CommerceDiscountPersistence commerceDiscountPersistence;

	@BeanReference(type = CommerceDiscountFinder.class)
	protected CommerceDiscountFinder commerceDiscountFinder;

	@BeanReference(
		type = com.liferay.commerce.discount.service.CommerceDiscountAccountRelLocalService.class
	)
	protected
		com.liferay.commerce.discount.service.
			CommerceDiscountAccountRelLocalService
				commerceDiscountAccountRelLocalService;

	@BeanReference(type = CommerceDiscountAccountRelPersistence.class)
	protected CommerceDiscountAccountRelPersistence
		commerceDiscountAccountRelPersistence;

	@BeanReference(type = CommerceDiscountAccountRelFinder.class)
	protected CommerceDiscountAccountRelFinder commerceDiscountAccountRelFinder;

	@BeanReference(
		type = com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelLocalService.class
	)
	protected com.liferay.commerce.discount.service.
		CommerceDiscountCommerceAccountGroupRelLocalService
			commerceDiscountCommerceAccountGroupRelLocalService;

	@BeanReference(
		type = CommerceDiscountCommerceAccountGroupRelPersistence.class
	)
	protected CommerceDiscountCommerceAccountGroupRelPersistence
		commerceDiscountCommerceAccountGroupRelPersistence;

	@BeanReference(type = CommerceDiscountCommerceAccountGroupRelFinder.class)
	protected CommerceDiscountCommerceAccountGroupRelFinder
		commerceDiscountCommerceAccountGroupRelFinder;

	@BeanReference(
		type = com.liferay.commerce.discount.service.CommerceDiscountRelLocalService.class
	)
	protected
		com.liferay.commerce.discount.service.CommerceDiscountRelLocalService
			commerceDiscountRelLocalService;

	@BeanReference(type = CommerceDiscountRelPersistence.class)
	protected CommerceDiscountRelPersistence commerceDiscountRelPersistence;

	@BeanReference(type = CommerceDiscountRelFinder.class)
	protected CommerceDiscountRelFinder commerceDiscountRelFinder;

	@BeanReference(type = CommerceDiscountRuleLocalService.class)
	protected CommerceDiscountRuleLocalService commerceDiscountRuleLocalService;

	@BeanReference(type = CommerceDiscountRulePersistence.class)
	protected CommerceDiscountRulePersistence commerceDiscountRulePersistence;

	@BeanReference(type = CommerceDiscountRuleFinder.class)
	protected CommerceDiscountRuleFinder commerceDiscountRuleFinder;

	@BeanReference(
		type = com.liferay.commerce.discount.service.CommerceDiscountUsageEntryLocalService.class
	)
	protected
		com.liferay.commerce.discount.service.
			CommerceDiscountUsageEntryLocalService
				commerceDiscountUsageEntryLocalService;

	@BeanReference(type = CommerceDiscountUsageEntryPersistence.class)
	protected CommerceDiscountUsageEntryPersistence
		commerceDiscountUsageEntryPersistence;

	@ServiceReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ClassNameLocalService.class
	)
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.ResourceLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@ServiceReference(
		type = com.liferay.portal.kernel.service.UserLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}