package sale

import javax.transaction.Transactional;

import org.grails.web.json.JSONArray
import org.springframework.http.HttpStatus;

import com.google.gson.JsonElement

import br.com.shopping.api.ApiAuthenticatorUtil;
import grails.converters.JSON;
import grails.rest.RestfulController
import product.Product;;

class SaleController{
	static responseFormats = ['json', 'xml'];
	
	def retrieve() {
		print ApiAuthenticatorUtil.validateAdminRequest(request);
		if (!ApiAuthenticatorUtil.validateAdminRequest(request)) {
			response.setStatus(HttpStatus.FORBIDDEN.value);
			render 'Only admins can access this endpoint';
			return;
		}
		JSON.use('deep');
		respond Sale.getAll();
	}
	
	@Transactional
	def create() {
		def body = request.reader.text;
		Sale sale = new Sale();
		sale.date = new Date();
		JSON.use('deep');
		JSONArray jsonArray = JSON.parse(body);
		def errors = validateSale(jsonArray); 
		if (!errors) {
			jsonArray.each { jsonElement ->
				Product product = Product.get(jsonElement.product);
				product.quantity -= jsonElement.quantity;
				product.save(); 
				sale.addToSaleProducts new SaleProduct(jsonElement);
			}
			sale.save();
			respond sale;
		} else {
			response.status = 400;
			respond errors;
		}
	}
	
	private List<String> validateSale(JSONArray productsJSON) {
		List<String> errors = new ArrayList<String>();
		productsJSON.each { productJSON ->
			if (!productJSON.product || !productJSON.quantity) {
				errors.add("Invalid request");
				return
			}
			Product product = Product.get(productJSON.product); 
			if (!product) {
				errors.add("${productJSON.product} is not a valid product");
				return
			}
			if (product.getQuantity() < productJSON.quantity) {
				errors.add("There is not enough of product ${productJSON.product}");
				return
			}
		}
		return errors;
	}
}
