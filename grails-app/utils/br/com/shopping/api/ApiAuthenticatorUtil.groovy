package br.com.shopping.api

class ApiAuthenticatorUtil {
	
	// Validate if a request is from and admin
	public static boolean validateAdminRequest (request) {
		def auth = request.getHeader('Authorization')
		if (auth == 'admin') {
			return true;
		}
		return false;
	}
	
}
