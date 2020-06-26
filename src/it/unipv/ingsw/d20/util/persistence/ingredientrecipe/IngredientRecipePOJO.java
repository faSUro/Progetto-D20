package it.unipv.ingsw.d20.util.persistence.ingredientrecipe;

public class IngredientRecipePOJO {
	
	private String idRecipe;
	private String ingredientName;
	private double quantity;
	public static int maxIngredients=5;
	
	
	/**
	 * 
	 * @param idRecipe
	 * @param ingredientName
	 * @param quantity
	 */
	public IngredientRecipePOJO(String idRecipe, String ingredientName, double quantity) {
		this.idRecipe = idRecipe;
		this.ingredientName = ingredientName;
		this.quantity = quantity;
	}

	public String getIdRecipe() {
		return idRecipe;
	}

	public void setIdRecipe(String idRecipe) {
		this.idRecipe = idRecipe;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "IngredientRecipePOJO [idRecipe=" + idRecipe + ", ingredientName=" + ingredientName + ", quantity="
				+ quantity + "]";
	}
	
	
}
