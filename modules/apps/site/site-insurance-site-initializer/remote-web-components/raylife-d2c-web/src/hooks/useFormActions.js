import {useState, useEffect, useCallback} from 'react';
import {useFormContext, useWatch} from 'react-hook-form';
import {LiferayService} from '../services/liferay';
import {useStepWizard} from './useStepWizard';
import Cookies from 'js-cookie';

/**
 *
 * @param {String?} previousSection
 * @param {String?} nextSection
 * @param {String?} errorMessage
 * @returns
 */

const useFormActions = (previousSection, nextSection, errorMessage) => {
	const [applicationId, setApplicationId] = useState();
	const {setError, setValue} = useFormContext();
	const {setSection} = useStepWizard();
	const form = useWatch();

	/**
	 * @description When the application is created, we set the value to Form Context
	 * We tried to use setValue directly on goToPrevious and goToNextForm
	 * and for reasons unknowns, the section is not called.
	 */

	useEffect(() => {
		if (applicationId) {
			setValue('basics.applicationId', applicationId);

			Cookies.set('raylife-application-id', applicationId);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [applicationId]);

	const _SaveData = useCallback(async () => {
		try {
			const response = await LiferayService.createOrUpdateRaylifeApplication(
				form
			);

			setApplicationId(response.data.id);

			return response;
		} catch (error) {
			setError('continueButton', {
				type: 'manual',
				message:
					errorMessage ||
					'There was an error processing your request. Please try again.',
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [form]);

	const onPrevious = async () => {
		await _SaveData();

		if (previousSection) {
			setSection(previousSection);
		}
	};

	const onSave = async () => {
		await _SaveData();

		window.location.href = '/web/raylife';
	};

	/**
	 * @state disabled for now
	 * @param {*} data
	 */
	const onNext = async () => {
		await _SaveData();

		if (nextSection) {
			return setSection(nextSection);
		}

		window.location.href = '/web/raylife/hang-tight';
	};

	return {onNext, onPrevious, onSave};
};

export default useFormActions;
