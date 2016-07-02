package sale

import product.Product;

class SaleProduct {
	static belongsTo = [sale:Sale]
	Product product;
	int quantity;
	
}
