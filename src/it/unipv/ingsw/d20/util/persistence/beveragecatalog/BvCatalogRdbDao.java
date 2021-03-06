package it.unipv.ingsw.d20.util.persistence.beveragecatalog;

import java.util.ArrayList;
import it.unipv.ingsw.d20.util.persistence.RdbOperations;
import it.unipv.ingsw.d20.util.persistence.beveragedescription.BeverageDescriptionPOJO;
import it.unipv.ingsw.d20.util.persistence.ingredientrecipe.IngredientRecipePOJO;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.BeverageCatalog;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.BeverageDescription;
import it.unipv.ingsw.d20.vendingmachine.model.beverage.Ingredients;

/**
 * Implementazione di IBvCatalogDao. Accede ai dati del DB.
 * 
 */
public class BvCatalogRdbDao implements IBvCatalogDao {
	
	private RdbOperations op;
	
	public BvCatalogRdbDao(RdbOperations op) {
		this.op = op;
	}

	@Override
	public ArrayList<BvCatalogPOJO> getBeverageCatalogByType(int type) {
		return op.getBeverageCatalogByType(type);
	}
	
	public BeverageCatalog getBeverageCatalog(int type) {
		BeverageCatalog bvCatalog = new BeverageCatalog();
		
		ArrayList<BvCatalogPOJO> bvcatArray = op.getBeverageCatalogByType(type);
		ArrayList<BeverageDescriptionPOJO> bvdescArray = new ArrayList<>();
		for(BvCatalogPOJO bvCat : bvcatArray) {
			bvdescArray.add(op.getBeverageDescriptionByBevName(bvCat.getIdBevDesc()));
		}
		
		int i = 1; // Contatore per creare il codice della BeverageDescription
		for(BeverageDescriptionPOJO bvDesc : bvdescArray) {
			
			BeverageDescription bd = new BeverageDescription(String.valueOf(i),bvDesc.getBvName(),bvDesc.getPrice()); // Beverage da aggiungere al catalogo
			i++; // incremento del contatore
		
			ArrayList<IngredientRecipePOJO> allIngr = new ArrayList<>();               // Ottiene tutti gli ingredienti della bevanda e li
			allIngr.addAll(op.getAllIngredientRecipeByIdRecipe(bvDesc.getIdRecipe())); // aggiunge tutti all'array
			for (IngredientRecipePOJO irp : allIngr) { // per aggiungere tutti gli ingredienti nella beverageDescription
				// CONTROLLARE Ingredients.valueof che funzioni
				bd.addIngredient(Ingredients.valueOf(irp.getIngredientName()), irp.getQuantity()); 
			}
			
			bvCatalog.addBeverageDescription(bd); // Aggiunge la Beverage Description al catalogo
	
		}
			
		return bvCatalog;
	}

}
