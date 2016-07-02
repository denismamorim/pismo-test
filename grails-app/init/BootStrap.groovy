import product.Product;

class BootStrap {

    def init = { servletContext ->
		if (Product.count() == 0) {
			createProductsScenario();
		}
    }
	
    def destroy = {
    }
	
	def createProductsScenario() {
		print "criando cenário produtos";
		new Product(name : "Lapis", description : "Lapis de madeira", quantity : 3).save();
		new Product(name : "Caneta", description : "Caneta de tinta", quantity : 4).save();
		new Product(name : "Estojo", description : "Estojo para canetas", quantity : 7).save();
	}
	
}
