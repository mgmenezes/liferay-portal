import React, {useState} from 'react';
import {useFormContext, useWatch} from 'react-hook-form';

import {AVAILABLE_STEPS} from '../../../utils/constants';
import {useStepWizard} from '../../../hooks/useStepWizard';
import {TIP_EVENT} from '../../../events';
import {CardFormActionsWithSave} from '../../fragments/Card/FormActionsWithSave';
import {ControlledSwitch} from '../../connectors/Controlled/Switch';
import {NumberControlledInput} from '../../connectors/Controlled/Input/Number';
import {PercentageControlledInput} from '../../connectors/Controlled/Input/WithMask/Percentage';
import {LegalEntityControlledSelect} from '../../connectors/Controlled/Select/LegalEntity';
import {PERCENTAGE_REGEX_MAX_100} from '../../../utils/patterns';
import {
	validateOverallSales,
	validateOwnBrandLabel,
	validatePercentSales,
} from '../../../utils/businessFields';
import useFormActions from '../../../hooks/useFormActions';

const setFormPath = (value) => `business.${value}`;

export const FormBusiness = () => {
	const {
		control,
		formState: {isValid},
	} = useFormContext();
	const {selectedStep} = useStepWizard();
	const {onNext, onPrevious, onSave} = useFormActions(
		AVAILABLE_STEPS.BASICS_PRODUCT_QUOTE,
		AVAILABLE_STEPS.EMPLOYEES
	);
	const form = useWatch();

	const [selectedKey, setSelectedKey] = useState('');

	const changeMoreInfoSelected = (inputName) => {
		if (inputName === selectedKey) {
			setSelectedKey('');
		} else {
			setSelectedKey(inputName);
		}
	};

	return (
		<div className="card">
			<div className="card-content">
				<NumberControlledInput
					name={setFormPath('yearsOfExperience')}
					label="Years of industry experience?"
					rules={{
						required: 'This field is required',
						min: {
							value: 0,
							message: 'Must be equal or grater than 0.',
						},
					}}
					moreInfoProps={{
						event: TIP_EVENT,
						value: {
							templateName: 'years-of-industry-experience',
							step: selectedStep,
							inputName: setFormPath('yearsOfExperience'),
							value: form?.business?.yearsOfExperience,
						},
						selected:
							setFormPath('yearsOfExperience') === selectedKey,
						callback: () =>
							changeMoreInfoSelected(
								setFormPath('yearsOfExperience')
							),
					}}
					control={control}
				/>
				<ControlledSwitch
					name={setFormPath('hasStoredCustomerInformation')}
					label="Do you store personally identifiable information about your customers?"
					rules={{required: true}}
					control={control}
					defaultValue="true"
				/>
				<ControlledSwitch
					name={setFormPath('hasAutoPolicy')}
					label="Do you have a Raylife Auto policy?"
					rules={{required: true}}
					control={control}
				/>
				<LegalEntityControlledSelect
					name={setFormPath('legalEntity')}
					label="Legal Entity"
					rules={{
						required: 'This field is required.',
					}}
					control={control}
				/>
				{validatePercentSales(form?.basics?.properties?.naics) && (
					<PercentageControlledInput
						name={setFormPath('salesMerchandise')}
						label="Percent of sales from used merchandise?"
						rules={{
							pattern: {
								value: PERCENTAGE_REGEX_MAX_100,
								message: 'Value must not be greater than 100%.',
							},
							required: 'Percent of sales is required.',
						}}
						moreInfoProps={{
							event: TIP_EVENT,
							value: {
								templateName:
									'percent-of-sales-from-used-merchandise',
								step: selectedStep,
								inputName: setFormPath('salesMerchandise'),
								value: form?.business?.salesMerchandise,
							},
							selected:
								setFormPath('salesMerchandise') === selectedKey,
							callback: () =>
								changeMoreInfoSelected(
									setFormPath('salesMerchandise')
								),
						}}
						control={control}
					/>
				)}
				{validateOwnBrandLabel(form?.basics?.properties?.naics) && (
					<ControlledSwitch
						name={setFormPath('hasSellProductsUnderOwnBrand')}
						label="Do you sell products under your own brand or label?"
						rules={{required: true}}
						control={control}
					/>
				)}
				{validateOverallSales(form?.basics?.properties?.segment) && (
					<PercentageControlledInput
						name={setFormPath('overallSales')}
						label="What percentage of overall sales involve delivery?"
						rules={{
							pattern: {
								value: PERCENTAGE_REGEX_MAX_100,
								message: 'Value must not be greater than 100%.',
							},
							required: 'Percent of overall sales is required.',
						}}
						control={control}
					/>
				)}
			</div>
			<CardFormActionsWithSave
				isValid={isValid}
				onPrevious={onPrevious}
				onSave={onSave}
				onNext={onNext}
			/>
		</div>
	);
};
