package sale

import grails.rest.Resource;

class Sale {
	static hasMany = [saleProducts : SaleProduct];
	Date date;
	
	
	
}
