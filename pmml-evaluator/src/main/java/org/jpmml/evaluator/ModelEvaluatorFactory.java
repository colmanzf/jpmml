/*
 * Copyright (c) 2012 University of Tartu
 */
package org.jpmml.evaluator;

import org.jpmml.manager.*;

import org.dmg.pmml.*;

public class ModelEvaluatorFactory extends ModelManagerFactory {

	protected ModelEvaluatorFactory(){
	}

	@Override
	public ModelManager<? extends Model> getModelManager(PMML pmml, Model model){

		if(model instanceof AssociationModel){
			return new AssociationModelEvaluator(pmml, (AssociationModel)model);
		} else

		if(model instanceof MiningModel){
			return new MiningModelEvaluator(pmml, (MiningModel)model);
		} else

		if(model instanceof NeuralNetwork){
			return new NeuralNetworkEvaluator(pmml, (NeuralNetwork)model);
		} else

		if(model instanceof RegressionModel){
			return new RegressionModelEvaluator(pmml, (RegressionModel)model);
		} else

		if(model instanceof RuleSetModel){
			return new RuleSetModelEvaluator(pmml, (RuleSetModel)model);
		} else

		if(model instanceof Scorecard){
			return new ScorecardEvaluator(pmml, (Scorecard)model);
		} else

		if(model instanceof TreeModel){
			return new TreeModelEvaluator(pmml, (TreeModel)model);
		}

		throw new UnsupportedFeatureException(model);
	}

	static
	public ModelEvaluatorFactory getInstance(){
		return new ModelEvaluatorFactory();
	}
}