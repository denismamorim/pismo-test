package shopping.api.product

import org.grails.web.json.JSONArray;
import org.springframework.http.HttpStatus

import br.com.shopping.api.ApiAuthenticatorUtil
import grails.converters.JSON;
import grails.rest.RestfulController
import product.Product;;

class ProductController {
	static responseFormats = ['json', 'xml'];
	
	def retrieve() {
		respond Product.getAll();
	}
	
	def retrieveSingle() {
		respond Product.get(params.id);
	}
	
	def create() {
		if (!ApiAuthenticatorUtil.validateAdminRequest(request)) {
			notAuthorized();
			return;
		}
		def body = request.reader.text;
		def bodyJson = JSON.parse(body);
		def error = validateProduct(bodyJson);
		if (error) {
			render error;
			return
		}
		respond new Product(bodyJson).save();
	}
	
	def update() {
		if (!ApiAuthenticatorUtil.validateAdminRequest(request)) {
			notAuthorized();
			return;
		}
		Product product = Product.get(params.id);
		if (!product) {
			render "${id} is not a valid product";
			return;
		}
		def body = request.reader.text;
		def bodyJson = JSON.parse(body);
		def error = validateProduct(bodyJson);
		if (error) {
			render error;
			return
		}
		respond product.properties = bodyJson;
		
	}
	
	def delete() {
		if (!ApiAuthenticatorUtil.validateAdminRequest(request)) {
			notAuthorized();
			return;
		}
		Product product = Product.get(params.id);
		if (!product) {
			render "${id} is not a valid product";
			return;
		}
		respond product.delete();
	}
	
	def notAuthorized() {
		response.setStatus(HttpStatus.FORBIDDEN.value);
		render("Only admins can access this endpoint");
	}
	
	// Validate that the body of the request represent a valid product
	private String validateProduct(productJSON) {
		if (!productJSON.name || !productJSON.description || !productJSON.quantity) {
			return "Invalid request";
		}
		return null;
	}
	
}
