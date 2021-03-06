/*
 * Copyright (c) 2013 University of Tartu
 */
package org.jpmml.manager;

import java.util.*;

import org.dmg.pmml.*;

import com.google.common.collect.*;

import static com.google.common.base.Preconditions.*;

public class AssociationModelManager extends ModelManager<AssociationModel> implements HasEntityRegistry<AssociationRule> {

	private AssociationModel associationModel = null;


	public AssociationModelManager(){
	}

	public AssociationModelManager(PMML pmml){
		this(pmml, find(pmml.getContent(), AssociationModel.class));
	}

	public AssociationModelManager(PMML pmml, AssociationModel associationModel){
		super(pmml);

		this.associationModel = associationModel;
	}

	@Override
	public String getSummary(){
		return "Association rules model";
	}

	public FieldName getActiveField(){
		List<FieldName> activeFields = getActiveFields();

		if(activeFields.size() < 1){
			throw new InvalidFeatureException("No active fields", getMiningSchema());
		} else

		if(activeFields.size() > 1){
			throw new InvalidFeatureException("Too many active fields", getMiningSchema());
		}

		return activeFields.get(0);
	}

	@Override
	public FieldName getTargetField(){
		List<FieldName> predictedFields = getPredictedFields();

		if(predictedFields.size() < 1){
			return null;
		}

		return super.getTargetField();
	}

	@Override
	public AssociationModel getModel(){
		checkState(this.associationModel != null);

		return this.associationModel;
	}

	/**
	 * @see #getModel()
	 */
	public AssociationModel createModel(Double minimumSupport, Double minimumConfidence){
		checkState(this.associationModel == null);

		this.associationModel = new AssociationModel(new MiningSchema(), MiningFunctionType.ASSOCIATION_RULES, 0, minimumSupport, minimumConfidence, 0, 0, 0);

		getModels().add(this.associationModel);

		return this.associationModel;
	}

	/**
	 * @return A bidirectional map between {@link Item#getId Item identifiers} and {@link Item instances}.
	 */
	public BiMap<String, Item> getItemRegistry(){
		BiMap<String, Item> result = HashBiMap.create();

		List<Item> items = getItems();
		for(Item item : items){
			result.put(item.getId(), item);
		}

		return result;
	}

	/**
	 * @return A bidirectional map between {@link Itemset#getId() Itemset identifiers} and {@link Itemset instances}.
	 */
	public BiMap<String, Itemset> getItemsetRegistry(){
		BiMap<String, Itemset> result = HashBiMap.create();

		List<Itemset> itemsets = getItemsets();
		for(Itemset itemset : itemsets){
			result.put(itemset.getId(), itemset);
		}

		return result;
	}

	@Override
	public BiMap<String, AssociationRule> getEntityRegistry(){
		BiMap<String, AssociationRule> result = HashBiMap.create();

		List<AssociationRule> associationRules = getAssociationRules();

		EntityUtil.putAll(associationRules, result);

		return result;
	}

	public List<Item> getItems(){
		AssociationModel associationModel = getModel();

		return associationModel.getItems();
	}

	public List<Itemset> getItemsets(){
		AssociationModel associationModel = getModel();

		return associationModel.getItemsets();
	}

	public List<AssociationRule> getAssociationRules(){
		AssociationModel associationModel = getModel();

		return associationModel.getAssociationRules();
	}
}